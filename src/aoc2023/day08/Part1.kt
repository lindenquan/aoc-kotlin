package aoc2023.day08

import util.process
import util.readInput

data class Input(
    val steps: List<Int>, val map: Map<String, List<String>>
)

fun main() {
    process(readInput("aoc2023/day08/input.txt"), ::solvePart1)
}

fun solvePart1(inputText: List<String>) {
    val input = parseInputText(inputText)
    println(calcSteps(input, "AAA", Regex("ZZZ")))
}

fun calcSteps(input: Input, startLocation: String, destination: Regex): Long {
    val stepSize = input.steps.size
    var steps = 0
    var location = startLocation
    while (true) {
        val direction = input.steps[steps % stepSize]
        location = input.map[location]!![direction]
        steps++
        if (destination.matches(location)) {
            break
        }
    }

    return steps.toLong()
}

fun parseInputText(inputText: List<String>): Input {
    val steps = inputText[0].map { if (it == 'R') 1 else 0 }
    val map = mutableMapOf<String, List<String>>()

    inputText.drop(2).forEach {
        val key = it.substring(0, 3)
        val value = listOf(it.substring(7, 10), it.substring(12, 15))
        map[key] = value
    }

    return Input(steps, map)
}
