package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day9Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        val sample = "12345"
        val sample2 = "2333133121414131402"
        println(solution.part1(sample2))
    }

    @Test
    override fun testPart1() {
        println(solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        val sample = "2333133121414131402"
        println(solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        println(solution.part2(getInputString()))
    }
}