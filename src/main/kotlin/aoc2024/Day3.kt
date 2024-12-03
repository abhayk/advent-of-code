package aoc2024

import Solution

class Day3: Solution {

    override fun part1(input: String): Any {
        val mulRegex = """mul\((\d+),(\d+)\)""".toRegex()
        return mulRegex.findAll(input).sumOf {
            val (left, right) = it.destructured
            left.toLong() * right.toLong()
        }
    }

    override fun part2(input: String): Any {
        val regex = """(do\(\))|(don't\(\))|mul\((\d+),(\d+)\)""".toRegex()
        var enabled = true
        var sum = 0L
        regex.findAll(input).forEach {
            when {
                it.groupValues[1].isNotEmpty() -> enabled = true
                it.groupValues[2].isNotEmpty() -> enabled = false
                else -> {
                    if (enabled) {
                        val (left, right) = it.groupValues.drop(3)
                        sum += left.toLong() * right.toLong()
                    }
                }
            }
        }
        return sum
    }
}