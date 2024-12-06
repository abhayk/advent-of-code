package aoc2015

import Solution

class Day8 : Solution {
    override fun part1(input: String): Any {
        //preProcess(""""\x27"""")
        preProcessV2(""""zdvrvn\xd0mtfvpylbn\\\\sxcznrzugwznl"""")
        return input.lines().sumOf {
            //println("actual - ${it.length}, value - ${preProcessV2(it).length - 2}")
            it.length - (preProcessV2(it).length - 2)
        }
    }

    override fun part2(input: String): Any {
        TODO("Not yet implemented")
    }

    private fun getValueLength(input: String): Int {
        return input.replace("""\\""", """\""")
            .replace("""\"""", """"""")
            //.replace("""\\x[0-9a-f][0-9a-f]""".toRegex(), "p")
            .length - 2
    }

    private fun preProcess(input: String): String {
        val regex = """\\x[0-9a-fA-F][0-9a-fA-F]""".toRegex()
        var tmp = input
        regex.findAll(input).forEach { tmp = tmp.replace(it.value, getCharFromHex(it.value).toString()) }
        return tmp
    }

    private fun preProcessV2(input: String): String {
        var tmp = input
        while(containsReplaceableSequence(tmp)) {
            tmp = preProcess(tmp)
            tmp = tmp.replace("""\\""", """\""")
                .replace("""\"""", """"""")
        }
        return tmp
    }

    private fun containsReplaceableSequence(input: String): Boolean {
        return input.contains("""\\x[0-9a-f][0-9a-f]""".toRegex()) ||
                input.contains("""\\""") || input.contains("""\"""")
    }

    private fun getCharFromHex(input: String): Char {
        //println(input.removePrefix("\\x").toInt(16))
        return input.removePrefix("\\x").toInt(16).toChar()
    }
}