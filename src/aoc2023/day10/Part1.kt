package aoc2023.day10

import util.List2D
import util.process
import util.readInput

enum class Mark {
    NOT_VISITED,
    VISITED,
}

fun main() {
    process(readInput("aoc2023/day10/input.txt"), ::solvePart1)
}

fun solvePart1(inputText: List<String>) {
    val input = List2D(inputText, Char::class)
    val inputSize = input.size()
    val map = List(inputSize.first) {
        MutableList(inputSize.second) { Mark.NOT_VISITED }
    }

    println(getSteps(input, map).size / 2)
}

fun getSteps(
    input: List2D<Char>,
    map: List<MutableList<Mark>>
): List<Pair<Int, Int>> {
    val startLocation = input.find('S')!!
    map[startLocation.first][startLocation.second] = Mark.VISITED
    var currentPosition: Pair<Int, Int>? = startLocation
    var steps = 1
    val locations = mutableListOf<Pair<Int, Int>>()
    locations.add(startLocation)
    do {
        currentPosition = getNextLocation(input, map, currentPosition!!)
        if (currentPosition != null) {
            locations.add(currentPosition)
            map[currentPosition.first][currentPosition.second] = Mark.VISITED
            steps++
        }
    } while (currentPosition != null)

    return locations
}

// .....
// .F-7.
// .|.|.
// .L-J.
// .....
fun getNextLocation(
    input: List2D<Char>,
    map: List<MutableList<Mark>>,
    location: Pair<Int, Int>
): Pair<Int, Int>? {
    val nextLocation = when (input.get(location)) {
        'F' -> getLocationPart1(map, location, listOf(Pair(0, 1), Pair(1, 0)))
        '-' -> getLocationPart1(map, location, listOf(Pair(0, 1), Pair(0, -1)))
        '7' -> getLocationPart1(map, location, listOf(Pair(0, -1), Pair(1, 0)))
        '|' -> getLocationPart1(map, location, listOf(Pair(-1, 0), Pair(1, 0)))
        'J' -> getLocationPart1(map, location, listOf(Pair(-1, 0), Pair(0, -1)))
        'L' -> getLocationPart1(map, location, listOf(Pair(-1, 0), Pair(0, 1)))
        'S' -> getNextLocationForStartLocation(input, location)
        else -> null
    }

    return nextLocation
}

fun getNextLocationForStartLocation(
    input: List2D<Char>,
    location: Pair<Int, Int>
): Pair<Int, Int>? {
    val up = Pair(location.first - 1, location.second)
    val down = Pair(location.first + 1, location.second)
    val right = Pair(location.first, location.second + 1)
    val left = Pair(location.first, location.second - 1)

    if (listOf('F', '7', '|').contains(input.get(up))) return up
    if (listOf('7', 'J', '-').contains(input.get(right))) return right
    if (listOf('|', 'L', 'J').contains(input.get(down))) return down
    if (listOf('F', 'L', '-').contains(input.get(left))) return left

    return null
}


fun getLocationPart1(
    map: List<MutableList<Mark>>,
    location: Pair<Int, Int>,
    locations: List<Pair<Int, Int>>
): Pair<Int, Int>? {
    val direction = locations.find {
        map[location.first + it.first][location.second + it.second] == Mark.NOT_VISITED
    } ?: return null

    return Pair(location.first + direction.first, location.second + direction.second)
}
