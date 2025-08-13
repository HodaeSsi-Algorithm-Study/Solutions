import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

// https://ksb-dev.tistory.com/109
public class Main {
	private static int n;
	private static int[] arr, dp, revArr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer stz;

		n = Integer.parseInt(br.readLine());
		arr = new int[n + 1];
		dp = new int[n + 1];
		revArr = new int[n + 1]; // 역추적

		stz = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < n + 1; i++) {
			arr[i] = Integer.parseInt(stz.nextToken());
		}

		Arrays.fill(dp, -1);

		int maxPLen = 0; // 가장 긴 부분배열의 길이
		int lo = 0;
		for (int i = 1; i < n + 1; i++) {
			int len = lis(i);
			if (len > maxPLen) {
				maxPLen = len;
				lo = i;
			}
		}

		// 길이 출력
		bw.write(maxPLen+"\n");

		if(revArr[lo] == 0) {
			// 역추적 할 것이 없다면 자기 자신 출력
			bw.write(String.valueOf(arr[lo]));
		}else{
			// 역추적
			traceBack(lo, bw);

		}

		bw.flush();
		bw.close();
		br.close();
	}

	private static void traceBack(int lo, BufferedWriter bw) throws IOException {
		if (revArr[lo] != lo) {
			traceBack(revArr[lo], bw);
			bw.write(arr[lo] + " "); // 부분 배열중 가장 작은 것 부터 출력됨됨
		}
		return;
	}

	private static int lis(int i) {
		// 아직 탐색하지 않았다면
		if (dp[i] == -1) {
			dp[i] = 1; // 자기 자신

			// 자기 자신 위치 이전의 것을 봐서 작은 것을 찾음
			for (int j = i - 1; j > 0; j--) {
				if (arr[i] > arr[j]) {
					if (dp[j] + 1 > dp[i]) {
						dp[i] = dp[j] + 1;
						revArr[i] = j;
					} else {
						dp[i] = dp[i];
					}
				}
			}
		}

		return dp[i];
	}
}



public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] A = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

		List<List<Integer>> dpList = new ArrayList<>();

		for (int num : A) {
			List<Integer> bestSeq = new ArrayList<>(); // 가장 긴 후보 수열

			for (List<Integer> seq : dpList) {
				if (seq.get(seq.size() - 1) < num && seq.size() > bestSeq.size()) {
					bestSeq = seq;
				}
			}

			List<Integer> newSeq = new ArrayList<>(bestSeq);
			newSeq.add(num);
			dpList.add(newSeq); // 항상 새로 추가
		}

		// 가장 긴 수열 찾기
		List<Integer> answer = new ArrayList<>();
		for (List<Integer> seq : dpList) {
			if (seq.size() > answer.size()) {
				answer = seq;
			}
		}

		// 출력
		System.out.println(answer.size());
		for (int x : answer) System.out.print(x + " ");
	}
}


public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] A = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

		int[] dp = new int[N];             // dp[i] = LIS 길이 i+1에서 가장 작은 마지막 수
		int[] dpIndices = new int[N];      // dpIndices[i] = dp[i]에 해당하는 A의 인덱스
		int[] prev = new int[N];           // 역추적을 위한 배열
		Arrays.fill(prev, -1);

		int len = 0;

		for (int i = 0; i < N; i++) {
			int num = A[i];
			int pos = Arrays.binarySearch(dp, 0, len, num);
			if (pos < 0) pos = -pos - 1;

			dp[pos] = num;
			dpIndices[pos] = i; // 현재 인덱스 기록

			// 현재가 pos번째 수일 경우 이전은 pos-1번째 수열의 끝
			if (pos > 0) {
				prev[i] = dpIndices[pos - 1];
			}

			if (pos == len) len++;
		}

		// LIS 역추적
		LinkedList<Integer> lis = new LinkedList<>();
		int idx = dpIndices[len - 1];
		while (idx != -1) {
			lis.addFirst(A[idx]);
			idx = prev[idx];
		}

		// 출력
		System.out.println(len);
		for (int num : lis) System.out.print(num + " ");
	}
}