package aoc2015;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Day18
{
    static int[][] neighbours = new int[][]{ {-1,-1}, {0,-1}, {1,-1}, {-1,0}, {1,0}, {-1,1}, {0,1}, {1,1} };

    private static int[][] animate(int steps, int[][] grid, int maxX, int maxY, BiConsumer<int[][], int[][]> animate )
    {
        int[][] tmpHolder = new int[maxX][maxY];

        for( int i=0; i<steps; i++ )
        {
            //animateNextFrame( grid, tmpHolder, maxX, maxY );
            animate.accept( grid, tmpHolder );
            int[][] tmp = grid;
            grid = tmpHolder;
            tmpHolder = tmp;
            clear( tmpHolder, maxX, maxY );
        }
        return grid;
    }

    private static void animateNextFrame( int[][] grid, int[][] tmpHolder, int maxX, int maxY )
    {
        for( int i=0; i<maxX; i++ )
        {
            for( int j=0; j<maxY; j++ )
                tmpHolder[i][j] = getNewState( grid, i, j, maxX, maxY );
        }
    }

    private static void animateNextFramev2( int[][] grid, int[][] tmpHolder, int maxX, int maxY )
    {
        for( int i=0; i<maxX; i++ )
        {
            for( int j=0; j<maxY; j++ )
            {
                if( (i==0 && j==0) || (i==maxX-1 && j==maxY-1) || ( i==0 && j==maxY-1) || ( i==maxX-1 && j==0))
                    tmpHolder[i][j] = 1;
                else
                    tmpHolder[i][j] = getNewState(grid, i, j, maxX, maxY);
            }
        }
    }

    private static int getNewState( int[][] grid, int x, int y, int maxX, int maxY )
    {
        int litCount = 0;
        for( int i=0; i<neighbours.length; i++ )
        {
            int a = x + neighbours[i][0];
            int b = y + neighbours[i][1];
            if( a >= 0 && a < maxX && b >= 0 && b < maxY )
                litCount += grid[a][b];
        }
        if( grid[x][y] == 1 )
            return (litCount == 2 || litCount == 3) ? 1 : 0;
        else
            return litCount == 3 ? 1 : 0;
    }

    private static void clear( int[][] grid, int maxX, int maxY )
    {
        for( int i=0; i<maxX; i++ )
        {
            for( int j=0; j<maxY; j++ )
                grid[i][j] = 0;
        }
    }

    private static int[][] parseInput( List<String> input, int maxX, int maxY )
    {
        int[][] grid = new int[maxX][maxY];
        int i=0;
        for( String line : input )
        {
            int j=0;
            for( char c : line.toCharArray() )
                grid[i][j++] = c == '#' ? 1 : 0;
            i++;
        }
        return grid;
    }

    private static int litCount( int[][] grid, int maxX, int maxY )
    {
        int count = 0;
        for( int i=0; i<maxX; i++ )
        {
            for( int j=0; j<maxY; j++ )
                count += grid[i][j];
        }
        return count;
    }

    private static int getLitCountAfterAnimation( List<String> input, int steps, int maxX, int maxY )
    {
        int[][] grid = parseInput( input, maxX, maxY );
        BiConsumer<int[][], int[][]> animate = (g1, g2) ->
                animateNextFrame( g1, g2, maxX, maxY );
        int[][] postAnimateGrid = animate( steps, grid, maxX, maxY, animate );
        return litCount( postAnimateGrid, maxX, maxY );
    }

    private static int getLitCountAfterAnimationv2( List<String> input, int steps, int maxX, int maxY )
    {
        int[][] grid = parseInput( input, maxX, maxY );
        grid[0][0] = 1;
        grid[0][maxY-1] = 1;
        grid[maxX-1][0] = 1;
        grid[maxX-1][maxY-1] = 1;
        BiConsumer<int[][], int[][]> animate = (g1, g2) ->
                animateNextFramev2( g1, g2, maxX, maxY );
        int[][] postAnimateGrid = animate( steps, grid, maxX, maxY, animate );
        return litCount( postAnimateGrid, maxX, maxY );
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add(".#.#.#");
        input.add("...##.");
        input.add("#....#");
        input.add("..#...");
        input.add("#.#..#");
        input.add("####..");

        assert getLitCountAfterAnimation( input, 4, 6, 6 ) == 4;
        assert  getLitCountAfterAnimationv2( input, 5, 6, 6 ) == 17;

        input = Files.readAllLines( Util.getInputFilePath() );
        System.out.println( getLitCountAfterAnimation( input, 100, 100, 100));
        System.out.println( getLitCountAfterAnimationv2( input, 100, 100, 100));
    }
}
