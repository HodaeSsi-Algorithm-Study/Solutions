import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		List<List<Integer>> g = new ArrayList<>(n + 1);
		for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
		int[] indeg = new int[n + 1];

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			g.get(a).add(b);
			indeg[b]++;
		}

		PriorityQueue<Integer> pq = new PriorityQueue<>(); // 번호 작은 것 우선
		for (int i = 1; i <= n; i++) if (indeg[i] == 0) pq.offer(i);

		StringBuilder sb = new StringBuilder();
		int cnt = 0;
		while (!pq.isEmpty()) {
			int cur = pq.poll();
			sb.append(cur).append(' ');
			cnt++;
			for (int nxt : g.get(cur)) {
				if (--indeg[nxt] == 0) pq.offer(nxt);
			}
		}

		// 문제 조건상 DAG이라 cnt==n이어야 함
		System.out.println(sb.toString().trim());
	}
}