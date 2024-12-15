package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day14Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        super.testPart1Sample()
    }

    @Test
    override fun testPart1() {
        assertEquals(233709840, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        super.testPart2Sample()
    }

    @Test
    override fun testPart2() {
        assertEquals(6620, solution.part2(getInputString()))
    }
}