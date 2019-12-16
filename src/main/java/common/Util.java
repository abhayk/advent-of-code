package common;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util
{
    public static Path getInputFilePath()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = stackTraceElements[ stackTraceElements.length - 1].getClassName();
        String[] split = className.split("\\.");
        return Path.of("src", "main", "resources",
                split[split.length-2],
                split[split.length-1].toLowerCase() + ".txt");
    }

    public static int[] getAsIntArray(long n)
    {
        int size = String.valueOf( n ).length();
        int[] arr = new int[size];
        int i=size - 1;
        while( n > 0)
        {
            arr[i--] = (int) (n % 10);
            n /= 10;
        }
        return arr;
    }

    //from the function printHexBinary in jaxb-api
    public static String bytesToHex( byte[] bytes )
    {
        StringBuilder sb = new StringBuilder( bytes.length * 2 );
        char[] hexCode = "0123456789ABCDEF".toCharArray();
        for( byte b : bytes )
        {
            sb.append(hexCode[(b >> 4) & 0xF]);
            sb.append(hexCode[(b & 0xF)]);
        }
        return sb.toString();
    }

    public static int[] getAllNumbers( String input )
    {
        List<Integer> numbers = new ArrayList<>();
        Scanner scanner = new Scanner( input ).useDelimiter("[^-?\\d]+");
        while( scanner.hasNext() )
            numbers.add( scanner.nextInt() );
        return numbers.stream().mapToInt( a -> a ).toArray();
    }

    public static long lcm( int[] input )
    {
        long lcm = 1L;
        int divisor = 2;
        while( true )
        {
            int counter = 0;
            boolean divisible = false;
            for( int i=0; i<input.length; i++ )
            {
                //Another additional optimization we can do is to check if the element is prime.
                //If so just divide it with it immediately and we don't need to wait for the divisor to catch up.

                if( input[i] == 1)
                    counter++;
                if( input[i] % divisor == 0 )
                {
                    divisible = true;
                    input[i] = input[i] /= divisor;
                }
            }
            if( divisible )
                lcm *= divisor;
            else
                divisor++;
            if( counter == input.length )
                return lcm;
        }
    }
}
