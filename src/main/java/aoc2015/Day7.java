package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Day7
{
    private static void processCircuit( Map<String, Integer> map, List<String> instructions )
    {
        Queue<String> queue = new ArrayDeque<>( instructions );
        while( !queue.isEmpty() )
        {
            String ins = queue.poll();
            if( !canProcessInstruction( ins, map ) )
            {
                queue.add( ins );
                continue;
            }
            processInstruction( ins, map );
        }
    }

    private static void processInstruction( String ins, Map<String, Integer> map )
    {
        String[] split = ins.split("->");
        String[] input = split[0].split(" ");
        String output = split[1].trim();

        int result = 0;

        if( input.length == 1 )
            result = resolve( input[0], map );
        else if( input.length == 2 )
            result =  ~resolve( input[1], map) & 0xffff;
        else if( input.length == 3 )
            result = applyOperation( resolve( input[0], map), resolve( input[2], map), input[1]);

        map.put( output, result );
    }

    private static int applyOperation( int left, int right, String op )
    {
        switch (op)
        {
            case "AND": return left & right;
            case "OR": return left | right;
            case "LSHIFT": return left << right;
            case "RSHIFT": return left >>> right;
        }
        return 0;
    }

    private static boolean canProcessInstruction( String ins, Map<String, Integer> map )
    {
        String[] split = ins.substring(0, ins.indexOf("->")).split(" ");

        if( split.length == 1)
            return isKeyPresent( split[0], map);
        if( split.length == 2 )
            return isKeyPresent( split[1], map);
        if( split.length == 3 )
            return isKeyPresent( split[0], map ) && isKeyPresent( split[2], map );
        return false;
    }

    private static int resolve( String input, Map<String, Integer> map )
    {
        return isKey( input ) ? map.get( input ) : Integer.parseInt( input );
    }

    private static boolean isKeyPresent( String input, Map<String, Integer> map )
    {
        return isKey( input ) ? map.containsKey( input ) : true;
    }

    private static boolean isKey( String input )
    {
        return input.chars().allMatch(Character::isAlphabetic);
    }

    public static void main(String[] args) throws IOException
    {
        Map<String, Integer> map = new HashMap<>();
        List<String> instructions = new ArrayList<>();
        instructions.add("123 -> x");
        instructions.add("456 -> y");
        instructions.add("x AND y -> d");
        instructions.add("x OR y -> e");
        instructions.add("x LSHIFT 2 -> f");
        instructions.add("y RSHIFT 2 -> g");
        instructions.add("NOT x -> h");
        instructions.add("NOT y -> i");
        processCircuit( map, instructions );

        assert map.get("d") == 72;
        assert map.get("e") == 507;
        assert map.get("f") == 492;
        assert map.get("g") == 114;
        assert map.get("h") == 65412;
        assert map.get("i") == 65079;
        assert map.get("x") == 123;
        assert map.get("y") == 456;

        instructions = Files.readAllLines( Util.getInputFilePath() );

        // part 1
        map = new HashMap<>();
        processCircuit(map, instructions);
        int value = map.get("a");
        System.out.println( value );

        //part 2
        map = new HashMap<>();
        instructions.remove("1674 -> b");
        instructions.add(0, value + " -> b" );
        processCircuit( map, instructions );
        System.out.println( map.get("a"));
    }
}
