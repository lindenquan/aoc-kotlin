import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.reflect.KFunction1

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

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