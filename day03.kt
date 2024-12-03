import java.io.File

fun day03part1(text: String): Long {
    val pat = """mul\((\d+),(\d+)\)""".toRegex()
    val matches = pat.findAll(text)
    return matches.fold(0L) { acc, match ->
        val (l, r) = match.destructured
        acc + l.toLong() * r.toLong()
    }
}

fun day03part2(text: String): Long {
    val pat = """(mul\((\d+),(\d+)\)|do\(\)|don't\(\))""".toRegex()
    val matches = pat.findAll(text)
    var on = true
    return matches.fold(0L) { acc, match ->
        if (match.value == "do()") {
            on = true
            acc
        } else if (match.value == "don't()") {
            on = false
            acc
        } else if (!on) {
            acc
        } else {
            val (l, r) = match.groupValues[2] to match.groupValues[3]
            acc + l.toLong() * r.toLong()
        }
    }
}


fun main() {
    val text = File("day03.txt").readText()
    println(day03part1(text))
    println(day03part2(text))
}
