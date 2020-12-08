package aoc2020;

import common.Pair;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());

        Instruction[] instructions = input.stream().map(line -> {
            String[] split = line.split(" ");
            return new Instruction(split[0], Integer.parseInt(split[1]));
        }).toArray(Instruction[]::new);

        // part 1
        Pair<Boolean, Integer> result = runProgram(instructions);
        System.out.println(result.getSecond());

        // part 2
        for(int i=0; i< instructions.length; i++) {
            if(instructions[i].op.equals("acc"))
                continue;
            Instruction[] insCopy = Arrays.copyOf(instructions, instructions.length);
            insCopy[i] = new Instruction(insCopy[i].op.equals("jmp") ? "nop":"jmp", insCopy[i].arg);
            result = runProgram(insCopy);
            if(result.getFirst()) {
                System.out.println(result.getSecond());
                break;
            }
        }
    }

    private static Pair<Boolean, Integer> runProgram(Instruction[] instructions) {
        int ptr = 0;
        int acc = 0;
        boolean completed = false;
        Set<Integer> history = new HashSet<>();

        while(true) {
            if(!history.add(ptr))
                break;
            Instruction ins = instructions[ptr];
            switch (ins.op) {
                case "nop":
                    ptr++;
                    break;
                case "acc":
                    acc += ins.arg;
                    ptr++;
                    break;
                case "jmp":
                    ptr += ins.arg;
                    break;
            }
            if(ptr == instructions.length) {
                completed = true;
                break;
            }
        }
        return new Pair<>(completed, acc);
    }

    static class Instruction {
        String op;
        int arg;

        public Instruction(String op, int arg) {
            this.op = op;
            this.arg = arg;
        }
    }
}
