package extensions

import Cell

fun String.alphabetized() = toCharArray().sorted().joinToString("")

fun Array<IntArray>.findNeighbors(x: Int, y: Int, diagonal: Boolean = true): List<Cell> = listOfNotNull(
       if (diagonal) getOrNull(y.above())?.getOrNull(x.left())?.let { Cell(x.left(), y.above()) } else null,
       getOrNull(y.above())?.getOrNull(x)?.let { Cell(x, y.above()) },
       if (diagonal) getOrNull(y.above())?.getOrNull(x.right())?.let { Cell(x.right(), y.above()) } else null,
       getOrNull(y)?.getOrNull(x.left())?.let { Cell(x.left(), y) },
       getOrNull(y)?.getOrNull(x.right())?.let { Cell(x.right(), y) },
       if (diagonal) getOrNull(y.below())?.getOrNull(x.left())?.let { Cell(x.left(), y.below()) } else null,
       getOrNull(y.below())?.getOrNull(x)?.let { Cell(x, y.below()) },
       if (diagonal) getOrNull(y.below())?.getOrNull(x.right())?.let { Cell(x.right(), y.below()) } else null
)

fun Int.above() = this - 1
fun Int.below() = this + 1
fun Int.left() = this - 1
fun Int.right() = this + 1

fun Array<IntArray>.print(rowBreakAfterMatrix: Boolean = true) {
       map { println(it.contentToString()) }
       if (rowBreakAfterMatrix) println(" ")
}