package aoc2020;

import common.Pair;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

        //test();
        //System.out.println(Util.lcm(new int[]{323,221}));

        List<Pair<Integer, Integer>> times = new ArrayList<>();
        String[] split = input.get(1).split(",");
        for(int i=0; i< split.length; i++) {
            if(split[i].equals("x"))
                continue;
            times.add(new Pair<>(Integer.parseInt(split[i]), i));
        }
    }

    private static void test() {
        //17-13, 6,102,104,2 - cycle - 221, every 13th multiple of 17 starting from the 6th multiple
        //17-19, 11,187,190,3 - cycle - 323, every 19th multiple of 17 starting from the 11th multiple

        //201,3417,3419,2
        //201,3417,3420,3

        //6,102,104,2
        //11,187,190,3

        //13,26,39,52
        //6+13 -  19,32,45,58,71,84,97,110,123,136,149,162,175,188,201
        //11+19 - 30,49,68,87,106,125,144,163,182,201

        //13 - 13,26,39,52,65,78,91,104,117,130,143,156,169,182,195,208,221,234,247
        //19 - 19,38,,57,76,95,114,133,152,171,190,209,228,247

        int a = 17;
        int b = 13;

        for(int i=2; i<250; i++) {
            int m = a * i;
            int n = (m/b)*b + b;
            System.out.printf("%d,%d,%d,%d%n", i, m, n, n - m);
        }
    }
}
