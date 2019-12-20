package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2
{
    private static long findAreaRequired(List<String>input)
    {
        long area = 0;
        for( String line : input )
        {
            int[] a = parse( line );
            area += (3*a[0]*a[1] + 2*a[1]*a[2] + 2*a[0]*a[2]);
        }
        return area;
    }

    private static long findLengthOfRibbonRequired( List<String> input )
    {
        long length = 0;
        for( String line : input )
        {
            int[] a = parse( line );
            length += (2*a[0] + 2*a[1] + a[0]*a[1]*a[2]);
        }
        return length;
    }

    private static int[] parse( String input )
    {
        String[] split = input.split("x");
        int[] arr = new int[split.length];
        int i=0;
        for( String s : split)
            arr[i++] = Integer.parseInt( s );
        Arrays.sort( arr );
        return arr;
    }

    public static void main(String[] args) throws IOException
    {
        assert findAreaRequired( Collections.singletonList("2x3x4")) == 58;
        assert findAreaRequired( Collections.singletonList("1x1x10")) == 43;

        List<String> input = Files.readAllLines( Util.getInputFilePath() );

        //part 1
        System.out.println( findAreaRequired( input ) );

        assert findLengthOfRibbonRequired( Collections.singletonList("2x3x4")) == 34;
        assert findLengthOfRibbonRequired( Collections.singletonList("1x1x10")) == 14;

        //part 2
        System.out.println( findLengthOfRibbonRequired( input ) );
    }
}
