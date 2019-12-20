package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Day19
{
    private static Map<String, List<String>> parseTransformations( List<String> input )
    {
        Map<String, List<String>> transforms = new HashMap<>();
        for( String line : input )
        {
            if( line.contains("=>") )
            {
                String[] split = line.split("=>");
                transforms.computeIfAbsent( split[0].trim(), key -> new ArrayList<>() ).add( split[1].trim() );
            }
        }
        return transforms;
    }

    private static int getCountOfDistinctMolecules( String medicine, Map<String, List<String>> transformsPerElement )
    {
        char[] arr = medicine.toCharArray();
        Set<String> distinctMolecules = new HashSet<>();
        for( int i=0; i<medicine.length(); i++ )
        {
            StringBuilder sb = new StringBuilder();
            int start = i;
            int end = i+1;
            sb.append( arr[i] );
            if( i != arr.length - 1 && Character.isLowerCase( arr[i+1]))
            {
                sb.append(arr[++i]);
                end++;
            }
            List<String> transforms = transformsPerElement.get( sb.toString() );
            if( transforms != null )
            {
                for( String transform : transforms )
                {
                    StringBuilder sbm = new StringBuilder().append( medicine );
                    distinctMolecules.add(sbm.replace(start, end, transform).toString());
                }
            }
        }
        return distinctMolecules.size();
    }

    private static int getMinSteps( String initial, String target, Map<String, List<String>> transformsPerElement )
    {
        return getMinStepsInternalv2( new StringBuilder( initial ), target, 0,
                new AtomicInteger( Integer.MAX_VALUE), transformsPerElement );
    }


    // It generates the right answer but I'm still not sure if it's the optimal solution. But iterating through the
    // entire space takes a long time.
    private static int getMinStepsInternalv2(StringBuilder current, String target, int steps, AtomicInteger minSteps,
                                           Map<String, List<String>> transformsPerElement )
    {
        System.out.println( current.toString() );
        if( current.toString().equals( target ) )
        {
            if( steps < minSteps.get() )
            {
                System.out.println("current min steps - " + steps);
                minSteps.set(steps);
            }
            return steps;
        }
        for( Map.Entry<String, List<String>> entry : transformsPerElement.entrySet() )
        {
            for( String transform : entry.getValue() )
            {
                int index = current.indexOf( transform );
                if( index != -1 )
                {
                    StringBuilder sb = new StringBuilder( current ).replace( index, index + transform.length(), entry.getKey() );
                    getMinStepsInternalv2( sb, target, steps + 1, minSteps, transformsPerElement );
                }
            }
        }
        return minSteps.get();
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add("H => HO");
        input.add("H => OH");
        input.add("O => HH");

        Map<String, List<String>> transforms = parseTransformations( input );
        assert getCountOfDistinctMolecules("HOH", transforms ) == 4;
        assert getCountOfDistinctMolecules("HOHOHO", transforms ) == 7;

        input = Files.readAllLines( Util.getInputFilePath() );
        transforms = parseTransformations( input );

        System.out.println( getCountOfDistinctMolecules( input.get( input.size() - 1), transforms));

        List<String> inputv2 = new ArrayList<>();
        inputv2.add("e => H");
        inputv2.add("e => O");
        inputv2.add("H => HO");
        inputv2.add("H => OH");
        inputv2.add("O => HH");

        assert getMinSteps("HOH", "e", parseTransformations( inputv2 ) ) == 3;
        assert getMinSteps("HOHOHO", "e", parseTransformations( inputv2 ) ) == 6;

        System.out.println( getMinSteps( input.get( input.size() - 1), "e",  transforms));
    }
}
