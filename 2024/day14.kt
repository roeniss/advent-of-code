import java.io.File

data class Robot(
    val y: Int,
    val x: Int,
    val vy: Int,
    val vx: Int,
)

private fun getRobots(poses: List<String>) = poses.map {
    val p = it.split("=")[1].split(" ")[0]
    val v = it.split("=")[2]
    val (px, py) = p.split(",").map(String::toInt)
    val (vx, vy) = v.split(",").map(String::toInt)
    Robot(py, px, vy, vx)
}

private fun rearrangeRobots(
    robots: List<Robot>,
    field: Pair<Int, Int>,
    timeElapsed: Int,
) = robots.map {
    var ny = (it.y + it.vy * timeElapsed) % field.first
    if (ny < 0) ny += field.first
    var nx = (it.x + it.vx * timeElapsed) % field.second
    if (nx < 0) nx += field.second
    Robot(ny, nx, it.vy, it.vx)
}

private fun day14part1(poses: List<String>): Int {
    val robots = getRobots(poses)

    val field = 103 to 101 // y to x
//    val field = 7 to 11 // y to x

    val movedRobots = rearrangeRobots(robots, field, 100)

    val topLeftRobots = movedRobots.filter {
        it.y < field.first / 2 && it.x < field.second / 2
    }.count()
    val bottomRightRobots = movedRobots.filter {
        it.y > field.first / 2 && it.x > field.second / 2
    }.count()
    val topRightRobots = movedRobots.filter {
        it.y < field.first / 2 && it.x > field.second / 2
    }.count()
    val bottomLeftRobots = movedRobots.filter {
        it.y > field.first / 2 && it.x < field.second / 2
    }.count()

    return topLeftRobots * bottomRightRobots * topRightRobots * bottomLeftRobots
}


private  fun draw(robots: List<Robot>, field: Pair<Int, Int>) {
    println("\n\n\n\n\n")
    val grid = Array(field.first) { Array(field.second) { '.' } }
    robots.forEach {
        grid[it.y][it.x] = '#'
    }
    grid.forEach { row ->
        println(row.joinToString(""))
    }
    println()
}

private fun day14part2(poses: List<String>) {
    var robots = getRobots(poses)

    val field = 103 to 101 // y to x

    robots = rearrangeRobots(robots, field, 6600)
    for (i in 1..100){
        robots = rearrangeRobots(robots, field, 1)
        println(6000+i)
        draw(robots, field)
    }


}

private fun main() {
    val poses = File("day14.txt").readText().dropLastWhile { it == '\n' }.split("\n")
    println(day14part1(poses))
    println(day14part2(poses))
}
