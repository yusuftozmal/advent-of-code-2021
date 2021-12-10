package day4

class Day4Puzzle2 : Day4() {
    private val winningBoardIndexes = mutableListOf<Int>()
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
     * Play Bingo! Find the last winning board
     * Set -1 to board that represent marked numbers
     * */
    private fun playBingo(drawNumbers: List<Int>, boards: List<Array<IntArray>>, score: (Int) -> Unit) {
        val leftBoardIndexes = mutableListOf<Int>()
        boards.forEachIndexed { index, _ -> leftBoardIndexes.add(index) }

        drawNumbers.forEachIndexed { numberIndex, number ->
            boards.forEachIndexed boardsLoop@{ boardIndex, board ->
                if (winningBoardIndexes.contains(boardIndex)) return@boardsLoop // Do not check boards that already has a win
                for (currentRow in board) {
                    val numberInColumn = currentRow.indexOf(number)
                    if (numberInColumn >= 0) {
                        currentRow[numberInColumn] = MARKED_NUMBER
                        return@boardsLoop // A number can only exist once in a board, go to next board
                    }
                }
            }

            if (number == 9) {
                val dummyStop = 1
            }
            val winningBoardsThisRoundIndexes = checkFiveInARow(boards)
            if (winningBoardsThisRoundIndexes.isNotEmpty()) {
                winningBoardIndexes.addAll(winningBoardsThisRoundIndexes)
                if (leftBoardIndexes.size == 1) return@playBingo score(calculateScore(boards[leftBoardIndexes[0]], number))
                else if (drawNumbers.lastIndex == numberIndex) return@playBingo score(calculateScore(boards[winningBoardIndexes[winningBoardIndexes.lastIndex]], number))
                else leftBoardIndexes.removeAll(winningBoardsThisRoundIndexes)
            }
        }
    }

    /**
     * Check for if five number are marked in a row (vertical and horizontal)
     * @return the board index that have five number marked in a row
     */
    private fun checkFiveInARow(boards: List<Array<IntArray>>): List<Int> {
        val winningBoardsThisRound = mutableListOf<Int>()

        boards.forEachIndexed boardLoop@{ boardIndex, board ->
            if (winningBoardIndexes.contains(boardIndex)) return@boardLoop
            val markedNumbersInColumn = IntArray(5)
            for (currentRow in board) {
                var markedNumbersInRow = 0
                currentRow.forEachIndexed { rowIndex, number ->
                    if (number == MARKED_NUMBER) {
                        markedNumbersInRow += 1
                        markedNumbersInColumn[rowIndex] += 1
                    }
                    if (markedNumbersInRow == 5 || markedNumbersInColumn.contains(5)) {
                        winningBoardsThisRound.add(boardIndex)
                        return@boardLoop
                    }
                }
            }
        }
        return winningBoardsThisRound
    }
}