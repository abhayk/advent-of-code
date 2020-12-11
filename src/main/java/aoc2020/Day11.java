package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day11 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());
        char[][] grid = new char[input.size()][];
        int i=0;
        for(String line : input)
            grid[i++] = line.toCharArray();

        char[][] result = stabilize(copyOf(grid), Day11::simulateRound);
        System.out.println(getTotalOccupiedCount(result));

        result = stabilize(copyOf(grid), Day11::simulateRoundV2);
        System.out.println(getTotalOccupiedCount(result));
    }
    static int[][] neighbours = new int[][]{ {-1,-1}, {0,-1}, {1,-1}, {-1,0}, {1,0}, {-1,1}, {0,1}, {1,1} };

    private static char[][] stabilize(char[][] grid, Function<char[][], char[][]> simulator) {
        while(true) {
            char[][] tmp = simulator.apply(grid);
            if(areGridsEqual(grid, tmp))
                break;
            grid = tmp;
        }
        return grid;
    }

    private static char[][] simulateRound(char[][] input) {
        char[][] tmp = copyOf(input);
        for(int i=0; i<input.length; i++) {
            for(int j=0; j<input[0].length; j++) {
                int adjacentOccupiedCount = getAdjacentOccupiedCount(input, i, j);
                if(input[i][j] == 'L' && adjacentOccupiedCount == 0)
                    tmp[i][j] = '#';
                else if(input[i][j] == '#' && adjacentOccupiedCount >= 4)
                    tmp[i][j] = 'L';
            }
        }
        return tmp;
    }

    private static char[][] simulateRoundV2(char[][] input) {
        char[][] tmp = copyOf(input);
        for(int i=0; i<input.length; i++) {
            for(int j=0; j<input[0].length; j++) {
                int adjacentOccupiedCount = getAdjacentOccupiedCountV2(input, i, j);
                if(input[i][j] == 'L' && adjacentOccupiedCount == 0)
                    tmp[i][j] = '#';
                else if(input[i][j] == '#' && adjacentOccupiedCount >= 5)
                    tmp[i][j] = 'L';
            }
        }
        return tmp;
    }

    private static int getAdjacentOccupiedCount(char[][] grid, int x, int y) {
        int maxX = grid.length;
        int maxY = grid[0].length;

        int count = 0;
        for(int[] n : neighbours) {
            int a = n[0] + x;
            int b = n[1] + y;
            if(a >=0 && a < maxX && b >= 0 && b <maxY) {
                if(grid[a][b] == '#')
                    count++;
            }
        }
        return count;
    }

    private static int getAdjacentOccupiedCountV2(char[][] grid, int x, int y) {
        int maxX = grid.length;
        int maxY = grid[0].length;
        int count = 0;
        for(int[] n : neighbours) {
            int a = x;
            int b = y;
            while(true) {
                a += n[0];
                b += n[1];
                if(a >=0 && a < maxX && b >= 0 && b <maxY) {
                    if(grid[a][b] == 'L') // found empty seat
                        break;
                    if(grid[a][b] == '#') { // found occupied sear
                        count++;
                        break;
                    }
                }
                else
                    break;
            }
        }
        return count;
    }

    private static int getTotalOccupiedCount(char[][] grid) {
        int count = 0;
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                if(grid[i][j] == '#')
                    count++;
            }
        }
        return count;
    }

    private static char[][] copyOf(char[][] input) {
        char[][] tmp = new char[input.length][];
        IntStream.range(0, input.length)
            .forEach(i -> tmp[i] = Arrays.copyOf(input[i], input.length));
        return tmp;
    }

    private static boolean areGridsEqual(char[][] g1, char[][] g2) {
        return IntStream.range(0, g1.length)
            .allMatch(i -> Arrays.equals(g1[i], g2[i]));
    }
}
