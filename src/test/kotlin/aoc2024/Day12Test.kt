package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day12Test: BaseTest() {

    private val sample1 = """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent()

    private val sample2 = """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
        """.trimIndent()

    val sample3 = """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent()

    @Test
    override fun testPart1Sample() {
        assertEquals(140, solution.part1(sample1))
        assertEquals(772, solution.part1(sample2))
        assertEquals(1930, solution.part1(sample3))
    }

    @Test
    override fun testPart1() {
        assertEquals(1477762, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        super.testPart2Sample()

    }

    @Test
    override fun testPart2() {
        super.testPart2()
    }
}