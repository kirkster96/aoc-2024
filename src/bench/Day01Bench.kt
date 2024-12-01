package bench

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import org.openjdk.jmh.annotations.Scope
import part1
import part2
import kotlin.io.path.Path
import kotlin.io.path.reader

@State(Scope.Benchmark)
class Day01Bench {
    lateinit var group1: List<Int>
    lateinit var group2: List<Int>

    @Setup
    fun setUp() {
        group1 = listOf<Int>()
        group2 = listOf<Int>()
        Path("src/Day01.txt").reader().forEachLine { line -> run {
            val (entry1,entry2) =line.trim().split("   ")
            group1 = group1.plus(entry1.toInt())
            group2 = group2.plus(entry2.toInt())
        } }
    }
    @Benchmark
    fun benchmarkPart1(): Int {
        return part1(group1,group2)
    }

    @Benchmark
    fun benchmarkPart2(): Int {
        return part2(group1,group2)
    }

}


//main summary:
//Benchmark                   Mode  Cnt     Score     Error  Units
//Day01Bench.benchmarkPart1  thrpt    5  7117.312 ± 127.764  ops/s
//Day01Bench.benchmarkPart2  thrpt    5  3493.881 ± 169.925  ops/s