package aoc2024

import Solution
import common.Grid

class Day14: Solution {
    override fun part1(input: String): Any {
        val rowCount = 103
        val colCount = 101
        val robots = input.lines().map { parseRobot(it) }
        repeat(100) {
            for (robot in robots) {
                robot.updatePosition(
                    wrap(robot.currentRow + robot.velocity.first, rowCount),
                    wrap(robot.currentCol + robot.velocity.second, colCount)
                )
            }
        }
        return calculateSafetyMeasure(robots, rowCount, colCount)
    }

    override fun part2(input: String): Any {
        val rowCount = 103
        val colCount = 101
        val robots = input.lines().map { parseRobot(it) }
        for (index in 0..10000)
        {
            for (robot in robots) {
                robot.updatePosition(
                    wrap(robot.currentRow + robot.velocity.first, rowCount),
                    wrap(robot.currentCol + robot.velocity.second, colCount)
                )
            }
            val grid = Grid.init<Char>(rowCount, colCount)
            for (robot in robots) {
                grid.set(robot.currentRow, robot.currentCol, '*')
            }
            var potential = false
            for (i in grid.rowIndices) {
                val joined = grid.grid[i].joinToString("")
                if (joined.contains("***********")) {
                    potential = true
                    break
                }
            }
            if (potential) {
                for (i in grid.rowIndices) {
                    val joined = grid.grid[i].joinToString("")
                    println(joined)
                }
                println(index + 1)
                return index + 1
            }
        }
        return 0
    }

    private fun calculateSafetyMeasure(robots: List<Robot>, rowCount: Int, colCount: Int): Int {
        val rowMid = rowCount / 2
        val colMid = colCount / 2
        var q1 = 0
        var q2 = 0
        var q3 = 0
        var q4 = 0
        for (robot in robots) {
            if (robot.currentRow < rowMid) {
                if (robot.currentCol < colMid) q1++
                else if(robot.currentCol > colMid) q2++
            } else if(robot.currentRow > rowMid) {
                if (robot.currentCol < colMid) q3++
                else if (robot.currentCol > colMid) q4++
            }
        }
        return q1 * q2 * q3 * q4
    }


    private fun wrap(value: Int, limit: Int): Int {
        return if (value < 0) {
            limit + value
        } else if(value >= limit) {
            value - limit
        } else {
            value
        }
    }

    private fun parseRobot(input: String): Robot {
        val regex = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()
        val (col, row, v1, v2) = regex.find(input)!!.destructured
        return Robot(row.toInt() to col.toInt(), v2.toInt() to v1.toInt())
    }

    class Robot(val position: Pair<Int, Int>, val velocity: Pair<Int, Int>) {
        var currentRow = position.first
        var currentCol = position.second

        fun updatePosition(newRow: Int, newCol: Int) {
            currentRow = newRow
            currentCol = newCol
        }
    }
}