package day4

class Day4Puzzle1 : Day4() {
    init {
        createDrawNumbers(input) { drawNumbers ->
            createBoards(input) { boards ->
                playBingo(drawNumbers, boards) { score ->
                    println("The score is $score")
                }
            }
        }
    }

    /**
     * Play Bingo! Set -1 to board that represent marked numbers
     * */
    private fun playBingo(drawNumbers: List<Int>, boards: List<Array<IntArray>>, score: (Int) -> Unit) {
        drawNumbers.forEach { number ->
            boards.forEach { board ->
                for (currentRow in board) {
                    val numberInColumn = currentRow.indexOf(number)
                    if (numberInColumn >= 0) currentRow[numberInColumn] = MARKED_NUMBER
                }
            }
            if (nextTimeToCheckForFiveInARow == 0) {
                checkFiveInARow(boards)?.let { winningBoardIndex ->
                    return@playBingo score(calculateScore(boards[winningBoardIndex], number))
                }
            } else {
                nextTimeToCheckForFiveInARow -= 1
            }
        }
    }

    /**
     * Check for if five number are marked in a row (vertical and horizontal)
     * @return the board index that have five number marked in a row
     */
    private fun checkFiveInARow(boards: List<Array<IntArray>>): Int? {
        var mostInARow = 0

        boards.forEachIndexed { boardIndex, board ->
            val markedNumbersInColumn = IntArray(5)
            for (currentRow in board) {
                var markedNumbersInRow = 0
                currentRow.forEachIndexed { rowIndex, number ->
                    if (number == MARKED_NUMBER) {
                        markedNumbersInRow += 1
                        markedNumbersInColumn[rowIndex] += 1
                    }
                    if (markedNumbersInRow == 5 || markedNumbersInColumn.contains(5)) return boardIndex
                    if (markedNumbersInRow > mostInARow) mostInARow = markedNumbersInRow
                }
            }
            // We have 4 in a row, so check will be done after every new draw
            if (mostInARow == 4) nextTimeToCheckForFiveInARow = 0
            else {
                markedNumbersInColumn.forEach { if (it > mostInARow) mostInARow = it }
                nextTimeToCheckForFiveInARow = 4 - mostInARow
            }
        }
        return null
    }
}