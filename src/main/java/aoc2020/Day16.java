package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Day16 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());
        List<Rule> rules = new ArrayList<>();
        Iterator<String> it = input.listIterator();
        String line;
        while((line = it.next().trim()).length() > 0)
            rules.add(parseRule(line));
        it.next();
        Integer[] ourTicket = parseTicket(it.next());
        it.next();
        it.next();
        List<Integer[]> nearbyTickets = new ArrayList<>();
        it.forEachRemaining(s -> nearbyTickets.add(parseTicket(s)));

        // part 1
        System.out.println(nearbyTickets.stream()
            .flatMap(Arrays::stream)
            .filter(n -> !isNumberValid(n, rules))
            .mapToInt(i -> i)
            .sum());


    }

    private static boolean isTicketValid(Integer[] ticket, List<Rule> rules) {
        return Arrays.stream(ticket)
            .allMatch(n -> isNumberValid(n, rules));
    }

    private static boolean isNumberValid(Integer n, List<Rule> rules) {
        return rules.stream()
            .flatMap(r -> r.ranges.stream())
            .anyMatch(range -> n >= range[0] && n <= range[1] );
    }

    private static Integer[] parseTicket(String input) {
        return Arrays.stream(input.split(","))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
    }

    private static Rule parseRule(String input) {
        String[] split = input.split(":");
        Rule rule = new Rule();
        rule.name = split[0].trim();
        for(String range : split[1].split("or"))
            rule.ranges.add(Arrays.stream(range.trim().split("-"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new));
        return rule;
    }

    static class Rule {
        String name;
        List<Integer[]> ranges = new ArrayList<>();
    }
}
