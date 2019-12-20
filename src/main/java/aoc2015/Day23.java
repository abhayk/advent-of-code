package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day23
{
    private static void process(int[] register, String[][] instructions )
    {
        int pointer = 0;
        while( pointer >= 0 && pointer < instructions.length )
        {
            String[] ins = instructions[pointer];
            if ("hlf".equals(ins[0]))
            {
                register[getRegisterIndex(ins[1])] /= 2;
                pointer++;
            }
            else if ("tpl".equals(ins[0]))
            {
                register[getRegisterIndex(ins[1])] *= 3;
                pointer++;
            }
            else if ("inc".equals(ins[0]))
            {
                register[getRegisterIndex(ins[1])]++;
                pointer++;
            }
            else if ("jmp".equals(ins[0]))
            {
                pointer += Integer.parseInt(ins[1]);
            }
            else if ("jie".equals(ins[0]))
            {
                pointer += register[getRegisterIndex(ins[1])] % 2 == 0 ? Integer.parseInt(ins[2]) : 1;
            }
            else if ("jio".equals(ins[0]))
            {
                pointer += register[getRegisterIndex(ins[1])] == 1 ? Integer.parseInt(ins[2]) : 1;
            }
        }
    }

    private static int getRegisterIndex( String input )
    {
        return input.charAt(0) == 'a' ? 0 : 1;
    }

    private static String[][] parseInput( List<String> input )
    {
        String[][] arr = new String[input.size()][];
        int i=0;
        for( String line : input )
            arr[i++] = line.split(" ");
        return arr;
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add("inc a");
        input.add("jio a, +2");
        input.add("tpl a");
        input.add("inc a");
        int[] register = new int[2];
        process( register, parseInput( input ));
        assert register[0] == 2;

        input = Files.readAllLines( Util.getInputFilePath() );
        String[][] parsedInput = parseInput( input );

        register = new int[2];
        process( register, parsedInput );
        System.out.println( register[1] );

        register = new int[2];
        register[0] = 1;
        process( register, parsedInput );
        System.out.println( register[1] );
    }
}
