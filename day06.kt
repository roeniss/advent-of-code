import java.io.File
import java.lang.IllegalArgumentException

class Cursor(
    internal var i: Int,
    internal var j: Int,
    private var dir: Int,
) {
    fun take(field: MutableList<MutableList<Char>>) {
        field[i][j] = 'X'
    }

    fun isFinish(field: List<List<Char>>): Boolean {
        return i < 0 || i >= field.size || j < 0 || j >= field[0].size
    }

    fun canTake(field: List<List<Char>>): Boolean {
        return field[i][j] != '#'
    }

    fun forward() {
        when (dir) {
            0 -> i--
            1 -> j++
            2 -> i++
            3 -> j--
            else -> throw IllegalArgumentException("?")
        }
    }

    fun backward() {
        when (dir) {
            0 -> i++
            1 -> j--
            2 -> i--
            3 -> j++
            else -> throw IllegalArgumentException("?")
        }
    }

    fun turn() {
        dir = (dir + 1) % 4
    }
}

private fun visualize(field: List<List<Char>>) {
    field.forEach { row ->
        row.forEach { col ->
            if (col == '.') {
                print(" ")
            } else {
                print(col)
            }
        }
        println()
    }
}

private fun visualize(field: List<List<Char>>, cur: Cursor) {
    val i = cur.i
    val j = cur.j
    val n = 5
    for (a in i - n..i + n) {
        for (b in j - n..j + n) {
            if (a < 0 || a >= field.size || b < 0 || b >= field[0].size) {
                print(" ")
            } else {
                print(field[a][b])
            }
        }
        println()
    }

    Thread.sleep(300)
    println()
    println()
}

fun day06part1(text: String): Int {
    val field = text.split("\n").map { it.toCharArray().toMutableList() }.toMutableList()

    val (i, j) = getPos(field)
    val cur = Cursor(i, j, 0)
    cur.take(field)

    var sz = field.flatten().size + 1
    while (sz > 0) {
        sz--
        cur.forward()
        if(cur.isFinish(field)){
            break
        }

        if (cur.canTake(field)) {
            cur.take(field)
        } else {
            cur.backward()
            cur.turn()
        }
    }
    return field.flatten().count { it == 'X' }
}

private fun day06part1For2(field: MutableList<MutableList<Char>>): Boolean {
    val (i, j) = getPos(field)
    val cur = Cursor(i, j, 0)
    cur.take(field)

    var sz = field.flatten().size + 1
    while (sz > 0) {
        sz--
        cur.forward()
        if(cur.isFinish(field)){
            return false
        }

        if (cur.canTake(field)) {
            cur.take(field)
        } else {
            cur.backward()
            cur.turn()
        }
    }

    return true
}

private fun getPos(field: List<List<Char>>): Pair<Int, Int> {
    field.forEachIndexed { i, row ->
        row.forEachIndexed { j, col ->
            if (col == '^') {
                return Pair(i, j)
            }
        }
    }

    throw IllegalArgumentException("?")
}

private fun day06part2(text: String): Int {
    val field = text.split("\n").map { it.toCharArray().toList() }.toList()

    var cnt = 0
    field.forEachIndexed { i, row ->
        row.forEachIndexed { j, col ->
            if (col == '.') {
                val _field = field.map { it.toMutableList() }.toMutableList()
                _field[i][j] = '#'
                if(day06part1For2(_field)){
                    cnt++
                }
            }
        }
    }

    return cnt
}

private fun main() {
    val text = File("day06.txt").readText().dropLastWhile { it == '\n' }
    println(day06part1(text))
    println(day06part2(text))
}
