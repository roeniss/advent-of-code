import java.io.File

private fun success(line: String, funcs: List<(Long, Long) -> Long>): Long {
    val (left, right) = line.split(": ")
    val result = left.toLong()
    val values = right.split(" ").map { it.toLong() }

    tailrec fun branches(prev: List<Long>, values: List<Long>, cur: Int): List<Long> {
        if (cur == values.size) {
            return prev
        }

        val curVal = values[cur]
        val applied = prev.flatMap {
            funcs.map { func -> func(it, curVal) }
        }
        return branches(applied, values, cur + 1)
    }

    val cases = branches(listOf(values[0]), values, 1)

    return if (cases.any { it == result }) result else 0
}

private fun day07part1(lines: List<String>): Long {
    return lines.sumOf {
        success(
            it,
            listOf(
                { a, b -> a + b },
                { a, b -> a * b },
            )
        )
    }
}

private fun day07part2(lines: List<String>): Long {
    return lines.sumOf {
        success(
            it,
            listOf(
                { a, b -> a + b },
                { a, b -> a * b },
                { a, b -> (a.toString() + "0".repeat(b.toString().length)).toLong() + b },
            )
        )
    }
}

private fun main() {
    val lines = File("day07.txt").readText().dropLastWhile { it == '\n' }.split("\n")
    println(day07part1(lines))
    println(day07part2(lines))
}
