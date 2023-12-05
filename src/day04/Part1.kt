package day04

import process
import readInput

fun main() {
    process(readInput("day04/input"), ::solvePart1)
}

fun solvePart1(input: List<String>) {
    var sum = 0
    input.forEach {
        sum += parse(it)
    }

    println(sum)
}

fun parse(line: String): Int {
    val numbers = getNumbers(line)
    val winningNumbers = numbers[1] as List<Int>
    val allNumbers = numbers[2] as List<Int>
    val allNumbersMap = allNumbers.map { it to 1 }.toMap()
    var count = 0
    winningNumbers.forEach { if (allNumbersMap.getOrDefault(it, 0) == 1) count++ }
    return if (count == 0) 0 else 1 shl count - 1
}

fun getNumbers(line: String): List<Any> {
    val match = Regex("Card\\s+(\\d+):\\s+(.*)\\s+\\|\\s+(.*)").find(line)!!
    val (number, winningNumbers, allNumbersStr) = match.destructured
    return listOf(
            number.toInt(),
            winningNumbers.split(Regex("\\s+")).map { it.toInt() },
            allNumbersStr.split(Regex("\\s+")).map { it.toInt() }
    )
}



