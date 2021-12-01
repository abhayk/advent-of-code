package aoc2021;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;

public class Day1 {
    public static void main(String[] args) throws IOException {
        int[] input = Files.readAllLines(Util.getInputFilePath())
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        System.out.println(getCountOfIncreasesInWindow(input, 1));
        System.out.println(getCountOfIncreasesInWindow(input, 3));
    }

    public static int getCountOfIncreasesInWindow(int[] input, int windowSize) {
        int count = 0;
        for(int i=windowSize; i<input.length; i++) {
            if(input[i] > input[i-windowSize]) {
                count++;
            }
        }
        return count;
    }
}
