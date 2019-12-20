package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class Day6
{
    private static int processInstructionsV2( List<String> instructions )
    {
        int maxX = 1000, maxY = 1000;
        int[][] grid = new int[maxX][maxY];

        Map<Integer, BiConsumer<Integer, Integer>> opsV1 = new HashMap<>();
        opsV1.put(0, (i, j) -> grid[i][j] = Math.max( grid[i][j] - 1, 0));
        opsV1.put(1, (i, j) -> grid[i][j]++ );
        opsV1.put(2, (i, j) -> grid[i][j] += 2);

        for( String ins : instructions )
            processInstruction( grid, ins, opsV1 );

        return litCount( grid, maxX, maxY );
    }

    private static int processInstructions(List<String> instructions)
    {
        int maxX = 1000, maxY = 1000;
        int[][] grid = new int[maxX][maxY];

        Map<Integer, BiConsumer<Integer, Integer>> opsV1 = new HashMap<>();
        opsV1.put(0, (i, j) -> grid[i][j] = 0);
        opsV1.put(1, (i, j) -> grid[i][j] = 1);
        opsV1.put(2, (i, j) -> grid[i][j] = (grid[i][j] == 1 ? 0 : 1));

        for( String ins : instructions )
            processInstruction( grid, ins, opsV1 );

        return litCount( grid, maxX, maxY );
    }

    private static void processInstruction( int[][] grid, String ins, Map<Integer, BiConsumer<Integer, Integer>> ops )
    {
        String[] split = ins.split(" ");

        String[] bottom = split[ split.length - 1].split(",");
        int bottomX = Integer.parseInt( bottom[0] );
        int bottomY = Integer.parseInt( bottom[1] );

        String[] top = split[ split.length - 3].split(",");
        int topX = Integer.parseInt( top[0] );
        int topY = Integer.parseInt( top[1] );

        int op = ins.startsWith("turn off") ? 0 : ( ins.startsWith("turn on") ? 1 : 2);
        BiConsumer<Integer, Integer> consumer = ops.get( op );

        for( int i=topX; i<=bottomX; i++ )
        {
            for( int j=topY; j<=bottomY; j++ )
            {
                consumer.accept(i, j);
            }
        }
    }

    private static int litCount( int[][] grid, int rowCount, int colCount )
    {
        int count = 0;
        for( int i=0;i<rowCount; i++)
        {
            for( int j=0; j<colCount; j++ )
            {
                count += grid[i][j];
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException
    {
        assert processInstructions(Collections.singletonList("turn on 0,0 through 999,999")) == 1000000;
        assert processInstructions(Collections.singletonList("toggle 0,0 through 999,0")) == 1000;
        assert processInstructions(Collections.singletonList("turn on 499,499 through 500,500")) == 4;

        List<String> input = Files.readAllLines( Util.getInputFilePath() );

        //part 1
        System.out.println( processInstructions( input ) );

        assert processInstructionsV2( Collections.singletonList("turn on 0,0 through 0,0")) == 1;
        assert processInstructionsV2( Collections.singletonList("toggle 0,0 through 999,999")) == 2000000;

        //part 2
        System.out.println( processInstructionsV2( input ));
    }
}
