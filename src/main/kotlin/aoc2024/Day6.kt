package aoc2024

import Solution
import common.Grid

class Day6: Solution {
    override fun part1(input: String): Any {
        val grid = Grid.parse(input, '$')
        val start = grid.find('^')
        return simulate(grid, start!!)?.size ?: -1
    }

    override fun part2(input: String): Any {
        val grid = Grid.parse(input, '$')
        val start = grid.find('^')
        var count = 0
        val steps = simulate(grid, start!!)!!
        for (step in steps) {
            val i = step.first
            val j = step.second
            val value = grid.get(i, j)
            if (value == '#' || value == '^') {
                continue
            }
            val tmp = Grid.clone(grid)
            tmp.set(i, j, 'O')
            val result = simulate(tmp, start)
            if (result == null) {
                count++
            }
        }
        return count
    }


    private fun simulate(grid: Grid<Char>, start: Pair<Int, Int>): Set<Pair<Int, Int>>? {
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
            val nextChar = grid.get(current.first + currentDirection.first,
                current.second + currentDirection.second)
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
}