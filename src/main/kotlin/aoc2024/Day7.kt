package aoc2024

import Solution

class Day7: Solution {
    override fun part1(input: String): Any {
        return calculateTotalCalibration(input, listOf("+", "*"))
    }

    override fun part2(input: String): Any {
        return calculateTotalCalibration(input, listOf("+", "*", "|"))
    }

    private fun calculateTotalCalibration(input: String, operators: List<String>): Long {
        var totalCalibrationResult = 0L
        for (line in input.lines()) {
            val parts = line.split(": ")
            val target = parts[0].toLong()
            val numbers = parts[1].split(" ").map { it.toLong() }
            for (permutation in generatePermutations(operators, numbers.size - 1)) {
                var total = numbers[0]
                var counter = 1
                for (operator in permutation) {
                    when(operator) {
                        '+' -> total += numbers[counter]
                        '*' -> total *= numbers[counter]
                        '|' -> total = "$total${numbers[counter]}".toLong()
                    }
                    counter++
                }
                if (total == target) {
                    totalCalibrationResult += target
                    break
                }
            }
        }
        return totalCalibrationResult
    }

    private fun generatePermutations(elements: List<String>, length: Int): List<String> {
        val final = elements.toMutableList()
        for (i in 0 until length - 1) {
            val tmp = final.toList()
            for (item in tmp) {
                if (item.length != i+1) {
                    continue
                }
                for (element in elements) {
                    final.add(element + item)
                }
            }
        }
        return final.filter { it.length == length }
    }
}