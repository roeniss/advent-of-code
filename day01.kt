import java.io.File
import java.util.*
import kotlin.math.abs

fun part1(): Int {
    val lines = File("day01.txt").readText().split("\n").filter { it.isNotBlank() }

    val A = PriorityQueue<Int>()
    val B = PriorityQueue<Int>()

    lines.forEach {
        val (a, b) = it.split("   ").map { it.toInt() }
        A.add(a)
        B.add(b)
    }

    val ans = lines.fold(initial = 0) { acc, _ ->
        val a = A.poll()
        val b = B.poll()
        acc + abs(a - b)
    }

    return ans
}


fun part2(): Int {
    val lines = File("day01.txt").readText().split("\n").filter { it.isNotBlank() }

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

    return ans
}

fun main() {
    println(part1())
    println(part2())
}
