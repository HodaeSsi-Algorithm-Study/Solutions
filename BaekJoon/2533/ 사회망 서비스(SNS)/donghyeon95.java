import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
	public static int answer;
	public static void main(String[] args) throws IOException {
		// 어떤 노트를 Root로 잡아도 동일
		// 리프 노드부터 타고 올라가면서 순회
		// if (리프노드의 부모라면 얼리어답터)
		// if (내 자식이 보무 얼리어답터가 아니라면 얼리답터)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		HashMap<Integer, ArrayList<Integer>> tree = new HashMap<>();
		for (int i=0; i<N-1; i++) {
			int[] edges = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			tree.computeIfAbsent(edges[0], k -> new ArrayList<>()).add(edges[1]);
			tree.computeIfAbsent(edges[1], k -> new ArrayList<>()).add(edges[0]);
		}

		Solution(tree);
		System.out.println(answer);
	}

	public static void Solution(HashMap<Integer, ArrayList<Integer>> tree) {
		int root = 1;

		isEarly(1, 1, tree);
	}

	public static boolean isEarly(int node, int parent, HashMap<Integer, ArrayList<Integer>> tree) {
		// 내 자식 중 하나라도 leaf Node이거나,
		// 내자식이 모두 얼리가 아니라면 나는 얼리
		ArrayList<Integer> children = tree.getOrDefault(node, null);
		if (children == null) return false;
		boolean needEarly = false;


		for (int child: children) {
			if (child == parent)
				continue;
			if (!isEarly(child, node,tree)) {
				needEarly = true;
			}
		}

		if (needEarly) {
			answer++;
			return true;  // 나는 얼리어답터
		} else {
			return false; // 나는 얼리어답터 아님
		}
	}
}
