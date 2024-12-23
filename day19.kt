private fun main() {
    val (patterns, displays) = getFile("day19.txt").split("\n\n")
//    println(day19part1(patterns.split(", "), displays.split("\n")))
    println(day19part2(patterns.split(", "), displays.split("\n")))
}

private fun day19part1(patterns: List<String>, displays: List<String>): Int {
    return displays.count { canMake(it, patterns, 0) }
}

private fun day19part2(patterns: List<String>, displays: List<String>): Long {
    val coll = mutableSetOf<String>()
    patterns.forEach { pat ->
//        why am i doing this ???
//        for (i in 1..pat.length) {
//            coll.add(pat.substring(0, i))
//        }
        coll.add(pat)
    }


    return displays.sumOf {
        val cache = mutableMapOf<Int, Long>()
        val res = collectWays(it, coll, 0, cache)
        res
    }
}

private fun canMake(it: String, patterns: List<String>, i: Int): Boolean {
    if (i == it.length) return true

    return patterns.any { pat ->
        var j = 0
        while (i + j < it.length && j < pat.length) {
            if (it[i + j] != pat[j]) {
                break
            }
            j++
        }
        if (j < pat.length) false
        else canMake(it, patterns, i + j)
    }
}

private fun collectWays(it: String, coll: Set<String>, i: Int, cache: MutableMap<Int, Long>): Long {
    if (it.length == i) return 1L
    if (i in cache) return cache[i]!!

    var res = 0L
    for (j in 1..8) {
        if (i + j > it.length) continue
        val s = it.substring(i, i + j)
        if (s in coll) {
            res += collectWays(it, coll, i + j, cache)
        }
    }

    cache[i] = res
    return res
}

