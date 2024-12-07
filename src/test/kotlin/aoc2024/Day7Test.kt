package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Test: BaseTest() {

    private val sample = """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
        """.trimIndent()

    @Test
    override fun testPart1Sample() {
        assertEquals(3749L, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(12553187650171L, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(11387L, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(96779702119491L, solution.part2(getInputString()))
    }
}