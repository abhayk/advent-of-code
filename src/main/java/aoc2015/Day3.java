package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Day3
{
    private static Set<String> findHousesWithPresentsWithRoboSanta( String input )
    {
        Set<String> housesSantaVisited = findHousesWithPresents( getStringWithEveryOtherChar( input, 0));
        Set<String> housesRoboSantaVisited = findHousesWithPresents( getStringWithEveryOtherChar( input, 1));

        Set<String> set = new HashSet<>();
        set.addAll( housesSantaVisited );
        set.addAll( housesRoboSantaVisited );

        return set;
    }

    private static Set<String> findHousesWithPresents(String input )
    {
        int x=0, y=0;
        Set<String> set = new HashSet<>();
        set.add( pointToString( x, y));

        for( char c : input.toCharArray() )
        {
            switch(c)
            {
                case '^': y++; break;
                case 'v': y--; break;
                case '>': x++; break;
                case '<': x--; break;
            }
            set.add( pointToString(x, y));
        }
        return set;
    }

    private static String pointToString( int x, int y )
    {
        return x + "~" + y;
    }

    private static String getStringWithEveryOtherChar( String input, int start )
    {
        StringBuilder sb = new StringBuilder();
        char[] arr = input.toCharArray();
        for( int i=start; i<input.length(); i+=2 )
            sb.append( arr[i] );
        return sb.toString();
    }

    public static void main(String[] args) throws IOException
    {
        assert findHousesWithPresents(">").size() == 2;
        assert findHousesWithPresents("^>v<").size() == 4;
        assert findHousesWithPresents("^v^v^v^v^v").size() == 2;

        String input = Files.readString( Util.getInputFilePath() );

        // part 1
        System.out.println( findHousesWithPresents( input ).size());

        assert getStringWithEveryOtherChar("^v", 0).equals("^");
        assert getStringWithEveryOtherChar("^v", 1).equals("v");
        assert getStringWithEveryOtherChar("^>v<", 0).equals("^v");
        assert getStringWithEveryOtherChar("^>v<", 1).equals("><");

        assert findHousesWithPresentsWithRoboSanta("^v").size() == 3;
        assert findHousesWithPresentsWithRoboSanta("^>v<").size() == 3;
        assert findHousesWithPresentsWithRoboSanta("^v^v^v^v^v").size() == 11;

        // part 2
        System.out.println( findHousesWithPresentsWithRoboSanta( input ).size() );
    }
}
