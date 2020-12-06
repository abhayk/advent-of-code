package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Util.getInputFilePath());
        String[] group = input.split("\n\n");

        // part 1
        long count = Arrays.stream(group)
            .map(s -> s.replace("\n", ""))
            .mapToLong(s -> s.chars().distinct().count())
            .sum();
        System.out.println(count);

        // part 2
        count = Arrays.stream(group)
            .mapToInt(Day6::getCountOfPeopleWhoAllAnsweredYes)
            .sum();
        System.out.println(count);
    }

    private static int getCountOfPeopleWhoAllAnsweredYes(String input) {
        List<List<Integer>> responses = Arrays.stream(input.split("\n"))
            .map(s -> s.chars().boxed().collect(Collectors.toList()))
            .collect(Collectors.toList());
        for (int i = 1; i < responses.size(); i++)
            responses.get(0).retainAll(responses.get(i));
        return responses.get(0).size();
    }
}
