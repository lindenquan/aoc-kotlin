package aoc2023.day12

import util.process
import util.readInput
import util.repeat

fun main() {
    process(readInput("aoc2023/day12/input.txt"), ::solvePart2)
}

fun solvePart2(inputText: List<String>) {
    val sum = inputText.map {
        Record(unfold(it, 5))
    }.sumOf { it.calcPossibilityWithDynamicProgramming() }
    println(sum)
}

fun unfold(line: String, multiple: Int): String {
    check(multiple > 1)
    val (part1, part2) = line.split(" ")
    val newPart1 = repeat(part1, "?", multiple)
    val newPart2 = repeat(part2, ",", multiple)
    return "$newPart1 $newPart2"
}

