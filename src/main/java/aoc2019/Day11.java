package aoc2019;

import common.IntCodeComputer;
import common.Point;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day11
{
    private static int getNewDirection( int currentDirection, int moveDirection )
    {
        int newDirection = moveDirection == 0 ? --currentDirection : ++currentDirection;
        return newDirection > 3 ? 0 : ( newDirection < 0 ? 3 : newDirection );
    }

    private static Map<Point, Integer> runRobot(long[] input, int startingColour ) throws InterruptedException
    {
        IntCodeComputer computer = new IntCodeComputer( input );
        Map<Point, Integer> pointColourMap = new HashMap<>();
        Thread thread = new Thread( computer::runProgram );
        thread.start();
        int x=0, y=0;
        // 0-N, 1-E, 2-S, 3-W
        int currentDirection = 0;
        Map<Integer, int[]> moveMap = Map.of(
                0, new int[]{0, 1},
                1, new int[]{1, 0},
                2, new int[]{0, -1},
                3, new int[]{-1, 0});
        pointColourMap.put( new Point(0,0), startingColour );
        while( !computer.isDoneProcessing() )
        {
            Point currentPoint = new Point(x, y);
            int colour = pointColourMap.computeIfAbsent( currentPoint, key -> 0 );
            computer.provideInput( colour );
            while( computer.isRunning() )
                Thread.sleep(1);
            if( computer.isDoneProcessing() )
                break;
            int newColour = (int) computer.takeOutput();
            pointColourMap.put( currentPoint, newColour );
            int moveDirection = (int) computer.takeOutput();
            currentDirection = getNewDirection( currentDirection, moveDirection );
            x += moveMap.get( currentDirection )[0];
            y += moveMap.get( currentDirection )[1];
        }
        return pointColourMap;
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        long[] input = IntCodeComputer.parseInput( Files.readString( Util.getInputFilePath() ) );
        System.out.println(runRobot( input, 0).size() );

        Map<Point, Integer> pointColourMap = runRobot( input, 1);
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        for( Point p : pointColourMap.keySet() )
        {
            maxX = Math.max( maxX, p.x );
            maxY = Math.max( maxY, p.y );
            minX = Math.min( minX, p.x );
            minY = Math.min( minY, p.y );
        }

        int xShift = 0, yShift = 0;
        if( minX < 0 )
            xShift = minX * -1;
        if( minY < 0 )
            yShift = minY * -1;

        maxX += xShift;
        maxY += yShift + 1;

        int[][] grid = new int[maxY+1][maxX+1];
        for( Map.Entry<Point, Integer> entry : pointColourMap.entrySet() )
        {
            Point p = entry.getKey();
            grid[p.y+yShift][p.x+xShift] = entry.getValue();
        }
        // output is mirrored.
        for( int i=maxY-1; i>=0; i-- )
        {
            for( int j=maxX-1; j>=0; j-- )
            {
                System.out.print(grid[i][j] == 1 ? '#' : ' ');
            }
            System.out.println();
        }
    }
}
