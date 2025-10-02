import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static int[][] dp;
	static String s1, s2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		s1 = br.readLine();
		s2 = br.readLine();

		dp = new int[s1.length()][s2.length()];
		for (int i = 0; i < s1.length(); i++) {
			Arrays.fill(dp[i], -1);
		}

		System.out.println(dfs(0, 0));
	}

	public static int dfs(int y, int x) {
		// 인덱스 범위 벗어나면 0
		if (y >= s1.length() || x >= s2.length()) return 0;

		if (dp[y][x] != -1) return dp[y][x]; // 이미 계산했으면 반환

		int result;
		if (s1.charAt(y) == s2.charAt(x)) {
			// 두 문자가 같으면 같이 한 칸씩 이동
			result = 1 + dfs(y + 1, x + 1);
		} else {
			// 다르면 한쪽만 이동 (두 가지 선택 중 큰 값)
			result = Math.max(dfs(y + 1, x), dfs(y, x + 1));
		}

		return dp[y][x] = result;
	}
}
