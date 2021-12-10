package day4

import java.io.File

open class Day4 {
//    open val input = File("src/main/kotlin/day4/test-input.txt").readLines()
//    open val input = File("src/main/kotlin/day4/test-input-hor-column-win.txt").readLines()
    open val input = File("src/main/kotlin/day4/input.txt").readLines()


    open fun createDrawNumbers(input: List<String>, drawNumbers: (List<Int>) -> Unit) =
        drawNumbers(input.first().split(',').map(String::toInt))

    open fun createBoards(input: List<String>, createdBoards: (List<Array<IntArray>>) -> Unit) {
        val boards = mutableListOf<Array<IntArray>>()
        input.forEachIndexed { rowIndex, rowString ->
            if (rowString.isBlank()) {
                val boardAsStrings = input.subList(rowIndex + 1, rowIndex + 6)
                boards.add(createBoard(boardAsStrings))
            }
        }
        createdBoards(boards)
    }

    private fun createBoard(boardAsStrings: List<String>): Array<IntArray> {
        val boardArray: Array<IntArray> = arrayOf(IntArray(5), IntArray(5), IntArray(5), IntArray(5), IntArray(5))
        boardAsStrings.forEachIndexed { rowIndex, rowString ->
            val formattedRowString = formatRowString(rowString)
            boardArray[rowIndex] = formattedRowString.split(',').map(String::toInt).toIntArray()
        }
        return boardArray
    }

    /** Replace all space and double spaces with comma (,)*/
    private fun formatRowString(rowString: String): String {
        val formattedRowString: String =
            if (rowString[0] == ' ') rowString.substring(1, rowString.length) else rowString
        return formattedRowString.replace("  ", ",").replace(' ', ',')
    }

    open fun calculateScore(winningBoard: Array<IntArray>, lastNumber: Int): Int {
        var sumOfUnmarkedNumbers = 0
        winningBoard.forEach { row ->
            row.forEach { number ->
                if (number != MARKED_NUMBER) sumOfUnmarkedNumbers += number
            }
        }
        return sumOfUnmarkedNumbers * lastNumber
    }

    companion object {
        const val MARKED_NUMBER = -1
        var nextTimeToCheckForFiveInARow = 4
    }
}
