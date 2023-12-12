package day01

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day01/input.txt"), ::solvePart1)
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
        if (c in '0'..'9') {
            first = c - '0'
            break
        }
    }

    var last = 0
    for (i in line.length - 1 downTo 0) {
        val c = line[i]
        if (c in '0'..'9') {
            last = c - '0'
            break
        }
    }

    return if (first > 0) first * 10 + last else last
}
