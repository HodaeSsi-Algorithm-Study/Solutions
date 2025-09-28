import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
	static int N, M;
	static int[][] arr;
	static int[][][] dp;
	static boolean[][][] visited;
	static int[][] moves = {{1, 0}, {0, 1}, {-1, 0}}; // 오 아 왼
	static final int INF = (int)-1e9;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new int[N][M];
		dp = new int[N][M][3];
		visited = new boolean[N][M][3];

		// dp 초기값을 -INF로 설정
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				Arrays.fill(dp[i][j], INF);
			}
		}

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 시작점에서는 prevIndex = -1 (방향 없음)
		int answer = solution(0, 0, -1);

		System.out.println(answer);
	}

	public static int solution(int x, int y, int prevIndex) {
		// 종점 도착
		if (y == N - 1 && x == M - 1) {
			return arr[y][x];
		}

		// 메모이제이션
		if (prevIndex != -1 && visited[y][x][prevIndex]) {
			return dp[y][x][prevIndex];
		}

		int best = INF;
		for (int i = 0; i < moves.length; i++) {
			int newX = x + moves[i][0];
			int newY = y + moves[i][1];

			if (newX < 0 || newX >= M || newY < 0 || newY >= N) continue;
			if (prevIndex != -1 && isOpposite(prevIndex, i)) continue; // 반대 방향 차단

			int next = solution(newX, newY, i);
			if (next != INF) {
				best = Math.max(best, next + arr[y][x]);
			}
		}

		if (prevIndex != -1) {
			visited[y][x][prevIndex] = true;
			dp[y][x][prevIndex] = best;
		}

		return best;
	}

	// 반대 방향 판별
	static boolean isOpposite(int a, int b) {
		return (a == 0 && b == 2) || (a == 2 && b == 0); // 오 <-> 왼
	}
}
