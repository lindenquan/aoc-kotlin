package aoc2023.day03

import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day03/input.txt"), ::solvePart1)
}

fun solvePart1(input: List<String>) {
    val engine = mutableListOf<List<Char>>()
    input.forEach {
        engine.add(it.toList())
    }

    val allNumbers = findAllNumbers(engine)
    val allValidNumbers = findAllValidNumbers(engine, allNumbers)
    println(allValidNumbers.sum())
}

fun findAllValidNumbers(engine: MutableList<List<Char>>, allNumbers: List<List<Int>>): List<Int> {
    val validNumbers = mutableListOf<Int>()
    allNumbers.forEach {
        val number = it[0]
        if (isSurroundedBySymbol(engine, it[1], it[2], it[3])) {
            validNumbers.add(number)
        }
    }

    return validNumbers
}

fun isSurroundedBySymbol(
    engine: MutableList<List<Char>>,
    rowIndex: Int,
    colIndex: Int,
    digits: Int
): Boolean {
    val maxColIndex = engine[0].size - 1
    val maxRowIndex = engine.size - 1
    val upperRowIndex = rowIndex - 1
    val lowerRowIndex = rowIndex + 1
    val frontColIndex = colIndex - 1
    val rearColIndex = frontColIndex + digits + 1
    val startColIndex = if (frontColIndex > -1) frontColIndex else 0
    val endColIndex = if (rearColIndex > maxColIndex) maxColIndex else rearColIndex

    if (upperRowIndex > -1) {
        for (j in startColIndex..endColIndex) {
            val c = engine[upperRowIndex][j]
            if (isSymbol(c)) {
                return true
            }
        }
    }

    if (frontColIndex > -1) {
        if (isSymbol(engine[rowIndex][frontColIndex])) {
            return true
        }
    }

    if (rearColIndex <= maxColIndex) {
        if (isSymbol(engine[rowIndex][rearColIndex])) {
            return true
        }
    }

    if (lowerRowIndex <= maxRowIndex) {
        for (j in startColIndex..endColIndex) {
            val c = engine[lowerRowIndex][j]
            if (isSymbol(c)) {
                return true
            }
        }
    }

    return false
}

fun isSymbol(c: Char): Boolean {
    return c.code !in 48..57 && c.code != 46
}

fun findAllNumbers(engine: MutableList<List<Char>>): List<List<Int>> {
    val allNumbers = mutableListOf<List<Int>>()
    for (i in engine.indices) {
        var lastPointIndex = -1
        var number = 0
        var digits = 0
        for (j in engine[i].indices) {
            val point = engine[i][j]
            if (point.code in 48..57) {
                // point is number
                if (lastPointIndex + 1 == j) {
                    number = number * 10 + point.code - 48
                    digits++
                } else {
                    number = point.code - 48
                    digits = 1
                }
                lastPointIndex = j
            } else {
                if (number != 0) {
                    allNumbers.add(listOf(number, i, j - digits, digits))
                    number = 0
                }
            }
        }

        if (number != 0) {
            allNumbers.add(listOf(number, i, engine[i].size - digits, digits))
        }
    }

    return allNumbers
}
