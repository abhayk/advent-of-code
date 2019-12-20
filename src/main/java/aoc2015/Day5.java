package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5
{
    private static boolean isNice( String input )
    {
        List<String> excludedStrings = Arrays.asList("ab", "cd", "pq", "xy");

        return !containsExcludedStrings( input, excludedStrings ) &&
                getVowelCount( input ) >=3 &&
                containsLetterTwiceInInterval( input, 1 );
    }

    private static boolean isNiceV2( String input )
    {
        return containsPairsThatAppearTwice( input ) &&
                containsLetterTwiceInInterval( input, 2 );
    }

    private static boolean containsExcludedStrings(String input, List<String> excludedStrings )
    {
        for( String s : excludedStrings )
        {
            if( input.contains(s))
                return true;
        }
        return false;
    }

    private static int getVowelCount( String input )
    {
        int count = 0;
        for( char c : input.toCharArray() )
        {
            if( c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                count++;
        }
        return count;
    }

    private static boolean containsLetterTwiceInInterval( String input, int interval )
    {
        char[] arr = input.toCharArray();
        for( int i=0; i<input.length() - interval; i++ )
        {
            if( arr[i] == arr[i+interval])
                return true;
        }
        return false;
    }

    private static boolean containsPairsThatAppearTwice( String input )
    {
        char[] arr = input.toCharArray();
        Map<String, Integer> map = new HashMap<>();

        for( int i=0; i<input.length() - 1; i++ )
        {
            String pair = String.valueOf(arr[i]) + arr[i + 1];
            if( map.containsKey( pair ) )
            {
                if( !(map.get( pair ) == i-1))
                    return true;
            }
            map.put( pair, i );
        }
        return false;
    }

    public static void main(String[] args) throws IOException
    {
        assert isNice("ugknbfddgicrmopn");
        assert isNice("aaa");
        assert !isNice("jchzalrnumimnmhp");
        assert !isNice("haegwjzuvuyypxyu");
        assert !isNice("dvszwmarrgswjxmb");

        List<String> input = Files.readAllLines( Util.getInputFilePath() );
        int count = 0;
        for( String line : input )
        {
            if( isNice( line ) )
                count++;
        }
        System.out.println(count);

        assert isNiceV2("qjhvhtzxzqqjkmpb");
        assert isNiceV2("xxyxx");
        assert !isNiceV2("uurcxstgmygtbstg");
        assert !isNiceV2("ieodomkazucvgmuy");

        count = 0;

        for( String line : input )
        {
            if( isNiceV2( line ) )
                count++;
        }
        System.out.println(count);
    }
}
