import sys
from collections import deque

input = sys.stdin.readline
t = int(input())

for _ in range(t):
	n = int(input())
	n_list = list(map(int, input().split()))
	
	indegree = [0] * (n + 1)
	graph = [[] for _ in range(n + 1)]
	
	for i in range(n):
		for j in range(i + 1, n):
			graph[n_list[i]].append(n_list[j])
			indegree[n_list[j]] += 1
	
	m = int(input())
	for _ in range(m):
		a, b = map(int, input().split())
		if b in graph[a]:
			graph[a].remove(b)
			graph[b].append(a)
			indegree[b] -= 1
			indegree[a] += 1
		else:
			graph[b].remove(a)
			graph[a].append(b)
			indegree[a] -= 1
			indegree[b] += 1
	
	result = []
	q = deque()
	
	for i in range(1, n + 1):
		if indegree[i] == 0:
			q.append(i)
	
	certain = True
	for _ in range(n):
		if len(q) == 0:
			print("IMPOSSIBLE")
			certain = False
			break
		if len(q) > 1:
			print("?")
			certain = False
			break
			
		now = q.popleft()
		result.append(now)
		
		for next_node in graph[now]:
			indegree[next_node] -= 1
			if indegree[next_node] == 0:
				q.append(next_node)
	
	if certain:
		print(*result)
	