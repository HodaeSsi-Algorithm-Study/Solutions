import sys

input = sys.stdin.readline
N, R, Q = map(int, input().split())

adj_list = [[] for _ in range(N + 1)]
for _ in range(N - 1):
	u, v = map(int, input().split())
	adj_list[u].append(v)
	adj_list[v].append(u)

subtree_size = [0] * (N + 1)
visited = [False] * (N + 1)
stack = [(R, -1, False)]  # (node, parent, is_processed)

while stack:
	node, parent, is_processed = stack.pop()
	
	if not is_processed:
		visited[node] = True
		stack.append((node, parent, True))
		for child in adj_list[node]:
			if child != parent and not visited[child]:
				stack.append((child, node, False))
	else:
		subtree_size[node] = 1
		for child in adj_list[node]:
			if child != parent:
				subtree_size[node] += subtree_size[child]

for _ in range(Q):
	query = int(input())
	print(subtree_size[query])