import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException  {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int cityCnt = Integer.parseInt(br.readLine());
		int[] gifts = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		HashMap<Integer, ArrayList<Integer>> graphs = new HashMap<>();

		for (int i=0; i<cityCnt-1; i++) {
			int[] edges = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			ArrayList<Integer> arr1 = graphs.getOrDefault(edges[0], new ArrayList<>());
			arr1.add(edges[1]);
			graphs.put(edges[0], arr1);

			ArrayList<Integer> arr2 = graphs.getOrDefault(edges[1], new ArrayList<>());
			arr2.add(edges[0]);
			graphs.put(edges[1], arr2);
		}
		// 쿼리 목록
		int queryCnt = Integer.parseInt(br.readLine());
		int[][] queries = new int[queryCnt][3];

		for (int i=0; i<queryCnt; i++) {
			queries[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}


		// dfs를 통해서 숫자가 있는 노드들 간의 간격을 구한다.
		System.out.println(dfs(1, 1, graphs, gifts, false, 0, 0)*2);

		for (int i=0; i<queryCnt; i++) {
			int[] query = queries[i];
			gifts[query[0]-1] += query[2];
			gifts[query[1]-1] -= query[2];
			System.out.println(dfs(1, 1, graphs, gifts, false, 0, 0)*2);
		}
	}

	public static int dfs(int node, int parent, HashMap<Integer, ArrayList<Integer>> graphs, int[] gifts, boolean isCounting, int sum, int dist) {
		// leafNode라면 종료

		if (gifts[node-1] !=0) {
			if (!isCounting) isCounting=true;
			sum += dist;
			dist = 0;
		}
		if (isCounting) dist++;

		// 리프노드라면 return
		ArrayList<Integer> arr = graphs.get(node);

		if (arr.size() == 1 &&arr.get(0) == parent) return sum;
		int newSum = sum;
		for (int next: arr) {
			if (next == parent) continue;

			newSum = dfs(next, node, graphs, gifts, isCounting, newSum, dist);
		}

		return newSum;

	}


}
