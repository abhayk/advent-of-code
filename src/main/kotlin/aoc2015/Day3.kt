package aoc2015

import Solution

class Day3 : Solution {
    override fun part1(input: String): Any {
        return housesWithPresents(input).size
    }

    override fun part2(input: String): Any {
        return input.withIndex().partition { it.index % 2 == 0 }
            .toList()
            .map { path -> housesWithPresents(path.map { it.value }.toString()) }
            .flatten()
            .toSet()
            .size
    }

    private fun housesWithPresents(input: String): Set<Pair<Int, Int>> {
        var current = Pair(0, 0)
        val houses = mutableSetOf(current)
        for (c in input) {
            current = getNextPoint(current, c)
            houses.add(current)
        }
        return houses
    }

    private fun getNextPoint(currentPoint: Pair<Int, Int>, direction: Char): Pair<Int, Int> {
        val (x, y) = currentPoint
        return when(direction) {
            '^' -> x to y + 1
            'v' -> x to y - 1
            '>' -> x + 1 to y
            '<' -> x - 1 to y
            else -> x to y
        }
    }
}