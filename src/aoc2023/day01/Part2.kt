package aoc2023.day01

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day01/input.txt"), ::solvePart2)
}

fun solvePart2(input: List<String>) {
    var sum = 0L
    input.forEach {
        sum += advancedParse(it)
    }

    println(sum)
}

fun advancedParse(line: String): Int {
    val n1 = "one"
    val n2 = "two"
    val n3 = "three"
    val n4 = "four"
    val n5 = "five"
    val n6 = "six"
    val n7 = "seven"
    val n8 = "eight"
    val n9 = "nine"

    var first = 0
    var last = 0
    for (i in line.indices) {
        val c = line[i]
        var digit = 0
        if (c in '0'..'9') {
            digit = c - '0'
        }

        if (digit == 0 && i + 3 <= line.length) {
            when (line.substring(i, i + 3)) {
                n1 -> digit = 1
                n2 -> digit = 2
                n6 -> digit = 6
            }
        }

        if (digit == 0 && i + 4 <= line.length) {
            when (line.substring(i, i + 4)) {
                n4 -> digit = 4
                n5 -> digit = 5
                n9 -> digit = 9
            }
        }

        if (digit == 0 && i + 5 <= line.length) {
            when (line.substring(i, i + 5)) {
                n3 -> digit = 3
                n7 -> digit = 7
                n8 -> digit = 8
            }
        }

        if (digit > 0) {
            if (first == 0) {
                first = digit
            } else {
                last = digit
            }
        }
    }

    if (last == 0) {
        last = first
    }

    return first * 10 + last
}
