package aoc2024

import Solution
import common.getChar
import java.lang.IllegalArgumentException

class Day6: Solution {
    override fun part1(input: String): Any {
        val lines = input.lines()
        val start = getGuardPosition(lines)
        return simulate(lines, start)?.size ?: -1
    }

    override fun part2(input: String): Any {
        val lines = input.lines()
        var count = 0
        val start = getGuardPosition(lines)
        val steps = simulate(lines, start)!!
        for (step in steps) {
            val i = step.first
            val j = step.second
            if (lines[i][j] == '#' || lines[i][j] == '^') {
                continue
            }
            val updatedLine = lines[i].toMutableList()
            updatedLine[j] = 'O'
            val linesCopy = input.lines().toMutableList()
            linesCopy[i] = updatedLine.joinToString("")
            val result = simulate(linesCopy, start)
            if (result == null) {
                count++
            }
        }
        return count
    }


    private fun simulate(lines: List<String>, start: Pair<Int, Int>): Set<Pair<Int, Int>>? {
        var current = start.copy()
        val visited = mutableMapOf<Pair<Int, Int>, Int>()
        visited[current] = 1
        val nextTurn = mapOf(
            (0 to 1) to (1 to 0),
            (1 to 0) to (0 to -1),
            (0 to -1) to (-1 to 0),
            (-1 to 0) to (0 to 1)
        )
        var currentDirection = (-1 to 0)
        while (true) {
            if (visited.getOrDefault(current, 0) > 4) {
                return null // loop detected
            }
            val nextChar = getChar(lines,
                current.first + currentDirection.first,
                current.second + currentDirection.second, '$')
            if (nextChar == '$') {
                return visited.keys
            }
            if (nextChar == '#' || nextChar == 'O') {
                currentDirection = nextTurn[currentDirection]!!
            } else if (nextChar == '.' || nextChar == '^') {
                current = current.copy(current.first + currentDirection.first, current.second + currentDirection.second)
                visited[current] = visited.getOrDefault(current, 0) + 1
            }
        }
    }

    private fun getGuardPosition(input: List<String>): Pair<Int, Int> {
        for (i in input.indices) {
            for (j in 0 until input[0].length) {
                if (input[i][j] == '^') {
                    return i to j
                }
            }
        }
        throw IllegalArgumentException("guard not found")
    }
}