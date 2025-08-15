import kotlin.math.abs

fun main() {
    fun dist(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
        return abs(a.first - b.first) + abs(a.second - b.second)
    }

    val N = readLine()!!.toInt() // 좌표는 1 ~ N 까지 존재
    val W = readLine()!!.toInt()

    val wPoints = mutableListOf<Pair<Int, Int>>()
    wPoints.add(Pair(-1, -1)) // 패딩
    for (i in 0 until W) {
        val (y, x) = readLine()!!.split(" ").map { it.toInt() }
        wPoints.add(Pair(y, x))
    }

    // dp[n][m][0] = 1번 경찰차가 n번째 사건 포인트에, 2번 경찰차가 m번째 사건 포인트에 있을 때 최소 이동 거리
    // dp[n][m][1] = 이전 dp의 y좌표
    // dp[n][m][2] = 이전 dp의 x좌표
    val dp = Array(W + 1) { Array(W + 1) { Triple(Int.MAX_VALUE, 0, 0) } }
    dp[0][1] = Triple(dist(Pair(N, N), wPoints[1]), 0, 0)
    dp[1][0] = Triple(dist(Pair(1, 1), wPoints[1]), 0, 0)

    for (w in 2..W) {
        // dp[n][w]
        for (n in 0..w-1) {
            if (n == w-1) {
                var minValue = Triple(Int.MAX_VALUE, -1, -1)
                for (i in 0..w - 2) {
                    if (i == 0 ) {
                        if (dp[w - 1][i].first + dist(Pair(N, N), wPoints[w]) < minValue.first) {
                            minValue = Triple(
                                dp[w - 1][i].first + dist(Pair(N, N), wPoints[w]),
                                w-1,
                                i)
                        }
                    } else {
                        if (dp[w - 1][i].first + dist(wPoints[i], wPoints[w]) < minValue.first) {
                            minValue = Triple(
                                dp[w - 1][i].first + dist(wPoints[i], wPoints[w]),
                                w-1,
                                i
                            )
                        }
                    }
                }
                dp[n][w] = minValue
            } else {
                dp[n][w] = Triple(
                    dp[n][w-1].first + dist(wPoints[w-1], wPoints[w]),
                    n,
                    w-1
                )
            }
        }

        for (m in 0..w-1) {
            if (m == w-1) {
                var minValue = Triple(Int.MAX_VALUE, -1, -1)
                for (i in 0..w - 2) {
                    if (i == 0 ) {
                        if (dp[i][w - 1].first + dist(Pair(1, 1), wPoints[w]) < minValue.first) {
                            minValue = Triple(
                                dp[i][w - 1].first + dist(Pair(1, 1), wPoints[w]),
                                i,
                                w-1
                            )
                        }
                    } else {
                        if (dp[i][w - 1].first + dist(wPoints[i], wPoints[w]) < minValue.first) {
                            minValue = Triple(
                                dp[i][w - 1].first + dist(wPoints[i], wPoints[w]),
                                i,
                                w-1
                            )
                        }
                    }
                }
                dp[w][m] = minValue
            } else {
                dp[w][m] = Triple(
                    dp[w-1][m].first + dist(wPoints[w-1], wPoints[w]),
                    w-1,
                    m
                )
            }
        }
    }

    var minValue = Triple(Int.MAX_VALUE, -1, -1)
    for (i in 0..W) {
        if (dp[i][W].first < minValue.first) {
            minValue = Triple(dp[i][W].first, i, W)
        }
        if (dp[W][i].first < minValue.first) {
            minValue = Triple(dp[W][i].first, W, i)
        }
    }
    println(minValue.first)

    val intList = mutableListOf<Int>()
    while (!(minValue.second == 0 && minValue.third == 0)) {
        val prevValue = dp[minValue.second][minValue.third]
        if (minValue.second == prevValue.second) {
            intList.add(2)
        } else if (minValue.third == prevValue.third) {
            intList.add(1)
        }
        minValue = Triple(prevValue.first, prevValue.second, prevValue.third)
    }
    intList.reverse()
    for (i in intList.indices) {
        println(intList[i])
    }
}