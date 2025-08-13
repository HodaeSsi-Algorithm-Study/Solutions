import java.util.PriorityQueue

fun main() {
    val n = readLine()!!.toInt()
    val m = readLine()!!.toInt()

    // [[최단 거리, 이전 노드], ...] (모두 -1, i로 초기화)
    val distList = Array(n+1) {
        IntArray(2) { -1 }
    }
    val adjList = Array(n+1) { mutableListOf<Pair<Int, Int>>() } // [[다음 노드, 가중치], ...]

    for (i in 1..m) {
        val (a, b, c) = readLine()!!.split(" ").map { it.toInt() }
        adjList[a].add(Pair(b, c))
    }

    val (start, end) = readLine()!!.split(" ").map { it.toInt() }

    if (start == end) {
        println(0)
        println(1)
        println(start)
        return
    }

    // 다익스트라 알고리즘
    val pq = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first }) // [(최단 거리, 이전 노드, 다음 노드), ...]

    pq.add(Triple(0, -1, start))

    while (pq.isNotEmpty()) {
        val (dist, from, to) = pq.poll()

        if (distList[to][0] != -1) continue

        distList[to][0] = dist
        distList[to][1] = from

        for ((next, weight) in adjList[to]) {
            if (distList[next][0] == -1 || dist + weight < distList[next][0]) {
                pq.add(Triple(dist + weight, to, next))
            }
        }
    }

    val nodeList = mutableListOf<Int>()
    var prevNode = distList[end][1]
    nodeList.add(end)
    while (prevNode != start) {
        nodeList.add(prevNode)
        prevNode = distList[prevNode][1]
    }
    println(distList[end][0])
    println(nodeList.size + 1)
    println("$start " + nodeList.reversed().joinToString(" ") { it.toString() })
}