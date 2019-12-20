package aoc2015;

import java.util.ArrayList;
import java.util.List;

public class Day20
{
    private static List<Integer> getDivisors( int n )
    {
        List<Integer> divisors = new ArrayList<>();
        int i=1;
        while( i <= Math.sqrt( n ))
        {
            if( n % i == 0 )
            {
                divisors.add(i);
                if( i != ( n / i ) )
                    divisors.add( n / i );
            }
            i++;
        }
        return divisors;
    }

    private static int presentCount( int house )
    {
        return getDivisors( house ).stream().mapToInt( Integer::valueOf ).sum() * 10;
    }

    private static int findHouseWithPresentCountAtleastAs( int count )
    {
        for( int i=1; i<10000000; i++ )
        {
            if( presentCount(i) >= count )
                return i;
        }
        return 0;
    }

    private static int part2( int count )
    {
        for( int i=1; i<10000000; i++ )
        {
            List<Integer> elves = getDivisors( i );
            int sum = 0;
            for( Integer elf : elves )
            {
                if( elf * 50  >= i  )
                    sum += elf;
            }
            if( sum * 11 >= count )
                return i;
        }
        return 0;
    }

    public static void main(String[] args)
    {
        assert presentCount( 1 ) == 10;
        assert presentCount( 2 ) == 30;
        assert presentCount( 3 ) == 40;
        assert presentCount( 4 ) == 70;
        assert presentCount( 5 ) == 60;
        assert presentCount( 6 ) == 120;
        assert presentCount( 7 ) == 80;
        assert presentCount( 8 ) == 150;
        assert presentCount( 9 ) == 130;

        System.out.println( findHouseWithPresentCountAtleastAs(33100000));
        System.out.println( part2( 33100000 ));
    }
}
