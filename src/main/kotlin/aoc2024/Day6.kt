package aoc2024

import Solution
import common.GridUtils.getChar
import common.GridUtils.toCharGrid
import java.lang.IllegalArgumentException

class Day6: Solution {
    override fun part1(input: String): Any {
        val grid = toCharGrid(input)
        val start = getGuardPosition(grid)
        return simulate(grid, start)?.size ?: -1
    }

    override fun part2(input: String): Any {
        val grid = toCharGrid(input)
        val start = getGuardPosition(grid)
        var count = 0
        val steps = simulate(grid, start)!!
        for (step in steps) {
            val i = step.first
            val j = step.second
            if (grid[i][j] == '#' || grid[i][j] == '^') {
                continue
            }
            val tmp = grid.map { it.copyOf() }.toTypedArray()
            tmp[i][j] = 'O'
            val result = simulate(tmp, start)
            if (result == null) {
                count++
            }
        }
        return count
    }


    private fun simulate(grid: Array<CharArray>, start: Pair<Int, Int>): Set<Pair<Int, Int>>? {
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
            val nextChar = getChar(grid,
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

    private fun getGuardPosition(grid: Array<CharArray>): Pair<Int, Int> {
        for (i in grid.indices) {
            for (j in 0 until grid[0].size) {
                if (grid[i][j] == '^') {
                    return i to j
                }
            }
        }
        throw IllegalArgumentException("guard not found")
    }
}