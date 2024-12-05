package aoc2024

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5Test: BaseTest() {
    private val sample = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13

            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
        """.trimIndent()

    @Test
    override fun testPart1Sample() {
        assertEquals(143, solution.part1(sample))
    }

    @Test
    override fun testPart1() {
        assertEquals(4814, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(123, solution.part2(sample))
    }

    @Test
    override fun testPart2() {
        assertEquals(5448, solution.part2(getInputString()))
    }
}