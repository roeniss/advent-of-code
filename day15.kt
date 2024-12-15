import java.io.File

private fun main() {
    val (field, moves) = File("day15.txt").readText().dropLastWhile { it == '\n' }.split("\n\n")
    val _field = field.split("\n").map { it.toCharArray().toMutableList() }.toMutableList()
    val _moves = moves.replace("\n", "")
//    println(day15part1(_field, _moves))
    println(day15part2(_field, _moves))
}

private fun day15part2(field: MutableList<MutableList<Char>>, moves: String): Long {
    val field = scaleUp(field)
    var robot: Pair<Int, Int> = findRobotPos(field)

//    draw(field)

    moves.forEach {
        val d: Pair<Int, Int> = getDirection(it)
        if (d == Pair(0, -1) || d == Pair(0, 1)) {
            robot = move(robot, d, field)
        } else {
            if (canGo((robot.first + d.first to robot.second + d.second), d, field)) {
                move2((robot.first + d.first to robot.second + d.second), d, field)
                field[robot.first][robot.second] = '.'
                robot = robot.first + d.first to robot.second + d.second
//                draw(field)
            }
        }
    }

    draw(field)

    return getGPS(field, '[')
}

private fun scaleUp(field: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
    val res: MutableList<MutableList<Char>> = mutableListOf(mutableListOf())
    field.forEach {
        val list = mutableListOf<Char>()
        it.forEach { cell ->
            if (cell == '.') {
                list.addAll(listOf('.', '.'))
            } else if (cell == '#') {
                list.addAll(listOf('#', '#'))
            } else if (cell == 'O') {
                list.addAll(listOf('[', ']'))
            } else if (cell == '@') {
                list.addAll(listOf('@', '.'))
            }
        }
        res.add(list)
    }
    return res.subList(1, res.size - 1)
}

private fun canGo(pos: Pair<Int, Int>, d: Pair<Int, Int>, field: MutableList<MutableList<Char>>): Boolean {
    val cell = field.getOrNull(pos.first)?.getOrNull(pos.second) ?: return false
    if (cell == '.') return true
    if (cell == '#') return false
    if (cell == '[') {
        return canGo(Pair(pos.first + d.first, pos.second + d.second), d, field) &&
                canGo(Pair(pos.first + d.first, pos.second + d.second + 1), d, field)
    }
    if (cell == ']') {
        return canGo(Pair(pos.first + d.first, pos.second + d.second), d, field) &&
                canGo(Pair(pos.first + d.first, pos.second + d.second - 1), d, field)
    }
    throw Exception("Invalid cell")
}

private fun move2(pos: Pair<Int, Int>, d: Pair<Int, Int>, field: MutableList<MutableList<Char>>) {
    // move self to next pos
    val cell = field[pos.first ][pos.second ]
    if(cell == '.'){
        field[pos.first][pos.second] = field[pos.first - d.first][pos.second - d.second]
    }else if (cell == '#'){
        throw Exception("Invalid move")
    }else if (cell == '[') {
        move2(pos.first + d.first to pos.second + d.second, d, field)
        move2(pos.first + d.first to pos.second + d.second + 1, d, field)
        field[pos.first][pos.second] = field[pos.first - d.first][pos.second - d.second]
        field[pos.first][pos.second + 1] = '.'
    }else if (cell == ']') {
        move2(pos.first + d.first to pos.second + d.second, d, field)
        move2(pos.first + d.first to pos.second + d.second - 1, d, field)
        field[pos.first][pos.second] = field[pos.first - d.first][pos.second - d.second]
        field[pos.first][pos.second - 1] = '.'
    }
}

private fun day15part1(field: MutableList<MutableList<Char>>, moves: String): Long {
    var robot: Pair<Int, Int> = findRobotPos(field)

    moves.forEach {
        val d: Pair<Int, Int> = getDirection(it)
        robot = move(robot, d, field)
//        draw(field)
    }

    return getGPS(field, 'O')
}

private fun draw(field: MutableList<MutableList<Char>>) {
    field.forEach { row ->
        println(row.joinToString(""))
    }

    println()
}

private fun getGPS(field: MutableList<MutableList<Char>>, ch: Char): Long {
    return field.flatMapIndexed { i, row ->
        row.mapIndexed { j, cell ->
            if (cell == ch) {
                i * 100L + j
            } else {
                0
            }
        }
    }.sum()
}

private fun MutableList<MutableList<Char>>.get(y: Int, x: Int): Char? {
    return this.getOrNull(y)?.getOrNull(x)?.let { if (it == '#') null else it }
}

private fun MutableList<MutableList<Char>>.canGo(y: Int, x: Int): Boolean {
    return this.getOrNull(y)?.getOrNull(x) == '.'
}

private fun move(robot: Pair<Int, Int>, d: Pair<Int, Int>, field: MutableList<MutableList<Char>>): Pair<Int, Int> {
    var (y, x) = robot

    while (field.get(y, x) != '.') {
        if (field.get(y, x) == null) return robot
        y += d.first
        x += d.second
    }

    while (y != robot.first || x != robot.second) {
        field[y][x] = field[y - d.first][x - d.second]
        y -= d.first
        x -= d.second
    }
    field[y][x] = '.'

    return Pair(y + d.first, x + d.second)
}

private fun getDirection(it: Char): Pair<Int, Int> {
    return when (it) {
        '^' -> Pair(-1, 0)
        'v' -> Pair(1, 0)
        '<' -> Pair(0, -1)
        '>' -> Pair(0, 1)
        else -> throw Exception("Invalid direction")
    }
}

private fun findRobotPos(field: MutableList<MutableList<Char>>): Pair<Int, Int> {
    field.forEachIndexed { i, row ->
        row.forEachIndexed { j, cell ->
            if (cell == '@') {
                return Pair(i, j)
            }
        }
    }

    throw Exception("Robot not found")
}
