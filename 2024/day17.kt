private fun main() {
    val file = getFile("day17.txt")
    val (first, second) = file.split("\n\n")
    val A = first.split("\n")[0].substringAfter("Register A: ").toLong()
    val B = first.split("\n")[1].substringAfter("Register B: ").toLong()
    val C = first.split("\n")[2].substringAfter("Register C: ").toLong()
    val instructions = second.substringAfter("Program: ").split(",").map(String::toInt)
    println(day17part1(A, B, C, instructions))

    val a =day17part1(7734433233334433333, B, C, instructions)
    println(a)
    return

    val result = instructions.joinToString(",")
    for (i in 30000000000000..100000000000000) {
        if(i % 10000000 == 0L) {
            println(i)
        }
        if (result == day17part1(30000000000000, B, C, instructions)) {
            println(i)
            return
        }
    }
}

private fun day17part1(A: Long, B: Long, C: Long, instructions: List<Int>): String {
    var A = A
    var B = B
    var C = C

    val instructions = instructions
    val res = mutableListOf<Long>()
    var i = 0
    while (i < instructions.size) {
        val opcode = instructions[i]
        when (opcode) {
            0 -> {  // 5. A = A / 8
                A = (A / Math.pow(2.0, combo(A, B, C, instructions, ++i) + 0.0)).toLong()
                i++
            }

            1 -> { // 2. LBS 를 반전시킨다 (따라서 B 의 원래값 또는 B-1 이 된다)
                // 6. B = B xor C
                B = B xor instructions[++i].toLong()
                i++
            }

            2 -> { // 1. B 에 A%8 을 넣는다 (0~7)
                B = combo(A, B, C, instructions, ++i) % 8L
                i++
            }

            3 -> { // 8. A 가 0 이 아니면 처음부터 다시
                if (A == 0L) {
                    // noop
                    i++
                    i++
                } else {
                    i = instructions[++i]
                }
            }

            4 -> { // 4. B % C    (B %= min(8, C))
                ++i
                B = B xor C
                i++
            }

            5 -> { // 7. B % 8 을 출력(저장)
                val v = combo(A, B, C, instructions, ++i) % 8
                res.add(v)
                i++
            }

            6 -> {
                B = (A / Math.pow(2.0, combo(A, B, C, instructions, ++i) + 0.0)).toLong()
                i++
            }

            7 -> { // 3. C = A / (2^B)
                C = (A / Math.pow(2.0, combo(A, B, C, instructions, ++i) + 0.0)).toLong()
                i++
            }

            else -> throw IllegalArgumentException("Invalid opcode")
        }
    }

    return res.joinToString(",")
}

private fun combo(A: Long, B: Long, C: Long, instructions: List<Int>, cursor: Int): Long {
    return when (instructions[cursor]) {
        0 -> 0
        1 -> 1
        2 -> 2
        3 -> 3
        4 -> A
        5 -> B
        6 -> C
        else -> throw IllegalArgumentException("Invalid opcode")
    }
}
