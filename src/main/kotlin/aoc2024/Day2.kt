package aoc2024

import Solution
import kotlin.math.absoluteValue

class Day2: Solution {
    override fun part1(input: String): Any {
        return input.lines()
            .map { parseLine(it) }
            .count { isSafe(it) }
    }

    override fun part2(input: String): Any {
        return input.lines()
            .map { parseLine(it) }
            .count { isSafeWithDampener(it) }
    }

    private fun isSafe(input: List<Int>): Boolean {
        val diffs = input.windowed(2).map { it[0] - it[1] }
        return diffs.all { it.absoluteValue in 1..3 } && (diffs.all { it > 0 } || diffs.all { it < 0 })
    }

    private fun isSafeWithDampener(input: List<Int>): Boolean {
        if (isSafe(input)) {
            return true
        }
        for (i in input.indices) {
            val tmp = input.toMutableList()
            tmp.removeAt(i)
            if (isSafe(tmp)) {
                return true
            }
        }
        return false
    }

    private fun parseLine(line: String): List<Int> {
        return line.split(" ").map { it.toInt() }
    }
}