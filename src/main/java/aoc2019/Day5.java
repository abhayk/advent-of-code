package aoc2019;

import common.IntCodeComputer;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Day5
{
    public static void main(String[] args) throws IOException
    {
        String input = new String(Files.readAllBytes(Util.getInputFilePath()));
        long[] instructions = IntCodeComputer.parseInput( input );

        IntCodeComputer computer = new IntCodeComputer( instructions );
        computer.provideInput(1);
        computer.runProgram();
        List<Long> outputs = computer.getOutput();
        long output = outputs.get( outputs.size() - 1);
        System.out.println( output );
        assert output == 3122865;

        computer = new IntCodeComputer( instructions );
        computer.provideInput( 5 );
        computer.runProgram();
        outputs = computer.getOutput();
        output = outputs.get( outputs.size() - 1);
        System.out.println( output );
        assert output == 773660;
    }
}
