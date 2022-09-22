import org.junit.jupiter.api.Test

class AutomataTests {
    @Test
    fun `Small DFA`() {
        val n = 2
        val m = 2
        val q0 = listOf(0)
        val F = hashSetOf(1)
        val automata = NFA(n, m, q0, F)
        automata.addTransition(0, 0, 1)
        assert(automata.accept("0"))
        assert(!automata.accept("1"))
    }

    @Test
    fun `Small Cyclic DFA`() {
        val n = 1
        val m = 2
        val q0 = listOf(0)
        val F = hashSetOf(0)
        val automata = NFA(n, m, q0, F)
        automata.addTransition(0, 0, 0)
        assert(automata.accept("0"))
        assert(automata.accept("0000"))
        assert(automata.accept("0000000"))
        assert(!automata.accept("00001000"))
        assert(!automata.accept("1"))
    }

    @Test
    fun `0^n 1 DFA`() {
        val n = 2
        val m = 2
        val q0 = listOf(0)
        val F = hashSetOf(1)
        val automata = NFA(n, m, q0, F)
        automata.addTransition(0, 0, 0)
        automata.addTransition(0, 1, 1)
        assert(automata.accept("00001"))
        assert(automata.accept("1"))
        assert(!automata.accept("0"))
        assert(!automata.accept("000000011"))
        assert(!automata.accept("00001000"))
    }

    @Test
    fun `0^(2k + 1) NFA`() {
        val n = 3
        val m = 3
        val q0 = listOf(0)
        val F = hashSetOf(2)
        val automata = NFA(n, m, q0, F)
        automata.addTransition(0, 0, 1)
        automata.addTransition(1, 0, 0)
        automata.addTransition(0, 0, 2)
        assert(automata.accept("00000"))
        assert(!automata.accept("0000"))
        assert(automata.accept("0"))
        assert(!automata.accept("0001"))
    }

    @Test
    fun `(0|1)^* 1 (0|1)^3 NFA`() {
        val n = 5
        val m = 2
        val q0 = listOf(0)
        val F = hashSetOf(4)
        val automata = NFA(n, m, q0, F)
        automata.addTransition(0, 0, 0)
        automata.addTransition(0, 1, 0)
        automata.addTransition(0, 1, 1)
        automata.addTransition(1, 0, 2)
        automata.addTransition(1, 1, 2)
        automata.addTransition(2, 0, 3)
        automata.addTransition(2, 1, 3)
        automata.addTransition(3, 0, 4)
        automata.addTransition(3, 1, 4)
        assert(automata.accept("000001110"))
        assert(automata.accept("001010101000"))
        assert(automata.accept("1000"))
        assert(automata.accept("1111"))
    }
}