package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day10 {
    public static void main(String[] args) throws IOException {
        Integer[] sortedInput = Files.readAllLines(Util.getInputFilePath()).stream()
            .map(Integer::parseInt)
            .sorted()
            .toArray(Integer[]::new);

        // part 1
        int[] diffs = new int[4];
        for(int i=0; i<sortedInput.length - 1; i++)
            diffs[sortedInput[i+1] - sortedInput[i]]++;
        System.out.println((diffs[1]+1) * (diffs[3]+1));

        // part 2
        Integer[] arr = new Integer[sortedInput.length + 1];
        arr[0] = 0;
        System.arraycopy(sortedInput, 0, arr, 1, sortedInput.length);
        System.out.println(findAvailableJumps(arr, 0, new long[arr.length]));
    }

    private static long findAvailableJumps(Integer[] input, int index, long[] jumpsFromIndex) {
        if(index == input.length - 1)
            return 1L;
        long count = 0L;
        List<Integer> neighbours = getNeighbourIndexes(input, index);
        for(int i : neighbours) {
            if(jumpsFromIndex[i] == 0) {
                jumpsFromIndex[i] = findAvailableJumps(input, i, jumpsFromIndex);
            }
            count += jumpsFromIndex[i];
        }
        return count;
    }

    private static List<Integer> getNeighbourIndexes(Integer[] input, int index) {
        List<Integer> neighbours = new ArrayList<>();
        int i=1;
        while(index+i < input.length && input[index+i] - input[index] <= 3) {
            neighbours.add(index + i);
            i++;
        }
        return neighbours;
    }
}
