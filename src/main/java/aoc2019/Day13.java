package aoc2019;

import common.IntCodeComputer;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

public class Day13
{
    private static int getBlocksLeft( String instructions )
    {
        long[] input = IntCodeComputer.parseInput( instructions );

        IntCodeComputer computer = new IntCodeComputer( input );
        computer.runProgram();
        List<Long> output = computer.getOutput();

        long[][] grid = new long[50][50];

        Iterator<Long> iterator = output.iterator();
        while( iterator.hasNext() )
        {
            long x = iterator.next();
            long y = iterator.next();
            long tile = iterator.next();
            grid[(int)y][(int)x] = tile;
        }

        int count = 0;
        for( int i=0; i<50; i++ )
        {
            for( int j=0; j<50; j++ )
            {
                if( grid[i][j] == 2L )
                    count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException
    {
        String instructions = Files.readString(Util.getInputFilePath());
        System.out.println( getBlocksLeft( instructions ) );
    }
}
