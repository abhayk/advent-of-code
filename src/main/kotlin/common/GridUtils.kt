package common

object GridUtils {
    fun getChar(grid: Array<CharArray>, x: Int, y: Int, default: Char = '.'): Char {
        val rowCount = grid.size
        val colCount = grid[0].size
        if (x < 0 || x >= rowCount || y < 0 || y >= colCount) {
            return default
        }
        return grid[x][y]
    }

    fun toCharGrid(input: String): Array<CharArray> {
        return input.lines().map { it.toCharArray() }.toTypedArray()
    }
}

