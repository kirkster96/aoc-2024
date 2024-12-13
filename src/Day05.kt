import kotlin.io.path.Path
import kotlin.io.path.reader


fun main() {

    fun ruleLut(rules: List<String>): Map<Long, List<Pair<Long, Long>>> {

        var result: MutableMap<Long, List<Pair<Long, Long>>> = mutableMapOf()
        rules
            .map { it.split("|").map { it.toLong() }.zipWithNext() }
            .flatten()
            .forEach {
                val firstList: List<Pair<Long, Long>>? = result.putIfAbsent(it.first, listOf(it))

                if (!firstList.isNullOrEmpty()){
                    result[it.first] = firstList.plus(it)
                }

                val secondList: List<Pair<Long, Long>>? = result.putIfAbsent(it.second, listOf(it))

                if (!secondList.isNullOrEmpty()){
                    result[it.second] = secondList.plus(it)
                }
            }
        return result
    }

    fun filterProductions(rules: List<String>, pageProductions: List<String>, getValid: Boolean = true): List<List<Long>> {
        val ruleLUT: Map<Long, List<Pair<Long,Long>>> = ruleLut(rules)

        return pageProductions
            .map { it.split(",").map { item -> item.toLong() } }
            .filterIndexed { index, longs ->
                run {
                    var result = getValid

                    val rulesToCheck = longs.map { ruleLUT[it] }.filterNotNull().flatten()
                        .filter {
                            val indexOfFirst = longs.indexOf(it.first)
                            val indexOfSecond = longs.indexOf(it.second)
                            (indexOfFirst != -1) && (indexOfSecond != -1)
                        }

                    for (rule in rulesToCheck) {
                        if (longs.indexOf(rule.first) >= longs.indexOf(rule.second)) {
                            result = !result
                            break
                        }

                    }

                    result
                }
            }

    }

    fun part1(rules: List<String>, pageProductions: List<String>): Long {

        val filterProductions = filterProductions(rules, pageProductions)

        return filterProductions.map { it[it.size/2] }.sum()
    }

    fun remediateProduction(longs: List<Long>, rules: List<String>): List<Long> {
        val ruleLUT: Map<Long, List<Pair<Long,Long>>> = ruleLut(rules)

        val rulesToApply = longs.map { ruleLUT[it] }.filterNotNull().flatten()
            .filter {
                val indexOfFirst = longs.indexOf(it.first)
                val indexOfSecond = longs.indexOf(it.second)
                (indexOfFirst != -1) && (indexOfSecond != -1)
            }

        // TODO implement applying the List<Pair<Long,Long>> to construct the new valid List<Long> using every element in longs
        // ...

        var result = longs.toMutableList()
        var i = 0
        start@ while (i <= longs.lastIndex){
            var num = result[i]
            for(rule in ruleLUT[num]!!){
                var indexOfFirst = result.indexOf(rule.first)
                var indexOfSecond = result.indexOf(rule.second)
                if((indexOfFirst == -1) || (indexOfSecond == -1)){
                    continue    // skip rule
                }

                if (rule.first == num){
                    // num comes first in list
                    var movedItem = (indexOfFirst > indexOfSecond)
                    while (indexOfFirst > indexOfSecond){
                        var newIndex = indexOfFirst - 1
                        var temp = result[newIndex]
                        result[newIndex] = num
                        result[indexOfSecond] = temp
                        indexOfFirst = newIndex
                    }
                    if (movedItem) {
                        i = 0
                        continue@start
                    }

                } else {
                    //num comes last in list
                    var movedItem = (indexOfFirst > indexOfSecond)
                    while (indexOfFirst > indexOfSecond){
                        var newIndex = indexOfSecond + 1
                        var temp = result[newIndex]
                        result[newIndex] = num
                        result[indexOfSecond] = temp
                        indexOfSecond = newIndex
                    }
                    if (movedItem) {
                        i = 0
                        continue@start
                    }

                }

            }
            i++

        }
        return result
    }




    fun part2(rules: List<String>, pageProductions: List<String>): Long {

        val invalidProductions = filterProductions(rules, pageProductions, false).map { remediateProduction(it, rules) }


        return invalidProductions.map { it[it.size/2] }.sum()

    }

    var secondInput = false
    var rules = listOf<String>()
    var pageProductions = listOf<String>()
    Path("src/Day05.txt").reader().forEachLine { line -> run {

        if (line.isEmpty()){
            secondInput = true
        } else if(secondInput){
            pageProductions = pageProductions.plus(line)
        } else {
            rules = rules.plus(line)
        }
    } }

    part1(rules, pageProductions).println()
    part2(rules, pageProductions).println()
}
