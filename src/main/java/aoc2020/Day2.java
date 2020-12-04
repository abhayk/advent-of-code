package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Day2
{
    public static void main(String[] args) throws IOException {
        assert isPasswordValid("1-3 a: abcde");
        assert !isPasswordValid("1-3 b: cdefg");
        assert isPasswordValid("2-9 c: ccccccccc");

        List<String> input = Files.readAllLines(Util.getInputFilePath());

        System.out.println(input.stream().filter(Day2::isPasswordValid).count());

        assert isPasswordValidUsingNewRule("1-3 a: abcde");
        assert !isPasswordValidUsingNewRule("1-3 b: cdefg");
        assert !isPasswordValidUsingNewRule("2-9 c: ccccccccc");

        System.out.println(input.stream().filter(Day2::isPasswordValidUsingNewRule).count());
    }

    private static boolean isPasswordValidUsingNewRule(String input) {
        Policy policy = new Policy(input);
        char[] arr = policy.text.toCharArray();
        return (arr[policy.lower - 1] == policy.ch)^(arr[policy.upper - 1] == policy.ch);
    }

    private static boolean isPasswordValid(String input) {
        Policy policy = new Policy(input);
        long charCount = policy.text.chars().filter( c -> c== policy.ch).count();;
        return charCount >= policy.lower && charCount <= policy.upper;
    }

    static class Policy {
        String text;
        char ch;
        int lower;
        int upper;

        public Policy(String input) {
            String[] split = input.split(":");
            this.text = split[1].trim();
            String[] lhs = split[0].split(" ");
            this.ch = lhs[1].charAt(0);
            String[] limits = lhs[0].split("-");
            this.lower = Integer.parseInt(limits[0]);
            this.upper = Integer.parseInt(limits[1]);
        }
    }
}
