from collections import deque
from itertools import chain, repeat


def solution(edges, target):
    target.insert(0, 0)
    root = 1
    nodes = len(edges) + 1

    graph = [[] for _ in range(nodes + 1)]
    rocks = [[] for _ in range(nodes + 1)]

    fulfil = [target[node] == 0 for node in range(nodes + 1)]
    left = sum(1 for i in target if i != 0)

    for parent, child in edges:
        graph[parent].append(child)

    for node in range(1, nodes):
        graph[node].sort()
        graph[node] = deque(graph[node])

    for rock_id in range(sum(target)):
        node = root

        while graph[node]:
            nxt_node = graph[node].popleft()
            graph[node].append(nxt_node)
            node = nxt_node

        rocks[node].append(rock_id)

        if not fulfil[node] and len(rocks[node]) <= target[node] <= 3 * len(rocks[node]):
            left -= 1
            fulfil[node] = True

        if target[node] < len(rocks[node]):
            return [-1]

        if left == 0:
            break

    ret = [0] * (rock_id + 1)

    for node in range(1, nodes + 1):
        point = target[node]
        rock_ids = rocks[node]

        if not point:
            continue

        left = point - len(rock_ids)
        threes = left // 2
        twos = left % 2
        ones = len(rock_ids) - twos - threes

        for rock_id, num in zip(rock_ids, chain(repeat(1, ones), repeat(2, twos), repeat(3, threes))):
            ret[rock_id] = num

    return ret


assert [1, 1, 2, 2, 2, 3, 3] == \
    solution([[2, 4], [1, 2], [6, 8], [1, 3], [5, 7], [2, 5], [
             3, 6], [6, 10], [6, 9]], [0, 0, 0, 3, 0, 0, 5, 1, 2, 3])
assert [1, 1, 3, 2, 3] == \
    solution([[1, 2], [1, 3]], [0, 7, 3])
assert [-1] == \
    solution([[1, 3], [1, 2]], [0, 7, 1])
