import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] num = Arrays.stream(br.readLine().split("")).mapToInt(Integer::parseInt).toArray();
		int[] target = Arrays.stream(br.readLine().split("")).mapToInt(Integer::parseInt).toArray();

		int[][] dp = new int[N+1][10];
		for (int[] arr : dp) {
			Arrays.fill(arr, Integer.MAX_VALUE);
		}

		 // 첫번쩨 거
		int firstNum = num[0];
		int firstTarget = target[0];
		int lefLot = (firstTarget - firstNum + 10) % 10;   // 왼쪽으로 몇 번 돌려야 하는지
		int righLot = (firstNum - firstTarget + 10) % 10; // 오른쪽으로 몇 번 돌려야 하는지

		dp[1][0] = righLot;
		dp[1][lefLot] = lefLot;


		for (int i=2; i<N+1; i++) {
			int n = num[i - 1];
			int targetNum = target[i - 1];


			for (int j=0; j<10; j++) {
				if (dp[i - 1][j] == Integer.MAX_VALUE) continue;

				int preNum = dp[i - 1][j];

				int nowNum = (n + j) % 10;

				int leftLot = (targetNum > nowNum) ? targetNum - nowNum : 10 - (nowNum - targetNum);
				int index1 = (j + leftLot) % 10;
				dp[i][index1] = Math.min(dp[i][index1], preNum + leftLot);

				int rightLot = 10 - leftLot;
				dp[i][j] = Math.min(dp[i][j], preNum + rightLot);
			}
		}

		int answer = Integer.MAX_VALUE;
		for (int result: dp[N]) {

			answer = Math.min(result, answer);
		}
		System.out.println(answer);
	}
}
