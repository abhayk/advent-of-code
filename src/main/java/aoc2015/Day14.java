package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day14
{
    static class Reindeer
    {
        Reindeer(int speed, int flyTime, int restTime)
        {
            this.speed = speed;
            this.flyTime = flyTime;
            this.restTime = restTime;
        }

        int speed;
        int flyTime;
        int restTime;
        int points;
        int distanceTravelled;

        int distanceTravelledInTime(int time)
        {
            int distance = (time / ( flyTime + restTime)) * speed * flyTime;
            int remaining = time % ( flyTime + restTime );
            distance += (Math.min(remaining, flyTime)) * speed;
            this.distanceTravelled = distance;
            return distance;
        }

        void incrementPoint(){ points ++; }
        int getPoints(){ return points; }
        int getDistanceTravelled(){ return distanceTravelled; }
    }

    private static List<Reindeer> parse( List<String> input )
    {
        List<Reindeer> reindeers = new ArrayList<>();
        for( String line : input )
        {
            String[] split = line.split(" ");
            reindeers.add( new Reindeer( Integer.parseInt( split[3]),
                    Integer.parseInt( split[6]), Integer.parseInt( split[13])));
        }
        return reindeers;
    }

    private static int maxDistanceTravelled( int endTime, List<String> input )
    {
        List<Reindeer> reindeers = parse( input );
        reindeers.sort(Comparator.comparingInt(( Reindeer r) -> r.distanceTravelledInTime(endTime)).reversed());
        return reindeers.get(0).distanceTravelledInTime( endTime );
    }

    private static int maxPoints( int endTime, List<String> input )
    {
        List<Reindeer> reindeers = parse( input );
        for( int i=1; i<= endTime; i++ )
        {
            int finalI = i;
            reindeers.sort(Comparator.comparingInt(( Reindeer r) -> r.distanceTravelledInTime(finalI)).reversed());
            reindeers.get(0).incrementPoint();
            int j=1;
            while( j < reindeers.size() && reindeers.get(j).getDistanceTravelled() == reindeers.get(0).getDistanceTravelled() )
            {
                reindeers.get(j).incrementPoint();
                j++;
            }
        }
        reindeers.sort( Comparator.comparingInt(Reindeer::getPoints).reversed());
        return reindeers.get(0).getPoints();
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.");
        input.add("Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.");

        assert maxDistanceTravelled(1000, input ) == 1120;
        assert maxPoints(1000, input) == 689;

        input = Files.readAllLines( Util.getInputFilePath() );

        System.out.println( maxDistanceTravelled( 2503, input));
        System.out.println( maxPoints( 2503, input ));
    }
}
