package common

fun solveLinearEquation(eq1: Triple<Double, Double, Double>, eq2: Triple<Double, Double, Double>): Pair<Double, Double>? {
    val determinant = eq1.first * eq2.second - eq2.first * eq1.second
    if (determinant == 0.0) {
        println("No unique solution")
        return null
    }
    val x = (eq1.third * eq2.second - eq2.third * eq1.second) / determinant
    val y = (eq1.first * eq2.third - eq2.first * eq1.third) / determinant
    return Pair(x, y)
}