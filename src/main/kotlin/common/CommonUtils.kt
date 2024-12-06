package common

fun getChar(lines: List<String>, x: Int, y: Int, default: Char = '.'): Char {
    val rowCount = lines.size
    val colCount = lines[0].length
    if (x < 0 || x >= rowCount || y < 0 || y >= colCount) {
        return default
    }
    return lines[x][y]
}