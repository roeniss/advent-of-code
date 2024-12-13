import java.io.File

data class Plot(
    val poses: List<Pair<Int, Int>>
)


private val d = listOf(
    0 to 1,
    1 to 0,
    0 to -1,
    -1 to 0,
)

private fun getPlots(garden: List<List<Char>>): List<Plot> {
    val vis = mutableSetOf<Pair<Int, Int>>()
    val plots = mutableListOf<Plot>()
    garden.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            if (!vis.contains(y to x)) {
                vis.add(y to x)
                val poses = mutableSetOf<Pair<Int, Int>>()
                val queue = mutableListOf(y to x)

                poses.add(y to x)
                while (queue.isNotEmpty()) {
                    val (cy, cx) = queue.removeFirst()

                    d.forEach { (dy, dx) ->
                        val (ny, nx) = cy + dy to cx + dx
                        if (!(ny !in garden.indices || nx !in garden[0].indices || garden[ny][nx] != cell || vis.contains(
                                ny to nx
                            ))
                        ) {
                            poses.add(ny to nx)
                            vis.add(ny to nx)
                            queue.add(ny to nx)
                        }
                    }
                }
                plots.add(Plot(poses.toList()))
            }
        }
    }
    return plots
}

private fun getPerimeter(garden: List<List<Char>>, p: Plot): Long {
    return p.poses.sumOf { (y, x) ->
        d.count { (dy, dx) ->
            (y + dy !in garden.indices || x + dx !in garden[0].indices || garden[y + dy][x + dx] != garden[y][x])
        }
    } + 0L
}

private fun day12part1(garden: List<List<Char>>): Long {
    val plots: List<Plot> = getPlots(garden)

    return plots.sumOf {
        val sz = it.poses.size
        val perimeter: Long = getPerimeter(garden, it)
        sz * perimeter
    }
}

fun List<List<Char>>.pos(y: Int, x: Int): Char {
    return this.getOrNull(y)?.getOrNull(x) ?: ' '
}

private fun getSides(garden: List<List<Char>>, p: Plot): Long {
    return p.poses.sumOf { (y, x) ->
        val cell = garden.pos(y, x)

        val bottom = garden.pos(y + 1, x)
        val right = garden.pos(y, x + 1)
        val left = garden.pos(y, x - 1)
        val top = garden.pos(y - 1, x)
        val bottomright = garden.pos(y + 1, x + 1)
        val righttop = garden.pos(y - 1, x + 1)
        val lefttop = garden.pos(y - 1, x - 1)
        val bottomleft = garden.pos(y + 1, x - 1)

        ((if (bottom != cell && right != cell) 1 else if (bottom == cell && right == cell && bottomright != cell) 1 else 0) +
                (if (right != cell && top != cell) 1 else if (right == cell && top == cell && righttop != cell) 1 else 0) +
                (if (top != cell && left != cell) 1 else if (top == cell && left == cell && lefttop != cell) 1 else 0) +
                (if (left != cell && bottom != cell) 1 else if (left == cell && bottom == cell && bottomleft != cell) 1 else 0))
    } + 0L

}

private fun day12part2(garden: List<List<Char>>): Any {
    val plots: List<Plot> = getPlots(garden)

    return plots.sumOf {
        val sz = it.poses.size
        val sides: Long = getSides(garden, it)
        sz * sides
    }
}

private fun main() {
    val garden = File("day12.txt").readText().dropLastWhile { it == '\n' }.split("\n").map { it.toCharArray().toList() }
//    println(day12part1(garden))
    println(day12part2(garden))
}
