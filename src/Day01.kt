import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.reader

fun distance(a:Int, b:Int):Int {
    if (a == b) return 0
    else if (a > b) return a - b
    else return b - a
}

fun main() {
    fun part1(input: List<Int>): Int {
        return input.size
    }

    fun part2(input: List<Int>): Int {
        return input.size
    }


    // Read the input from the `src/Day01.txt` file.
    var group1 = listOf<Int>()
    var group2 = listOf<Int>()
    Path("src/Day01.txt").reader().forEachLine { line -> run {
        val (entry1,entry2) =line.trim().split("   ")
        group1 = group1.plus(entry1.toInt())
        group2 = group2.plus(entry2.toInt())
    } }
    group1 = group1.sorted()
    group2 = group2.sorted()

    val groupPairs = group1.zip(group2)

    val resultPart1: Int = groupPairs.map { distance(it.first, it.second) }.reduce { acc, i -> acc + i }

    resultPart1.println()

    var g2Counts: MutableMap<Int, Int>  = group2
        .distinct()
        .map { mutableMapOf<Int, Int>(Pair(it, 0)) }
        .reduce { acc, mutableMap -> run {
            acc.putAll(mutableMap)
            acc
        }}

    group2.forEach {  run {
        val x = g2Counts.getValue(it)
        g2Counts[it] = x + 1
    } }

    val result2 = group1
        .map { it * g2Counts.getOrDefault(it, 0) }   // count * multiplier
        .reduce { acc, i -> acc + i }

    println(result2)


}
