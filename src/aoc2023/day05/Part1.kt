package day05

import process
import readInput

class Map(
        val from: Long,
        val to: Long,
        val size: Long
) {
    override fun toString(): String {
        return "{from: $from, to: $to, size: $size}"
    }
}

class Input(
        var seeds: Iterable<Long>,
        val seedToSoil: MutableList<Map>,
        val soilToFertilizer: MutableList<Map>,
        val fertilizerToWater: MutableList<Map>,
        val waterToLight: MutableList<Map>,
        val lightToTemp: MutableList<Map>,
        val tempToHumidity: MutableList<Map>,
        val humidityToLocation: MutableList<Map>,
)

fun main() {
    process(readInput("aoc2023/day05/input.txt"), ::solvePart1)
}

fun solvePart1(input: List<String>) {
    val inputObj = getInputObj(input)
    val minLocation = getMinLocation(inputObj)
    println(minLocation)
}

fun getInputObj(input: List<String>): Input {
    val firstLine = input[0].split(" ")
    val seeds = firstLine.subList(1, firstLine.size).map { it.toLong() }
    val seedToSoil = mutableListOf<Map>()
    val soilToFertilizer = mutableListOf<Map>()
    val fertilizerToWater = mutableListOf<Map>()
    val waterToLight = mutableListOf<Map>()
    val lightToTemp = mutableListOf<Map>()
    val tempToHumidity = mutableListOf<Map>()
    val humidityToLocation = mutableListOf<Map>()
    var currentMap = seedToSoil

    for (i in 1..<input.size) {
        when (val line = input[i]) {
            "seed-to-soil map:" -> currentMap = seedToSoil
            "soil-to-fertilizer map:" -> currentMap = soilToFertilizer
            "fertilizer-to-water map:" -> currentMap = fertilizerToWater
            "water-to-light map:" -> currentMap = waterToLight
            "light-to-temperature map:" -> currentMap = lightToTemp
            "temperature-to-humidity map:" -> currentMap = tempToHumidity
            "humidity-to-location map:" -> currentMap = humidityToLocation
            "" -> Unit
            else -> {
                currentMap.add(parseMap(line))
            }
        }
    }

    return Input(seeds, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHumidity, humidityToLocation)
}

fun getMinLocation(input: Input): Long {
    return input.seeds.minOf {
        val soil = getTarget(it, input.seedToSoil)
        val fertilizer = getTarget(soil, input.soilToFertilizer)
        val water = getTarget(fertilizer, input.fertilizerToWater)
        val light = getTarget(water, input.waterToLight)
        val temp = getTarget(light, input.lightToTemp)
        val humidity = getTarget(temp, input.tempToHumidity)
        getTarget(humidity, input.humidityToLocation)
    }
}

fun getTarget(seed: Long, map: MutableList<Map>): Long {
    map.forEach {
        if (seed in it.from..<it.from + it.size) {
            return it.to + seed - it.from
        }
    }

    return seed
}

fun parseMap(line: String): Map {
    val list = line.split(" ").map { it.toLong() }
    return Map(list[1], list[0], list[2])
}