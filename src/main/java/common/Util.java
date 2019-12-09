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
}
