package util

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KClass

open class List2D<T : Any>(_data: List<String>, _type: KClass<T>, delimiter: Regex = Regex("")) {
    private val data: List<List<T>>

    init {
        data = _data.map { row ->
            val list2D = row.split(delimiter)
                .map { it }
                .filter { it.isNotEmpty() }
                .map {
                    when (_type) {
                        Int::class -> it.toInt()
                        Long::class -> it.toLong()
                        Char::class -> it[0]
                        else -> it
                    }
                }

            list2D as List<T>
        }
    }

    fun find(item: T): Pair<Int, Int>? {
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (data[i][j] == item) {
                    return Pair(i, j)
                }
            }
        }

        return null
    }

    fun findAll(item: T): MutableList<Pair<Int, Int>> {
        val locations = mutableListOf<Pair<Int, Int>>()
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (data[i][j] == item) {
                    locations.add(Pair(i, j))
                }
            }
        }

        return locations
    }

    fun size(): Pair<Int, Int> {
        return Pair(data.size, data[0].size)
    }

    fun get(location: Pair<Int, Int>): T? {
        return try {
            data[location.first][location.second]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    fun print() {
        data.forEach { row ->
            for (t in row) {
                print(t)
            }

            println()
        }
    }

    fun rowsAll(item: Char): MutableList<Int> {
        val rows = mutableListOf<Int>()
        for (i in data.indices) {
            if (data[i].all { it == item }) {
                rows.add(i)
            }
        }

        return rows
    }

    fun colsAll(item: Char): MutableList<Int> {
        val cols = mutableListOf<Int>()
        a@ for (i in data[0].indices) {
            for (j in data.indices) {
                val same = data[j][i] == item
                if (!same) continue@a
            }
            cols.add(i)
        }
        return cols
    }

    fun shortestPathSteps(l1: Pair<Int, Int>, l2: Pair<Int, Int>): Int {
        return abs(l1.first - l2.first) + abs(l1.second - l2.second)
    }

    fun shortestPathSteps(
        l1: Pair<Int, Int>,
        l2: Pair<Int, Int>,
        multiple: Int,
        rows: List<Int>,
        cols: List<Int>
    ): Long {
        val minRow = min(l1.first, l2.first)
        val maxRow = max(l1.first, l2.first)
        val minCol = min(l1.second, l2.second)
        val maxCol = max(l1.second, l2.second)

        val rowCount = rows.filter { it in (minRow + 1)..<maxRow }.size
        val colCount = cols.filter { it in (minCol + 1)..<maxCol }.size

        val totalRow = abs(l1.first - l2.first)
        val totalCol = abs(l1.second - l2.second)

        return (totalRow.toLong() + rowCount.toLong() * (multiple - 1)) + (totalCol.toLong() + colCount.toLong() * (multiple - 1))
    }
}
