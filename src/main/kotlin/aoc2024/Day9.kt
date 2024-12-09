package aoc2024

import Solution

class Day9: Solution {
    override fun part1(input: String): Any {
        val arr = parseInput(input)
        var left = 0
        var right = arr.size - 1
        while(left < right) {
            if (arr[left] == -1) {
                arr[left] = arr[right]
                left++
                right--
            } else {
                left++
            }
            while (arr[right] == -1) {
                right--
            }
        }
        return arr.toList()
            .subList(0, right + 1)
            .withIndex()
            .sumOf { it.value.toLong() * it.index }
    }

    override fun part2(input: String): Any {
        val arr = parseInput(input)
        var right = arr.size - 1
        while (right > 0) {
            // find size of current file
            var fileSize = 0
            var tmpRight = right
            while(tmpRight >= 0 && arr[tmpRight] == arr[right]) {
                tmpRight--
                fileSize++
            }
            // look for a gap where the file would fit
            var left = 0
            var gapFound = false
            var gapSize = 0
            while(left < right) {
                gapSize = 0
                if (arr[left] != -1) { left ++ }
                else {
                    while (left < arr.size && arr[left] == -1) {
                        gapSize++
                        left++
                    }
                    if (gapSize >= fileSize) {
                        gapFound = true
                        break
                    }
                }
            }
            // if it fits move the file
            if (gapFound) {
                var gapStart = left - gapSize
                for (i in 0 until fileSize) {
                    arr[gapStart] = arr[right]
                    arr[right] = -1
                    gapStart++
                    right--
                }
            } else {
                right -= fileSize
            }
        }
        return arr.toList()
            .withIndex()
            .filter { it.value != -1 }
            .sumOf { it.value.toLong() * it.index }
    }

    private fun parseInput(input: String): Array<Int> {
        val list = mutableListOf<Int>()
        var index = 0
        var fileIndex = 0
        for (id in input.chars().map { it - 49 }) {
            if ((index + 1) % 2 == 0) {
                for (j in 0..id) {
                    list.add(-1)
                }
            } else {
                for (j in 0..id) {
                    list.add(fileIndex)
                }
                fileIndex++
            }
            index++
        }
        return list.toTypedArray()
    }
}