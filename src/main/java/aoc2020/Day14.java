package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day14 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());

        // part 1
        Map<Long, Long> mem = new HashMap<>();
        applyInstructions(input, mem);
        System.out.println(mem.values().stream().mapToLong(i -> i).sum());

        // part 2
        mem = new HashMap<>();
        applyInstructionsV2(input, mem);
        System.out.println(mem.values().stream().mapToLong(i -> i).sum());
    }

    private static void applyInstructions(List<String> input, Map<Long, Long> mem) {
        String mask = null;
        for(String line : input) {
            if(line.startsWith("mask")) {
                mask = line.split("=")[1].trim();
            }
            else if(line.startsWith("mem")) {
                Long address = Long.parseLong(line.substring(4, line.indexOf("]")));
                Long value = Long.parseLong(line.split("=")[1].trim());
                mem.put(address, applyMask(value, mask));
            }
        }
    }

    private static Long applyMask(Long value, String mask) {
        StringBuilder sb = new StringBuilder(format(value, 36));
        IntStream.range(0, mask.length())
                .filter(i -> mask.charAt(i) != 'X')
                .forEach(i -> sb.setCharAt(i, mask.charAt(i)));
        return Long.parseLong(sb.toString(), 2);
    }

    private static void applyInstructionsV2(List<String> input, Map<Long, Long> mem) {
        String mask = null;
        for(String line : input) {
            if(line.startsWith("mask")) {
                mask = line.split("=")[1].trim();
            }
            else if(line.startsWith("mem")) {
                Long address = Long.parseLong(line.substring(4, line.indexOf("]")));
                Long value = Long.parseLong(line.split("=")[1].trim());
                String result = applyMaskV2(address, mask);
                List<Long> addresses = generateAddresses(result);
                for(Long add : addresses)
                    mem.put(add, value);
            }
        }
    }

    private static String applyMaskV2(Long value, String mask) {
        StringBuilder sb = new StringBuilder(format(value, 36));
        IntStream.range(0, mask.length())
                .filter(i -> mask.charAt(i) != '0')
                .forEach(i -> sb.setCharAt(i, mask.charAt(i)));
        return sb.toString();
    }

    private static List<Long> generateAddresses(String value) {
        int count = (int) value.chars().filter(c -> c == 'X').count();
        List<Long> addresses = new ArrayList<>();
        for(long i=0; i<Math.pow(2, count); i++) {
            String s = format(i, count);
            StringBuilder sb = new StringBuilder(value);
            for(char c : s.toCharArray()) {
                sb.setCharAt(sb.indexOf("X"), c);
            }
            addresses.add(Long.parseLong(sb.toString(), 2));
        }
        return addresses;
    }

    private static String format(Long value, int length) {
        return String.format("%" + length + "s", Long.toBinaryString(value)).replace(" ", "0");
    }
}
