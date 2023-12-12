package aoc2023.day11

import util.List2D
import util.combination
import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day11/input.txt"), ::solvePart2)
}

fun solvePart2(inputText: List<String>) {
    val input = List2D(inputText, Char::class)
    val locations = input.findAll('#')
    val combinations = combination(IntArray(locations.size) { it }, 2)
    val rows = input.rowsAll('.')
    val cols = input.colsAll('.')
    var sum = 0L
    combinations.forEach {
        sum += input.shortestPathSteps(locations[it[0]], locations[it[1]], 1_000_000, rows, cols)
    }
    println(sum)
}
