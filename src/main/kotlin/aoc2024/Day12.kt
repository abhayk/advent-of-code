package aoc2024

import Solution
import common.Grid

class Day12: Solution {
    override fun part1(input: String): Any {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val grid = Grid.parse<Char>(input)
        val stats = mutableListOf<Pair<Int, Int>>()
        for (i in grid.rowIndices) {
            for (j in grid.colIndices) {
                val current = i to j
                if (visited.contains(current)) {
                    continue
                }
                val (params, currentVisited) = getAreaAndPerimeter(grid, i to j)
                stats.add(params)
                visited.addAll(currentVisited)
            }
        }
        return stats.sumOf { it.first * it.second }
    }

//    private fun countTurns(input: Set<Pair<Int, Int>>): Int {
//        val sorted = input.sortedWith{ o1, o2 ->
//            if (o1.first == o2.first) {
//                o1.second.compareTo(o2.second)
//            } else {
//                o1.first.compareTo(o2.first)
//            }
//        }
//        var turns = 0
//        for (i in sorted.indices - 1) {
//        }
//    }

    private fun getAreaAndPerimeter(grid: Grid<Char>, start: Pair<Int, Int>): Pair<Pair<Int, Int>, Set<Pair<Int, Int>>> {
        var area = 0
        var perimeter = 0
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = ArrayDeque<Pair<Int, Int>>()
        visited.add(start)
        queue.add(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            var matchingNeighbours = 4
            for (index in grid.getNSEWAdjacent(current)) {
                if (grid.get(current) == grid.get(index)) {
                    matchingNeighbours--
                    if (!visited.contains(index)) {
                        area++
                        queue.add(index)
                        visited.add(index)
                    }
                }
            }
            perimeter += matchingNeighbours
        }
        return ((area + 1 to perimeter) to visited)
    }

    override fun part2(input: String): Any {
        TODO()
    }
}