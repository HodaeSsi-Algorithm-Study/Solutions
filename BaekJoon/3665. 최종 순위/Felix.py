from collections import deque
from sys import stdin
input = stdin.readline

test_cases = int(input())


def solve(n, order, quries):
    reqs = [0] * (n+1)
    losers = [set() for _ in range(n+1)]

    for i in range(n-1):
        for j in range(i+1, n):
            win, lose = order[i], order[j]

            losers[win].add(lose)
            reqs[lose] += 1

    for one, two in quries:
        if one in losers[two]:
            win, lose = one, two
        else:
            win, lose = two, one
        losers[lose].remove(win)
        losers[win].add(lose)
        reqs[lose] += 1
        reqs[win] -= 1

    queue = deque()
    for player in range(1, n+1):
        if reqs[player] == 0:
            queue.append(player)

    new_order = []

    while queue:
        if 2 <= len(queue):
            return "?"

        win = queue.popleft()
        new_order.append(win)

        for lose in losers[win]:
            reqs[lose] -= 1
            if reqs[lose] == 0:
                queue.append(lose)

    return " ".join(map(str, new_order)) if len(new_order) == n else "IMPOSSIBLE"


answer = []

for _test_case in range(test_cases):
    n = int(input())
    order = list(map(int, input().split()))
    quriy_cnt = int(input())
    quries = [tuple(map(int, input().split())) for _ in range(quriy_cnt)]
    answer.append(solve(n, order, quries))

print(*answer, sep="\n")
