package day06

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day06/input.txt"), ::solvePart2)
}

fun solvePart2(inputText: List<String>) {
    val input = getInputForPart2(inputText)
    println(getNumberOfWinWays(input))
}

fun getInputForPart2(inputText: List<String>): Race {
    val time = getNumber(inputText[0])
    val distance = getNumber(inputText[1])
    return Race(time, distance)
}

fun getNumber(s: String): Long {
    return s.substringAfter(":").replace(" ", "").toLong()
}
