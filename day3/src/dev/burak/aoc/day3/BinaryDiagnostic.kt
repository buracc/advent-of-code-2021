package dev.burak.aoc.day3

import dev.burak.aoc.utils.ResourceUtils

class BinaryDiagnostic

fun main() {
    val dataSet = ResourceUtils.readDataSet(BinaryDiagnostic::class.java)

    part1(dataSet)
    part2(dataSet)
}

fun part1(dataSet: List<String>) {
    var gammaBits = ""
    val size = dataSet.first().length

    repeat(size) { index ->
        gammaBits += dataSet.map { it[index] }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }?.key
    }

    val gamma = gammaBits.toInt(2)
    val epsilon = gammaBits.replace("0", "x")
        .replace("1", "0")
        .replace("x", "1")
        .toInt(2)

    println(gamma * epsilon)
}

fun part2(dataSet: List<String>) {
    val size = dataSet.first().length
    var oxygenBits = dataSet
    var scrubberBits = dataSet

    repeat(size) { index ->
        if (oxygenBits.size == 1) {
            return@repeat
        }

        oxygenBits = oxygenBits.filter { s ->
            val counts = oxygenBits.map { it[index] }
                .groupingBy { it }
                .eachCount()
            val zeros = counts['0']
            val ones = counts['1']
            val most = if (zeros == ones) {
                '1'
            } else {
                counts.maxByOrNull { it.value }?.key
            }

            s[index] == most
        }
    }

    repeat(size) { index ->
        if (scrubberBits.size == 1) {
            return@repeat
        }

        scrubberBits = scrubberBits.filter { s ->
            val counts = scrubberBits.map { it[index] }
                .groupingBy { it }
                .eachCount()
            val zeros = counts['0']
            val ones = counts['1']
            val most = if (zeros == ones) {
                '0'
            } else {
                counts.minByOrNull { it.value }?.key
            }

            s[index] == most
        }
    }

    println(oxygenBits)
    println(scrubberBits)

    val oxygen = oxygenBits.first().toInt(2)
    val scrubber = scrubberBits.first().toInt(2)
    println(oxygen * scrubber)
}
