package day9

import process
import readInput

data class Input(
    val steps: List<Int>, val map: Map<String, List<String>>
)

fun main() {
    process(readInput("aoc2023/day09/input.txt"), ::solvePart1)
}

fun solvePart1(inputText: List<String>) {
  val input = inputText.map {
      it.split(" ").map { it.toLong() }
  }

  val result = input.sumOf {
      var list = it
      val last = mutableListOf<Long>()
      while(!list.all { item -> item == 0L }) {
          last.add(list.last())
          list = list.windowed(2) { item -> item[1] - item[0] }
      }

      last.sum()
  }

    println(result)
}
