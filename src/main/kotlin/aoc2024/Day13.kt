package aoc2024

import Solution
import common.solveLinearEquation

class Day13: Solution {
    override fun part1(input: String): Any {
        return input.split("\n\n")
            .map { parseMachine(it) }
            .sumOf { tokensForPrize(it) ?: 0 }
    }

    override fun part2(input: String): Any {
        return input.split("\n\n")
            .map { parseMachine(it, part2 = true) }
            .sumOf { tokensForPrize(it) ?: 0 }
    }

    private fun tokensForPrize(machine: Machine): Long? {
        val solution = solveLinearEquation(
            Triple(machine.ax, machine.bx, machine.px),
            Triple(machine.ay, machine.by, machine.py))

        if (solution == null) {
            return null
        }
        if (hasDecimalPart(solution.first) || hasDecimalPart(solution.second)) {
            return null
        }
        return (solution.first * 3 + solution.second).toLong()
    }

    private fun hasDecimalPart(input: Double): Boolean {
        return !input.rem(1).equals(0.0)
    }

    private fun parseMachine(input: String, part2: Boolean = false): Machine {
        val lines = input.split("\n")
        val buttonRegex = """Button .: X\+(\d+), Y\+(\d+)""".toRegex()
        val prizeRegex = """Prize: X=(\d+), Y=(\d+)""".toRegex()
        val (ax, ay) = buttonRegex.find(lines[0])!!.destructured
        val (bx, by) = buttonRegex.find(lines[1])!!.destructured
        val (px, py) = prizeRegex.find(lines[2])!!.destructured
        return Machine(
            ax.toDouble(),
            ay.toDouble(),
            bx.toDouble(),
            by.toDouble(),
            px.toDouble() + (if (part2) 10000000000000 else 0),
            py.toDouble() + (if (part2) 10000000000000 else 0))
    }

    data class Machine(
        val ax: Double,
        val ay: Double,
        val bx: Double,
        val by: Double,
        val px: Double,
        val py: Double
    )
}