package aoc2023.day04

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day04/input.txt"), ::solvePart1)
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
    val winningNumbers = numbers[1]
    val allNumbers = numbers[2]
    val allNumbersMap = allNumbers.associateWith { 1 }
    var count = 0
    winningNumbers.forEach { if (allNumbersMap.getOrDefault(it, 0) == 1) count++ }
    return if (count == 0) 0 else 1 shl count - 1
}

fun getNumbers(line: String): List<List<Int>> {
    val match = Regex("Card\\s+(\\d+):\\s+(.*)\\s+\\|\\s+(.*)").find(line)!!
    val (number, winningNumbers, allNumbersStr) = match.destructured
    return listOf(
        listOf(number.toInt()),
        winningNumbers.split(Regex("\\s+")).map { it.toInt() },
        allNumbersStr.split(Regex("\\s+")).map { it.toInt() }
    )
}
