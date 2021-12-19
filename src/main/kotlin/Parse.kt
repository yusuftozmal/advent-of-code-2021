import java.io.File

fun parseFileToTwoDimensionalArray(path: String): Array<IntArray> =
    File(path).readLines().toTypedArray().map { row ->
        row.split("")
            .filter { it.isNotBlank() }
            .map { value -> value.toInt() }
            .toIntArray()
    }.toTypedArray()