import kotlin.math.abs

private fun main() {
    val file = getFile("day16.txt")
    println(day16part1(file.toImmutable2DChars()))
    println(day16part2(file.toImmutable2DChars()))
}

private fun day16part2(m: List<List<Char>>): Any {
    val cur = find(m, 'S')
    val goal = find(m, 'E')
    val dp = MutableList(m.size) { MutableList(m[0].size) { MutableList(4) { Int.MAX_VALUE } } }
    val ans = mutableSetOf<Pos>()

    dp[cur.y][cur.x][0] = 0
    dp[cur.y][cur.x][1] = 0
    dp[cur.y][cur.x][2] = 0
    dp[cur.y][cur.x][3] = 0

    dfs(m, dp, cur, 0, 0)
    dfs2(m, dp, cur, 0, 0, setOf(), ans, goal)

    return ans.count()
}

private fun day16part1(m: List<List<Char>>): Int {
    val cur = find(m, 'S')
    val goal = find(m, 'E')
    val dp = MutableList(m.size) { MutableList(m[0].size) { MutableList(4) { Int.MAX_VALUE } } }

    dfs(m, dp, cur, 0, 0)

    return dp[goal.y][goal.x].min()
}

private fun dfs(m: List<List<Char>>, dp: MutableList<MutableList<MutableList<Int>>>, cur: Pos, dir: Int, v: Int) {
    D4.forEachIndexed { nd, (dy, dx) ->
        val ny = cur.y + dy
        val nx = cur.x + dx
        val nv = (if (abs(nd - dir) % 2 == 0) abs(nd - dir) * 1000 else 1000) + v + 1

        if (ny in m.indices && nx in m[0].indices && m[ny][nx] != '#' && dp[ny][nx][dir] > nv) {
            dp[ny][nx][dir] = nv
            dfs(m, dp, Pos(ny, nx), nd, nv)
        }
    }
}

private fun dfs2(
    m: List<List<Char>>,
    dp: MutableList<MutableList<MutableList<Int>>>,
    cur: Pos,
    dir: Int,
    v: Int,
    history: Set<Pos>,
    ans: MutableSet<Pos>,
    goal: Pos
) {
    val newHistory = history + cur
    if (cur == goal) {
        ans.addAll(newHistory)
        return
    }

    D4.forEachIndexed { nd, (dy, dx) ->
        val ny = cur.y + dy
        val nx = cur.x + dx
        val nv = (if (abs(nd - dir) % 2 == 0) abs(nd - dir) * 1000 else 1000) + v + 1

        if (ny in m.indices && nx in m[0].indices && m[ny][nx] != '#' && dp[ny][nx][dir] >= nv) {
            dfs2(m, dp, Pos(ny, nx), nd, nv, newHistory, ans, goal)
        }
    }
}

private fun find(m: List<List<Char>>, v: Char): Pos {
    for (i in m.indices) {
        for (j in m[0].indices) {
            if (m[i][j] == v) {
                return Pos(i, j)
            }
        }
    }
    TODO()
}
