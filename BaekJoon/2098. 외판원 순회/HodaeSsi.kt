fun main() {
    val N = readLine()!!.toInt()
    val adjMatrix = Array(N) { IntArray(N) }

    for (i in 0 until N) {
        val row = readLine()!!.split(" ").map { it.toInt() }
        for (j in 0 until N) {
            adjMatrix[i][j] = row[j]
        }
    }

    // dp[node][mask] = 방문 상태(mask)를 가지고 node에 도달하는 최소 거리
    val dp = Array(N) { IntArray(1 shl N) { Int.MAX_VALUE } }
    dp[0][1] = 0

    for (mask in 1 until (1 shl N)) {
        for (n in 0 until N) {
            if (mask and (1 shl n) == 0) continue

            val prevMask = mask xor (1 shl n)
            var minValue = Int.MAX_VALUE
            for (m in 0 until N) {
                if (n == m || prevMask and (1 shl m) == 0) continue

                if (dp[m][prevMask] != Int.MAX_VALUE &&
                    adjMatrix[m][n] != 0 &&
                    dp[m][prevMask] + adjMatrix[m][n] < minValue) {
                    minValue = dp[m][prevMask] + adjMatrix[m][n]
                    dp[n][mask] = minValue
                }
            }
        }
    }

    var minValue = Int.MAX_VALUE
    for (i in 0 until N) {
        if (adjMatrix[i][0] != 0 &&
            dp[i][(1 shl N) - 1] != Int.MAX_VALUE &&
            dp[i][(1 shl N) - 1] + adjMatrix[i][0] < minValue) {
            minValue = dp[i][(1 shl N) - 1] + adjMatrix[i][0]
        }
    }
    println(minValue)
}