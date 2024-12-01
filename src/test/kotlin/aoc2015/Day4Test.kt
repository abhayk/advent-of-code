package aoc2015

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test : BaseTest() {
    @Test
    override fun testPart1Sample() {
        assertEquals(609043, solution.part1("abcdef"))
        assertEquals(1048970, solution.part1("pqrstuv"))
    }
}