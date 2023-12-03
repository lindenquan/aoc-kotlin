package day03

import readInput

fun main() {
    val input = readInput("day03/input")
    val engine = mutableListOf<List<Char>>()
    input.forEach {
        engine.add(it.toList())
    }

    val allNumbers = findAllNumbers(engine)
    val gearRatios = findAllGearRatios(engine, allNumbers)
    println(gearRatios.sum())
}

fun findAllGearRatios(engine: MutableList<List<Char>>, allNumbers: List<List<Int>>): List<Int> {
    val allGearRatios = mutableListOf<Int>()
    for (i in engine.indices) {
        for (j in engine.indices) {
            val c = engine[i][j]
            if (c == '*') {
                val gearRatio = calcGearRatio(allNumbers, i, j)
                if (gearRatio > 0) {
                    allGearRatios.add(gearRatio)
                }
            }
        }
    }

    return allGearRatios
}

fun calcGearRatio(allNumbers: List<List<Int>>, i: Int, j: Int): Int {
    val adjacentNumbers = mutableListOf<Int>()
    allNumbers.forEach {
        if (isAdjacent(it, i, j)) {
            adjacentNumbers.add(it[0])
        }
    }

    if (adjacentNumbers.size == 2) {
        return adjacentNumbers[0] * adjacentNumbers[1]
    }

    return 0
}

fun isAdjacent(it: List<Int>, i: Int, j: Int): Boolean {
    val upperRowIndex = it[1] - 1
    val lowerRowIndex = it[1] + 1
    val frontColIndex = it[2] - 1
    val rearColIndex = it[2] + it[3]

    if (i == upperRowIndex && j in frontColIndex..rearColIndex) {
        return true
    }

    if (i == it[1] && j == frontColIndex) {
        return true
    }

    if (i == it[1] && j == rearColIndex) {
        return true
    }

    if (i == lowerRowIndex && j in frontColIndex..rearColIndex) {
        return true
    }

    return false
}




