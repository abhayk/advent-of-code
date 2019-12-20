package aoc2015;

public class Day11
{
    private static String findNextValidPassword( String input )
    {
        char[] arr = input.toCharArray();
        while( true )
        {
            increment( arr, arr.length - 1);
            if( isPasswordValid( arr ) )
                return new String(arr);
        }
    }

    private static void increment( char[] arr, int index )
    {
        if( arr[index] == 'z')
        {
            arr[index] = 'a';
            increment( arr, index - 1);
        }
        else
            arr[index] += 1;
    }

    private static boolean isPasswordValid( char[] password )
    {
        return containsValidChars( password ) &&
                containsIncreasingSequence( password, 3) &&
                containsPairs( password, 2 );
    }

    private static boolean containsValidChars( char[] password )
    {
        for (char c : password)
        {
            if (c == 'i' || c == 'o' || c == 'l')
                return false;
        }
        return true;
    }

    private static boolean containsIncreasingSequence( char[] password, int interval )
    {
        for( int i=0; i<password.length - interval; i++ )
        {
            if( ( password[i+1] == password[i] + 1 ) && ( password[i+2] == password[i] + 2))
                return true;
        }
        return false;
    }

    private static boolean containsPairs( char[] password, int count )
    {
        int pairCount = 0;
        for( int i=0; i<password.length - 1; i++ )
        {
            if( password[i] == password[i+1])
            {
                pairCount++;
                i++;
            }
        }
        return pairCount >= count;
    }

    public static void main(String[] args)
    {
        assert findNextValidPassword("abcdefgh").equals("abcdffaa");
        assert findNextValidPassword("ghijklmn").equals("ghjaabcc");

        String nextPassword = findNextValidPassword("hepxcrrq");
        System.out.println( nextPassword );
        System.out.println( findNextValidPassword( nextPassword ));
    }
}
