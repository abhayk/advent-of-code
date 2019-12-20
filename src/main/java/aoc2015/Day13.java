package aoc2015;

import common.PermutationUtil;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;

public class Day13
{
    private static Map<String, Map<String, Integer>> getHappinessMap(List<String> input )
    {
        Map<String, Map<String, Integer>> happinessMap = new HashMap<>();

        for( String line : input )
        {
            String[] split = line.split(" ");
            String from = split[0];
            String to = split[ split.length - 1].replace(".", "");
            int happiness = Integer.parseInt( split[3] );
            happiness = happiness * ( split[2].equals("gain") ? 1 : -1 );

            happinessMap.computeIfAbsent( from, key -> new HashMap<>()).put( to, happiness );
        }
        return happinessMap;
    }

    private static int calculateHappinessGain( String[] persons, Map<String, Map<String, Integer>> happinessMap )
    {
        int happiness = 0;
        for( int i=0; i<persons.length; i++ )
        {
            Map<String, Integer> happinessMapOfPerson = happinessMap.get( persons[i] );
            String personToTheLeft = (i == 0 ? persons[persons.length - 1] : persons[i-1] );
            String personToTheRight = ( i == persons.length - 1 ? persons[0] : persons[i+1] );
            happiness += (happinessMapOfPerson.get( personToTheLeft ) + happinessMapOfPerson.get( personToTheRight ));
        }
        return happiness;
    }

    private static TreeMap<Integer, String[]> getSeatingMap(List<String> input,
                                                            Consumer<Map<String, Map<String, Integer>>> postProcessHappinessMap)
    {
        Map<String, Map<String, Integer>> happinessMap = getHappinessMap( input );
        postProcessHappinessMap.accept( happinessMap );
        String[] persons = happinessMap.keySet().toArray( new String[]{} );
        TreeMap<Integer, String[]> seatingMap = new TreeMap<>();
        PermutationUtil.permute( persons, permutation ->
        {
            int happinessGain = calculateHappinessGain( permutation, happinessMap );
            seatingMap.put( happinessGain, permutation );
        });

        return seatingMap;
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();

        input.add("Alice would gain 54 happiness units by sitting next to Bob.");
        input.add("Alice would lose 79 happiness units by sitting next to Carol.");
        input.add("Alice would lose 2 happiness units by sitting next to David.");
        input.add("Bob would gain 83 happiness units by sitting next to Alice.");
        input.add("Bob would lose 7 happiness units by sitting next to Carol.");
        input.add("Bob would lose 63 happiness units by sitting next to David.");
        input.add("Carol would lose 62 happiness units by sitting next to Alice.");
        input.add("Carol would gain 60 happiness units by sitting next to Bob.");
        input.add("Carol would gain 55 happiness units by sitting next to David.");
        input.add("David would gain 46 happiness units by sitting next to Alice.");
        input.add("David would lose 7 happiness units by sitting next to Bob.");
        input.add("David would gain 41 happiness units by sitting next to Carol.");

        TreeMap<Integer, String[]> seatingMap = getSeatingMap(input, happinessMap -> {});
        assert seatingMap.lastKey() == 330;

        input = Files.readAllLines( Util.getInputFilePath() );

        // part 1
        seatingMap = getSeatingMap( input, happinessMap -> {});
        System.out.println( seatingMap.lastKey() );

        //part 2
        seatingMap = getSeatingMap(input, happinessMap ->
        {
            Set<String> persons = happinessMap.keySet();

            for( String person : persons )
                happinessMap.get( person ).put("me", 0);

            Map<String, Integer> map = new HashMap<>();
            for( String person : persons )
                map.put( person, 0);
            happinessMap.put("me", map );

        });
        System.out.println( seatingMap.lastKey() );
    }
}
