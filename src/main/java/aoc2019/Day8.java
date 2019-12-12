package aoc2019;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day8
{
    private static List<int[][]> parseInput(String input, int width, int height )
    {
        List<int[][]> layers = new ArrayList<>();
        char[] arr = input.toCharArray();
        int index=0;
        while( index < input.length() - 1 )
        {
            int[][] layer = new int[height][width];
            for( int i=0; i<height; i++ )
            {
                for( int j=0; j<width; j++ )
                    layer[i][j] = Character.digit( arr[index++], 10);
            }
            layers.add( layer );
        }
        return layers;
    }

    private static int getCountOfDigit( int[][] layer, int digit, int width, int height )
    {
        int count = 0;
        for( int i=0; i<height; i++ )
        {
            for( int j=0; j<width; j++ )
            {
                if( layer[i][j] == digit )
                    count++;
            }
        }
        return count;
    }

    private static int getIndexOfLayerContainingFewestDigit( List<int[][]> layers, int digit, int width, int height )
    {
        int leastCount = Integer.MAX_VALUE;
        int indexOfLayerHavingLeastCount = 0;
        for( int i=0; i<layers.size(); i++ )
        {
            int[][] layer = layers.get( i );
            int count = getCountOfDigit( layer, digit, width, height );
            if( count < leastCount )
            {
                leastCount = count;
                indexOfLayerHavingLeastCount = i;
            }
        }
        return indexOfLayerHavingLeastCount;
    }

    private static int part1( List<int[][]> layers, int width, int height )
    {
        int[][] layer = layers.get( getIndexOfLayerContainingFewestDigit( layers, 0, width, height ) );
        return getCountOfDigit( layer, 1, width, height ) * getCountOfDigit( layer, 2, width, height );
    }

    private static int findFirstValidPixel( List<int[][]> layers, int i, int j )
    {
        for( int[][] layer : layers )
        {
            if( layer[i][j] == 2 )
                continue;
            return layer[i][j];
        }
        return -1;
    }

    private static void printImage( List<int[][]> layers, int width, int height )
    {
        for( int i=0; i<height; i++ )
        {
            for( int j=0; j<width; j++ )
            {
                System.out.print( findFirstValidPixel( layers, i, j) == 1 ? "#" : " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException
    {
        String input = new String( Files.readAllBytes(Util.getInputFilePath()));
        int width = 25;
        int height = 6;
        List<int[][]> layers = parseInput( input, width, height );
        System.out.println( part1( layers, width, height));
        printImage( layers, width, height );
    }
}
