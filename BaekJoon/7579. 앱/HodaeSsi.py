import sys
input = sys.stdin.readline

N, M = map(int, input().split())

memoryList = list(map(int, input().split()))

costList = list(map(int, input().split()))

left, right = 0, sum(costList)
answer = right

# 비용을 기준으로 이진탐색 수행
while left <= right:
    mid = (left + right) // 2
    
    dp = [0] * (mid + 1)
    for i in range(N):
        memory = memoryList[i]
        cost = costList[i]
        
        for j in range(mid, cost - 1, -1):
            dp[j] = max(dp[j], dp[j - cost] + memory)
    
    if dp[mid] >= M:
        answer = mid
        right = mid - 1
    else:
        left = mid + 1

print(answer)
