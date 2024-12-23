import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.*


private fun main() {
    val file = getFile("day18.txt").split("\n").map { it.split(",") }.map { it[0].toInt() to it[1].toInt() }
    println(day18part1(file))
    println(day18part2(file))
}

private fun day18part1(yxs: List<Pair<Int, Int>>): Int {
    val m = MutableList(70 + 1) { MutableList(70 + 1) { '▮' } } // 1 == road

    yxs.take(1024).forEach { (x, y) ->
        m[y][x] = ' '
    }

    return getExitDistance(m)
}


private fun day18part2(yxs: List<Pair<Int, Int>>): String {
    val m = MutableList(70 + 1) { MutableList(70 + 1) { '▮' } } // 1 == road
    val d = MutableList(70 + 1) { MutableList(70 + 1) { Int.MAX_VALUE } }

    yxs.take(1024).forEach { (x, y) ->
        m[y][x] = ' '
    }

    yxs.drop(1024).forEach { (x, y) ->
        m[y][x] = ' '

        runCatching { getExitDistance(m) }
            .onFailure {
                return "$x,$y"
            }
    }

    TODO()
}

private fun getExitDistance(
    m: MutableList<MutableList<Char>>,
): Int {
    val d = MutableList(70 + 1) { MutableList(70 + 1) { Int.MAX_VALUE } }

    val q = PriorityQueue<Triple<Int, Int, Int>> { a, b ->
        compareValues(a.first, b.first)
    }

    q.add(Triple(0, 0, 0))
    d[0][0] = 0
    while (q.isNotEmpty()) {
        val (w, y, x) = q.poll()
        if (y == 70 && x == 70) {
            return d[70][70]
        }
        D4.forEach { (dy, dx) ->
            val (ny, nx) = (dy + y to dx + x)
            if (ny in 0..70 && nx in 0..70 && m[ny][nx] == '▮' && d[ny][nx] > w + 1) {
                d[ny][nx] = w + 1
                q.add(Triple(w + 1, ny, nx))
            }
        }
    }

    throw RuntimeException("No Route")
}
