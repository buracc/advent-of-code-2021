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
        .windowed(2)
        .count { (prev, cur) -> prev < cur }
    )
}

fun part2(dataSet: List<Int>) {
    println(dataSet
        .windowed(4)
        .count { it.first() < it.last() }
    )
}
