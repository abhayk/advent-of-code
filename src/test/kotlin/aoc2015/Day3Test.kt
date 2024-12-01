package aoc2015

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day3Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        assertEquals(2, solution.part1(">"))
        assertEquals(4, solution.part1("^>v<"))
        assertEquals(2, solution.part1("^v^v^v^v^v"))
    }

    @Test
    override fun testPart1() {
        assertEquals(2565, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(3, solution.part2("^v"))
        assertEquals(3, solution.part2("^>v<"))
        assertEquals(11, solution.part2("^v^v^v^v^v"))
    }

    @Test
    override fun testPart2() {
        assertEquals(2639, solution.part2(getInputString()))
    }
}