package dev.burak.aoc.day2

import dev.burak.aoc.utils.ResourceUtils

class Dive

fun main() {
    val commands = ResourceUtils.readDataSet(Dive::class.java)

    solve(commands)
    solve(commands, true)
}

fun solve(commands: List<String>, part2: Boolean = false) {
    var x = 0
    var depth = 0
    var aim = 0

    commands.forEach {
        val command = parseCommand(it)
        val horizontal = command.first
        val moves = command.second

        if (horizontal) {
            x += moves
            if (part2) {
                depth += (aim * moves)
            }
        } else {
            if (!part2) {
                depth += moves
            }

            aim += moves
        }
    }

    println("Final position: ${x * depth}")
}

fun parseCommand(value: String): Pair<Boolean, Int> {
    val split = value.split(" ")
    val command = split[0]
    val moves = split[1].toInt()

    // True if horizontal movement
    // Y coordinate is inverse, because we are measuring depth
    return when (command) {
        "forward" -> true to moves
        "up" -> false to -moves
        "down" -> false to moves
        else -> throw Exception("Unrecognized command: $command")
    }
}
