fun main() {
    val input = readInput("Day02")
    var sum = 0

    var target = hashMapOf("red" to 12, "green" to 13, "blue" to 14)
    input.forEach {
        sum += possible(it, target)
    }

    println(sum)
}

fun possible(line: String, target: HashMap<String, Int>): Int {
    val match = Regex("Game (\\d+): (.*)").find(line)!!
    val (gameNumber, game) = match.destructured

    val redCount = Regex("(\\d+) red").findAll(game).map { it.groupValues[1].toInt() }.max()
    val greenCount = Regex("(\\d+) green").findAll(game).map { it.groupValues[1].toInt() }.max()
    val blueCount = Regex("(\\d+) blue").findAll(game).map { it.groupValues[1].toInt() }.max()

    val targetRed = target.get("red")!!
    val targetGreen = target.get("green")!!
    val targetBlue = target.get("blue")!!

    if (redCount <= targetRed && greenCount <= targetGreen && blueCount <= targetBlue) {
        return gameNumber.toInt()
    }

    return 0
}

