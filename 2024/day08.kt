import java.io.File


private fun collectAntennas(field: List<List<Char>>): Collection<List<Pos>> {
    return field
        .flatMapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                cell to Pos(y, x)
            }
        }
        .filter { it.first != '.' }
        .groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .values
}

private fun <T> createPairs(l: List<T>): List<Pair<T, T>> {
    return l
        .flatMap { a -> l.map { b -> a to b } }
        .filter { (a, b) -> a != b }
        .map { (a, b) -> setOf(a, b) }
        .toSet()
        .map { it.first() to it.last() }
}

private fun collectAntinodes(field: List<List<Char>>, sameFrequencyAntennas: List<Pos>): Collection<Pos> {
    return createPairs(sameFrequencyAntennas)
        .flatMap { (a, b) ->
            val yDelta = b.y - a.y
            val xDelta = b.x - a.x
            val collections = mutableListOf<Pos>()
            for (i in 0 until 150) { // for part1, i is only 1
                collections.addAll(
                    listOf(
                        Pos((a.y - yDelta * i), (a.x - xDelta * i)),
                        Pos((b.y + yDelta * i), (b.x + xDelta * i)),
                    )
                )
            }
            collections
        }
        .filter { (y, x) -> y in field.indices && x in field[0].indices }
}

private fun day08part1(field: List<List<Char>>): Int {
    return collectAntennas(field)
        .flatMap { collectAntinodes(field, it) }
        .toSet()
        .count()
}


private fun day08part2(field: List<List<Char>>): Int {
    return collectAntennas(field)
        .flatMap { collectAntinodes(field, it) }
        .toSet()
        .count()
}

private fun main() {
    val lines = File("day08.txt").readText().dropLastWhile { it == '\n' }.split("\n").map { it.toCharArray().toList() }
//    println(day08part1(lines))
    println(day08part2(lines))
}
