package dev.burak.aoc.day5

import dev.burak.aoc.utils.ResourceUtils

class HydrothermalVenture

fun main() {
    val dataSet = ResourceUtils.readDataSet(HydrothermalVenture::class.java)

    solve(dataSet)
    solve(dataSet, true)
}

fun solve(dataSet: List<String>, part2: Boolean = false) {
    val edges = dataSet.map {
        val split = it.split(" -> ")
        val (x, y) = split[0].split(",").map { s -> s.trim().toInt() }
        val (toX, toY) = split[1].split(",").map { s -> s.trim().toInt() }
        Edge(Coordinate(x, y), Coordinate(toX, toY))
    }

    val totalCoverage = mutableMapOf<Coordinate, Int>()
    edges.forEach {
        it.getCoverage(part2).forEach { c ->
            totalCoverage[c] = totalCoverage.getOrDefault(c, 0) + 1
        }
    }

    println(totalCoverage.filter { it.value > 1 }.keys.size)
}

data class Coordinate(val x: Int, val y: Int)

data class Edge(val from: Coordinate, val to: Coordinate) {
    fun getCoverage(part2: Boolean = false): List<Coordinate> {
        val out = mutableListOf<Coordinate>()

        if (isVertical()) {
            for (i in from.y toward to.y) {
                out.add(Coordinate(from.x, i))
            }

            return out
        }

        if (isHorizontal()) {
            for (i in from.x toward to.x) {
                out.add(Coordinate(i, from.y))
            }

            return out
        }

        if (!part2) {
            return out
        }

        val xChange = if (to.x < from.x) -1 else 1
        val yChange = if (to.y < from.y) -1 else 1

        var x = from.x
        var y = from.y

        out.add(Coordinate(x, y))

        while (x != to.x && y != to.y) {
            x += xChange
            y += yChange
            out.add(Coordinate(x, y))
        }

        return out
    }

    fun isVertical(): Boolean {
        return from.x == to.x
    }

    fun isHorizontal(): Boolean {
        return from.y == to.y
    }
}

private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}
