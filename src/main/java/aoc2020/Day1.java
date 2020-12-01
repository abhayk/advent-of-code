package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Util.getInputFilePath() );

        Map<Integer, Integer> inputCountMap = input.stream()
            .map(Integer::parseInt)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));

        int target = 2020;

        int entry = findEntry(target, inputCountMap);
        System.out.println(entry * (target-entry));

        findEntry2(target, inputCountMap);
    }

    private static void findEntry2(int target, Map<Integer,Integer> inputCountMap)
    {
        for(Map.Entry<Integer, Integer> entry : inputCountMap.entrySet())
        {
            int subTarget = target - entry.getKey();
            int result = findEntry(subTarget, inputCountMap);
            if(result != -1)
            {
                System.out.println(entry.getKey() * result * (subTarget - result));
                return;
            }
        }
    }

    private static int findEntry(int target, Map<Integer, Integer> inputCountMap)
    {
        if(target%2 == 0)
        {
            if(inputCountMap.containsKey(target/2) && inputCountMap.get(target/2) > 1)
                return target/2;
        }
        for(Map.Entry<Integer, Integer> entry : inputCountMap.entrySet())
        {
            if(inputCountMap.containsKey(target - entry.getKey()))
                return entry.getKey();
        }
        return -1;
    }
}
