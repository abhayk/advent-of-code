package aoc2024

import Solution
import common.Grid

class Day15: Solution {
    override fun part1(input: String): Any {
        val parts = input.split("\n\n")
        val grid = Grid.parse<Char>(parts[0])
        val instructions = parts[1].replace("\n", "")
        var current = grid.find('@')!!
        val directions = mapOf(
            '^' to (-1 to 0),
            'v' to (1 to 0),
            '>' to (0 to 1),
            '<' to (0 to -1),
        )
        for (ins in instructions) {
            val step = directions[ins]!!
            val next = current.first + step.first to current.second + step.second
            when(grid.get(next)) {
                '.' -> {
                    grid.set(current, '.')
                    grid.set(next, '@')
                    current = next
                }
                'O' -> {
                    var nextFree = next
                    while(true) {
                        nextFree = nextFree.first + step.first to nextFree.second + step.second
                        val entity = grid.get(nextFree)
                        if (entity == '.') {
                            grid.set(nextFree, 'O')
                            grid.set(next, '@')
                            grid.set(current, '.')
                            current = next
                            break
                        } else if(entity == '#') {
                            break
                        }
                    }
                }
                '#' -> {}
            }
        }
        var sum = 0
        for (i in grid.rowIndices) {
            for (j in grid.colIndices) {
                if (grid.get(i, j) == 'O') {
                    sum += (100*i + j)
                }
            }
        }
        return sum
    }

    override fun part2(input: String): Any {
        val parts = input.split("\n\n")
        val map = parts[0]
        map.replace("#", "##")
        map.replace("O", "[]")
        map.replace(".", "..")
        map.replace("@", "@.")
        val grid = Grid.parse<Char>(map)
        val instructions = parts[1].replace("\n", "")
        var current = grid.find('@')!!
        val directions = mapOf(
            '^' to (-1 to 0),
            'v' to (1 to 0),
            '>' to (0 to 1),
            '<' to (0 to -1),
        )
        for (ins in instructions) {
            val step = directions[ins]!!
            val next = current.first + step.first to current.second + step.second
            when(grid.get(next)) {
                '.' -> {
                    grid.set(current, '.')
                    grid.set(next, '@')
                    current = next
                }
                ']','[' -> {

                }
                '#' -> {}
            }
        }
        return 0
    }
}