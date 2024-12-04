package aoc2024

import Solution

class Day4: Solution {

    override fun part1(input: String): Any {
        var count = 0
        val lines = input.lines()
        for (x in lines.indices) {
            for (y in 0 until lines[0].length) {
                if (lines[x][y] == 'X') {
                    val strings = listOf(
                    (0..3).map { getChar(lines, x-it, y) }.joinToString(""),
                    (0..3).map { getChar(lines, x, y-it) }.joinToString(""),
                    (0..3).map { getChar(lines, x, y+it) }.joinToString(""),
                    (0..3).map { getChar(lines, x+it, y) }.joinToString(""),
                    (0..3).map { getChar(lines, x+it, y+it) }.joinToString(""),
                    (0..3).map { getChar(lines, x-it, y-it) }.joinToString(""),
                    (0..3).map { getChar(lines, x+it, y-it) }.joinToString(""),
                    (0..3).map { getChar(lines, x-it, y+it) }.joinToString("")
                    )
                    count += strings.count { it == "XMAS" }
                }
            }
        }
        return count
    }

    private fun getChar(lines: List<String>, x: Int, y: Int): Char {
        val rowCount = lines.size
        val colCount = lines[0].length
        if (x < 0 || x >= rowCount || y < 0 || y >= colCount) {
            return '.'
        }
        return lines[x][y]
    }

    override fun part2(input: String): Any {
        var count = 0
        val lines = input.lines()
        for (i in 1 until lines.size - 1) {
            for (j in 1 until lines[i].length - 1) {
                if (lines[i][j] == 'A') {
                    val topLeft = lines[i-1][j-1]
                    val topRight = lines[i-1][j+1]
                    val bottomLeft = lines[i+1][j-1]
                    val bottomRight = lines[i+1][j+1]
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