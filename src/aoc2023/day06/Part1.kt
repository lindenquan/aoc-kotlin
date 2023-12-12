package aoc2023.day06

import util.process
import util.readInput

data class Race(val time: Long, val distance: Long) {
    override fun toString(): String {
        return "{time: $time, distance: $distance}"
    }
}

fun main() {
    process(readInput("aoc2023/day06/input.txt"), ::solvePart1)
}

fun solvePart1(inputTxt: List<String>) {
    val input = getInput(inputTxt)
    val output = input.map {
        getNumberOfWinWays(it)
    }.reduce { acc, unit -> acc * unit }

    println(output)
}

fun getNumberOfWinWays(race: Race): Long {
    var sum = 0L
    for (i in 0..race.time) {
        val distance = i * (race.time - i)
        if (distance > race.distance) sum++
    }

    return sum
}

fun getInput(inputTxt: List<String>): MutableList<Race> {
    val times = getNumberList(inputTxt[0])
    val distances = getNumberList(inputTxt[1])
    val input = mutableListOf<Race>()
    for (i in times.indices) {
        input.add(Race(times[i], distances[i]))
    }

    return input
}

fun getNumberList(s: String): List<Long> {
    return s.substringAfter(":")
        .split(Regex("\\s+"))
        .filter { it.isNotEmpty() }
        .map { it.toLong() }
}
