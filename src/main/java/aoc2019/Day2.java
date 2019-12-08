package aoc2019;

import common.IntCodeComputer;
import common.Util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day2
{
    private static int[] parseInput( String input )
    {
        return Arrays.stream( input.split(",") ).mapToInt( val -> Integer.parseInt( val.trim() ) ).toArray();
    }

    private static int part2( int[] input )
    {
        int noun = 0, verb = 0;
        boolean found = false;
        for( int i=0; i<99; i++ )
        {
            for( int j=0; j<99; j++ )
            {
                int[] tmp = Arrays.copyOf( input, input.length );
                tmp[1] = i;
                tmp[2] = j;
                IntCodeComputer.runDiagnostic( tmp );
                if( tmp[0] == 19690720 )
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

    public static void main(String[] args) throws IOException
    {
        int[] input = new int[]{ 1,9,10,3,2,3,11,0,99,30,40,50 };
        IntCodeComputer.runDiagnostic( input );
        assert Arrays.equals( input , new int[]{ 3500,9,10,70,2,3,11,0,99,30,40,50} );

        input = new int[]{ 1,0,0,0,99 };
        IntCodeComputer.runDiagnostic( input );
        assert Arrays.equals( input, new int[]{ 2,0,0,0,99} );

        input = new int[]{ 2,3,0,3,99 };
        IntCodeComputer.runDiagnostic( input );
        assert Arrays.equals( input, new int[]{ 2,3,0,6,99} );

        input = new int[]{ 2,4,4,5,99,0 };
        IntCodeComputer.runDiagnostic( input );
        assert Arrays.equals( input, new int[]{ 2,4,4,5,99,9801} );

        input = new int[]{ 1,1,1,4,99,5,6,0,99 };
        IntCodeComputer.runDiagnostic( input );
        assert Arrays.equals( input, new int[]{ 30,1,1,4,2,5,6,0,99} );

        input = parseInput( new String( Files.readAllBytes(Util.getInputFilePath())));

        //part 1
        int[] tmpInput = Arrays.copyOf( input, input.length );
        tmpInput[1] = 12;
        tmpInput[2] = 2;
        IntCodeComputer.runDiagnostic(  tmpInput );
        System.out.println( tmpInput[0] );
        assert tmpInput[0] == 4484226;

        //part 2
        int result = part2( input );
        System.out.println( result );
        assert result == 5696;
    }
}
