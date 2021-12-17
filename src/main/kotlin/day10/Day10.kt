package day10

import java.io.File

private const val TEST = false

fun main() {
    Day10().dayTenPuzzleOne()
    Day10().dayTenPuzzleTwo()
}
class Day10 {
    private val navigationSystem by lazy { parseInput() }

    fun dayTenPuzzleOne() {
        val wrongTokens = mutableListOf<String>()
        navigationSystem.forEach navigationLoop@{ line ->
            val expectedCloseTokens = mutableListOf<String>()
            line.forEach lineLoop@{ token ->
                if (isCloseToken(token)) {
                    when (expectedCloseTokens.isExpectedCloseToken(token)) {
                        true -> {
                            expectedCloseTokens.removeAt(expectedCloseTokens.lastIndex)
                            return@lineLoop
                        }
                        false -> {
                            wrongTokens.add(token)
                            return@navigationLoop
                        }
                    }
                }
                when (token) {
                    OPEN_PARENTHESES -> expectedCloseTokens.add(CLOSE_PARENTHESES)
                    OPEN_SQUARE_BRACKET -> expectedCloseTokens.add(CLOSE_SQUARE_BRACKET)
                    OPEN_CURLY_BRACKET -> expectedCloseTokens.add(CLOSE_CURLY_BRACKET)
                    else -> expectedCloseTokens.add(CLOSE_ANGLE_BRACKET)
                }
            }
        }
        println("Total syntax error score is ${calculateSyntaxErrorScore(wrongTokens)}")
    }

    fun dayTenPuzzleTwo() {
        val missingCloseTokens = mutableListOf<List<String>>()
        navigationSystem.forEach navigationLoop@{ line ->
            val expectedCloseTokens = mutableListOf<String>()
            line.forEach lineLoop@{ token ->
                if (isCloseToken(token)) {
                    when (expectedCloseTokens.isExpectedCloseToken(token)) {
                        true -> {
                            expectedCloseTokens.removeAt(expectedCloseTokens.lastIndex)
                            return@lineLoop
                        }
                        false -> {
                            // Corrupt line, continue with next line
                            return@navigationLoop
                        }
                    }
                }
                when (token) {
                    OPEN_PARENTHESES -> expectedCloseTokens.add(CLOSE_PARENTHESES)
                    OPEN_SQUARE_BRACKET -> expectedCloseTokens.add(CLOSE_SQUARE_BRACKET)
                    OPEN_CURLY_BRACKET -> expectedCloseTokens.add(CLOSE_CURLY_BRACKET)
                    else -> expectedCloseTokens.add(CLOSE_ANGLE_BRACKET)
                }
            }
            missingCloseTokens.add(expectedCloseTokens.reversed())
        }
        println("The missing close token score is ${calculateMissingCloseTokenScore(missingCloseTokens)}")
    }

    private fun calculateMissingCloseTokenScore(missingCloseTokens: MutableList<List<String>>): Long {
        val scores = mutableListOf<Long>()
        missingCloseTokens.forEach { tokens ->
            var score = 0L
            tokens.forEach { token ->
                score = (5 * score)
                score += when (token) {
                    CLOSE_PARENTHESES -> 1
                    CLOSE_SQUARE_BRACKET -> 2
                    CLOSE_CURLY_BRACKET -> 3
                    else -> 4
                }
            }
            scores.add(score)
        }

        scores.sort()
        val middleScore = scores.lastIndex.div(2)

        return scores[middleScore]
    }

    private fun calculateSyntaxErrorScore(wrongTokens: MutableList<String>): Int {
        var score = 0
        wrongTokens.forEach {
            score += when (it) {
                CLOSE_PARENTHESES -> 3
                CLOSE_SQUARE_BRACKET -> 57
                CLOSE_CURLY_BRACKET -> 1197
                else -> 25137
            }
        }
        return score
    }

    private fun isCloseToken(token: String) = closeTokens.contains(token)

    companion object {
        const val OPEN_PARENTHESES = "("
        const val OPEN_SQUARE_BRACKET = "["
        const val OPEN_CURLY_BRACKET = "{"
        const val CLOSE_PARENTHESES = ")"
        const val CLOSE_SQUARE_BRACKET = "]"
        const val CLOSE_CURLY_BRACKET = "}"
        const val CLOSE_ANGLE_BRACKET = ">"
        private val closeTokens = listOf(CLOSE_PARENTHESES, CLOSE_SQUARE_BRACKET, CLOSE_CURLY_BRACKET, CLOSE_ANGLE_BRACKET)
    }
}

private fun <E> MutableList<E>.isExpectedCloseToken(token: String) = last() == token

private fun parseInput(): Array<Array<String>> {
    val input = if(TEST) File("src/main/kotlin/day10/test-input.txt").readLines().toTypedArray()
    else File("src/main/kotlin/day10/input.txt").readLines().toTypedArray()

    val arraysOfInputTokens = mutableListOf<Array<String>>()
    input.forEach { inputRow ->
        val inputTokens = mutableListOf<String>()
        inputRow.split("").forEach { char ->
            if (char.isNotBlank()) inputTokens.add(char)
        }
        arraysOfInputTokens.add(inputTokens.toTypedArray())
    }

    return arraysOfInputTokens.toTypedArray()
}