import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.reader
import kotlin.math.abs


fun main() {
    fun checkLevel(levels: List<Long>): Boolean {
        val nextLevel = levels.subList(1,levels.size)
        val deltas = levels.zip(nextLevel).map { it.second - it.first }.toList()
        require(deltas.size == levels.size - 1)
        val smallest: Long = deltas.min()
        val largest: Long = deltas.max()
        if (smallest == 0L || largest == 0L){
            // must differ by at least one
            return false
        }
        if (abs(largest) > 3L || abs(smallest) > 3L){
            // must differ by at most three
            return false
        }

        val sum = deltas.sum()
        val magnitude = deltas.sumOf { abs(it) }

        if (abs(sum) != abs(magnitude)){
            // must be ascending or descending only
            return false

        }
        return true
    }
    fun part1(reports: List<List<Long>>): Long {

        var safeCount: Long = 0
        for (levels in reports){
            if (checkLevel(levels)){
                safeCount++
            }
        }

        return safeCount
    }

    fun part2(reports: List<List<Long>>): Long {

        var safeCount: Long = 0

        for (levels in reports){
            if (checkLevel(levels)){
                safeCount++
            } else {
                var validDampenedList = false
                for ( x in 0..< levels.size){
                    val dampenedList = levels.filterIndexed { index, l -> index!=x }
                    if (checkLevel(dampenedList)){
                        validDampenedList=true
                        break
                    }
                }
                if (validDampenedList){
                    safeCount++
                }
            }
        }

        return safeCount
    }

    // Read the input from the `src/Day01.txt` file.
    var reports = listOf<List<Long>>()
    Path("src/Day02.txt").reader().forEachLine { line -> run {
        val report =line
        val levels: List<Long> = report.split(' ').map { it.toLong() }.toList()
        reports = reports.plus(listOf(levels) )
    } }

    part1(reports).println()
    part2(reports).println()
}
