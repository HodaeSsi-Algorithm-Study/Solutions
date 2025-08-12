fun main() {
    val MAX_SIZE = 100_000
    val MIN_SIZE = 0

    val (N, K) = readln().split(" ").map { it.toInt() }
    // 첫 번째 원소는 거리(방문한 적 없으면 -1, 이전 경로의 노드)
    val dist = Array(MAX_SIZE + 1) { mutableListOf<Int>() }
    for (i in 0..MAX_SIZE) {
        dist[i].add(-1) // 방문하지 않은 상태로 초기화
        dist[i].add(-1)
    }

    if (N == K) {
        println(0)
        println(N)
        return
    }

    // BFS를 위한 큐
    val q = ArrayDeque<Int>()
    q.add(N)
    dist[N][0] = 0

    var isFind = false
    while (q.isNotEmpty()) {
        val now = q.removeFirst()

        for (i in 0 until 3) {
            var next = now
            if (i == 0) {
                next *= 2
                if (next > MAX_SIZE) continue
            } else if (i == 1) {
                next += 1
                if (next > MAX_SIZE) continue
            } else {
                next -= 1
                if (next < MIN_SIZE) continue
            }

            if (dist[next][0] != -1) continue

            q.add(next)
            dist[next][0] = dist[now][0] + 1
            dist[next][1] = now

            if (next == K) {
                isFind = true
                break
            }
        }

        if (isFind) {
            println(dist[K][0])
            var prevNode = K
            val path = mutableListOf<Int>()
            path.add(K)
            while (prevNode != N) {
                path.add(dist[prevNode][1])
                prevNode = dist[prevNode][1]
            }
            path.reverse()
            println(path.joinToString(" "))
            return
        }
    }
}