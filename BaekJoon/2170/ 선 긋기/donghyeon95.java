import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] arr = new int[N][2];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

		long answer = 0;
		int end = arr[0][0];

		for (int i=0; i<N; i++) {
			int[] dots = arr[i];

			if (dots[0] >= end) {
				answer += dots[1] - dots[0];
				end = dots[1];
			} else if (dots[1] > end) {
				answer += dots[1] - end;
				end = dots[1];
			}
		}

		System.out.println(answer);
	}
}