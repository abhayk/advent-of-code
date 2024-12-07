package aoc2024

import Solution
import java.lang.IllegalArgumentException

class Day7: Solution {
    override fun part1(input: String): Any {
        return calculateTotalCalibration(input, "+*")
    }

    override fun part2(input: String): Any {
        return calculateTotalCalibration(input, "+*|")
    }

    private fun calculateTotalCalibration(input: String, operators: String): Long {
        return input.lines().map {
            val parts = it.split(": ")
            val target = parts[0].toLong()
            val numbers = parts[1].split(" ").map { str -> str.toLong() }
            target to numbers
        }.filter { (target, numbers) ->
            isCalibrated(numbers, target, operators, 1, numbers[0])
        }.sumOf { (target, _) -> target }
    }

    private fun isCalibrated(numbers: List<Long>, target: Long, operators: String, counter: Int, currentTotal: Long): Boolean {
        if (counter == numbers.size) {
            return currentTotal == target
        }
        if (currentTotal > target) {
            return false
        }
        for (operator in operators) {
            val result = when(operator) {
                '+' -> isCalibrated(numbers, target, operators, counter + 1, currentTotal + numbers[counter])
                '*' -> isCalibrated(numbers, target, operators, counter + 1, currentTotal * numbers[counter])
                '|' -> isCalibrated(numbers, target, operators, counter + 1, "$currentTotal${numbers[counter]}".toLong())
                else -> throw IllegalArgumentException("unknown operator")
            }
            if (result) {
                return true
            }
        }
        return false
    }
}