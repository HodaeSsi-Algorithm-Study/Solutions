import java.io.*;
import java.util.*;

public class Main {
	static int N, W;
	static int[][] events;      // events[1..W][2]
	static int[][] dp;          // dp[i][j] = (i,j) 상태 최소거리
	static byte[][] choice;     // choice[i][j] = 1(차1), 2(차2)

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine().trim());
		W = Integer.parseInt(br.readLine().trim());

		events = new int[W + 1][2];
		for (int k = 1; k <= W; k++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			events[k][0] = Integer.parseInt(st.nextToken());
			events[k][1] = Integer.parseInt(st.nextToken());
		}

		dp = new int[W + 1][W + 1];
		for (int[] row : dp) Arrays.fill(row, -1);
		choice = new byte[W + 1][W + 1];

		int answer = dfs(1, 0, 0);          // 계산과 choice 기록을 한 번에
		System.out.println(answer);

		// 경로 출력: 재계산 없이 기록된 choice[i][j]만 따라감
		StringBuilder out = new StringBuilder();
		int i = 0, j = 0;
		for (int index = 1; index <= W; index++) {
			if (choice[i][j] == 1) { out.append("1\n"); i = index; }
			else                   { out.append("2\n"); j = index; }
		}
		System.out.print(out);
	}

	// 항상 index = max(i, j) + 1을 유지
	static int dfs(int index, int i, int j) {
		if (index > W) return 0;
		if (dp[i][j] != -1) return dp[i][j];

		int cost1 = distCar1(i, index) + dfs(index + 1, index, j); // 차1이 index 처리
		int cost2 = distCar2(j, index) + dfs(index + 1, i, index); // 차2가 index 처리

		if (cost1 <= cost2) {
			dp[i][j] = cost1;
			choice[i][j] = 1; // 이 상태에서는 차1을 선택
		} else {
			dp[i][j] = cost2;
			choice[i][j] = 2; // 이 상태에서는 차2를 선택
		}
		return dp[i][j];
	}

	static int distCar1(int last, int next) {
		int sx = (last == 0) ? 1 : events[last][0];
		int sy = (last == 0) ? 1 : events[last][1];
		int tx = events[next][0], ty = events[next][1];
		return Math.abs(sx - tx) + Math.abs(sy - ty);
	}

	static int distCar2(int last, int next) {
		int sx = (last == 0) ? N : events[last][0];
		int sy = (last == 0) ? N : events[last][1];
		int tx = events[next][0], ty = events[next][1];
		return Math.abs(sx - tx) + Math.abs(sy - ty);
	}
}