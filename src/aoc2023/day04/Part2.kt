package day04

import util.process
import util.readInput
import java.util.*

class Card(
    val number: Int,
    val winningNumbers: List<Int>,
    val allNumbers: List<Int>
) {
    var point: Int = -1
    override fun toString(): String {
        return "number: $number, winningNumbers: $winningNumbers, allNumbers: $allNumbers"
    }
}

fun main() {
    process(readInput("aoc2023/day04/input.txt"), ::solvePart2)
}

fun solvePart2(input: List<String>) {
    val originCards = mutableListOf<Card>()
    val queue: Queue<Card> = LinkedList()
    input.forEach {
        val card = convertToCard(it)
        originCards.add(card)
        queue.add(card)
    }

    var count = 0
    while (queue.isNotEmpty()) {
        val card = queue.remove()
        count++
        val points = getPoints(card)
        val cardNumber = card.number
        for (i in 1..points) {
            queue.add(originCards[i + cardNumber - 1])
        }
    }

    println(count)
}

fun getPoints(card: Card): Int {
    if (card.point != -1) {
        return card.point
    }

    val allNumbersMap = card.allNumbers.map { it to 1 }.toMap()
    var count = 0
    card.winningNumbers.forEach { if (allNumbersMap.getOrDefault(it, 0) == 1) count++ }
    card.point = count
    return count
}

fun convertToCard(line: String): Card {
    val numbers = getNumbers(line)
    val number = numbers[0][0]
    val winningNumbers = numbers[1]
    val allNumbers = numbers[2]
    return Card(number, winningNumbers, allNumbers)
}





