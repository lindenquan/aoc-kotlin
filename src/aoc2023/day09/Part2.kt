package day09

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day09/input.txt"), ::solvePart2)
}

fun solvePart2(inputText: List<String>) {
    val input = inputText.map {
        it.split(" ").map { it.toLong() }
    }

    val result = input.sumOf {
        var list = it
        val first = mutableListOf<Long>()
        while (!list.all { item -> item == 0L }) {
            first.add(list.first())
            list = list.windowed(2) { item -> item[1] - item[0] }
        }

        var sum = 0L
        for (x in first.reversed()) {
            sum = x - sum
        }

        sum
    }

    println(result)
}
