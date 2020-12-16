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

        // part 1
        Arrays.stream(input.get(1).split(","))
            .filter(s -> !s.equals("x"))
            .map(Long::parseLong)
            .map(i -> new Pair<>((((time / i) * i) + i) - time, i))
            .min(Comparator.comparing(Pair::getFirst))
            .map(p -> p.getFirst() * p.getSecond())
            .ifPresent(System.out::println);

        assert findNextTimestamp(parseTimes("7,13,x,x,59,x,31,19")) == 1068781;
        assert findNextTimestamp(parseTimes("17,x,13,19")) == 3417;
        assert findNextTimestamp(parseTimes("67,7,59,61")) == 754018;
        assert findNextTimestamp(parseTimes("67,x,7,59,61")) == 779210;
        assert findNextTimestamp(parseTimes("67,7,x,59,61")) == 1261476;
        assert findNextTimestamp(parseTimes("1789,37,47,1889")) == 1202161486;

        // part 2
        System.out.println(findNextTimestamp(parseTimes(input.get(1))));
    }

    private static List<Pair<Integer, Integer>> parseTimes(String input) {
        String[] buses = input.split(",");
        return IntStream.range(0, buses.length)
            .filter(i -> !buses[i].equals("x"))
            .mapToObj(i -> new Pair<>(Integer.parseInt(buses[i]), i))
            .collect(Collectors.toList());
    }

    // Starting with multiples of the first number keep looking for numbers which return the required remainder
    // for the next number. Once that is found the steps to jump forward can be the lcm of the 2 numbers.
    // Since when we jump by the lcm the remainders returned for the numbers remain the same.
    private static long findNextTimestamp(List<Pair<Integer, Integer>> numberRemainderPairs) {
        long steps = numberRemainderPairs.get(0).getFirst();
        long counter = steps;
        for(Pair<Integer, Integer> pair : numberRemainderPairs) {
            while((counter + pair.getSecond()) % pair.getFirst() != 0)
                counter += steps;
            steps = Util.lcm(new long[]{steps, pair.getFirst()});
        }
        return counter;
    }
}
