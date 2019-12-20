package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day16
{
    private static Map<String, Integer> parseClues(List<String> input )
    {
        Map<String, Integer> map = new HashMap<>();
        for( String line : input )
        {
            String[] split = line.split(":");
            map.put( split[0], Integer.parseInt( split[1].trim()) );
        }
        return map;
    }

    private static Map<Integer, Map<String, Integer>> parseInput( List<String> input )
    {
        Map<Integer, Map<String, Integer>> map = new HashMap<>();
        for( String line : input )
        {
            String[] split =  line.replace(":", "")
                    .replace(",", "").split(" ");
            int sue = Integer.parseInt( split[1] );
            for( int i=2; i<split.length - 1; i+=2 )
            {
                String item = split[i];
                int count = Integer.parseInt( split[i+1] );
                map.computeIfAbsent( sue, key -> new HashMap<>() ).put( item, count );
            }
        }
        return map;
    }

    private static TreeMap<Integer, List<Integer>> getScoreMap(Map<String, Integer> clues,
                                                               Map<Integer, Map<String, Integer>> input )
    {
        TreeMap<Integer, List<Integer>> scoreMap = new TreeMap<>();
        for( Map.Entry<Integer, Map<String, Integer>> entry : input.entrySet() )
        {
            int score = 0;
            for( Map.Entry<String, Integer> clue : clues.entrySet() )
            {
                if( entry.getValue().get(clue.getKey()) != null &&
                        entry.getValue().get(clue.getKey()).equals(clue.getValue()) )
                    score++;
            }
            scoreMap.computeIfAbsent( score, key -> new ArrayList<>()).add( entry.getKey() );
        }
        return scoreMap;
    }

    private static TreeMap<Integer, List<Integer>> getScoreMapv2(Map<String, Integer> clues,
                                                               Map<Integer, Map<String, Integer>> input )
    {
        TreeMap<Integer, List<Integer>> scoreMap = new TreeMap<>();
        for( Map.Entry<Integer, Map<String, Integer>> entry : input.entrySet() )
        {
            int score = 0;
            for( Map.Entry<String, Integer> clue : clues.entrySet() )
            {
                if(entry.getValue().get(clue.getKey()) == null)
                    continue;
                if( clue.getKey().equals("cats") || clue.getKey().equals("trees"))
                {
                    if( entry.getValue().get( clue.getKey() ) > clue.getValue() )
                        score++;
                }
                else if( clue.getKey().equals("pomeranians") || clue.getKey().equals("goldfish"))
                {
                    if( entry.getValue().get( clue.getKey() ) < clue.getValue() )
                        score++;
                }
                else
                {
                    if(entry.getValue().get(clue.getKey()).equals(clue.getValue()) )
                        score++;
                }
            }
            scoreMap.computeIfAbsent( score, key -> new ArrayList<>()).add( entry.getKey() );
        }
        return scoreMap;
    }

    public static void main(String[] args) throws IOException
    {
        List<String> clues = new ArrayList<>();
        clues.add("children: 3");
        clues.add("cats: 7");
        clues.add("samoyeds: 2");
        clues.add("pomeranians: 3");
        clues.add("akitas: 0");
        clues.add("vizslas: 0");
        clues.add("goldfish: 5");
        clues.add("trees: 3");
        clues.add("cars: 2");
        clues.add("perfumes: 1");

        List<String> input = Files.readAllLines( Util.getInputFilePath() );

        Map<String, Integer> parsedClues = parseClues( clues );
        Map<Integer, Map<String, Integer>> parsedInput = parseInput( input );

        TreeMap<Integer, List<Integer>> scoreMap = getScoreMap( parsedClues, parsedInput);
        System.out.println(scoreMap.lastEntry().getValue());

        TreeMap<Integer, List<Integer>> scoreMapv2 = getScoreMapv2( parsedClues, parsedInput);
        System.out.println(scoreMapv2.lastEntry().getValue());
    }
}
