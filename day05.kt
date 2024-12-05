import java.io.File

fun createComparator(flowMap: Map<Int, Map<Int, Int>>) = object : Comparator<Int> {
    override fun compare(a: Int, b: Int): Int {
        if (b in flowMap && flowMap[b]!![a] != null) return -1
        return 1
    }
}

private fun noProblem(ints: List<Int>, map: MutableMap<Int, MutableMap<Int, Int>>): Boolean {
    ints.forEachIndexed { i, a ->
        ints.forEachIndexed { j, b ->
            if (i < j && map.getOrDefault(b, mutableMapOf())[a] != null) {
                return false
            }
        }
    }
    return true
}

fun day05part1(rules: List<String>, updates: List<String>): Int {
    val flowMap = mutableMapOf<Int, MutableMap<Int, Int>>()
    rules.map { it.split("|") }.map { it[0].toInt() to it[1].toInt() }.associateBy { (l, r) ->
        if (l !in flowMap)
            flowMap[l] = mutableMapOf()
        flowMap[l]!![r] = 1
    }

    return updates.map { vals ->
        vals.split(",").map { int -> int.toInt() }
    }.filter {
        noProblem(it, flowMap)
    }.sumOf {
        it[it.size / 2]
    }
}

fun day05part2(rules: List<String>, updates: List<String>): Int {
    val flowMap = mutableMapOf<Int, MutableMap<Int, Int>>()
    rules.map { it.split("|") }.map { it[0].toInt() to it[1].toInt() }.associateBy { (l, r) ->
        if (l !in flowMap)
            flowMap[l] = mutableMapOf()
        flowMap[l]!![r] = 1
    }

    val comparator = createComparator(flowMap)

    return updates.map { vals ->
        vals.split(",").map { int -> int.toInt() }
    }.filterNot {
        noProblem(it, flowMap)
    }.map {
        it.sortedWith(comparator)
    }.sumOf {
        it[it.size / 2]
    }
}

fun main() {
    val text = File("day05.txt").readText().dropLastWhile { it == '\n' }
    val (rules, updates) = text.split("\n\n")
    println(day05part1(rules.split("\n"), updates.split("\n")))
    println(day05part2(rules.split("\n"), updates.split("\n")))
}
