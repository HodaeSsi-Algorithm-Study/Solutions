import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {
	static HashMap<Integer, ArrayList<Integer>> tree;
	static Node[] nodes;
	static int[] weights;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		weights = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		tree = new HashMap<>();
		nodes = new Node[N + 1];

		for (int i = 1; i < N + 1; i++) {
			nodes[i] = new Node(0, 0);
		}

		for (int i = 0; i < N - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());

			tree.computeIfAbsent(node1, k -> new ArrayList<>()).add(node2);
			tree.computeIfAbsent(node2, k -> new ArrayList<>()).add(node1);
		}

		// 루트(1번)에서 시작해서 최대값 계산
		int[] result = trace(1, 0);
		System.out.println(Math.max(result[0], result[1]));

		// 독립집합 정점 경로 찾기
		ArrayList<Integer> path;
		if (result[0] > result[1]) {
			path = findPath(1, 0, true);
		} else {
			path = findPath(1, 0, false);
		}

		// 정렬 + 중복 제거
		TreeSet<Integer> sorted = new TreeSet<>(path);

		StringBuilder sb = new StringBuilder();
		for (int num : sorted) {
			sb.append(num).append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb);
	}

	public static int[] trace(int node, int parentNode) {
		int weight = weights[node - 1];
		Node n = nodes[node];
		ArrayList<Integer> children = tree.get(node);

		// 리프 노드일 경우
		if (children.size() == 1 && children.get(0) == parentNode) {
			n.chooseMax = weight;
			n.nonChooseMax = 0;
			return new int[]{weight, 0};
		}

		int chooseMax = weight;
		int nonChooseMax = 0;

		for (int child : children) {
			if (child == parentNode) continue;

			int[] result = trace(child, node);
			chooseMax += result[1];
			nonChooseMax += Math.max(result[0], result[1]);
		}

		n.chooseMax = chooseMax;
		n.nonChooseMax = nonChooseMax;

		return new int[]{chooseMax, nonChooseMax};
	}

	// 독립집합 경로 찾기
	public static ArrayList<Integer> findPath(int node, int parent, boolean pick) {
		ArrayList<Integer> result = new ArrayList<>();
		Node n = nodes[node];
		ArrayList<Integer> children = tree.get(node);

		if (pick) {
			result.add(node); // 내가 선택되면 한 번만 추가
			for (int child : children) {
				if (child == parent) continue;
				result.addAll(findPath(child, node, false));
			}
		} else {
			for (int child : children) {
				if (child == parent) continue;
				Node childNode = nodes[child];
				if (childNode.chooseMax > childNode.nonChooseMax) {
					result.addAll(findPath(child, node, true));
				} else {
					result.addAll(findPath(child, node, false));
				}
			}
		}

		return result;
	}

	static class Node {
		int chooseMax;
		int nonChooseMax;

		public Node(int chooseMax, int nonChooseMax) {
			this.chooseMax = chooseMax;
			this.nonChooseMax = nonChooseMax;
		}
	}
}
