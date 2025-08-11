fun main() {
    // 입력
//    3 4 (N, M)
//    1 2 4 (M만큼 반복)
//    1 3 3
//    2 3 -1
//    3 1 -2

    val (n, m) = readln().split(" ").map { it.toInt() }
    val dist = LongArray(n + 1) { Long.MAX_VALUE }
    dist[1] = 0
    val edges = mutableListOf<Triple<Int, Int, Int>>()

    repeat(m) {
        val (a, b, c) = readln().split(" ").map { it.toInt() }
        edges.add(Triple(a, b, c))
    }

    // 벨만 포드(음수 루프가 있다면 -1출력 후 종료)
    for (i in 0 until n) {
        // 모든 간선 조회
        for (e in edges) {
            val (start, end, cost) = e

            if (dist[start] != Long.MAX_VALUE && dist[start] + cost < dist[end]) {
                if (i == n - 1) {
                    // 음수 사이클이 존재하는 경우
                    println("-1")
                    return
                }

                dist[end] = dist[start] + cost
            }
        }
    }

    // 결과 출력
    for (i in 2..n) {
        if (dist[i] == Long.MAX_VALUE) {
            println("-1")
        } else {
            println(dist[i])
        }
    }
}