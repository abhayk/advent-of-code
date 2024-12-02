package aoc2024

import BaseTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day2Test: BaseTest() {

    private val sampleInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()

    @Test
    override fun testPart1() {
        assertEquals(257, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2() {
        assertEquals(328, solution.part2(getInputString()))
    }

    @Test
    override fun testPart1Sample() {
        assertEquals(2, solution.part1(sampleInput))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(4, solution.part2(sampleInput))
    }
}