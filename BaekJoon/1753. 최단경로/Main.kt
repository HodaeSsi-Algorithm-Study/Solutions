import java.util.PriorityQueue

fun main() {
    val (v, e) = readln().split(" ").map { it.toInt() }
    val start = readln().toInt()
    val graph = Array(v + 1) { mutableListOf<Pair<Int, Int>>() }

    repeat(e) {
        val (a, b, c) = readln().split(" ").map { it.toInt() }
        graph[a].add(Pair(b, c))
    }

    // 다익스트라 알고리즘
    val distance = IntArray(v + 1) { Int.MAX_VALUE }

    // 최소힙
    val minHeap = PriorityQueue<Pair<Int, Int>>(compareBy { it.first }) // Pair : (거리, 노드)

    distance[start] = 0
    minHeap.add(Pair(0, start))
    while (minHeap.isNotEmpty()) {
        val (dist, node) = minHeap.poll()

        if (distance[node] < dist) continue // 이미 더 짧은 경로가 있는 경우

        for ((nextNode, weight) in graph[node]) {
            val newDist = dist + weight
            if (newDist < distance[nextNode]) {
                distance[nextNode] = newDist
                minHeap.add(Pair(newDist, nextNode))
            }
        }
    }

    // 결과 출력
    for (i in 1..v) {
        if (distance[i] == Int.MAX_VALUE) {
            println("INF")
        } else {
            println(distance[i])
        }
    }
}