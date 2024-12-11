import java.io.File

private fun day11part1(stones: List<String>, left: Int): List<String> {
    if (left == 0) return stones

    val nextStones = mutableListOf<String>()
    stones.forEach {
        if (it == "0") {
            nextStones.add("1")
        } else if (it.length % 2 == 0) {
            val (l, r) = it.chunked(it.length / 2).map(String::toLong)
            nextStones += l.toString()
            nextStones += r.toString()
        } else {
            nextStones += (it.toLong() * 2024).toString()
        }
    }

    return day11part1(nextStones, left - 1)
}

private fun day11part2(stones: Map<String, Long>, left: Int): Map<String, Long> {
    if (left == 0) return stones

    val nextStones = mutableMapOf<String, Long>()
    stones.forEach { (it, sz) ->
        if (it == "0") {
            nextStones["1"] = nextStones.getOrDefault("1", 0) + sz
        } else if (it.length % 2 == 0) {
            val (l, r) = it.chunked(it.length / 2).map(String::toLong).map(Long::toString)
            nextStones[l] = nextStones.getOrDefault(l, 0) + sz
            nextStones[r] = nextStones.getOrDefault(r, 0) + sz
        } else {
            val v = it.toLong() * 2024
            nextStones[v.toString()] = nextStones.getOrDefault(v.toString(), 0) + sz
        }
    }

    return day11part2(nextStones, left - 1)
}



private fun main() {
    val stones = File("day11.txt").readText().dropLastWhile { it == '\n' }.split(" ")
    println(day11part1(stones, 25).size)

    val stonesMap = stones.groupBy { it }.mapValues { it.value.size.toLong() }

    println(day11part2(stonesMap, 75).entries.sumOf { it.value })
}
