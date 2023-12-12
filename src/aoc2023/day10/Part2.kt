package day10

import util.List2D
import util.process
import util.readInput
import kotlin.math.abs

fun main() {
    process(readInput("aoc2023/day10/input.txt"), ::solvePart2)
}

fun solvePart2(inputText: List<String>) {
    val input = List2D(inputText, Char::class)
    val inputSize = input.size()
    val map = List(inputSize.first) {
        MutableList(inputSize.second) { Mark.NOT_VISITED }
    }

    val locations = getSteps(input, map)
    val area = getAreaByShoelaceFormula(locations, inputSize.first)
    val i = picksTheorem(area, locations.size)
    println(i)
}

fun getAreaByShoelaceFormula(locations: List<Pair<Int, Int>>, rowNumber: Int): Int {
    val xy = mutableListOf<Pair<Int, Int>>()
    // convert row & column to x & y
    locations.forEach {
        xy.add(Pair(it.second, rowNumber - it.first))
    }

    xy.add(Pair(locations[0].second, rowNumber - locations[0].first))

    var sum1 = 0
    var sum2 = 0
    for (i in 0..<xy.size - 1) {
        sum1 += xy[i].first * xy[i + 1].second
        sum2 += xy[i].second * xy[i + 1].first
    }

    return abs(sum1 - sum2) / 2
}

fun picksTheorem(area: Int, locations: Int): Int {
    return area + 1 - locations / 2
}
