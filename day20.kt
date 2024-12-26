import java.util.PriorityQueue
import kotlin.math.abs

private fun main() {
    val field = getFile("day20.txt").toImmutable2DChars()
//    println(day20part1(field))
    println(day20part2(field))
}

private fun day20part1(field: List<List<Char>>): Int {
    val dist = MutableList(field.size) { MutableList(field[0].size) { Long.MAX_VALUE } }
    calcOriginalPathLength(field, dist).also { println("originalPathLength : $it") }
    return getShorterPathCount(field, dist, 100).also { println("shorterPathCount : $it") }
}

private fun day20part2(field: List<List<Char>>): Int {
    val dist = MutableList(field.size) { MutableList(field[0].size) { Long.MAX_VALUE } }
    calcOriginalPathLength(field, dist).also { println("originalPathLength : $it") }
    val originalPath = getOriginalPath(field)
    return getShorterPathCountV2(field, dist, 100, originalPath).also { println("shorterPathCount : $it") }
}

private fun calcOriginalPathLength(field: List<List<Char>>, dist: MutableList<MutableList<Long>>): Long {
    val S = field.find('S')
    dist.set(S, 0)
    val E = field.find('E')
    val q = PriorityQueue<Pair<Pos, Long>>(compareBy { it.second })
    q.add(S to 0)
    while (q.isNotEmpty()) {
        val (pos, d) = q.poll()
        if (pos == E) return d
        D4.forEach { (dy, dx) ->
            val newPos = pos.copy(y = pos.y + dy, x = pos.x + dx)
            if (!newPos.inside(field) || field.get(newPos) == '#' || dist.get(newPos) <= d + 1) return@forEach
            dist.set(newPos, d + 1)
            q.add(newPos to d + 1)
        }
    }
    throw Exception("No path found")
}

private fun getShorterPathCount(field: List<List<Char>>, dist: MutableList<MutableList<Long>>, savingTime: Long): Int {
    val S = field.find('S')
    val E = field.find('E')
    val q = PriorityQueue<Triple<Pos, Long, Boolean>>(compareBy { it.second }) // pos, d, cheated
    var cnt = 0
    q.add(Triple(S, 0, false))
    while (q.isNotEmpty()) {
        val (pos, d, cheated) = q.poll()
        if (pos == E) {
            val originalTime = dist.get(E)
            if (originalTime - d >= savingTime) cnt++
            continue
        }

        D4.forEach { (dy, dx) ->
            val newPos = pos.copy(y = pos.y + dy, x = pos.x + dx)
            if (!newPos.inside(field)) {
                return@forEach
            }
            if (cheated && field.get(newPos) == '#') {
                return@forEach
            }
            if (!cheated && field.get(newPos) == '#') {
                dist.set(newPos, d + 1)
                q.add(Triple(newPos, d + 1, true))
                return@forEach
            }
            if (field.get(newPos) != '#' && dist.get(newPos) >= d + 1) {
                if (cheated) {
                    val originalTime = dist.get(newPos)
                    if (originalTime - (d + 1) >= savingTime) {
                        cnt++
                    }
                    return@forEach
                }
                q.add(Triple(newPos, d + 1, cheated))
                return@forEach
            }
            return@forEach
        }
    }

    return cnt
}

private fun getOriginalPath(field: List<List<Char>>): List<Pos> {
    val dist = MutableList(field.size) { MutableList(field[0].size) { Long.MAX_VALUE } }
    val S = field.find('S')
    dist.set(S, 0)
    val E = field.find('E')
    val q = PriorityQueue<Triple<Pos, Long, MutableList<Pos>>>(compareBy { it.second })
    q.add(Triple(S, 0, mutableListOf(S)))
    while (q.isNotEmpty()) {
        val (pos, d, path) = q.poll()
        if (pos == E) return path
        D4.forEach { (dy, dx) ->
            val newPos = pos.copy(y = pos.y + dy, x = pos.x + dx)
            if (!newPos.inside(field) || field.get(newPos) == '#' || dist.get(newPos) <= d + 1) return@forEach
            dist.set(newPos, d + 1)
            q.add(Triple(newPos, d + 1, (path + newPos).toMutableList()))
        }
    }
    throw Exception("No path found")
}


private fun getShorterPathCountV2(
    field: List<List<Char>>,
    dist: MutableList<MutableList<Long>>,
    savingTime: Long,
    originalPath: List<Pos>
): Int {
//    val tmp = mutableMapOf<Int, Int>()
    return originalPath.sumOf { cur ->
        val circularRange: List<Pos> = getCircularRange(field, cur, 20)

        return@sumOf circularRange.count {
            val originalTime = dist.get(it)
            val len = linearDistance(cur, it)
            val shorterTime = dist.get(cur)
            if(originalTime == Long.MAX_VALUE) return@count false
            if(originalTime <= shorterTime) return@count false
            if(originalTime - (shorterTime+len) >= savingTime){
                val v = (originalTime - (shorterTime + len)).toInt()
//                tmp[v] = tmp.getOrDefault(v, 0) + 1
                return@count true
            }else{
                return@count false
            }
        }
    }/*.also {
        tmp.toSortedMap().forEach(::println)
    }*/
}

private fun getCircularRange(field: List<List<Char>>, cur: Pos, maxLen: Int): List<Pos> {
    val res = mutableListOf<Pos>()
    val vis = MutableList(field.size) { MutableList(field[0].size) { false } }
    val q = PriorityQueue<Pair<Pos, Int>>(compareBy { it.second })
    q.add(cur to 0)
    while (q.isNotEmpty()) {
        val (pos, d) = q.poll()
        if (d > maxLen) continue
        res.add(pos)
        D4.forEach { (dy, dx) ->
            val newPos = pos.copy(y = pos.y + dy, x = pos.x + dx)
            if (!newPos.inside(field) || vis.get(newPos)) return@forEach
            vis.set(newPos, true)
            q.add(newPos to d + 1)
        }
    }

    return res.filter { field.get(it) != '#' }.filter{ it != cur }
}

private fun linearDistance(cur: Pos, it: Pos): Int {
    return abs(cur.y - it.y) + abs(cur.x - it.x)
}
