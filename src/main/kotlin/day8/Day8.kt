package day8

import extensions.alphabetized
import java.io.File

private const val TEST = true

fun main() {
    val segments = parseInput()
    dayEightPuzzleOne(segments)
    dayEightPuzzleTwo(segments)
}

/**
 * Returns list of arrays that hold the segments, the segments are ordered alphabetically
 */
private fun parseInput(): List<Array<String>> {
    val input =
        if (TEST) File("src/main/kotlin/day8/test-input.txt").readLines()
        else File("src/main/kotlin/day8/input.txt").readLines()

    val listOfSegments = input.map { it.replace(" | ", " ").split(" ").toTypedArray() }
    return listOfSegments.map { array -> array.map { segment -> segment.alphabetized() }.toTypedArray() }

}

fun dayEightPuzzleOne(segments: List<Array<String>>) {
    val outputs = segments.map { it.copyOfRange(10, 14) }
    var amountUniqueDigitSegment = 0
    outputs.forEach { output ->
        output.forEach { segment ->
            if (segment.length == 2 || segment.length == 3 || segment.length == 4 || segment.length == 7) amountUniqueDigitSegment += 1
        }
    }
    println("There are totaly $amountUniqueDigitSegment unique segments (digit 1, 4, 7, 8)")
}

fun dayEightPuzzleTwo(segments: List<Array<String>>) {
    val convertedSegments = mutableListOf<IntArray>()
    segments.forEach { segmentRow ->
        var converter: Array<String> = findUniqueDigitSegment(segmentRow)
        converter = findOtherDigitSegments(segmentRow, converter)
        convertedSegments.add(convertSegmentsToDigits(segmentRow, converter))
        }
    val outputs = convertedSegments.map { it.copyOfRange(10, 14).joinToString().replace(", ", "").toInt() }
    val sum = outputs.sum()
    println("The sum of outputs are $sum")
}

/**
 * Finds unique number (1, 4, 7, 8) segments and adds them to their corresponding order in array
 * segment for 1 goes to converter[1] segment for 4 gors to converter[4] and so on
 */
fun findUniqueDigitSegment(segmentRow: Array<String>): Array<String> {
    val converter = arrayOf("","","","","","","","","","")
    converter[1] = segmentRow.find { it.length == 2 }.toString()
    converter[4] = segmentRow.find { it.length == 4 }.toString()
    converter[7] = segmentRow.find { it.length == 3 }.toString()
    converter[8] = segmentRow.find { it.length == 7 }.toString()
    return converter
}

fun findOtherDigitSegments(segments: Array<String>, uniqueNumberSegments: Array<String>): Array<String> {
    segments.forEach { segment ->
        if (!uniqueNumberSegments.contains(segment)) {
            val one = countMatchingCharacters(segment, uniqueNumberSegments[1])
            val four = countMatchingCharacters(segment, uniqueNumberSegments[4])
            val seven = countMatchingCharacters(segment, uniqueNumberSegments[7])

            when {
                segment.length == 6 && one == 2 && four == 3 && seven == 3 -> uniqueNumberSegments[0] = segment
                segment.length == 5 && one == 1 && four == 2 && seven == 2 -> uniqueNumberSegments[2] = segment
                segment.length == 5 && one == 2 && four == 3 && seven == 3 -> uniqueNumberSegments[3] = segment
                segment.length == 5 && one == 1 && four == 3 && seven == 2 -> uniqueNumberSegments[5] = segment
                segment.length == 6 && one == 1 && four == 3 && seven == 2 -> uniqueNumberSegments[6] = segment
                segment.length == 6 && one == 2 && four == 4 && seven == 3 -> uniqueNumberSegments[9] = segment
            }
        }
    }
    return uniqueNumberSegments
}

fun countMatchingCharacters(segment: String, uniqueNumberSegment: String): Int {
    var count = 0
    segment.forEach { segmentChar ->
        if (uniqueNumberSegment.contains(segmentChar)) count += 1
    }
    return count
}

fun convertSegmentsToDigits(segmentRow: Array<String>, converter: Array<String>): IntArray {
    val convertedSegment = IntArray(14) { -1 }
    converter.forEachIndexed { digit, digitSegment ->
        segmentRow.forEachIndexed { i, segment ->
            if (segment == digitSegment) convertedSegment[i] = digit
        }
    }
    return convertedSegment
}

