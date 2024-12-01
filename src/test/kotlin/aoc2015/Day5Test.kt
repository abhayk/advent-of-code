package aoc2015

import BaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5Test: BaseTest() {
    @Test
    override fun testPart1Sample() {
        assertEquals(1, solution.part1("ugknbfddgicrmopn"))
        assertEquals(1, solution.part1("aaa"))
        assertEquals(0, solution.part1("jchzalrnumimnmhp"))
        assertEquals(0, solution.part1("haegwjzuvuyypxyu"))
        assertEquals(0, solution.part1("dvszwmarrgswjxmb"))
    }

    @Test
    override fun testPart1() {
        assertEquals(258, solution.part1(getInputString()))
    }

    @Test
    override fun testPart2Sample() {
        assertEquals(1, solution.part2("qjhvhtzxzqqjkmpb"))
        assertEquals(1, solution.part2("xxyxx"))
        assertEquals(0, solution.part2("uurcxstgmygtbstg"))
        assertEquals(0, solution.part2("ieodomkazucvgmuy"))
    }

    @Test
    override fun testPart2() {
        assertEquals(53, solution.part2(getInputString()))
    }
}