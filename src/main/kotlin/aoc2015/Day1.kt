package aoc2015

import Solution

class Day1 : Solution {
    override fun part1(input: String): Any {
        return input.map { step(it) }.sum()
    }

    override fun part2(input: String): Any {
        var currentFloor = 0
        return input.indexOfFirst {
            currentFloor += step(it)
            currentFloor == -1
        } + 1
    }

    private fun step(input: Char): Int =
        when(input) {
            '(' -> 1
            ')' -> -1
            else -> 0
        }
}