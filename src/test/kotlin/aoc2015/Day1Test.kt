package aoc2015

import BaseTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day1Test : BaseTest() {

    @Test
    override fun testPart1Sample() {
        val expectationsMap = mapOf(
            "(())" to 0,
            "()()" to 0,
            "(((" to 3,
            "(()(()(" to 3,
            "))(((((" to 3,
            "())" to -1,
            "))(" to -1,
            ")))" to -3,
            ")())())" to -3
        )
        expectationsMap.forEach { (input, expected) -> assertEquals(expected, solution.part1(input)) }
    }

    @Test
    override fun testPart2Sample() {
        val day1 = Day1()
        assertEquals(1, solution.part2(")"))
        assertEquals(5, solution.part2("()())"))
    }

    @Test
    override fun testPart1() {
        assertEquals(280, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2() {
        assertEquals(1797, solution.part2(getInputString()))
    }
}