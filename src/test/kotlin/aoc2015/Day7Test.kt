package aoc2015

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Test: BaseTest() {
    @Test
    override fun testPart1() {
        assertEquals(46065, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2() {
        assertEquals(14134, solution.part2(getInputString()))
    }
}