package aoc2019;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Day1
{
    private static int getFuelRequired( int input )
    {
        return (input / 3) - 2;
    }

    private static int getFuelRequiredv2(int input )
    {
        int count = 0;
        while( input > 0 )
        {
            input = getFuelRequired( input );
            if( input > 0 )
                count += input;
        }
        return count;
    }

    public static void main(String[] args) throws IOException
    {
        assert getFuelRequired(12) == 2;
        assert getFuelRequired(14) == 2;
        assert getFuelRequired(1969) == 654;
        assert getFuelRequired(100756) == 33583;

        assert getFuelRequiredv2(14) == 2;
        assert getFuelRequiredv2(1969) == 966;
        assert getFuelRequiredv2(100756) == 50346;

        List<String> input = Files.readAllLines( Util.getInputFilePath() );

        int count1 = 0, count2 = 0;
        for( String line : input )
        {
            int a = Integer.parseInt( line );
            count1 += getFuelRequired(a);
            count2 += getFuelRequiredv2(a);
        }
        System.out.println(count1);
        System.out.println(count2);
    }
}
