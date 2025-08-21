import java.util.ArrayDeque

fun main() {
    val N = readln().toInt()
    if (N == 1) {
        println(0)
        return
    }

    val nodeCountList = IntArray(N + 1) { 1 } // 자신 포함 서브트리의 노드 개수
    val distanceList = LongArray(N + 1) { 0L } // 모든 노드까지의 거리 총합
    val adjList = Array(N + 1) { mutableListOf<Pair<Int, Int>>() } // {인접 노드, 거리}

    for (i in 0 until N - 1) {
        val (u, v, d) = readln().split(" ").map { it.toInt() }
        adjList[u].add(Pair(v, d))
        adjList[v].add(Pair(u, d))
    }

    // 첫 번째 DFS: 서브트리 크기 및 루트로부터의 거리 계산
    val parent = IntArray(N + 1) { 0 }
    val distFromRoot = LongArray(N + 1) { 0L }
    val visitOrder = ArrayList<Int>(N)
    val stack = ArrayDeque<Int>()

    stack.addLast(1)
    val visited = BooleanArray(N + 1) { false }
    visited[1] = true

    while (stack.isNotEmpty()) {
        val u = stack.removeLast()
        visitOrder.add(u)

        for ((v, d) in adjList[u]) {
            if (!visited[v]) {
                visited[v] = true
                parent[v] = u
                distFromRoot[v] = distFromRoot[u] + d
                stack.addLast(v)
            }
        }
    }

    for (node in visitOrder.asReversed()) {
        if (parent[node] != 0) {
            nodeCountList[parent[node]] += nodeCountList[node]
        }
    }

    val fromRoot = distFromRoot.sum()
    distanceList[1] = fromRoot

    // 두 번째 DFS: 각 노드의 거리 총합 계산
    for (node in visitOrder) {
        for ((next, d) in adjList[node]) {
            if (parent[next] == node) {
                distanceList[next] =
                    distanceList[node] - d.toLong() * nodeCountList[next] + d.toLong() * (N - nodeCountList[next])
            }
        }
    }

    val sb = StringBuilder()
    for (i in 1..N) {
        sb.append(distanceList[i]).append('\n')
    }
    print(sb)
}