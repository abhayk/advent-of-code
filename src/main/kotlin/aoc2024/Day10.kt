package aoc2024

import Solution
import common.Grid

class Day10: Solution {
    override fun part1(input: String): Any {
        val grid = Grid.parse<Int>(input)
        var sum = 0
        for (i in grid.rowIndices) {
            for (j in grid.colIndices) {
                if (grid.get(i, j) == 0) {
                    val endPoints = mutableListOf<Pair<Int, Int>>()
                    dfs(grid, i, j, mutableSetOf(), endPoints)
                    sum += endPoints.toSet().size
                }
            }
        }
        return sum
    }

    override fun part2(input: String): Any {
        val grid = Grid.parse<Int>(input)
        var sum = 0
        for (i in grid.rowIndices) {
            for (j in grid.colIndices) {
                if (grid.get(i, j) == 0) {
                    val paths = mutableListOf<Pair<Int, Int>>()
                    dfs(grid, i, j, mutableSetOf(), paths, skipVisited = true)
                    sum += paths.size
                }
            }
        }
        return sum
    }

    private fun dfs(
        grid: Grid<Int>,
        x: Int,
        y: Int,
        visited: MutableSet<Pair<Int, Int>>,
        endPoints: MutableList<Pair<Int, Int>>,
        skipVisited: Boolean = false
    ) {
        if (grid.get(x, y) == 9) {
            endPoints.add(x to y)
            return
        }
        val adjacent = listOf(0 to 1, -1 to 0, 0 to -1, 1 to 0)
        visited.add(x to y)
        for (index in adjacent) {
            val adjX = x + index.first
            val adjY = y + index.second
            if (!grid.isValid(adjX, adjY)) {
                continue
            }
            if (grid.get(adjX, adjY) == (grid.get(x, y) + 1) && (skipVisited || !visited.contains(adjX to adjY))) {
                dfs(grid, adjX, adjY, visited, endPoints, skipVisited)
            }
        }
    }
}