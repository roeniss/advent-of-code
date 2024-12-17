import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter

private val BW = BufferedWriter(OutputStreamWriter(System.out))

data class Pos(
    val y: Int,
    val x: Int,
)


fun draw(field: MutableList<MutableList<Char>>) {
    field.forEach { row ->
        BW.write(row.joinToString(""))
        BW.newLine()
    }
    BW.newLine()
    BW.flush()
}

fun getFile(filename: String) = File(filename).readText().dropLastWhile { it == '\n' }

fun String.toImmutable2DChars() = this.split("\n").map { it.toCharArray().toList() }.toList()
fun String.toMutable2DChars() = this.split("\n").map { it.toCharArray().toMutableList() }.toMutableList()

// r b l t
val D4 = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

// r br b bl l tl t tr
val D8 = listOf(0 to 1, 1 to 1, 1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1)

fun List<List<Char>>.get(pos: Pos): Char? = getOrNull(pos.y)?.getOrNull(pos.x)
fun List<List<Int>>.get(pos: Pos): Int? = getOrNull(pos.y)?.getOrNull(pos.x)
fun MutableList<MutableList<Char>>.set(y: Int, x: Int, c: Char) {
    this[y][x] = c
}

fun Pair<Int, Int>.add(p: Pair<Int, Int>) = this.first + p.first to this.second + p.second
