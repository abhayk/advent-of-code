package aoc2015

import Solution

class Day5 : Solution {
    override fun part1(input: String): Any {
        return input.lines().filter { isNice(it) }.size
    }

    override fun part2(input: String): Any {
        return input.lines().filter { isNiceV2(it) }.size
    }

    private fun isNice(input: String): Boolean {
        val filterSet = setOf("ab", "cd", "pq", "xy")
        return vowelCount(input) >= 3 &&
                hasRepeatingDigraphs(input) &&
                filterSet.none { input.contains(it) }
    }

    private fun isNiceV2(input: String): Boolean {
        return hasTwoLetterPairWithNoOverlap(input) && hasSandwich(input)
    }

    private fun vowelCount(input: String): Int {
        val vowels = setOf('a', 'e', 'i', 'o', 'u')
        return input.filter { vowels.contains(it) }.length
    }

    private fun hasRepeatingDigraphs(input: String): Boolean {
        return input.indices.drop(1).any { input[it-1] == input[it] }
    }

    private fun hasTwoLetterPairWithNoOverlap(input: String): Boolean {
        val pairs = mutableSetOf<String>()
        var i=1
        while (i < input.length) {
            val pair = "${input[i-1]}${input[i]}"
            if (pairs.contains(pair)) {
                return true
            }
            pairs.add(pair)
            if (input[i-1] == input[i] && i<input.length - 1 && input[i] == input[i+1])
                i+=2
            else
                i++
        }
        return false
    }

    private fun hasSandwich(input: String): Boolean {
        return input.indices.drop(2).any { input[it-2] == input[it] }
    }
}