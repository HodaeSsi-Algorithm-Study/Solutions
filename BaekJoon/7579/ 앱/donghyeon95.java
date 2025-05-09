import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int N = arr[0];
		int M = arr[1];

		int[] processes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int[] workTime = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

		System.out.println(Solution(N, M, processes, workTime));
	}


	public static int Solution(int N, int M, int[] processes, int[] workTime) {
		// dp[remain][2] => 마지막에 dp[0] 인 애들 값을 처리 => 이러면 천만 * 100 => 10억건 (안될 거 같음>> 메모리 효율성도.. )
		int[][] dp = new int[2][M+1];
		Arrays.fill(dp[0], Integer.MAX_VALUE);
		dp[0][M] = 0;

		for (int i=0; i<N; i++) {
			Arrays.fill(dp[1], Integer.MAX_VALUE);
			for (int j=0; j< M+1; j++) {
				int c = dp[0][j];
				if (c ==Integer.MAX_VALUE) continue;

				// 나를 죽이는 경우
				int remain = j - processes[i];
				if (j - processes[i] < 0) remain = 0;
				dp[1][remain] = Math.min(dp[1][remain], dp[0][j] + workTime[i]);


				// 나를 죽이지 않는 경우,
				dp[1][j] = Math.min(dp[1][j], dp[0][j]);
			}
			int[] temp = dp[0];
			dp[0] = dp[1];
			dp[1] = temp;
		}

		// dp[N-1][0]
		return dp[0][0];
	}
}


// 뭔가 이분 탐색으로도 풀수 있을 거 같은데...
// 걸리는 최대 값을 Cj의 합으로 보고 절반씩
// 조합을 구하기가 빡센가?
