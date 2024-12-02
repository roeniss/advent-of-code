import java.io.File

fun day02part1(lines: List<String>): Int {
    val reports = lines
        .filter { it.isNotBlank() }
        .map { line ->
            line.split(" ").map { it.toInt() }
        }

    val safes = reports.count { report ->
        val zip = report.zip(report.drop(1))

        zip.count(::graduallyIncreasing) == report.size - 1 || zip.count(::graduallyDecreasing) == report.size - 1
    }

    return safes
}

private fun graduallyIncreasing(pair: Pair<Int, Int>) = pair.first > pair.second && pair.first - pair.second <= 3
private fun graduallyDecreasing(pair: Pair<Int, Int>) = pair.first < pair.second && pair.second - pair.first <= 3

fun day02part2(lines: List<String>): Int {
    val reports = lines
        .filter { it.isNotBlank() }
        .map { line ->
            line.split(" ").map { it.toInt() }
        }


    val safes = reports.count { report ->
        val zip = report.zip(report.drop(1))
        if (zip.count(::graduallyIncreasing) == report.size - 1 || zip.count(::graduallyDecreasing) == report.size - 1) return@count true

        for (i in 0 until report.size) {
            val newReport = report.toMutableList()
            newReport.removeAt(i)
            val newZip = newReport.zip(newReport.drop(1))
            if (newZip.count(::graduallyIncreasing) == newReport.size - 1 || newZip.count(::graduallyDecreasing) == newReport.size - 1) return@count true
        }

        return@count false
    }

    return safes
}


fun main() {
    val lines = File("day02.txt").readText().split("\n").filter { it.isNotBlank() }
    println(day02part1(lines))
    println(day02part2(lines))
}
