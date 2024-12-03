package aoc2024

import Solution
import java.util.TreeMap

class Day3: Solution {
    private val mulRegex = """mul\((\d+),(\d+)\)""".toRegex()

    override fun part1(input: String): Any {
        var total = 0L
        for (matchResult in mulRegex.findAll(input)) {
            val (left, right) = matchResult.destructured
            total += left.toLong() * right.toLong()
        }
        return total
    }

    override fun part2(input: String): Any {
        val doRegex = """do\(\)""".toRegex()
        val dontRegex = """don't\(\)""".toRegex()
        val treeMap = TreeMap<Int, String>()
        for(result in doRegex.findAll(input)) {
            treeMap[result.range.first] = "DO"
        }
        for(result in dontRegex.findAll(input)) {
            treeMap[result.range.first] = "DONT"
        }
        return mulRegex.findAll(input)
            .filter { (treeMap.floorEntry(it.range.first)?.value ?: "DO") == "DO" }
            .sumOf {
                val (left, right) = it.destructured
                left.toLong() * right.toLong()
            }
    }
}