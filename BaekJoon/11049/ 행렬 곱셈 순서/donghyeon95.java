import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		int[] dims = new int[N + 1];
		for (int i = 0; i < N; i++) {
			String[] parts = br.readLine().split(" ");
			int r = Integer.parseInt(parts[0]);
			int c = Integer.parseInt(parts[1]);
			dims[i] = r;
			if (i == N - 1) dims[i + 1] = c;
		}

		int[][] dp = new int[N][N];
		for (int len = 2; len <= N; len++) {
			for (int i = 0; i <= N - len; i++) {
				int j = i + len - 1;
				dp[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					dp[i][j] = Math.min(dp[i][j],
						dp[i][k] + dp[k + 1][j] + dims[i] * dims[k + 1] * dims[j + 1]);
				}
			}
		}

		System.out.println(dp[0][N - 1]);
	}


	// public static void Solution(ArrayList<int[]> matirxies) {
	// 	dfs(matirxies, 0);
	// }

	// /// 메모리 초과 // 시간 초과
	// public static void dfs(ArrayList<int[]> matirxies, int usedExecCount) {
	// 	if (matirxies.size()==1) {
	// 		answer = Math.min(usedExecCount, answer);
	// 		return;
	// 	}
	//
	// 	for (int i=1; i<matirxies.size(); i++) {
	// 		ArrayList<int[]> clonedMatirxies = new ArrayList<>(matirxies);
	//
	// 		int[] matrixA = matirxies.remove(i-1);
	// 		int[] matrixB = matirxies.remove(i-1);
	//
	// 		int execCount = matrixA[0] * matrixA[1] * matrixB[1];
	// 		clonedMatirxies.add(i-1, new int[]{matrixA[0], matrixB[1]});
	//
	// 		dfs(clonedMatirxies, usedExecCount + execCount);
	// 	}
	// }
}