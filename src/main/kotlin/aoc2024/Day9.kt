package aoc2024

import Solution

class Day9: Solution {
    override fun part1(input: String): Any {
        val list = mutableListOf<Int>()
        var index = 0
        var fileIndex = 0
        for (c in input.toCharArray()) {
            val id = c.code - 49
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
        //println(list)
        val arr = list.toTypedArray()
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
        val final = arr.toList().subList(0, right + 1)
        //println(final)
        var sum = 0L
        var tmp = 0L
        for (item in final) {
            sum += item*tmp
            tmp++
        }
        return sum
    }

    override fun part2(input: String): Any {
        val list = mutableListOf<Int>()
        var index = 0
        var fileIndex = 0
        for (c in input.toCharArray()) {
            val id = c.code - 49
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
        //println(list)
        val arr = list.toTypedArray()
        var left = 0
        var right = arr.size - 1
        while (right > 0) {
            var fileSize = 0
            var tmpRight = right
            while(tmpRight >= 0 && arr[tmpRight] == arr[right]) {
                tmpRight--
                fileSize++
            }
            left = 0
            var gapFound = false
            var gap = 0
            while(left < right) {
                gap = 0
                if (arr[left] != -1) { left ++ }
                else {
                    while (arr[left] == -1) {
                        gap++
                        left++
                        if (gap == fileSize) {
                            gapFound = true
                            break
                        }
                    }
                    if (gapFound) {
                        break
                    }
                }
            }
            if (gapFound) {
                var gapStart = left - fileSize
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
        val final = arr.toList()
        //println(final)
        var sum = 0L
        var tmp = 0L
        for (item in final) {
            if (item == -1) {
                tmp++
                continue
            }
            sum += item*tmp
            tmp++
        }
        return sum
    }
}