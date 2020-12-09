package aoc2020;

import common.Pair;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 {
    public static void main(String[] args) throws IOException {
        List<Long> input = Files.readAllLines(Util.getInputFilePath()).stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());

        // part 1
        long invalidEntry = findInvalidEntry(input, 25);
        System.out.println(invalidEntry);

        // part 2
        findIndexOfNumbersThatAddUpTo(invalidEntry, input).ifPresent(pair -> {
            List<Long> subList = input.subList(pair.getFirst(), pair.getSecond());
            subList.sort(Long::compareTo);
            System.out.println(subList.get(0) + subList.get(subList.size() - 1));
        });
    }


    private static Optional<Pair<Integer, Integer>> findIndexOfNumbersThatAddUpTo(long target, List<Long> input) {
        long sum = input.get(0);
        int start = 0;

        for(int i=1; i<=input.size(); i++) {

            while(sum > target && start < i-1)
                sum -= input.get(start++);

            if(sum == target)
                return Optional.of(new Pair<>(start, i-1));

            if(i < input.size())
                sum += input.get(i);
        }
        return Optional.empty();
    }

    private static Long findInvalidEntry(List<Long> input, int preambleLength) {
        Set<Long> preamble = new HashSet<>(input.subList(0, preambleLength));
        for(int i=preambleLength; i<input.size(); i++ ) {
            Long number = input.get(i);
            boolean valid = preamble.stream().anyMatch(n -> preamble.contains(number-n));
            if(!valid)
                return number;
            preamble.remove(input.get(i-preambleLength));
            preamble.add(number);
        }
        return -1L;
    }
}
