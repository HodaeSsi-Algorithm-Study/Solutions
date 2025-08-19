import java.util.PriorityQueue
import kotlin.math.max

fun main() {
    val N = readLine()!!.toInt()
    val heightList = IntArray(N)

    for (i in 0 until N) {
        heightList[i] = readLine()!!.toInt()
    }

    var maxArea = 0
    val maxHeap = PriorityQueue<Pair<Int, Int>> {
            a, b -> b.first.compareTo(a.first)
    } // {높이, 시작위치}
    var lastHeight = -1
    for (i in 0 until N) {
        if (heightList[i] > lastHeight) {
            maxHeap.offer(Pair(heightList[i], i))
        } else if (heightList[i] == lastHeight) {
            continue
        } else {
            var lowPoint = Int.MAX_VALUE
            while (maxHeap.isNotEmpty() && maxHeap.peek().first > heightList[i]) {
                val (height, startPoint) = maxHeap.poll()
                if ((i - startPoint) * height > maxArea) {
                    maxArea = (i - startPoint) * height
                }
                if (startPoint < lowPoint) {
                    lowPoint = startPoint
                }
            }

            if (maxHeap.isEmpty() || maxHeap.peek().first < heightList[i]) {
                maxHeap.offer(Pair(heightList[i], lowPoint))
            }
        }
        lastHeight = heightList[i]
    }

    while (maxHeap.isNotEmpty()) {
        val (height, startPoint) = maxHeap.poll()
        if ((N - startPoint) * height > maxArea) {
            maxArea = (N - startPoint) * height
        }
    }

    println(maxArea)
}