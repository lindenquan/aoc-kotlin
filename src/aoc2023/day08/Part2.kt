package aoc2023.day08

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day08/input.txt"), ::solvePart2)
}

fun solvePart2(inputText: List<String>) {
    val input = parseInputText(inputText)
    val allZSteps = input.map.keys.filter {
        it.endsWith("A")
    }.map { calcSteps(input, it, Regex("..Z")) }

    var steps = 1L
    for (l in allZSteps) steps = leastCommonMultiple(steps, l)
    println(steps)
}

tailrec fun greatestCommonDivisor(x: Long, y: Long): Long =
    if (y == 0L) x else greatestCommonDivisor(y, x % y)

fun leastCommonMultiple(x: Long, y: Long) = x * y / greatestCommonDivisor(x, y)
