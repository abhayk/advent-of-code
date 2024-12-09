package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day9Test: BaseTest() {

    private val sample = "2333133121414131402"

    @Test
    override fun testPart1Sample() {
        assertEquals(1928L, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(6398608069280L, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(2858L, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(6427437134372L, solution.part2(getInputString()))
    }
}