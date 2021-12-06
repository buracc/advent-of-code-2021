package dev.burak.aoc.day4

import dev.burak.aoc.utils.ResourceUtils
import java.util.*

class GiantSquid

const val BOARD_SIZE = 5

fun main() {
    val dataSet = ResourceUtils.readDataSet(GiantSquid::class.java)

    solve(dataSet)
}

fun solve(dataSet: List<String>) {
    val pulledNumbers = dataSet.first().split(",").map { it.toInt() }
    val bingoBoards = dataSet
        .asSequence()
        .drop(1)
        .filter { it.isNotBlank() }
        .chunked(BOARD_SIZE)
        .map { board ->
            board.map {
                it.trim()
                    .split(" ")
                    .filter { number ->
                        number.isNotBlank()
                    }
                    .associate { number ->
                        number.toInt() to false
                    }
                    .toMutableMap()
            }.toMutableList()
        }.map {
            BingoBoard(it)
        }
        .toList()

    val winners = LinkedList<BingoBoard>()

    for (n in pulledNumbers) {
        for (board in bingoBoards) {
            board.pull(n)

            if (board.isBingo() && !winners.contains(board)) {
                board.score = board.getUnmarkedNumbers().sum() * n
                winners.addLast(board)
            }
        }
    }

    println("First winner: ${winners.first.score}")
    println("Last winner: ${winners.last.score}")
}

data class BingoBoard(val rows: MutableList<MutableMap<Int, Boolean>>) {
    val columns: MutableList<MutableMap<Int, Boolean>> = mutableListOf()
    var score = 0

    init {
        for (row in rows) {
            columns.add(mutableMapOf())
        }

        for (row in rows) {
            val rowNumbers = row.keys.toList()

            repeat(row.size) {
                columns[it][rowNumbers[it]] = false
            }
        }
    }

    fun pull(number: Int) {
        rows.forEach { row -> if (row.containsKey(number)) row.replace(number, true) }
        columns.forEach { col -> if (col.containsKey(number)) col.replace(number, true) }
    }

    fun getUnmarkedNumbers(): Set<Int> {
        val unmarkedNumbers = mutableSetOf<Int>()

        rows.forEach { row -> unmarkedNumbers.addAll(row.filter { !it.value }.keys) }
        columns.forEach { col -> unmarkedNumbers.addAll(col.filter { !it.value }.keys) }

        return unmarkedNumbers
    }

    fun isBingo(): Boolean {
        return rows.any { it.values.all { r -> r } } || columns.any { it.values.all { c -> c } }
    }
}
