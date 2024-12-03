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
        val regex = """(?<do>do\(\))|(?<dont>don't\(\))|mul\((\d+),(\d+)\)""".toRegex()
        var enabled = true
        var sum = 0L
        regex.findAll(input).forEach {
            when {
                it.groups["do"] != null -> enabled = true
                it.groups["dont"] != null -> enabled = false
                else -> {
                    if (enabled) {
                        val(_, _, left, right) = it.destructured
                        sum += left.toLong() * right.toLong()
                    }
                }
            }
        }
        return sum
    }
}