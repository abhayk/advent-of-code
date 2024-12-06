package aoc2024

import Solution
import common.GridUtils.getChar
import common.GridUtils.toCharGrid

class Day4: Solution {

    override fun part1(input: String): Any {
        var count = 0
        val grid = toCharGrid(input)
        for (x in grid.indices) {
            for (y in 0 until grid[0].size) {
                if (grid[x][y] == 'X') {
                    val strings = listOf(
                    (0..3).map { getChar(grid, x-it, y) }.joinToString(""),
                    (0..3).map { getChar(grid, x, y-it) }.joinToString(""),
                    (0..3).map { getChar(grid, x, y+it) }.joinToString(""),
                    (0..3).map { getChar(grid, x+it, y) }.joinToString(""),
                    (0..3).map { getChar(grid, x+it, y+it) }.joinToString(""),
                    (0..3).map { getChar(grid, x-it, y-it) }.joinToString(""),
                    (0..3).map { getChar(grid, x+it, y-it) }.joinToString(""),
                    (0..3).map { getChar(grid, x-it, y+it) }.joinToString("")
                    )
                    count += strings.count { it == "XMAS" }
                }
            }
        }
        return count
    }

    override fun part2(input: String): Any {
        var count = 0
        val grid = toCharGrid(input)
        for (i in 1 until grid.size - 1) {
            for (j in 1 until grid[i].size - 1) {
                if (grid[i][j] == 'A') {
                    val topLeft = grid[i-1][j-1]
                    val topRight = grid[i-1][j+1]
                    val bottomLeft = grid[i+1][j-1]
                    val bottomRight = grid[i+1][j+1]
                    val tmp = listOf(topLeft, topRight, bottomLeft, bottomRight).joinToString("")
                    if (tmp.count { it == 'M' } == 2 &&
                        tmp.count { it == 'S' } == 2 &&
                        topLeft != bottomRight &&
                        topRight != bottomLeft) {
                        count++
                    }
                }
            }
        }
        return count
    }
}