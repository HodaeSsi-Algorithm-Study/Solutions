import sys

n = int(sys.stdin.readline().rstrip())

weight_list = list(map(int, sys.stdin.readline().rstrip().split()))
root_idx = next((i + 1 for i, w in enumerate(weight_list) if w != 0), None)

adj_list = [[] for _ in range(n + 1)]
distance_list = [-1] * (n + 1) # 모든 서브 노드의 선물을 해결하는데 필요한 거리를 저장합니다.
for i in range(n - 1):
	a, b = map(int, sys.stdin.readline().rstrip().split())
	adj_list[a].append(b)
	adj_list[b].append(a)

q = int(sys.stdin.readline().rstrip())

queries = []
for _ in range(q):
	x, y, z = map(int, sys.stdin.readline().rstrip().split())
	queries.append((x, y, z))

# dfs를 통해서 distance_list를 초기화합니다.
def dfs1(node, parent=None):
	len = 0
	for child in adj_list[node]:
		if child == parent:
			continue

		len += dfs1(child, node)

	if weight_list[node - 1] != 0:
		distance_list[node] = len
	if len != 0:
		distance_list[node] = len
	
	if weight_list[node - 1] == 0 and len == 0:
		return 0
	elif node == root_idx:
		return len
	else:
		return len + 2
	
len = dfs1(root_idx, None)
print(len)

# 쿼리 처리
for x, y, z in queries:
	# 노드에서 가장 가까운 선물 경로까지의 거리를 계산합니다.
	def dfs2(node, parent=None, depth=0):
		if parent is not None and (weight_list[node - 1] != 0 or distance_list[node] != -1):
			return depth
		
		for child in adj_list[node]:
			if child == parent:
				continue

			result = dfs2(child, node, depth + 1)
			if result != 0:
				distance_list[node] = 1 # 새로운 경로로 연결됨 표시(정확한 값은 필요 없음)
				return result
			
		return 0

	weight_list[x - 1] += z
	# 이미 경로로 연결되어 있는 경우 거리에 영향을 미치지 않습니다.
	if distance_list[x] == -1:
		distance = dfs2(x, None, 0)
		distance_list[root_idx] += distance * 2

	weight_list[y - 1] -= z
	if distance_list[y] == -1:
		distance = dfs2(y, None, 0)
		distance_list[root_idx] += distance * 2

	print(distance_list[root_idx])