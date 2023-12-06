package day05

import process
import readInput


fun main() {
    process(readInput("aoc2023/day05/input.txt"), ::solvePart2)
}

// Apple M2 Max CPU took 3.56 minutes
fun solvePart2WithBruteForce(input: List<String>) {
    val inputObj = getInputObj(input)
    val minLocation = inputObj.seeds.chunked(2).minOf { seed ->
        val seedRange = seed[0]..<seed[0] + seed[1]
        inputObj.seeds = seedRange
        getMinLocation(inputObj)
    }

    println(minLocation)
}

// Apple M2 Max CPU took 16 ms
fun solvePart2(input: List<String>) {
    val inputObj = getInputObj(input)
    val minLocation = inputObj.seeds.chunked(2).minOf { seed ->
        val seedRange = seed[0]..<seed[0] + seed[1]
        val soilRanges = getTargetRanges(mutableListOf(seedRange), inputObj.seedToSoil)
        val fertilizerRanges = getTargetRanges(soilRanges, inputObj.soilToFertilizer)
        val waterRanges = getTargetRanges(fertilizerRanges, inputObj.fertilizerToWater)
        val lightRanges = getTargetRanges(waterRanges, inputObj.waterToLight)
        val tempRanges = getTargetRanges(lightRanges, inputObj.lightToTemp)
        val humidityRanges = getTargetRanges(tempRanges, inputObj.tempToHumidity)
        val locationRanges = getTargetRanges(humidityRanges, inputObj.humidityToLocation)
        locationRanges.minOf { it.first }
    }

    println(minLocation)
}

fun getTargetRanges(sourceRanges: MutableList<LongRange>, maps: MutableList<Map>): MutableList<LongRange> {
    val targetRanges = mutableListOf<LongRange>()
    sourceRanges.forEach { sourceRange ->
        val mappedSourceRanges = mutableListOf<LongRange>()
        maps.forEach { map ->
            val overlap = getOverlap(sourceRange, map.from..<map.from + map.size)
            if (!overlap.isEmpty()) {
                mappedSourceRanges.add(overlap)
                val targetRange = getTargetRange(overlap, map)
                targetRanges.add(targetRange)
            }
        }

        targetRanges.addAll(getUnmappedRanges(sourceRange, mappedSourceRanges))
    }

    return targetRanges
}

fun getUnmappedRanges(range: LongRange, mappedRanges: MutableList<LongRange>): MutableList<LongRange> {
    if (mappedRanges.isEmpty()) {
        return mutableListOf(range)
    }

    val unmappedRanges = mutableListOf<LongRange>()
    mappedRanges.sortBy {
        it.first()
    }

    var currentRange = range
    mappedRanges.forEach {
        val unmappedRange = currentRange.first..<it.first
        if (!unmappedRange.isEmpty()) {
            unmappedRanges.add(unmappedRange)
        }

        currentRange = it.last + 1..currentRange.last
    }

    return unmappedRanges
}

// this method assumes source range must be inside of map.from ..< map.from + map.size range
fun getTargetRange(sourceRange: LongRange, map: Map): LongRange {
    val sourceRangeSize = sourceRange.last - sourceRange.first + 1
    val targetRangeStart = sourceRange.first - map.from + map.to
    return targetRangeStart..<targetRangeStart + sourceRangeSize
}

fun getOverlap(range1: LongRange, range2: LongRange): LongRange {
    return Math.max(range1.first, range2.first)..Math.min(range1.last, range2.last)
}