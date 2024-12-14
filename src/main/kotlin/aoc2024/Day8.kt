package aoc2024

import Solution
import common.Grid

class Day8: Solution {
    override fun part1(input: String): Any {
        val grid = Grid.parse<Char>(input)
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        locateAntennas(grid)
            .filter { it.value.size > 1 }
            .forEach {
                val points = it.value
                for (i in points.indices) {
                    for ( j in i+1 until points.size) {
                        val p1 = points[i]
                        val p2 = points[j]
                        val rowDistance = p1.first - p2.first
                        val colDistance = p1.second - p2.second
                        val antiNode1 = p1.first + rowDistance to p1.second + colDistance
                        val antiNode2 = p2.first - rowDistance to p2.second - colDistance
                        if (grid.isValid(antiNode1)) {
                            antiNodes.add(antiNode1)
                        }
                        if (grid.isValid(antiNode2)) {
                            antiNodes.add(antiNode2)
                        }
                    }
                }
            }
        return antiNodes.size
    }

    override fun part2(input: String): Any {
        val grid = Grid.parse<Char>(input)
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        val antennas = locateAntennas(grid)
        antennas.filter { it.value.size > 1 }
            .forEach {
                val points = it.value
                for (i in points.indices) {
                    for ( j in i+1 until points.size) {
                        val p1 = points[i]
                        val p2 = points[j]
                        val rowDistance = p1.first - p2.first
                        val colDistance = p1.second - p2.second
                        var leftPending = true
                        var rightPending = true
                        var counter = 1
                        while(leftPending || rightPending) {
                            val antiNode1 = p1.first + (rowDistance * counter) to p1.second + (colDistance * counter)
                            val antiNode2 = p2.first - (rowDistance * counter) to p2.second - (colDistance * counter)
                            if (grid.isValid(antiNode1)) {
                                antiNodes.add(antiNode1)
                            } else { leftPending = false }
                            if (grid.isValid(antiNode2)) {
                                antiNodes.add(antiNode2)
                            } else { rightPending = false }
                            counter++
                        }
                    }
                }
            }
        antiNodes.addAll( antennas.values.flatten() )
        return antiNodes.size
    }

    private fun locateAntennas(grid: Grid<Char>): Map<Char, List<Pair<Int, Int>>> {
        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        for (i in grid.rowIndices) {
            for (j in grid.colIndices) {
                if (grid.get(i, j) == '.') {
                    continue
                }
                antennas.computeIfAbsent(grid.get(i, j)) { mutableListOf() }.add(i to j)
            }
        }
        return antennas
    }
}