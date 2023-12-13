package util

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.reflect.KFunction1

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name").readLines()

fun repeat(item: String, separator: String, multiple: Int): String {
    check(multiple > 0)
    if (multiple == 1) {
        return item
    }
    return item + "${separator}${item}".repeat(multiple - 1)
}

fun process(input: List<String>, solve: KFunction1<List<String>, Unit>) {
    val start = System.currentTimeMillis()
    solve(input)
    val end = System.currentTimeMillis()
    println("spent: ${end - start}ms")
}

fun combination(arr: IntArray, r: Int): MutableList<MutableList<Int>>? {
    if (arr.size < r || r < 1) {
        return null
    }

    val input = MutableList(arr.size) { arr[it] }
    if (input.size == r) {
        return mutableListOf(input)
    }

    val first = input.removeFirst()
    val subArray = IntArray(input.size) { input[it] }
    val otherRSizeCombinations = combination(subArray, r)!!
    if (r > 1) {
        val otherCombinations = combination(subArray, r - 1)
        otherCombinations?.forEach {
            it.add(0, first)
            otherRSizeCombinations.add(it)
        }
    } else {
        otherRSizeCombinations.add(mutableListOf(first))
    }

    return otherRSizeCombinations
}

// The main function that prints all combinations of size r
// in arr[] of size n. This function mainly uses combinationUtil()
fun combination2(arr: IntArray, r: Int): List<List<Int>> {
    val combinationList = mutableListOf<List<Int>>()

    // A temporary array to store all combination one by one
    val data = IntArray(r)

    //    arr[]  ---> Input Array
    //    data[] ---> Temporary array to store current combination
    //    start & end ---> Starting and Ending indexes in arr[]
    //    index  ---> Current index in data[]
    //    r ---> Size of a combination to be printed
    fun combinationUtil(
        arr: IntArray, data: IntArray, start: Int,
        end: Int, index: Int, r: Int
    ) {
        // Current combination is ready to be printed, print it
        if (index == r) {
            val comb = mutableListOf<Int>()
            for (j in 0 until r) comb.add(data[j])
            combinationList.add(comb)
            return
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        var i = start
        while (i <= end && end - i + 1 >= r - index) {
            data[index] = arr[i]
            combinationUtil(arr, data, i + 1, end, index + 1, r)
            i++
        }
    }

    combinationUtil(arr, data, 0, arr.size - 1, 0, r)
    return combinationList
}
