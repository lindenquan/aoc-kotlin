package util

import kotlin.reflect.KClass

open class List2D<T : Any>(_data: List<String>, _type: KClass<T>, delimiter: Regex = Regex("")) {
    val data: List<List<T>>

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
                if (data[i][j].equals(item)) {
                    return Pair(i, j)
                }
            }
        }

        return null
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
}


