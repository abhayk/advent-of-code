package aoc2024

import Solution

class Day11: Solution {
    override fun part1(input: String): Any {
        return blink(25, input)
    }

    override fun part2(input: String): Any {
        return blink(75, input)
    }

    private fun blink(times: Int, input: String): Long {
        val counts = input.split(" ")
            .map { it.toLong() }
            .associateWith { 1L }
            .toMutableMap()

        repeat(times) {
            val updatedCounts = mutableMapOf<Long, Long>()
            for ((stone, count) in counts) {
                when {
                    stone == 0L -> updatedCounts[1] = updatedCounts.getOrDefault(1, 0) + count
                    stone.toString().length % 2 == 0 -> {
                        val s = stone.toString()
                        val left = s.substring(0, s.length / 2).toLong()
                        val right = s.substring(s.length / 2).toLong()
                        updatedCounts[left] = updatedCounts.getOrDefault(left, 0) + count
                        updatedCounts[right] = updatedCounts.getOrDefault(right, 0) + count
                    }
                    else -> {
                        val newStone = stone * 2024
                        updatedCounts[newStone] = updatedCounts.getOrDefault(newStone, 0) + count
                    }
                }
            }
            counts.clear()
            counts.putAll(updatedCounts)
        }
        return counts.values.sum()
    }
}