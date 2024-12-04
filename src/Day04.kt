import kotlin.io.path.Path
import kotlin.io.path.reader


fun main() {
    fun transposeWordSearch(wordsearch: List<String>): List<String> {
        var col = 0
        var lastColumn = wordsearch[0].lastIndex
        var wordsearchNew: MutableList<String> = mutableListOf()

        while (col <= lastColumn){
            wordsearch.forEachIndexed { index, row ->  run {
                if (wordsearchNew.isEmpty() || wordsearchNew.size-1<col){
                    wordsearchNew.add(row[col].toString())
                } else {
                    wordsearchNew[col] = wordsearchNew[col].plus(row[col])
                }
            }}
            col++
        }
        require(wordsearchNew.size == wordsearch.size)
        return wordsearchNew
    }

    fun diagWordSearch(wordsearch: List<String>): List<String> {

        var row = 0
        var col = 0
        var rowProgress = 0
        var colProgress = 0
        var lastColumn = wordsearch[0].lastIndex
        var lastRow = wordsearch.lastIndex
        var wordsearchNew: MutableList<String> = mutableListOf()

        while (rowProgress <= lastRow){
            row = rowProgress
            var diag: String = ""
            while (row>=0){
                diag += wordsearch[row][col]
                col++
                row--
            }
            wordsearchNew.add(diag)
            rowProgress++
            col=0
        }
        colProgress++

        while (colProgress <= lastColumn){
            row = lastRow
            col=colProgress
            var diag: String = ""
            while (col<=lastColumn){
                diag += wordsearch[row][col]
                col++
                row--
            }
            wordsearchNew.add(diag)
            colProgress++

        }

        return wordsearchNew
    }

    fun otherDiagWordSearch(wordsearch: List<String>): List<String> {

        var lastColumn = wordsearch[0].lastIndex
        var row = 0
        var col = lastColumn
        var colProgress=lastColumn
        var rowProgress=0
        var lastRow = wordsearch.lastIndex
        var wordsearchNew: MutableList<String> = mutableListOf()

        while (rowProgress <= lastRow){
            row = rowProgress
            var diag: String = ""
            while (row>=0){
                diag += wordsearch[row][col]
                col--
                row--
            }
            wordsearchNew.add(diag)
            rowProgress++
            col=lastColumn
        }


        colProgress--

        while (colProgress >= 0){
            row = lastRow
            col=colProgress
            var diag: String = ""
            while (col>=0){
                diag += wordsearch[row][col]
                col--
                row--
            }
            wordsearchNew.add(diag)
            colProgress--

        }

        return wordsearchNew
    }

    fun part1(wordsearch: List<String>): Long {
        var wordSearchTransposed: List<String> = transposeWordSearch(wordsearch)
        var wordSearchDiag: List<String> = diagWordSearch(wordsearch)
        var wordSearchOtherDiag: List<String> = otherDiagWordSearch(wordsearch)

        var total = 0L
        for(row in wordsearch){
            var A = "(?=(XMAS))|(?=(SAMX))".toRegex().findAll(row).toList()
            total += A.size.toLong()
        }
        for(row in wordSearchTransposed){
            var A = "(?=(XMAS))|(?=(SAMX))".toRegex().findAll(row).toList()
            total += A.size.toLong()
        }
        for(row in wordSearchDiag){
            var A = "(?=(XMAS))|(?=(SAMX))".toRegex().findAll(row).toList()
            total += A.size.toLong()
        }
        for(row in wordSearchOtherDiag){
            var A = "(?=(XMAS))|(?=(SAMX))".toRegex().findAll(row).toList()
            total += A.size.toLong()
        }


        return total
    }

    fun part2(crossMasSearch: List<String>): Int {
        var result = 0
        var row = 0 + 1
        var col = 0 + 1
        var lastRow = crossMasSearch.lastIndex - 1
        var lastCol = crossMasSearch[0].lastIndex - 1

        while (row <= lastRow){
            while (col <= lastCol){
                if (crossMasSearch[row][col] == 'A'){
                    var tl = crossMasSearch[row-1][col-1]
                    var br = crossMasSearch[row+1][col+1]
                    var tr = crossMasSearch[row-1][col+1]
                    var bl = crossMasSearch[row+1][col-1]
                    var diag1: String = tl+"A" + br
                    var diag2: String = tr+"A" + bl

                    if ((diag1=="MAS" || diag1=="SAM") && (diag2=="MAS" || diag2=="SAM")){
                        result++
                        }
                }
                col++
            }

            col=1
            row++
        }

        return result
    }

    var puzzleRows = listOf<String>()
    Path("src/Day04.txt").reader().forEachLine { line -> run {
        puzzleRows = puzzleRows.plus(line.split('\n'))
    } }

    part1(puzzleRows).println()
    part2(puzzleRows).println()
}
