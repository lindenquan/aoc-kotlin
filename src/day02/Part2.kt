package day02

import process
import readInput

fun main() {
    process(readInput("day02/input"), ::solvePart2)
}

fun solvePart2(input: List<String>) {
    var sum = 0

    input.forEach {
        sum += powerOfCubes(it)
    }

    println(sum)
}

fun powerOfCubes(line: String): Int {
    val match = Regex("Game \\d+: (.*)").find(line)!!
    val (game) = match.destructured
    val redCount = getColorCount("red", game)
    val greenCount = getColorCount("green", game)
    val blueCount = getColorCount("blue", game)

    return redCount * greenCount * blueCount
}

