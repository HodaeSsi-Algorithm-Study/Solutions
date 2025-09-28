import java.io.*;
import java.util.*;

public class Main {
	static int[] parent, size;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());

		// 1. 부모 정보 입력
		int[] P = new int[N + 1]; // P[i] = i번 노드의 부모
		P[1] = 1; // 루트
		for (int i = 2; i <= N; i++) {
			P[i] = Integer.parseInt(br.readLine());
		}

		// 2. 쿼리 입력 (역순으로 처리하기 위해 스택에 저장)
		Stack<int[]> queries = new Stack<>();
		for (int i = 0; i < N - 1 + Q; i++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken());
			if (t == 0) { // cut
				int node = Integer.parseInt(st.nextToken());
				queries.push(new int[]{0, node});
			} else { // check
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				queries.push(new int[]{1, a, b});
			}
		}

		// 3. DSU 초기화
		parent = new int[N + 1];
		size = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			parent[i] = i;
			size[i] = 1;
		}

		// 4. 쿼리 역순 처리
		Stack<String> answer = new Stack<>();
		while (!queries.isEmpty()) {
			int[] q = queries.pop();
			if (q[0] == 1) { // check 연결 여부
				if (find(q[1]) == find(q[2])) {
					answer.push("YES");
				} else {
					answer.push("NO");
				}
			} else { // cut → 역순에서는 union
				int node = q[1];
				union(node, P[node]);
			}
		}

		// 5. 정답 출력
		StringBuilder sb = new StringBuilder();
		while (!answer.isEmpty()) {
			sb.append(answer.pop()).append("\n");
		}
		System.out.print(sb);
	}

	// find with path compression
	static int find(int x) {
		if (x == parent[x]) return x;
		return parent[x] = find(parent[x]);
	}

	// union by size
	static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if (a == b) return;
		if (size[a] < size[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		parent[b] = a;
		size[a] += size[b];
	}
}
