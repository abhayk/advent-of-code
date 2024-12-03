package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day3Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        val sample = """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"""
        assertEquals(161L, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(178538786L, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        val sample = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""
        assertEquals(48L, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(102467299L, solution.part2(getInputString()))
    }
}