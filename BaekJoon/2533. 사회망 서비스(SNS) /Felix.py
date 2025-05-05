from sys import stdin, setrecursionlimit
input = stdin.readline
setrecursionlimit(1000000)


nodes = int(input())

graph = [[] for _ in range(nodes+1)]

for _ in range(nodes-1):
    one, two = map(int, input().split())

    graph[one].append(two)
    graph[two].append(one)


visited = [False] * (nodes+1)


def tree_dp(node):
    visited[node] = True

    check_cost, uncheck_cost = 1, 0

    for child in graph[node]:
        if visited[child]:
            continue
        child_check, child_uncheck = tree_dp(child)

        check_cost += min(child_check, child_uncheck)
        uncheck_cost += child_check

    return check_cost, uncheck_cost


print(min(tree_dp(1)))
