import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

class Main {
	static final int INF = 1_000_000_000;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		int[][] graph = new int[V+1][V+1];
		for (int i = 1; i <= V; i++) {
			Arrays.fill(graph[i], INF);
		}

		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());
			int edge = Integer.parseInt(st.nextToken());

			graph[node1][node2] = edge;
		}


		// 모든 노드에서 최단 거리
		// f(i, j ) =  f(i, K )+ f(k, i)
		for (int k=1; k<V+1; k++) {
			for (int i=1; i<V+1; i++) {
				for (int j=1; j<V+1; j++) {
						graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
				}
			}
		}

		int answer = INF;
		for (int i=1; i<V+1; i++) {
			answer = Math.min(graph[i][i], answer);
		}

		System.out.println(answer == INF ? -1 : answer);

	}
}