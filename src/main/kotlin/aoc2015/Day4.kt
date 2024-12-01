package aoc2015

import Solution
import java.security.MessageDigest

class Day4 : Solution {
    override fun part1(input: String): Any {
        return findMD5withPrefix(input, "00000")
    }

    override fun part2(input: String): Any {
        return findMD5withPrefix(input, "000000")
    }

    private fun findMD5withPrefix(key: String, prefix: String): Int {
        var counter = 0
        val md = MessageDigest.getInstance("MD5")
        while(true) {
            if (md5("$key$counter", md).startsWith(prefix)) {
                return counter
            }
            counter++
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun md5(input: String, md: MessageDigest): String {
        return md.digest(input.toByteArray()).toHexString()
    }
}