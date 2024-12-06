package aoc2015

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day8Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        assertEquals(12, solution.part1("""
            ""
            "abc"
            "aaa\"aaa"
            "\x27"
        """.trimIndent()))
    }

    @Test
    override fun testPart1() {
        println(solution.part1(getInputString()))
    }
}