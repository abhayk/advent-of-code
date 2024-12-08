package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day8Test: BaseTest() {

    private val sample = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()

    @Test
    override fun testPart1Sample() {
        assertEquals(14, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(341, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        val sample2 = """
            T.........
            ...T......
            .T........
            ..........
            ..........
            ..........
            ..........
            ..........
            ..........
            ..........
        """.trimIndent()
        assertEquals(9, solution.part2(sample2))
        assertEquals(34, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(1134, solution.part2(getInputString()))
    }
}