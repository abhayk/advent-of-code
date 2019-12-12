package aoc2019;

import common.IntCodeComputer;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day9
{
    public static void main(String[] args) throws IOException
    {
        IntCodeComputer computer;

        long[] input = new long[]{ 109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 };
        computer = new IntCodeComputer( input );
        computer.runProgram();
        assert computer.getOutput().equals( Arrays.stream( input ).boxed().collect(Collectors.toList()) );

        input = new long[]{ 1102,34915192,34915192,7,4,7,99,0 };
        computer = new IntCodeComputer( input );
        computer.runProgram();
        assert String.valueOf( computer.getOutput().get( 0 )).length() == 16;

        input = new long[]{ 104,1125899906842624L,99 };
        computer = new IntCodeComputer( input );
        computer.runProgram();
        assert computer.getOutput().get( 0 ) == 1125899906842624L;

        long[] instructions = IntCodeComputer.parseInput( new String(Files.readAllBytes(Util.getInputFilePath())));
        computer = new IntCodeComputer( instructions );
        computer.provideInput( 1 );
        computer.runProgram();
        System.out.println( computer.getOutput().toString() );
        assert computer.getOutput().get( 0 ) == 2789104029L;

        computer = new IntCodeComputer( instructions );
        computer.provideInput( 2 );
        computer.runProgram();
        System.out.println( computer.getOutput().toString() );
        assert computer.getOutput().get( 0 ) == 32869L;
    }
}
