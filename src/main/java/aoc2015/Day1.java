package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;

public class Day1
{
    private static int findFloor( String input )
    {
        int steps = 0;
        for( char c : input.toCharArray() )
        {
            steps += (c == '(' ? 1 : -1);
        }
        return steps;
    }

    private static int findStepInWhichTheBasementIsEntered( String input )
    {
        int steps = 0, i = 0;
        for( char c : input.toCharArray() )
        {
            i++;
            steps += (c == '(' ? 1 : -1);
            if( steps == -1 )
                break;
        }
        return i;
    }

    public static void main(String[] args) throws IOException
    {
        assert findFloor("(())") == 0;
        assert findFloor("))(((((") == 3;

        String input = Files.readString(Util.getInputFilePath() );

        //part 1
        System.out.println( findFloor( input ) );

        assert findStepInWhichTheBasementIsEntered(")") == 1;
        assert findStepInWhichTheBasementIsEntered("()())") == 5;

        //part 2
        System.out.println( findStepInWhichTheBasementIsEntered( input ) );
    }
}
