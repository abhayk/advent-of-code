package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day6Test: BaseTest() {
    private val sample = """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
        """.trimIndent()

    @Test
    override fun testPart1Sample() {
        assertEquals(41, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(5177, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(6, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(1686, solution.part2(getInputString()))
    }
}