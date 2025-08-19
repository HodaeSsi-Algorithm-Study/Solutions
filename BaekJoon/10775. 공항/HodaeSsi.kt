fun main() {
    val G = readLine()!!.toInt()
    val P = readLine()!!.toInt()
    val gList = IntArray(P)

    for (i in 0 until P) {
        gList[i] = readLine()!!.toInt()
    }

    var answer = 0
    val visited = Array(G) { Pair(false, 0) } // { 선점 플래그, 내 앞으로 몇개의 자리가 나갔는지 }
    for (i in 0 until P) {
        if (visited[gList[i] - 1].first) {
            var idx = gList[i] - 1
            while (true) {
                if (idx < 0) {
                    println(answer)
                    return
                } else if (!visited[idx].first) {
                    visited[idx] = Pair(true, 0)
                    answer++
                    break
                }

                visited[idx] = Pair(true, visited[idx].second + 1)
                idx -= (visited[idx].second)
            }
        } else {
            visited[gList[i] - 1] = Pair(true, 0)
            answer++
        }
    }

    println(answer)
}