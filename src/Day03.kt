import kotlin.io.path.Path
import kotlin.io.path.readText


fun main() {
    fun sumOfProducts(instructions: List<String>): Int {
        val regex2 = "[0-9]{1,3}".toRegex()

        val products: List<Int> = instructions
            .map { regex2.findAll(it).toList() }
            .map { it[0].value.toInt() * it[1].value.toInt() }

        return products.sum()
    }
    fun part1(program: String): Int {
        var instructionMatches= "mul\\([0-9]{1,3},[0-9]{1,3}\\)".toRegex().findAll(program).toList()
        var instructions = instructionMatches.map { it.value }


        return sumOfProducts(instructions)
    }

    fun part2(program: String): Int {
        var instructionMatches= "mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)".toRegex().findAll(program).toList()
        var instructions = instructionMatches.map { it.value }

        var enabledInstructions = mutableListOf<String>()
        var dontActive = false
        for (mnemonic in instructions){
            if (dontActive){
                if (mnemonic.startsWith("do(")){
                    dontActive = false
                }
            } else{
                if (mnemonic.startsWith("don't(")){
                    dontActive = true
                    continue
                } else if (mnemonic.startsWith("do(")){
                    continue
                }
                else {
                    enabledInstructions.add(mnemonic)
                }
            }
        }
        return sumOfProducts(enabledInstructions)
    }

    var program = Path("src/Day03.txt").readText()

    part1(program).println()
    part2(program).println()
}
