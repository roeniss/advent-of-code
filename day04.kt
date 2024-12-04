import java.io.File

private operator fun List<String>.get(i: Int, j: Int): String {
    if (this.size <= i || i < 0 || j < 0 || this[i].length <= j) {
        return ""
    }
    return this[i][j].toString()
}

fun day04part1(text: String): Int {
    val rows = text.split("\n").filter { it.isNotBlank() }
    val r = rows.size
    val c = rows[0].length

    var cnt = 0
    for (i in 0..<r) {
        for (j in 0..<c) {
            // right
            if (rows[i, j] + rows[i, j + 1] + rows[i, j + 2] + rows[i, j + 3] == "XMAS") {
                cnt++
            }
            // right bottom
            if (rows[i, j] + rows[i + 1, j + 1] + rows[i + 2, j + 2] + rows[i + 3, j + 3] == "XMAS") {
                cnt++
            }
            // bottom
            if (rows[i, j] + rows[i + 1, j] + rows[i + 2, j] + rows[i + 3, j] == "XMAS") {
                cnt++
            }
            // left bottom
            if (rows[i, j] + rows[i + 1, j - 1] + rows[i + 2, j - 2] + rows[i + 3, j - 3] == "XMAS") {
                cnt++
            }
            // left
            if (rows[i, j] + rows[i, j - 1] + rows[i, j - 2] + rows[i, j - 3] == "XMAS") {
                cnt++
            }
            // left top
            if (rows[i, j] + rows[i - 1, j - 1] + rows[i - 2, j - 2] + rows[i - 3, j - 3] == "XMAS") {
                cnt++
            }
            // top
            if (rows[i, j] + rows[i - 1, j] + rows[i - 2, j] + rows[i - 3, j] == "XMAS") {
                cnt++
            }
            // right top
            if (rows[i, j] + rows[i - 1, j + 1] + rows[i - 2, j + 2] + rows[i - 3, j + 3] == "XMAS") {
                cnt++
            }
        }
    }

    return cnt
}

fun day04part2(text: String): Int {
    val rows = text.split("\n").filter { it.isNotBlank() }
    val r = rows.size
    val c = rows[0].length

    var cnt = 0
    for (i in 0..<r) {
        for (j in 0..<c) {
            if (rows[i, j] != "A") {
                continue
            }

            // M.M
            if (rows[i - 1, j - 1] == "M" && rows[i - 1, j + 1] == "M" && rows[i + 1, j - 1] == "S" && rows[i + 1, j + 1] == "S") {
                cnt++
            }
            // S.M
            if (rows[i - 1, j - 1] == "S" && rows[i - 1, j + 1] == "M" && rows[i + 1, j - 1] == "S" && rows[i + 1, j + 1] == "M") {
                cnt++
            }
            // S.S
            if (rows[i - 1, j - 1] == "S" && rows[i - 1, j + 1] == "S" && rows[i + 1, j - 1] == "M" && rows[i + 1, j + 1] == "M") {
                cnt++
            }
            // M.S
            if (rows[i - 1, j - 1] == "M" && rows[i - 1, j + 1] == "S" && rows[i + 1, j - 1] == "M" && rows[i + 1, j + 1] == "S") {
                cnt++
            }
        }
    }

    return cnt
}

fun main() {
    val text = File("day04.txt").readText()
    println(day04part1(text))
    println(day04part2(text))
}
