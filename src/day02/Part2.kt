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
    val match = Regex("Game (\\d+): (.*)").find(line)!!
    val (gameNumber, game) = match.destructured

    val redCount = Regex("(\\d+) red").findAll(game).map { it.groupValues[1].toInt() }.max()
    val greenCount = Regex("(\\d+) green").findAll(game).map { it.groupValues[1].toInt() }.max()
    val blueCount = Regex("(\\d+) blue").findAll(game).map { it.groupValues[1].toInt() }.max()

    return redCount * greenCount * blueCount
}

