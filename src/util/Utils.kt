package util

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.reflect.KFunction1

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name").readLines()

fun process(input: List<String>, solve: KFunction1<List<String>, Unit>) {
    val start = System.currentTimeMillis()
    solve(input)
    val end = System.currentTimeMillis()
    println("spent: ${end - start}ms")
}

// The main function that prints all combinations of size r
// in arr[] of size n. This function mainly uses combinationUtil()
fun combination(arr: IntArray, r: Int): List<List<Int>> {
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
