package aoc2024

import Solution
import kotlin.math.abs

class Day1: Solution {
    override fun part1(input: String): Any {
        val (list1, list2) = parseInput(input)
        return list1.zip(list2)
            .sumOf { abs(it.first - it.second) }
    }

    override fun part2(input: String): Any {
        val (list1, list2) = parseInput(input)
        val frequencyMap = list2.groupingBy { it }.eachCount()
        return list1.sumOf { it * frequencyMap.getOrDefault(it, 0) }
    }

    private fun parseInput(input: String): Pair<List<Int>, List<Int>> {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        input.lines().forEach {
            val values = it.split("   ")
            list1.add(values[0].toInt())
            list2.add(values[1].toInt())
        }
        return Pair(list1.sorted(), list2.sorted())
    }
}