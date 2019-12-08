package aoc2019;

import common.Util;

import java.util.function.Function;
import java.util.stream.IntStream;

public class Day4
{
    private static boolean containsSameAdjacentDigits(int[] arr)
    {
        for( int i=0; i<arr.length - 1; i++ )
        {
            if( arr[i] == arr[i+1])
                return true;
        }
        return false;
    }

    private static boolean containsAdjacentDigitsNotPartOfGroup( int[] arr )
    {
        int i = 0;
        while( i < arr.length - 1 )
        {
            int count = 0;
            while( i < arr.length - 1 && arr[i++] == arr[i])
                count++;
            if( count + 1 == 2 )
                return true;
        }
        return false;
    }

    private static boolean areDigitsIncreasing( int[] arr )
    {
        for( int i=0; i<arr.length - 1; i++ )
        {
            if( arr[i] > arr[i+1])
                return false;
        }
        return true;
    }

    private static boolean meetsCriteria(int n, Function<int[], Boolean> criteria )
    {
        int[] arr = Util.getAsIntArray(n);
        return criteria.apply( arr );
    }

    private static long getCountOfPasswords(int start, int end, Function<int[], Boolean> criteria )
    {
        return IntStream.range( start, end ).filter( i -> meetsCriteria( i, criteria ) ).count();
    }

    public static void main(String[] args)
    {
        Function<int[], Boolean> criteria = arr -> containsSameAdjacentDigits( arr ) && areDigitsIncreasing( arr );
        assert meetsCriteria(111111, criteria);
        assert !meetsCriteria(223450, criteria);
        assert !meetsCriteria(123789, criteria);

        System.out.println( getCountOfPasswords(137683, 596253, criteria));

        criteria = arr -> areDigitsIncreasing( arr ) && containsAdjacentDigitsNotPartOfGroup( arr );
        assert meetsCriteria(112233, criteria );
        assert !meetsCriteria(123444, criteria );
        assert meetsCriteria(111122, criteria );

        System.out.println( getCountOfPasswords( 137683, 596253, criteria ));
    }
}
