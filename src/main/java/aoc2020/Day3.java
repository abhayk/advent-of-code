package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day3
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add("..##.......");
        input.add("#...#...#..");
        input.add(".#....#..#.");
        input.add("..#.#...#.#");
        input.add(".#...##..#.");
        input.add("..#.##.....");
        input.add(".#.#.#....#");
        input.add(".#........#");
        input.add("#.##...#...");
        input.add("#...##....#");
        input.add(".#..#...#.#");
        assert getTreeCountInPath(toCharArray(input), 3, 1) == 7;

        input = Files.readAllLines(Util.getInputFilePath());
        char[][] grid = toCharArray(input);
        System.out.println(getTreeCountInPath(grid, 3, 1));

        System.out.println(
            getTreeCountInPath(grid, 1, 1) *
            getTreeCountInPath(grid, 3, 1) *
            getTreeCountInPath(grid, 5, 1) *
            getTreeCountInPath(grid, 7, 1) *
            getTreeCountInPath(grid, 1, 2) );
    }

    private static char[][] toCharArray(List<String> input)
    {
        char[][] arr = new char[input.size()][];
        int i=0;
        for(String line : input)
            arr[i++] = line.toCharArray();
        return arr;
    }

    private static int getTreeCountInPath(char[][] grid, int right, int down)
    {
        int len = grid[0].length;
        int i = 0, j = 0;
        int treeCount = 0;
        while(true)
        {
            i += down;
            j += right;

            if(i >= grid.length)
                break;

            if(grid[i][j % len] == '#')
                treeCount++;
        }
        return treeCount;
    }
}
