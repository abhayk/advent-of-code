package aoc2024

import Solution
import common.GridUtils.isValidPointInGrid
import common.GridUtils.toCharGrid
import kotlin.math.abs

class Day8: Solution {
    override fun part1(input: String): Any {
        val grid = toCharGrid(input)
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        val antennas = locateAntennas(grid)
        for (antenna in antennas) {
            if (antenna.value.size == 1) {
                continue
            }
            val points = antenna.value
            for (i in points.indices) {
                for ( j in i+1 until points.size) {
                    val p1 = points[i]
                    val p2 = points[j]
                    val rowDistance = abs(p1.first - p2.first)
                    val colDistance = abs(p1.second - p2.second)
                    val nodes = mutableListOf<Pair<Int, Int>>()
                    if (p1.second < p2.second) {
                        nodes.add(p1.first - rowDistance to p1.second - colDistance)
                        nodes.add(p2.first + rowDistance to p2.second + colDistance)
                    } else {
                        nodes.add(p1.first - rowDistance to p1.second + colDistance,)
                        nodes.add(p2.first + rowDistance to p2.second - colDistance)
                    }
                    antiNodes.addAll(nodes.filter { isValidPointInGrid(grid, it) })
                }
            }
        }
        return antiNodes.size
    }

    override fun part2(input: String): Any {
        val grid = toCharGrid(input)
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        val antennas = locateAntennas(grid)
        for (antenna in antennas) {
            if (antenna.value.size == 1) {
                continue
            }
            val points = antenna.value
            for (i in points.indices) {
                for ( j in i+1 until points.size) {
                    val p1 = points[i]
                    val p2 = points[j]
                    val rowDistance = abs(p1.first - p2.first)
                    val colDistance = abs(p1.second - p2.second)
                    var leftPending = true
                    var rightPending = true
                    var counter = 1
                    while(leftPending || rightPending) {
                        val (antiNode1, antiNode2) = if (p1.second < p2.second) {
                            (p1.first - (rowDistance * counter) to p1.second - (colDistance * counter)) to
                                    (p2.first + (rowDistance * counter) to p2.second + (colDistance * counter))
                        } else {
                            (p1.first - (rowDistance * counter) to p1.second + (colDistance * counter)) to
                                    (p2.first + (rowDistance * counter) to p2.second - (colDistance * counter))
                        }
                        if (isValidPointInGrid(grid, antiNode1)) {
                            antiNodes.add(antiNode1)
                        } else { leftPending = false }
                        if (isValidPointInGrid(grid, antiNode2)) {
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

    private fun locateAntennas(grid: Array<CharArray>): Map<Char, List<Pair<Int, Int>>> {
        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '.') {
                    continue
                }
                antennas.computeIfAbsent(grid[i][j]) { mutableListOf() }.add(i to j)
            }
        }
        return antennas
    }
}