package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day10Test: BaseTest() {

    private val sample = """
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732
        """.trimIndent()

    @Test
    override fun testPart1Sample() {
        assertEquals(36, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(794, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(81, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(1706, solution.part2(getInputString()))
    }
}