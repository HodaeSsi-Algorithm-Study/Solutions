fun main() {
    val n = readln().toInt()
    val m = readln().toInt()
    val dist = Array(n+1) { IntArray(n+1) { if (it == 0) 0 else Int.MAX_VALUE } }
    for (i in 1..n) {
        dist[i][i] = 0
    }

    for (i in 0 until m) {
        val (a, b, c) = readln().split(" ").map { it.toInt() }
        dist[a][b] = minOf(dist[a][b], c)
    }

    // 플로이드-와샬
    for (k in 1..n) {
        for (i in 1..n) {
            for (j in 1..n) {
                if (dist[i][k] != Int.MAX_VALUE && dist[k][j] != Int.MAX_VALUE) {
                    dist[i][j] = minOf(dist[i][j], dist[i][k] + dist[k][j])
                }
            }
        }
    }

    // 결과 출력(도달할 수 없는 경우는 0 출력)
    for (i in 1..n) {
        for (j in 1..n) {
            if (dist[i][j] == Int.MAX_VALUE) {
                print("0 ")
            } else {
                print("${dist[i][j]} ")
            }
        }
        println()
    }
}