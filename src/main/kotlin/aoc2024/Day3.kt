package aoc2024

import Solution
import java.util.TreeMap

class Day3: Solution {
    private val mulRegex = """mul\((\d+),(\d+)\)""".toRegex()

    override fun part1(input: String): Any {
        return mulRegex.findAll(input).sumOf {
            val (left, right) = it.destructured
            left.toLong() * right.toLong()
        }
    }

    override fun part2(input: String): Any {
        val doRegex = """do\(\)""".toRegex()
        val dontRegex = """don't\(\)""".toRegex()
        val treeMap = TreeMap<Int, String>()
        doRegex.findAll(input).forEach { treeMap[it.range.first] = "DO" }
        dontRegex.findAll(input).forEach { treeMap[it.range.first] = "DONT" }
        return mulRegex.findAll(input)
            .filter { (treeMap.floorEntry(it.range.first)?.value ?: "DO") == "DO" }
            .sumOf {
                val (left, right) = it.destructured
                left.toLong() * right.toLong()
            }
    }
}