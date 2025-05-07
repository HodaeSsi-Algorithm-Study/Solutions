import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int N = arr[0];
		int R = arr[1];
		int Q = arr[2];

		// Tree
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
		for (int i =0; i<N-1; i++) {
			int[] edge = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			ArrayList<Integer> arr1 = graph.getOrDefault(edge[0], new ArrayList<>());
			graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
			graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
		}

		// 일단 Root 노드의 모시깽을 구해놓자
		HashMap<Integer, ArrayList<Integer>> tree = buildTree(graph, R, N);

		// 나의 subTree는 자식놈의 갯수와 같다.
		int[] subCnt = new int[N+1];
		Arrays.fill(subCnt, -1);


		for (int i = 0; i<Q; i++) {
			int queryNode =  Integer.parseInt(br.readLine());

			System.out.println(Solution(tree, subCnt, queryNode));
		}


	}

	public static HashMap<Integer, ArrayList<Integer>> buildTree(HashMap<Integer, ArrayList<Integer>> graph, int root, int N) {
		HashMap<Integer, ArrayList<Integer>> tree = new HashMap<>();
		boolean[] visited = new boolean[N + 1];
		Stack<Integer> stack = new Stack<>();
		stack.push(root);
		visited[root] = true;

		while (!stack.isEmpty()) {
			int node = stack.pop();
			for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
				if (!visited[neighbor]) {
					tree.computeIfAbsent(node, k -> new ArrayList<>()).add(neighbor);
					visited[neighbor] = true;
					stack.push(neighbor);
				}
			}
		}
		return tree;
	}

	// Stack으로 dfs 하는 것 구현해보기
	/**
	 *
 	 * @param trees
	 * @param root
	 * @return
	 *
	 */
	public static int Solution(HashMap<Integer, ArrayList<Integer>> trees, int[] subCnt, int root) {
		int result = 1;

		for (int child : trees.getOrDefault(root, new ArrayList<>())) {
			int subtreeCnt = subCnt[child];
			if (subtreeCnt == -1)
				subtreeCnt = Solution(trees, subCnt, child);

			result += subtreeCnt;
		}

		subCnt[root] = result;
		return result;
	}


}