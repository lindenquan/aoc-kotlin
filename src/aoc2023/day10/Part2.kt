package day10

import util.List2D
import util.process
import util.readInput

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
    for(i in 0 ..< xy.size -1) {
        sum1 += xy[i].first * xy[i +1].second
        sum2 += xy[i].second * xy[i +1].first
    }

    return Math.abs(sum1- sum2)/2
}

fun picksTheorem(area: Int, locations: Int): Int {
    return area + 1 - locations/2
}

fun count(map: List<MutableList<Mark>>, mark: Mark): Long {
    return map.sumOf { row ->
        row.sumOf {
            if (it == mark) 1L else 0L
        }
    }
}

fun copy(map: List<MutableList<Mark>>): List<MutableList<Mark>> {
    return List(map.size) {
        MutableList(map[it].size) { col ->
            map[it][col]
        }
    }
}

fun printMap(map: List<MutableList<Mark>>) {
    map.forEach { row ->
        run {
            row.forEach {
                when (it) {
                    Mark.NOT_VISITED -> print(0)
                    Mark.VISITED -> print(1)
                    Mark.SURROUNDED -> print(2)
                }
            }
            println()
        }
    }
}

tailrec fun markOutside(map: List<MutableList<Mark>>, location: Pair<Int, Int>) {
    map[location.first][location.second] = Mark.SURROUNDED
    if (location.first - 1 >= 0 && map[location.first - 1][location.second] != Mark.VISITED && map[location.first - 1][location.second] != Mark.SURROUNDED) markOutside(
        map,
        Pair(location.first - 1, location.second)
    )
    if (location.first + 1 < map.size && map[location.first + 1][location.second] != Mark.VISITED && map[location.first + 1][location.second] != Mark.SURROUNDED) markOutside(
        map,
        Pair(location.first + 1, location.second)
    )
    if (location.second - 1 >= 0 && map[location.first][location.second - 1] != Mark.VISITED && map[location.first][location.second - 1] != Mark.SURROUNDED) markOutside(
        map,
        Pair(location.first, location.second - 1)
    )
    if (location.second + 1 < map[location.first].size && map[location.first][location.second + 1] != Mark.VISITED && map[location.first][location.second + 1] != Mark.SURROUNDED) markOutside(
        map,
        Pair(location.first, location.second + 1)
    )
}
