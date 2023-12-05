package day01

import process
import readInput

fun main() {
    process(readInput("day01/input"), ::solvePart1)
}

fun solvePart1(input: List<String>) {
    var sum = 0L
    input.forEach {
        sum += parse(it)
    }

    println(sum)
}

fun parse(line: String): Int {
    var first = 0;
    for (c in line) {
        if (c.code in 48..57) {
            first = c.code - 48
            break
        }
    }

    var last = 0
    for (i in line.length - 1 downTo 0) {
        val c = line[i]
        if (c.code in 48..57) {
            last = c.code - 48
            break
        }
    }

    return if (first > 0) first * 10 + last else last
}
