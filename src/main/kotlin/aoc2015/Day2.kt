package aoc2015

import Solution

class Day2 : Solution {
    override fun part1(input: String): Any {
        return input.lines().sumOf { calculateWrappingPaperArea(it) }
    }

    override fun part2(input: String): Any {
        return input.lines().sumOf { calculateRibbonLength(it) }
    }

    private fun calculateWrappingPaperArea(input: String): Long {
        val (s1,s2,s3) = parse(input)
        return (2*s1*s2 + 2*s2*s3 + 2*s3*s1) + (s1*s2)
    }

    private fun calculateRibbonLength(input: String): Long {
        val (s1, s2, s3) = parse(input)
        return (s1*2 + s2*2) + (s1*s2*s3)
    }

    private fun parse(input: String) = input.split("x").map { it.toLong() }.sorted()
}