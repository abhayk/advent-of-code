package aoc2019;

import common.IntCodeComputer;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day5
{
    private static int[] parseInput( String input )
    {
        return Arrays.stream( input.split(",") ).mapToInt(val -> Integer.parseInt( val.trim() ) ).toArray();
    }

    public static void main(String[] args) throws IOException
    {
        String input = new String(Files.readAllBytes(Util.getInputFilePath()));
        int[] instructions = parseInput( input );

        List<Integer> outputs = IntCodeComputer.runDiagnostic( 1, Arrays.copyOf(instructions, instructions.length ) );
        int output = outputs.get( outputs.size() - 1);
        System.out.println( output );
        assert output == 3122865;

        outputs = IntCodeComputer.runDiagnostic( 5, Arrays.copyOf(instructions, instructions.length ) );
        output = outputs.get( outputs.size() - 1);
        System.out.println( output );
        assert output == 773660;
    }
}
