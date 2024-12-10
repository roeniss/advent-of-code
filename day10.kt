import java.io.File

private val d = listOf(
    0 to 1,
    1 to 0,
    0 to -1,
    -1 to 0,
)

private fun countTrails(tmap: List<List<Int>>, y: Int, x: Int, visited: MutableSet<Pair<Int, Int>>): Long {
    if (tmap[y][x] == 9) {
        return 1
    }

    return d.sumOf { (dy, dx) ->
        val ny = y + dy
        val nx = x + dx

        if (ny in tmap.indices && nx in tmap[0].indices && tmap[ny][nx] == tmap[y][x] + 1 && (ny to nx) !in visited) {
            visited.add(ny to nx)
            countTrails(tmap, ny, nx, visited)
                .also { visited.remove(ny to nx) } // this line is for part2 only
        } else {
            0
        }
    }
}

private fun day10part1(tmap: List<List<Int>>): Long {
    var res = 0L
    tmap.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            if (cell == 0) {
                val visited = mutableSetOf<Pair<Int, Int>>()
                res += countTrails(tmap, y, x, visited)
            }
        }
    }

    return res
}

private fun day10part2(tmap: List<List<Int>>): Long {
    var res = 0L
    tmap.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            if (cell == 0) {
                val visited = mutableSetOf<Pair<Int, Int>>()
                res += countTrails(tmap, y, x, visited)
            }
        }
    }

    return res
}


private fun main() {
    val tmap = File("day10.txt").readText().dropLastWhile { it == '\n' }.split("\n")
        .map { it.toCharArray().toList().map { it.digitToInt() } }
//    println(day10part1(tmap))
    println(day10part2(tmap))
}
