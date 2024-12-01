package aoc2015

import Solution

class Day7 : Solution {
    private val binaryExpression = """(\w+) ([A-Z]+) (\w+)""".toRegex()
    private val unaryExpression = """([A-Z]+) (\w+)""".toRegex()
    private val constantExpression = """\d+""".toRegex()

    override fun part1(input: String): Any {
        val circuit = parseCircuit(input)
        return resolveSignal(circuit, "a", mutableMapOf())
    }

    override fun part2(input: String): Any {
        val signal = part1(input)
        val updatedCircuit = buildMap {
            putAll(parseCircuit(input))
            put("b", signal.toString())
        }
        return resolveSignal(updatedCircuit, "a", mutableMapOf())
    }

    private fun parseCircuit(input: String): Map<String, String> {
        return input.lines()
            .map { it.split(" -> ") }
            .associate { it[1] to it[0] }
    }

    private fun resolveSignal(
        circuit: Map<String, String>,
        wire: String,
        resolvedSignals: MutableMap<String, Int>): Int {
        if (resolvedSignals.containsKey(wire)) {
            return resolvedSignals[wire]!!
        }
        if (constantExpression.matches(wire)) {
            return wire.toInt()
        }
        val input = circuit[wire]!!
        val resolvedValue = if (binaryExpression.matches(input)) {
            val (leftString, operator, rightString) = binaryExpression.matchEntire(input)!!.destructured
            val left = resolveSignal(circuit, leftString, resolvedSignals)
            val right = resolveSignal(circuit, rightString, resolvedSignals)
            when(operator) {
                "AND" -> left and right
                "OR" -> left or right
                "LSHIFT" -> left.shl(right)
                "RSHIFT" -> left.shr(right)
                else -> throw IllegalArgumentException("Unknown binary operator")
            }
        } else if(unaryExpression.matches(input)) {
            val(operator, rightString) = unaryExpression.matchEntire(input)!!.destructured
            val right = resolveSignal(circuit, rightString, resolvedSignals)
            when(operator) {
                "NOT" -> right.inv()
                else -> throw IllegalArgumentException("Unknown unary operator")
            }
        } else {
            resolveSignal(circuit, input, resolvedSignals)
        }
        resolvedSignals[wire] = resolvedValue
        return resolvedValue
    }
}