package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day24
{
    private static void generateSetsWithSumInternal( int startIndex, int currentSum, int targetSum, List<Integer> input,
                                             List<List<Integer>> sets, List<Integer> tmp )
    {
        if( currentSum == targetSum )
        {
            sets.add( new ArrayList<>( tmp ));
            return;
        }
        for( int i=startIndex; i<input.size(); i++ )
        {
            if( input.get(i) + currentSum > targetSum )
                continue;
            tmp.add( input.get(i) );
            generateSetsWithSumInternal( i + 1, input.get(i) + currentSum, targetSum, input, sets, tmp );
            tmp.remove( input.get(i));
        }
    }

    private static List<List<Integer>> generateSetsWithSum( int targetSum, List<Integer> input )
    {
        List<List<Integer>> sets = new ArrayList<>();
        generateSetsWithSumInternal(0, 0, targetSum, input, sets, new ArrayList<>() );
        return sets;
    }

    private static Comparator<List<Integer>> getEntanglementComparator()
    {
        return Comparator.comparing( Day24::getEntanglementForList);
    }

    private static Long getEntanglementForList(List<Integer> list )
    {
        long product = 1;
        for( Integer i : list )
            product *= i;
        return product;
    }

    private static Long getEntanglement( List<String> input, int groupCount )
    {
        List<Integer> list = new ArrayList<>();
        int sum = 0;
        for( String line : input )
        {
            int a = Integer.parseInt( line );
            sum += a;
            list.add( a );
        }

        List<List<Integer>> sets = generateSetsWithSum( sum / groupCount, list );
        sets.sort( Comparator.comparingInt( (List<Integer> l) -> l.size()).thenComparing( getEntanglementComparator() ) );
        return getEntanglementForList( sets.get( 0 ));
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = Arrays.asList("1","2","3","4","5","7","8","9","10","11");
        List<List<Integer>> sets;

        assert getEntanglement( input, 3 ) == 99;

        input = Files.readAllLines( Util.getInputFilePath() );
        System.out.println( getEntanglement( input, 3));
        System.out.println( getEntanglement( input, 4));
    }
}
