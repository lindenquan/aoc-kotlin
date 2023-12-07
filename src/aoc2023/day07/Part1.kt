package day07

import process
import readInput

enum class GameMode {
    D_MODE, // default mode
    J_MODE
}

class Card(
    val card: String,
    val point: Long,
    val gameMode: GameMode = GameMode.D_MODE
) : Comparable<Card> {
    // key -> card value
    // value -> count
    private val value: List<Pair<Int, Int>>

    // card in digit form
    private lateinit var digitCard: List<Int>

    val JOKER = 1

    init {
        fun convertToDigitCard(): List<Int> {
            return card.map {
                when (it) {
                    'A' -> 14
                    'K' -> 13
                    'Q' -> 12
                    'J' -> if (gameMode == GameMode.J_MODE) JOKER else 11
                    'T' -> 10
                    else -> it - '0'
                }
            }
        }

        fun calcValue(): List<Pair<Int, Int>> {
            val value = digitCard
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }

            if (gameMode == GameMode.J_MODE) {
                val mutableValue = value.toMutableList()
                for (i in mutableValue.indices) {
                    if (mutableValue[i].first == JOKER && mutableValue.size > 1) {
                        val jokerPoint = mutableValue[i].second
                        mutableValue.removeAt(i)
                        mutableValue[0] = Pair(mutableValue[0].first, mutableValue[0].second + jokerPoint)
                        break
                    }
                }

                return mutableValue.toList()
            }

            return value
        }

        digitCard = convertToDigitCard()
        value = calcValue()
    }

    override fun compareTo(other: Card): Int {
        val countListThis = value.map { it.second }
        val countListOther = other.value.map { it.second }
        val compareResult = compareList(countListThis, countListOther)
        if (compareResult != 0) {
            return compareResult
        }

        return compareList(digitCard, other.digitCard)
    }

    override fun toString(): String {
        return card
    }

    private fun compareList(list1: List<Int>, list2: List<Int>): Int {
        var compareResult = 0
        for (i in list1.indices) {
            compareResult = list1[i] - list2.getOrElse(i, ::defaultValue)
            if (compareResult != 0) {
                break
            }
        }

        return compareResult
    }

    private fun defaultValue(i: Int) = 0
}

data class Input(
    val cards: List<Card>
)

fun main() {
    process(readInput("aoc2023/day07/input.txt"), ::solvePart1)
}

fun solvePart1(inputText: List<String>) {
    val input = getInput(inputText)
    val sortedCards = input.cards.sorted()
    println(sortedCards.totalWinning())
}

fun List<Card>.totalWinning(): Long {
    return this.withIndex().sumOf { (index, card) ->
        card.point * (index + 1)
    }
}

fun getInput(inputText: List<String>, gameMode: GameMode = GameMode.D_MODE): Input {
    return Input(inputText.map {
        val parts = it.split(" ")
        Card(parts[0], parts[1].toLong(), gameMode)
    })
}
