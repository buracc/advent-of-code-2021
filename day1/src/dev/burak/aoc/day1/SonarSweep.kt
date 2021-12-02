package dev.burak.aoc.day1

import dev.burak.aoc.utils.ResourceUtils

class SonarSweep

fun main() {
    val dataSet = ResourceUtils.readDataSet(SonarSweep::class.java).map { it.toInt() }

    part1(dataSet)
    part2(dataSet)
}

fun part1(dataSet: List<Int>) {
    println(dataSet
        .filterIndexed { i, value -> i != 0 && dataSet[i - 1] < value }
        .count()
    )
}

fun part2(dataSet: List<Int>) {
    var count = 0
    for (i in dataSet.indices) {
        if (i == 0) {
            continue
        }

        val curSum = getSum(dataSet, i)
        val prevSum = getSum(dataSet, i - 1)
        if (curSum == Int.MAX_VALUE || prevSum == Int.MAX_VALUE) {
            break
        }

        if (curSum > prevSum) {
            count++
        }
    }

    println(count)
}

fun getSum(dataSet: List<Int>, index: Int): Int {
    if (index + 2 > dataSet.lastIndex) {
        return Int.MAX_VALUE
    }

    return dataSet[index] + dataSet[index + 1] + dataSet[index + 2]
}
