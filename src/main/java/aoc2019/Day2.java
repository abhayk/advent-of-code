package aoc2019;

import common.IntCodeComputer;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2
{
    private static int part2( long[] input )
    {
        int noun = 0, verb = 0;
        boolean found = false;
        for( int i=0; i<99; i++ )
        {
            for( int j=0; j<99; j++ )
            {
                long[] tmp = Arrays.copyOf( input, input.length );
                tmp[1] = i;
                tmp[2] = j;
                IntCodeComputer computer = new IntCodeComputer( tmp );
                computer.runProgram();
                long[] output = computer.getMemoryState();
                if( output[0] == 19690720 )
                {
                    noun = i;
                    verb = j;
                    found = true;
                    break;
                }
            }
            if( found )
                break;
        }
        return (100 * noun) + verb;
    }

    private static long[] runProgram( long[] input )
    {
        IntCodeComputer computer = new IntCodeComputer( input );
        computer.runProgram();
        return computer.getMemoryState();
    }

    public static void main(String[] args) throws IOException
    {
        long[] input;
        long[] output;
        IntCodeComputer computer;

        input = new long[]{ 1,9,10,3,2,3,11,0,99,30,40,50 };
        assert Arrays.equals( runProgram( input ) , new long[]{ 3500,9,10,70,2,3,11,0,99,30,40,50} );

        input = new long[]{ 1,0,0,0,99 };
        assert Arrays.equals( runProgram( input ), new long[]{ 2,0,0,0,99} );

        input = new long[]{ 2,3,0,3,99 };
        assert Arrays.equals( runProgram( input ), new long[]{ 2,3,0,6,99} );

        input = new long[]{ 2,4,4,5,99,0 };
        assert Arrays.equals( runProgram( input ), new long[]{ 2,4,4,5,99,9801} );

        input = new long[]{ 1,1,1,4,99,5,6,0,99 };
        assert Arrays.equals( runProgram( input ), new long[]{ 30,1,1,4,2,5,6,0,99} );

        input = IntCodeComputer.parseInput( new String( Files.readAllBytes(Util.getInputFilePath())));

        //part 1
        long[] tmpInput = Arrays.copyOf( input, input.length );
        tmpInput[1] = 12;
        tmpInput[2] = 2;
        output = runProgram( tmpInput );
        System.out.println( output[0] );
        assert output[0] == 4484226;

        //part 2
        int result = part2( input );
        System.out.println( result );
        assert result == 5696;
    }
}
