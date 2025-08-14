import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[] parent = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			parent[i] = i;
		}

		for (int i = 1; i <= M; i++) {
			int[] question = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			if (question[0] == 0) {
				unionParent(parent, question[1], question[2]);
			} else {
				if (findParent(parent, question[1]) == findParent(parent, question[2])) {
					System.out.println("YES");
				} else {
					System.out.println("NO");
				}
			}
		}
	}

	public static int findParent(int[] parent, int x) {
		if (parent[x] == x) return x;
		parent[x] = findParent(parent, parent[x]);
		return parent[x];
	}

	public static void unionParent(int[] parent, int a, int b) {
		int aParent = findParent(parent, a);
		int bParent = findParent(parent, b);

		if (aParent < bParent) parent[bParent] = aParent;
		else parent[aParent] = bParent;
	}
}