import java.io.*

class NFA(
    n: Int,
    m: Int,
    private val initialStates: List<Int>,
    private val acceptedStates: Set<Int>
) {
    private val sigma: Array<Array<MutableList<Int>>> =
        Array(n) { Array(m) { arrayListOf() } }

    fun addTransition(from: Int, symbol: Int, to: Int) = sigma[from][symbol].add(to)

    private fun accept(curState: Int, input: List<Int>, pointer: Int): Boolean =
        if (pointer == input.size) {
            curState in acceptedStates
        } else {
            if (sigma[curState][input[pointer]].isEmpty()) {
                false
            } else {
                sigma[curState][input[pointer]].any { next -> accept(next, input, pointer + 1) }
            }
        }

    fun accept(input: String) =
        initialStates.any { initialState ->
            accept(initialState, input.toCharArray().map { it - '0' }, 0)
        }
}


fun main(args: Array<String>) {
    val file = BufferedReader(FileReader("input.txt"))
    val n = file.readLine()!!.toInt()
    val m = file.readLine()!!.toInt()

    val q0 = file.readLine()!!.split(" ").map { it.toInt() }
    val F = file.readLine()!!.split(" ").map { it.toInt() }.toHashSet()

    val automata = NFA(n, m, q0, F)

    val lines = file.readLines()
    for (i in 0 until lines.size - 1) {
        val (from, symbol, to) = lines[i].split(" ").map { it.toInt() }
        automata.addTransition(from, symbol, to)
    }
    val input = lines.last()

    print(automata.accept(input))
}