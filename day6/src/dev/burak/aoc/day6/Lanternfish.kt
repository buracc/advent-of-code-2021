package dev.burak.aoc.day6

import dev.burak.aoc.utils.ResourceUtils

class Lanternfish

const val MAX_DAYS = 8

fun main() {
    val input = ResourceUtils.readDataSet(Lanternfish::class.java)
        .first()
        .split(",")
        .map { d -> d.toInt() }
        .groupingBy { it }
        .eachCount()
        .let { fish -> List(MAX_DAYS + 1) { fish[it]?.toLong() ?: 0L } }

    solve(input, 80)
    solve(input, 256)
}

fun solve(input: List<Long>, days: Int) {
    // Generate a new sequence using the input
    val fishes = generateSequence(input) {
        it.mapIndexed { index, _ ->
            when (index) {
                6 -> it[0] + it[7]      // Reset fish that just gave birth, and add fish that had 7 days left
                8 -> it[0]              // Newly born fish
                else -> it[index + 1]   // All other fish, cycle days
            }
        }
    }.drop(days).first() // Drop the 0..days-1 in the sequence, take the first (max) day.
                         // The dropped lists are not needed because they contain the fish of the generated sequences
                         // between the 0..days-1 days.

    println(fishes.sum())
}
