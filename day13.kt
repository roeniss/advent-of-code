import java.io.File
import kotlin.math.abs

private data class Button(
    val x: Long,
    val y: Long,
)

private data class Prize(
    val x: Long,
    val y: Long,
)

private fun parse(sheet: String): Triple<Button, Button, Prize> {
    val lines = sheet.split("\n")
    val ax = lines[0].substringAfter("Button A: X+").substringBefore(", Y+").toLong()
    val ay = lines[0].substringAfter(", Y+").toLong()

    val bx = lines[1].substringAfter("Button B: X+").substringBefore(", Y+").toLong()
    val by = lines[1].substringAfter(", Y+").toLong()

    val px = lines[2].substringAfter("Prize: X=").substringBefore(", Y=").toLong()
    val py = lines[2].substringAfter(", Y=").toLong()

    return Triple(Button(ax, ay), Button(bx, by), Prize(px, py))
}

private fun solve(a: Button, b: Button, p: Prize): Long {
    val maxB = minOf(p.x / b.x, p.y / b.y)
    for (bi in maxB downTo 0) {
        val (restX, restY) = p.x - bi * b.x to p.y - bi * b.y
        if (restX % a.x == 0L && restY % a.y == 0L && restX / a.x == restY / a.y) {
            val ai = restX / a.x
            return (ai * 3 + bi)
        }
    }

    return -1
}

private fun day13part1(sheets: List<String>): Long {
    return sheets.sumOf { sheet ->
        val (btnA, btnB, prize) = parse(sheet)
        val res = solve(btnA, btnB, prize)
        if (res == -1L) 0 else res
    }
}

private fun solve2(a: Button, b: Button, p: Prize): Long {
    val det = a.x * b.y - a.y * b.x
    if (det == 0L) return -1
    val x = b.y * p.x - b.x * p.y
    val y = -a.y * p.x + a.x * p.y
    if( x % det != 0L || y % det != 0L) return -1
    return abs(x/det) * 3 + abs(y/det)
}

private fun day13part2(sheets: List<String>): Long {
    return sheets.sumOf { sheet ->
        val (btnA, btnB, prize) = parse(sheet)
        val res = solve2(btnA, btnB, prize.copy(x = prize.x + 10000000000000, y = prize.y + 10000000000000))
        if (res == -1L) 0 else res
    }
}

private fun main() {
    val sheets = File("day13.txt").readText().dropLastWhile { it == '\n' }.split("\n\n")
    println(day13part1(sheets))
    println(day13part2(sheets))
}
