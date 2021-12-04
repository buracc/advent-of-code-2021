package dev.burak.aoc.utils

import java.nio.file.Files
import java.nio.file.Paths

object ResourceUtils {
    fun readDataSet(clazz: Class<*>): List<String> {
        val uri = clazz.getResource("dataset.txt")?.toURI() ?: error("Dataset not present")
        return Files.readAllLines(Paths.get(uri))
    }
}
