import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
	static int[][] edge;
	static int[] dp;
	static int N;
	public static void main(String[] agrs) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int M = Integer.parseInt(st.nextToken());
		int[] count = new int[N+1];
		edge = new int[N+1][N+1];
		dp = new int[N + 1];

		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int child = Integer.parseInt(st.nextToken());
			int parent = Integer.parseInt(st.nextToken());
			int num = Integer.parseInt(st.nextToken());
			++count[child];

			edge[parent][child] = num;
		}

		Queue<Integer> que = new ArrayDeque<>();

		for (int i=1; i<N+1; i++ ) {
			if (count[i] == 0) {
				que.add(i);
			}
		}

		while (!que.isEmpty()) {
			int num = que.poll();

			System.out.println(num + " " +dfs(num));
		}
	}

	public static int dfs(int node) {
		if (dp[node]!=0) // 이미 N까지 가는 갯수가 나온 경우
			return dp[node];

		// N일 경우
		if (node == N) return 1;

		int[] children = edge[node];
		int answer = 0;

		for (int i=0; i<children.length; i++) {
			// 자식까지 거리 * 자식에서 N까지 거리들의 합
			if (children[i] == 0)
				continue;

			int num = children[i];
			answer += dfs(i) * num;
		}

		dp[node] = answer;
		return answer;
	}
}

