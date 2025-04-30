from math import ceil

def solution(edges, target):
    n = len(target)
    tree = {i: [] for i in range(1, n+1)}
    for u, v in edges:
        tree[u].append(v)
    for u in tree:
        tree[u].sort()
    cursor = {i: 0 for i in range(1, n+1)}

    leaf_seq = []
    visits = {i:0 for i in range(1, n+1) if not tree[i]}

    def drop_once():
        u = 1
        while tree[u]:
            idx = cursor[u] % len(tree[u])
            v = tree[u][idx]
            cursor[u] += 1
            u = v
        visits[u] += 1
        leaf_seq.append(u)

    # 최소 시도 횟수 찾기
    while True:
        if all(visits[i] >= ceil(target[i-1]/3) for i in visits):
            break
        drop_once()
        if any(visits[i] > target[i-1] for i in visits):
            return [-1]

    # 사전 순으로 값 배분
    m = len(leaf_seq)
    answer = [1]*m
    for u in leaf_seq:
        target[u-1] -= 1

    for i in range(m-1, -1, -1):
        u = leaf_seq[i]
        rem = target[u-1]
        if rem >= 2:
            answer[i] += 2
            target[u-1] -= 2
        elif rem == 1:
            answer[i] += 1
            target[u-1] -= 1

    return answer
