package aoc2019;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Day12
{
    static class Moon
    {
        int[] position;
        int[] velocity;
        int[] newVelocity;

        public Moon(int[] position, int[] velocity)
        {
            this.position = position;
            this.velocity = velocity;
            this.newVelocity = Arrays.copyOf( velocity, velocity.length );
        }

        @Override
        public String toString()
        {
            return "Moon{" +
                    "position=" + Arrays.toString(position) +
                    ", velocity=" + Arrays.toString(velocity) +
                    '}';
        }
    }

    private static void applyGravity( Moon m1, Moon m2 )
    {
        for( int i=0; i<3; i++ )
        {
            if( m1.position[i] < m2.position[i])
            {
                m1.newVelocity[i]++;
                m2.newVelocity[i]--;
            }
            else if( m1.position[i] > m2.position[i] )
            {
                m2.newVelocity[i]++;
                m1.newVelocity[i]--;
            }
        }
    }

    private static void applyVelocity( List<Moon> moons )
    {
        for( Moon moon : moons )
        {
            for( int i=0; i<3; i++ )
            {
                moon.velocity[i] = moon.newVelocity[i];
                moon.position[i] += moon.velocity[i];
            }
        }
    }

    private static void applyGravity( List<Moon> moons )
    {
        for( int i=0; i<moons.size() - 1; i++ )
        {
            for( int j=i+1; j<moons.size(); j++ )
            {
                Moon m1 = moons.get(i);
                Moon m2 = moons.get(j);
                applyGravity( m1, m2 );
            }
        }
    }

    private static void apply(int steps, List<Moon> moons, Consumer<List<Moon>> onEachStep )
    {
        for( int i=0; i<steps; i++ )
        {
            applyGravity( moons );
            applyVelocity( moons );
            onEachStep.accept( moons );
        }
    }

    private static void apply( int steps, List<Moon> moons )
    {
        apply(steps, moons, m -> {});
    }

    private static int getEnergy( Moon moon )
    {
        int potential = 0, kinetic = 0;
        for( int i=0; i<3; i++ )
        {
            potential += Math.abs( moon.position[i] );
            kinetic += Math.abs( moon.velocity[i]);
        }
        return potential * kinetic;
    }

    private static int getTotalEnergy( List<Moon> moons )
    {
        int total = 0;
        for( Moon moon : moons )
            total += getEnergy( moon );
        return total;
    }

    private static List<Moon> parseInput( List<String> input )
    {
        List<Moon> moons = new ArrayList<>();
        for( String line : input )
        {
            int[] numbers = Util.getAllNumbers( line );
            moons.add( new Moon( numbers, new int[3] ) );
        }
        return moons;
    }

    private static int getIndexAtWhichCycleRepeats( int[] arr )
    {
        // A large enough frequency in which we assume the configuration repeats.
        int start = 5;
        while( start != arr.length )
        {
            int k=0;
            int j = start;
            while( k < arr.length && j < arr.length && arr[k++] == arr[j++] )
            {
                if( start == k )
                    return start;
            }
            start++;
        }
        return -1;
    }

    private static long findStepCountAfterWhichConfigurationRepeats( int tryForSteps, List<String> input )
    {
        List<Moon> moons = parseInput( input );

        List<List<Integer>> dimensions = new ArrayList<>();
        for( int i=0; i<6*moons.size(); i++ )
            dimensions.add( new ArrayList<>() );

        apply(tryForSteps, moons, m ->
        {
            int i=0;
            for( Moon moon : m )
            {
                for( int j=0; j<3; j++ )
                    dimensions.get( i++ ).add( moon.position[j] );
                for( int j=0; j<3; j++ )
                    dimensions.get( i++ ).add( moon.velocity[j] );
            }
        });

        List<Integer> repeatFrequencies = new ArrayList<>();
        for( List<Integer> list : dimensions )
            repeatFrequencies.add( getIndexAtWhichCycleRepeats( list.stream().mapToInt( a -> a).toArray() ) );
        return Util.lcm( repeatFrequencies.stream().mapToInt( a -> a ).toArray() );

    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add("<x=-1, y=0, z=2>");
        input.add("<x=2, y=-10, z=-7>");
        input.add("<x=4, y=-8, z=8>");
        input.add("<x=3, y=5, z=-1>");

        List<Moon> moons = parseInput( input );
        apply( 10, moons );

        assert getTotalEnergy( moons ) == 179;

        //The number of steps that might be required by trial and error by checking whether
        // we are able to find the repeat frequency for the given number of steps.
        assert findStepCountAfterWhichConfigurationRepeats( 5000, input ) == 2772;

        input = new ArrayList<>();
        input.add("<x=-8, y=-10, z=0>");
        input.add("<x=5, y=5, z=10>");
        input.add("<x=2, y=-7, z=3>");
        input.add("<x=9, y=-8, z=-3>");

        moons = parseInput( input );
        apply( 100, moons );
        assert getTotalEnergy( moons ) == 1940;
        assert findStepCountAfterWhichConfigurationRepeats( 20000, input ) == 4686774924L;

        input = Files.readAllLines( Util.getInputFilePath() );
        moons = parseInput( input );
        apply( 1000, moons );
        System.out.println( getTotalEnergy( moons ) );
        System.out.println( findStepCountAfterWhichConfigurationRepeats( 500000, input ));

    }
}
