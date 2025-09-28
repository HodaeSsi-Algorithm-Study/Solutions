import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
	static int[][] arr;
	static int[][] dp;
	static int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		dp = new int[N][N];

		for (int i=0; i<N; i++) {
			arr[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}
		for (int i=0; i<N; i++) {
			for (int j=0; j<N; j++) {
				dfs(i, j, i, j);
			}
		}


		int result = -1;
		for (int[] d: dp) {
			for (int n: d) {
				result = Math.max(result, n);
			}
		}

		System.out.println(result);
	}

	public static int dfs(int x, int y, int preX, int preY) {
		int result = 0;
		int value = arr[y][x];

		// 이미 dp에 결과물이 있다면
		if (dp[y][x] != 0) {
			return dp[y][x];
		}

		// 자식 확인
		for (int[] move: moves) {
			int newX = x + move[0];
			int newY = y + move[1];
			if (newX==preX && newY==preY) continue;
			if (newX<0 || newX>= dp[0].length || newY<0 || newY >=dp.length) continue;
			if (value >= arr[newY][newX]) continue;
			result = Math.max(dfs(newX, newY,x,y), result);
		}

		dp[y][x] = result + 1;
		return result+1;
	}

}
