package day02

import process
import readInput

fun main() {
    process(readInput("day02/input"), ::solvePart1)
}

fun solvePart1(input: List<String>) {
    var sum = 0
    val target = hashMapOf("red" to 12, "green" to 13, "blue" to 14)
    input.forEach {
        sum += possible(it, target)
    }

    println(sum)
}

fun possible(line: String, target: HashMap<String, Int>): Int {
    val match = Regex("Game (\\d+): (.*)").find(line)!!
    val (gameNumber, game) = match.destructured

    val redCount = getColorCount("red", game)
    val greenCount = getColorCount("green", game)
    val blueCount = getColorCount("blue", game)

    val targetRed = target.get("red")!!
    val targetGreen = target.get("green")!!
    val targetBlue = target.get("blue")!!

    if (redCount <= targetRed && greenCount <= targetGreen && blueCount <= targetBlue) {
        return gameNumber.toInt()
    }

    return 0
}

fun getColorCount(color: String, game: String): Int {
    return Regex("(\\d+) $color").findAll(game).map { it.groupValues[1].toInt() }.max()
}

