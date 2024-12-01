import java.io.File

fun main() {
    val lines = File("day01part1.txt").readText().split("\n").filter { it.isNotBlank() }

    val A = mutableMapOf<Int, Int>()
    val B = mutableMapOf<Int, Int>()

    lines.forEach {
        val (a, b) = it.split("   ").map { it.toInt() }
        A.putIfAbsent(a, 0)
        B.putIfAbsent(b, 0)
        A[a] = A[a]!! + 1
        B[b] = B[b]!! + 1
    }

    val ans = A.entries.fold(initial = 0) { acc, (k, v) ->
        acc + k * (B[k] ?: 0) * v
    }

    println(ans)
}
