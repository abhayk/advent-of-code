package aoc2021;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Day2 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(List<String> input) {
        int x = 0, y = 0;
        for(String line : input) {
            String[] split = line.split(" ");
            int step = Integer.parseInt(split[1]);
            switch (split[0]) {
                case "forward": x+=step;break;
                case "up": y-=step;break;
                case "down": y+=step;break;
            }
        }
        return x*y;
    }

    public static int part2(List<String> input) {
        int x = 0, y = 0, aim = 0;
        for(String line : input) {
            String[] split = line.split(" ");
            int step = Integer.parseInt(split[1]);
            switch (split[0]) {
                case "forward": x+=step; y+=(aim*step); break;
                case "up": aim-=step; break;
                case "down": aim+=step; break;
            }
        }
        return x*y;
    }
}
