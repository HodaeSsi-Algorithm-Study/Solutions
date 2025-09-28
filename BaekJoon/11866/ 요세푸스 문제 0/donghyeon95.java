import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 큐
		Queue<Integer> queue = new ArrayDeque<>();
		for (int i=1; i<=N; i++) {
			queue.add(i);
		}

		ArrayList<Integer> answer = new ArrayList<>();

		while (!queue.isEmpty()) {
			for (int i=0; i<K-1; i++) {  // K-1번은 다시 뒤로 보냄
				int temp = queue.poll();
				queue.add(temp);
			}
			// K번째는 제거
			answer.add(queue.poll());
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for (int i=0; i<answer.size()-1; i++) {
			int n = answer.get(i);
			sb.append(n);
			sb.append(", ");
		}
		sb.append(answer.get(answer.size() - 1) + ">");
		System.out.println(sb.toString());
	}
}