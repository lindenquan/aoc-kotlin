package aoc2023.day07

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day07/input.txt"), ::solvePart2)
}

fun solvePart2(inputText: List<String>) {
    val input = getInput(inputText, GameMode.J_MODE)
    val sortedCards = input.cards.sorted()
    println(sortedCards.totalWinning())
}
