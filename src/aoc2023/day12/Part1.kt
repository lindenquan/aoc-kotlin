package aoc2023.day12

import util.process
import util.readInput

class Record(text: String) {
    private val markList: String
    private val countList: List<Int>

    companion object {
        val MARK_MAP = mapOf('0' to '.', '1' to '#')
    }

    init {
        fun parse(text: String): List<Any> {
            val twoParts = text.split(" ")
            return listOf(twoParts[0], twoParts[1].split(",").map { it.toInt() })
        }

        val result = parse(text)
        markList = result[0] as String
        countList = result[1] as List<Int>
    }

    fun calcPossibilityWithBruteForce(): Int {
        val numberOfUnknown = markList.filter { it == '?' }.length
        val total = 1 shl numberOfUnknown
        var numberOfCorrect = 0
        for (i in 0..<total) {
            val potentialMark = getPotentialMark(numberOfUnknown, i)
            val potentialMarkCountList = getCountList(potentialMark)
            if (potentialMarkCountList == countList) {
                numberOfCorrect++
            }
        }
        return numberOfCorrect
    }

    // credit: https://github.com/elizarov/adventofcode2023
    fun calcPossibilityWithRecursive(): Long {
        fun cnt(s: String, g: IntArray, i: Int, j: Int, u: Int): Long {
            if (i >= s.length) return if (j >= g.size || j == g.size - 1 && u == g[j]) 1 else 0
            val c = s[i]
            var res = 0L
            if (c == '.' || c == '?') {
                if (u > 0 && g[j] == u) {
                    res += cnt(s, g, i + 1, j + 1, 0)
                } else if (u == 0) {
                    res += cnt(s, g, i + 1, j, 0)
                }
            }
            if (c == '#' || c == '?') {
                if (j < g.size && u < g[j]) {
                    res += cnt(s, g, i + 1, j, u + 1)
                }
            }
            return res
        }

        return cnt(markList, countList.toIntArray(), 0, 0, 0)
    }

    // credit: https://github.com/elizarov/adventofcode2023
    fun calcPossibilityWithDynamicProgramming(): Long {
        val dp = Array(markList.length) {
            Array(countList.size + 1) { j -> LongArray(countList.getOrElse(j) { 0 } + 1) { -1 } }
        }

        fun calculate(s: String, g: IntArray, i: Int, j: Int, u: Int): Long {
            if (i >= s.length) return if (j >= g.size || j == g.size - 1 && u == g[j]) 1 else 0
            if (dp[i][j][u] >= 0) return dp[i][j][u]
            val c = s[i]
            var res = 0L
            if (c == '.' || c == '?') {
                if (u > 0 && g[j] == u) {
                    res += calculate(s, g, i + 1, j + 1, 0)
                } else if (u == 0) {
                    res += calculate(s, g, i + 1, j, 0)
                }
            }
            if (c == '#' || c == '?') {
                if (j < g.size && u < g[j]) {
                    res += calculate(s, g, i + 1, j, u + 1)
                }
            }
            dp[i][j][u] = res
            return res
        }

        return calculate(markList, countList.toIntArray(), 0, 0, 0)
    }

    private fun getCountList(mark: String): List<Int> {
        return mark.split(".").filter { it.isNotEmpty() }.map { it.length }
    }

    private fun getPotentialMark(numberOfUnknown: Int, hint: Int): String {
        var hints = hint.toString(2)
        val paddingCount = numberOfUnknown - hints.length
        if (paddingCount > 0) {
            hints = "0".repeat(paddingCount) + hints
        }

        var hintIndex = -1
        return markList.map {
            if (it == '?') {
                hintIndex++
                MARK_MAP[hints[hintIndex]]
            } else {
                it
            }
        }.joinToString("")
    }
}

fun main() {
    process(readInput("aoc2023/day12/input.txt"), ::solvePart1)
}

fun solvePart1(inputText: List<String>) {
    val sum = inputText.map {
        Record(it)
    }.sumOf { it.calcPossibilityWithRecursive() }
    println(sum)
}
