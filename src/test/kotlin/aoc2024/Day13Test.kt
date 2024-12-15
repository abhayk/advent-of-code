package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day13Test: BaseTest() {

    val sample = """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400

            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176

            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450

            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent()

    @Test
    override fun testPart1Sample() {
        assertEquals(480L, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(33481L, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(875318608908L, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(92572057880885L, solution.part2(getInputString()))
    }
}