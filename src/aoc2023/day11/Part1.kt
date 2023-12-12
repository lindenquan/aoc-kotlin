package aoc2023.day11

import util.List2D
import util.combination
import util.process
import util.readInput

fun main() {
    process(readInput("aoc2023/day11/input.txt"), ::solvePart1)
}

fun solvePart1(inputText: List<String>) {
    val input = List2D(inputText, Char::class)
    val expandedInput = expendInput(input)
    val locations = expandedInput.findAll('#')
    val combinations = combination(IntArray(locations.size) { it }, 2)!!
    val sum = combinations.sumOf {
        expandedInput.shortestPathSteps(locations[it[0]], locations[it[1]])
    }
    println(sum)
}

fun expendInput(input: List2D<Char>): List2D<Char> {
    val size = input.size()
    val rows = input.rowsAll('.')
    val inputCols = input.colsAll('.')
    var usedRow = 0
    val newInput = List(size.first + rows.size) { row ->
        val cols = buildList { addAll(inputCols) }.toMutableList()
        var usedCol = 0
        var extraRow: List<Int> = listOf()
        val newString = List(size.second + cols.size) { col ->
            extraRow = rows.filter { it == row - usedRow }
            if (extraRow.isNotEmpty()) {
                '.'
            } else {
                val extraCol = cols.filter { it == col - usedCol }
                if (extraCol.isNotEmpty()) {
                    usedCol++
                    cols.removeIf { it == extraCol[0] }
                    '.'
                } else {
                    input.get(Pair(row - usedRow, col - usedCol))
                }
            }
        }.joinToString(separator = "")
        if (extraRow.isNotEmpty()) {
            usedRow++
            rows.removeIf { it == extraRow[0] }
        }
        newString
    }

    return List2D(newInput, Char::class)
}
