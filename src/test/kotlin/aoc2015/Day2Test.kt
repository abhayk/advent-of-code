package aoc2015

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2Test : BaseTest() {
    @Test
    override fun testPart1Sample() {
        assertEquals(58L, solution.part1("2x3x4"))
        assertEquals(43L, solution.part1("1x1x10"))
    }

    @Test
    override fun testPart1() {
        assertEquals(1598415L, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2() {
        assertEquals(3812909L, solution.part2(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(34L, solution.part2("2x3x4"))
        assertEquals(14L, solution.part2("1x1x10"))
    }
}