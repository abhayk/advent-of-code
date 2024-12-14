package aoc2024

import Solution
import common.Grid

class Day4: Solution {

    override fun part1(input: String): Any {
        var count = 0
        val grid = Grid.parse<Char>(input)
        for (x in grid.rowIndices) {
            for (y in grid.colIndices) {
                if (grid.get(x, y) == 'X') {
                    val strings = listOf(
                    (0..3).map { grid.get(x-it, y) }.joinToString(""),
                    (0..3).map { grid.get(x,y-it) }.joinToString(""),
                    (0..3).map { grid.get(x,y+it) }.joinToString(""),
                    (0..3).map { grid.get(x+it, y) }.joinToString(""),
                    (0..3).map { grid.get(x+it,y+it) }.joinToString(""),
                    (0..3).map { grid.get(x-it,y-it) }.joinToString(""),
                    (0..3).map { grid.get(x+it,y-it) }.joinToString(""),
                    (0..3).map { grid.get(x-it,y+it) }.joinToString("")
                    )
                    count += strings.count { it == "XMAS" }
                }
            }
        }
        return count
    }

    override fun part2(input: String): Any {
        var count = 0
        val grid = Grid.parse<Char>(input)
        for (i in 1 until grid.rowCount - 1) {
            for (j in 1 until grid.colCount - 1) {
                if (grid.get(i, j) == 'A') {
                    val topLeft = grid.get(i-1, j-1)
                    val topRight = grid.get(i-1,j+1)
                    val bottomLeft = grid.get(i+1,j-1)
                    val bottomRight = grid.get(i+1, j+1)
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