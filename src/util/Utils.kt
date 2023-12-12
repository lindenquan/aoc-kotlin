package util

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.reflect.KFunction1

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name").readLines()


/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun process(input: List<String>, solve: KFunction1<List<String>, Unit>) {
    val start = System.currentTimeMillis()
    solve(input)
    val end = System.currentTimeMillis()
    println("spent: ${end - start}ms")
}
