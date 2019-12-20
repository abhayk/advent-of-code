package aoc2015;

import common.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4
{
    private static long findHashWhichStartsWith( String input, String start ) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        long i=1;
        while( true )
        {
            md.update((input + i++).getBytes());
            byte[] digest = md.digest();
            String hash = Util.bytesToHex( digest );
            if( hash.startsWith(start))
                break;
        }
        return i-1;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException
    {
        assert findHashWhichStartsWith("abcdef", "00000") == 609043L;

        // part 1
        System.out.println( findHashWhichStartsWith("yzbqklnj", "00000"));

        //part 2
        System.out.println( findHashWhichStartsWith("yzbqklnj", "000000"));
    }
}
