import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력 처리
		int[] NM = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int N = NM[0];
		int M = NM[1];
		int[] numbers = new int[N];
		for (int i = 0; i < N; i++) {
			numbers[i] = Integer.parseInt(br.readLine());
		}

		// 세그먼트 트리 초기화
		Main main = new Main();
		Tree tree = main.init(numbers, 0, N - 1);

		// 쿼리 처리
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < M; i++) {
			int[] query = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

			int start = query[0] - 1; // 입력이 1-based라서 보정
			int end = query[1] - 1;

			NodeResult result = main.findMinMax(tree, start, end);
			sb.append(result.minVal).append(" ").append(result.maxVal).append("\n");
		}

		System.out.print(sb);
	}

	/**
	 * 구간 최소/최대값 탐색
	 */
	public NodeResult findMinMax(Tree tree, int start, int end) {
		if (tree == null) return new NodeResult(Integer.MAX_VALUE, Integer.MIN_VALUE);

		// 겹치지 않으면 반환
		if (tree.right < start || tree.left > end) return new NodeResult(Integer.MAX_VALUE, Integer.MIN_VALUE);

		// 완전히 포함되면 해당 노드 값 반환
		if (start <= tree.left && tree.right <= end) return new NodeResult(tree.minVal, tree.maxVal);

		// 리프 노드면 자기 값 반환
		if (tree.left == tree.right) return new NodeResult(tree.minVal, tree.maxVal);

		// 자식 탐색 (쿼리 범위를 자식에 맞게 조정해서 내려보냄)
		NodeResult leftResult = findMinMax(tree.leftNode, start, Math.min(end, tree.leftNode.right));
		NodeResult rightResult = findMinMax(tree.rightNode, Math.max(start, tree.rightNode.left), end);

		return new NodeResult(
			Math.min(leftResult.minVal, rightResult.minVal),
			Math.max(leftResult.maxVal, rightResult.maxVal)
		);
	}

	/**
	 * 세그먼트 트리 초기화
	 */
	public Tree init(int[] numbers, int start, int end) {
		if (start == end) {
			return new Tree(numbers[start], numbers[start], start, end, null, null);
		}

		int mid = (start + end) / 2;
		Tree leftTree = init(numbers, start, mid);
		Tree rightTree = init(numbers, mid + 1, end);

		return new Tree(
			Math.min(leftTree.minVal, rightTree.minVal),
			Math.max(leftTree.maxVal, rightTree.maxVal),
			start, end,
			leftTree, rightTree
		);
	}

	/**
	 * 세그먼트 트리 노드
	 */
	class Tree {
		int minVal;
		int maxVal;
		int left;
		int right;
		Tree leftNode;
		Tree rightNode;

		public Tree(int minVal, int maxVal, int left, int right, Tree leftNode, Tree rightNode) {
			this.minVal = minVal;
			this.maxVal = maxVal;
			this.left = left;
			this.right = right;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
		}
	}

	/**
	 * 최소값, 최대값 결과를 담는 클래스
	 */
	static class NodeResult {
		int minVal;
		int maxVal;

		public NodeResult(int minVal, int maxVal) {
			this.minVal = minVal;
			this.maxVal = maxVal;
		}
	}
}
