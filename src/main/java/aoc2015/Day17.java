package aoc2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Day17
{
    private static void generateCombinations(int[] input, int[] tmp, int start, int end,
                                             int index, int r, Consumer<int[]> processCombination )
    {
        if( index == r )
        {
            processCombination.accept( tmp );
            return;
        }
        for( int i=start; i<=end && end-i+1 >= r-index; i++ )
        {
            tmp[index] = input[i];
            generateCombinations( input, tmp, i+1, end, index+1, r, processCombination );
        }
    }

    private static int findCombinationsThatMatch(int[] input, int target )
    {
        AtomicInteger count = new AtomicInteger();
        for( int i=1; i<=input.length; i++ )
        {
            generateCombinations(input, new int[i], 0, input.length - 1, 0, i, arr ->
            {
                if( Arrays.stream( arr ).sum() == target )
                    count.incrementAndGet();
            });
        }
        return count.get();
    }

    private static int findCombinationsThatMatchv2( int[] input, int target )
    {
        // container count and its combinations.
        TreeMap<Integer, AtomicInteger> map = new TreeMap<>();

        for( int i=1; i<=input.length; i++ )
        {
            generateCombinations(input, new int[i], 0, input.length - 1, 0, i, arr ->
            {
                if( Arrays.stream( arr ).sum() == target )
                    map.computeIfAbsent( arr.length, key -> new AtomicInteger()).incrementAndGet();
            });
        }
        return map.firstEntry().getValue().get();
    }

    public static void main(String[] args) throws IOException
    {
        assert findCombinationsThatMatch( new int[]{ 20, 15, 10, 5, 5}, 25 ) == 4;

        List<String> input = Files.readAllLines(Paths.get("src/main/resources/aoc2015/day17.txt"));
        int[] arr = input.stream().mapToInt(Integer::parseInt).toArray();

        System.out.println( findCombinationsThatMatch( arr, 150 ));
        System.out.println( findCombinationsThatMatchv2( arr, 150 ));
    }
}
