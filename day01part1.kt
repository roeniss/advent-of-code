import java.io.File
import java.util.*
import kotlin.math.abs

fun main() {
    val lines = File("day01part1.txt").readText().split("\n").filter { it.isNotBlank() }

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

    println(ans)
}
