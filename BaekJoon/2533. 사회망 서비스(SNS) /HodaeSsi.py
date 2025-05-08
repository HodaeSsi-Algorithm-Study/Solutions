import sys

N = int(sys.stdin.readline().strip())

adj_list = [[] for _ in range(N + 1)]
for _ in range(N - 1):
	u, v = map(int, sys.stdin.readline().strip().split())
	adj_list[u].append(v)
	adj_list[v].append(u)

subtree_size = [0] * (N + 1)
visited = [False] * (N + 1)
painted = [False] * (N + 1)
stack = [(1, -1, False)]
 
answer = 0
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
		for child in adj_list[node]:
			# 자식 중 하나라도 painted 되어있지 않다면 노드 색칠
			if child != parent and not painted[child]:
				painted[node] = True
				answer += 1
				break
		# 자식이 모두 painted 되어있거나 
		# 리프 노드인 경우는 색칠하지 않음

print(answer)