package common

class Grid<T>(
    val grid: Array<Array<T>>,
    val defaultValue: T
) {
    val rowCount: Int by lazy { grid.size }
    val colCount: Int by lazy { grid[0].size }
    val rowIndices: IntRange by lazy { 0 until rowCount }
    val colIndices: IntRange by lazy { 0 until colCount }

    fun get(x: Int, y: Int): T {
        if (!isValid(x, y)) {
            return defaultValue
        }
        return grid[x][y]
    }

    fun get(point: Pair<Int, Int>): T {
        return get(point.first, point.second)
    }

    fun set(x: Int, y: Int, value: T) {
        grid[x][y] = value
    }

    fun isValid(x: Int, y: Int): Boolean {
        return x in 0..<rowCount && y in 0..<colCount
    }

    fun isValid(point: Pair<Int, Int>): Boolean {
        return isValid(point.first, point.second)
    }

    fun find(needle: T): Pair<Int, Int>? {
        for (i in rowIndices) {
            for (j in colIndices) {
                if (grid[i][j] == needle) {
                    return i to j
                }
            }
        }
        return null
    }

    fun getNSEWAdjacent(x: Int, y: Int): List<Pair<Int, Int>> {
        return ADJACENT_4.map { it.first + x to it.second + y }
            .filter { isValid(it) }
    }

    fun getNSEWAdjacent(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        return getNSEWAdjacent(point.first, point.second)
    }

    companion object {

        val ADJACENT_4 = listOf(0 to 1, -1 to 0, 0 to -1, 1 to 0)

        val unsupportedTypeError = NotImplementedError("Grid type not supported yet")

        inline fun <reified T> init(rowCount: Int, colCount: Int, defaultValue: T = getDefaultValue()): Grid<T> {
            val gridData = Array(rowCount) { Array(colCount) {defaultValue} }
            return Grid(gridData, defaultValue)
        }

        inline fun <reified T> parse(input: String, defaultValue: T = getDefaultValue()): Grid<T> {
            val gridData = input.lines().map { row ->
                row.map { char ->
                    when (T::class) {
                        Char::class -> char
                        Int::class -> (char.code - 48)
                        else -> throw unsupportedTypeError
                    } as T
                }.toTypedArray()
            }.toTypedArray()
            return Grid(gridData, defaultValue)
        }

        inline fun <reified T> getDefaultValue(): T {
            val defaultValue = when(T::class) {
                Char::class -> '.'
                Int::class -> -1
                else -> throw unsupportedTypeError
            } as T
            return defaultValue
        }

        inline fun <reified T> clone(grid: Grid<T>): Grid<T> {
            val cloned = grid.grid.map { it.copyOf() }.toTypedArray()
            return Grid(cloned, grid.defaultValue)
        }
    }
}