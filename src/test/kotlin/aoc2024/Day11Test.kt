package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day11Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        assertEquals(55312L, solution.part1("125 17"))
    }

    @Test
    override fun testPart1() {
        assertEquals(194782L, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        super.testPart2Sample()
    }

    @Test
    override fun testPart2() {
        assertEquals(233007586663131L, solution.part2(getInputString()))
    }
}