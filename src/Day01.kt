import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.reader

fun distance(a:Int, b:Int):Int {
    if (a == b) return 0
    else if (a > b) return a - b
    else return b - a
}
fun part1(l: List<Int>,r: List<Int>): Int {

    val listPairs = l.sorted().zip(r.sorted())

    val result: Int = listPairs.map { distance(it.first, it.second) }.reduce { acc, i -> acc + i }

    return result
}

fun part2(l: List<Int>,r: List<Int>): Int {
    var rCounts: MutableMap<Int, Int>  = r.sorted()
        .distinct()
        .map { mutableMapOf<Int, Int>(Pair(it, 0)) }
        .reduce { acc, mutableMap -> run {
            acc.putAll(mutableMap)
            acc
        }}

    r.sorted().forEach {  run {
        val x = rCounts.getValue(it)
        rCounts[it] = x + 1
    } }

    val result = l.sorted()
        .map { it * rCounts.getOrDefault(it, 0) }   // count * multiplier
        .reduce { acc, i -> acc + i }

    return result
}

fun main() {

    // Read the input from the `src/Day01.txt` file.
    var group1 = listOf<Int>()
    var group2 = listOf<Int>()
    Path("src/Day01.txt").reader().forEachLine { line -> run {
        val (entry1,entry2) =line.trim().split("   ")
        group1 = group1.plus(entry1.toInt())
        group2 = group2.plus(entry2.toInt())
    } }

    part1(group1,group2).println()
    part2(group1,group2).println()

}
