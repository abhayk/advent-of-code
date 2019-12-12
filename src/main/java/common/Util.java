package common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Util
{
    public static Path getInputFilePath()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = stackTraceElements[ stackTraceElements.length - 1].getClassName();
        String[] split = className.split("\\.");
        return Paths.get("src", "main", "resources",
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
}
