import java.io.File
import java.math.BigInteger
import java.util.*
import kotlin.math.max

private fun parseSpace(diskMap: String): MutableList<Long> {
    var i = -1
    return diskMap.windowed(size = 2, step = 2, partialWindows = true).flatMap {
        i++
        List(it[0].digitToInt()) { _ -> i + 0L } +
                if (it.length == 2) List(it[1].digitToInt()) { -1L } else emptyList()
    }.toMutableList()

}

private fun compact(space: List<Long>): List<Long> {
    var l = 0
    var r = space.size - 1
    val tmp = mutableListOf<Long>()
    while (l <= r) {
        if (l == r) {
            tmp += space[l]
            break
        }
        if (space[l] != -1L) {
            tmp += space[l]
            l++
        } else {
            l++
            while (space[r] == -1L) {
                r--
            }
            tmp += space[r]
            r--
        }
    }
    return tmp.toList()
}


private fun getChecksum(space: List<Long>): BigInteger {
    return space.mapIndexed { i, c ->
        (max(i, 0) * c).toBigInteger()
    }.fold(BigInteger.ZERO, BigInteger::add)
}

private fun visualize(space: List<Long>) {
    for (s in space) {
        print(if (s < 0) "." else s)
    }
}

private fun day09part1(diskMap: String): Long {
    val space = parseSpace(diskMap)
    val compactedSpace = compact(space)
//    visualize(space)
    return getChecksum(compactedSpace).toLong()
}


sealed class Node(
    var prev: Node?,
    var next: Node?,
)

class DataBlock(val id: Int, var size: Int, prev: Node?, next: Node?) : Node(prev, next)
class FreeSpace(var size: Int, prev: Node?, next: Node?) : Node(prev, next)

private fun nodize(diskMap: String): Node {
    val head: Node = FreeSpace(0, null, null)
    var cur = head
    var id = 0
    for (i in diskMap.indices) {
        val sz = diskMap[i].digitToInt()
        if (i % 2 == 0) {
            cur.next = DataBlock(id++, sz, cur, null)
            cur = cur.next!!
        } else {
            if (sz > 0) {
                cur.next = FreeSpace(sz, cur, null)
                cur = cur.next!!
            }
        }
    }
    return head.next!!
}

private fun compact(head: Node) {
    val dataBlockStack = Stack<DataBlock>()
    var cur: Node? = head
    while (cur != null) {
        if (cur is DataBlock) {
            dataBlockStack.push(cur)
        }
        cur = cur.next
    }

    while (dataBlockStack.isNotEmpty()) {
        cur = head
//        visualize(head)
        val dataBlock = dataBlockStack.pop()
        while (cur != null) {
            if (cur is FreeSpace && cur.size >= dataBlock.size) {
                val newFreeSpace = FreeSpace(dataBlock.size, dataBlock.prev, dataBlock.next)
                dataBlock.prev?.next = newFreeSpace
                dataBlock.next?.prev = newFreeSpace

                val newDataBlock = DataBlock(dataBlock.id, dataBlock.size, cur.prev, null)
                cur.prev?.next = newDataBlock
                val newFreeSpace2 = FreeSpace(cur.size - dataBlock.size, newDataBlock, cur.next)
                newDataBlock.next = newFreeSpace2
                cur.next?.prev = newFreeSpace2
                break
            }
            if (cur == dataBlock) {
                break
            }
            cur = cur.next
        }
//        visualize(head)
    }
}

private fun visualize(head: Node) {
    var cur: Node? = head
    while (cur != null) {
        if (cur is DataBlock) {
            val ch = if (cur.size == 0) "." else "${cur.id}"
            print(ch.repeat(cur.size))
        } else {
            print(".".repeat((cur as FreeSpace).size))
        }
        cur = cur.next
    }
    println()

}

private fun checkSum(head: Node): Long {
    var cur: Node? = head
    var sum = 0L
    var i = 0
    while (cur != null) {
        if (cur is DataBlock) {
            for (j in 0..<cur.size) {
                sum += cur.id * i
                i++
            }
        } else {
            i+=(cur as FreeSpace).size
        }
        cur = cur.next
    }
    return sum
}

private fun day09part2(diskMap: String): Long {
    val head = nodize(diskMap)

    compact(head)

//    visualize(head)

    return checkSum(head)
}

private fun main() {
    val diskMap = File("day09.txt").readText().dropLastWhile { it == '\n' }
//    println(day09part1(diskMap)) // relatively straightforward approach
    println(day09part2(diskMap)) // doubly-linked list approach
}
