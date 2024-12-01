package aoc2015

import Solution
import kotlin.math.max

class Day6 : Solution {
    private val regex = """([\w\s]+) (\d+),(\d+) through (\d+),(\d+)""".toRegex()

    override fun part1(input: String): Any {
        val grid = Array(1000) { IntArray(1000) }
        input.lines()
            .map { parseInstruction(it) }
            .forEach { instruction ->
                when(instruction.operation) {
                    "turn on" -> apply(grid, instruction) { 1 }
                    "turn off" -> apply(grid, instruction) { 0 }
                    "toggle" -> apply(grid, instruction) { if (it == 1) 0 else 1 }
                }
            }
        return litCount(grid)
    }

    override fun part2(input: String): Any {
        val grid = Array(1000) { IntArray(1000) }
        input.lines()
            .map { parseInstruction(it) }
            .forEach { instruction ->
                when(instruction.operation) {
                    "turn on" -> apply(grid, instruction) { it + 1 }
                    "turn off" -> apply(grid, instruction) { max(0, it - 1) }
                    "toggle" -> apply(grid, instruction) { it + 2 }
                }
            }
        return litCount(grid)
    }

    private fun parseInstruction(input: String): Instruction {
        val (operation, startX, startY, endX, endY) = regex.find(input)!!.destructured
        return Instruction(operation, startX.toInt(), endX.toInt(), startY.toInt(), endY.toInt())
    }

    private fun apply(grid: Array<IntArray>, instruction: Instruction, operation: (Int) -> Int) {
        for (i in instruction.startX..instruction.endX) {
            for (j in instruction.startY..instruction.endY) {
                grid[i][j] = operation.invoke(grid[i][j])
            }
        }
    }

    private fun litCount(grid: Array<IntArray>): Int {
        return grid.sumOf { it.sum() }
    }

    data class Instruction(val operation: String, val startX: Int, val endX: Int, val startY: Int, val endY: Int)
}