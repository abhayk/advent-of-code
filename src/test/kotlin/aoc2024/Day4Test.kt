package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        val sample = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
        assertEquals(18, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(2603, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        val sample = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
        assertEquals(9, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(1965, solution.part2(getInputString()))
    }
}