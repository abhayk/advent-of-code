package aoc2015;

import common.PermutationUtil;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day9
{
    private static Map<String, Map<String, Integer>> parseDistances(List<String> input)
    {
        Map<String, Map<String, Integer>> map = new HashMap<>();

        for( String line : input )
        {
            String[] split = line.split(" ");
            String from = split[0];
            String to = split[2];
            int distance = Integer.parseInt( split[4] );

            map.computeIfAbsent( from, key -> new HashMap<>()).put( to, distance );
            map.computeIfAbsent( to, key -> new HashMap<>()).put( from, distance );
        }
        return map;
    }

    private static int calculateDistance( String[] path, Map<String, Map<String, Integer>> distances )
    {
        int totalDistance = 0;
        for( int i=0; i<path.length - 1; i++ )
            totalDistance += distances.get( path[i] ).get( path[i+1]);
        return totalDistance;
    }

    private static TreeMap<Integer, String[]> getPathMap( List<String> input )
    {
        Map<String, Map<String, Integer>> distances = parseDistances( input );
        String[] cities = distances.keySet().toArray( new String[]{} );
        TreeMap<Integer, String[]> map = new TreeMap<>();
        PermutationUtil.permute( cities, permutation ->
        {
            int totalDistance = calculateDistance( permutation, distances );
            map.put( totalDistance, permutation );
        });
        return map;
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add("London to Dublin = 464");
        input.add("London to Belfast = 518");
        input.add("Dublin to Belfast = 141");

        assert getPathMap( input ).firstKey() == 605;

        input = Files.readAllLines( Util.getInputFilePath() );
        TreeMap<Integer, String[]> pathMap = getPathMap( input );

        //part 1
        System.out.println( pathMap.firstKey() );

        //part 2
        System.out.println( pathMap.lastKey() );
    }
}
