package aoc2020;

import common.Pair;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());
        long time = Long.parseLong(input.get(0));
        String[] buses = input.get(1).split(",");

        // part 1
        Arrays.stream(buses)
            .filter(s -> !s.equals("x"))
            .map(Long::parseLong)
            .map(i -> new Pair<>((((time / i) * i) + i) - time, i))
            .min(Comparator.comparing(Pair::getFirst))
            .map(p -> p.getFirst() * p.getSecond())
            .ifPresent(System.out::println);

        // part 2
        List<Pair<Integer, Integer>> numberRemainderPairs = IntStream.range(0, buses.length)
            .filter(i -> !buses[i].equals("x"))
            .mapToObj(i -> new Pair<>(Integer.parseInt(buses[i]), i))
            .collect(Collectors.toList());
        System.out.println(findNextTimestamp(numberRemainderPairs));
    }

    // Starting with multiples of the first number keep looking for numbers which return the required remainder
    // for the next number. Once that is found the steps to jump forward can be the lcm of the 2 numbers.
    // Since when we jump by the lcm the remainders returned for the numbers remain the same.
    private static long findNextTimestamp(List<Pair<Integer, Integer>> numberRemainderPairs) {
        long currentSteps = numberRemainderPairs.get(0).getFirst();
        int index = 1;
        Pair<Integer, Integer> currentPair = numberRemainderPairs.get(index);
        long counter = currentSteps;
        while(true) {
            counter += currentSteps;
            if((counter + currentPair.getSecond()) % currentPair.getFirst() == 0) {
                currentSteps = Util.lcm(new long[]{currentSteps, currentPair.getFirst()});
                index++;
                if(index >= numberRemainderPairs.size())
                    break;
                currentPair = numberRemainderPairs.get(index);
            }
        }
        return counter;
    }
}
