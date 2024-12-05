package aoc2024

import Solution

class Day5: Solution {
    override fun part1(input: String): Any {
        val parts = input.split("\n\n")
        val rules = parseRules(parts[0])
        return parts[1].lines()
            .map { it.split(",").map { item -> item.toInt() } }
            .sumOf {
                val sorted = sort(it, rules)
                if (sorted == it) {
                    it[it.size / 2]
                } else 0
            }
    }

    override fun part2(input: String): Any {
        val parts = input.split("\n\n")
        val rules = parseRules(parts[0])
        return parts[1].lines()
            .map { it.split(",").map { item -> item.toInt() } }
            .sumOf {
                val sorted = sort(it, rules)
                if (sorted != it) {
                    sorted[sorted.size / 2]
                } else 0
            }
    }

    private fun parseRules(input: String): List<Pair<Int, Int>> {
        return input.lines().map {
            val split = it.split("|")
            split[0].toInt() to split[1].toInt()
        }
    }

    private fun sort(input: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        val map = input.withIndex().associateBy({it.value}, {it.index})
        return input.toList().sortedWith{o1, o2 ->
            rules.find { (it.first == o1 && it.second == o2) || (it.first == o2 && it.second == o1) }?.let {
                if (map[it.first]!! > map[it.second]!!) -1 else 1
            } ?: 0
        }
    }
}