package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) throws IOException {
        assert calculateSeatId("FBFBBFFRLR") == 357;
        assert calculateSeatId("BFFFBBFRRR") == 567;
        assert calculateSeatId("FFFBBBFRRR") == 119;
        assert calculateSeatId("BBFFBBFRLL") == 820;

        List<String> input = Files.readAllLines(Util.getInputFilePath());
        List<Integer> seats = input.stream().map(Day5::calculateSeatId).collect(Collectors.toList());
        // part 1
        System.out.println(seats.stream().max(Integer::compareTo));

        //part 2
        seats.sort(Integer::compareTo);
        for(int i=0; i<seats.size() - 1; i++) {
            if(seats.get(i+1) - seats.get(i) != 1) {
                System.out.println(seats.get(i) + 1);
                break;
            }
        }
    }

    private static int calculateSeatId(String input) {
        int col = reduce(input.substring(0, 7), 'F', 'B', 0, 127);
        int row = reduce(input.substring(7), 'L', 'R', 0, 7);
        return (col * 8) + row;
    }

    private static int reduce(String input, char lowCh, char hiCh, double lo, double hi) {
        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) == lowCh)
                hi = Math.floor((lo + hi) / 2);
            else
                lo = Math.ceil((lo + hi) / 2);
        }
        return (int) (input.charAt(input.length() - 1) == lowCh ? lo : hi);
    }
}
