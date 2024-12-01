package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test: BaseTest() {
    private val sampleInput = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent()

    @Test
    override fun testPart1Sample() {
        Assertions.assertEquals(11, solution.part1(sampleInput))
    }

    @Test
    override fun testPart1() {
        Assertions.assertEquals(2057374, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        Assertions.assertEquals(31, solution.part2(sampleInput))
    }

    @Test
    override fun testPart2() {
        Assertions.assertEquals(23177084, solution.part2(getInputString()))
    }
}