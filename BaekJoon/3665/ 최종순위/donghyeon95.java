import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int testCnt = Integer.parseInt(br.readLine());

		for (int i = 0; i < testCnt; i++) {
			int N = Integer.parseInt(br.readLine());
			int[] lastYear = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			int m = Integer.parseInt(br.readLine());
			int[][] changes = new int[m][2];

			for (int j = 0; j < m; j++) {
				changes[j] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			}

			System.out.println(Solution(lastYear, changes));
		}
	}

	public static String Solution(int[] lastYear, int[][] changes) {
		int n = lastYear.length;
		HashMap<Integer, Set<Integer>> combination = makeCombination(lastYear);

		// 순서쌍 변경 반영
		for (int[] change : changes) {
			int big = change[0];
			int small = change[1];

			if (combination.getOrDefault(big, new HashSet<>()).contains(small)) {
				combination.get(big).remove(small);
				combination.computeIfAbsent(small, k -> new HashSet<>()).add(big);
			} else {
				combination.computeIfAbsent(small, k -> new HashSet<>()).remove(big);
				combination.computeIfAbsent(big, k -> new HashSet<>()).add(small);
			}
		}

		// 간접 사이클 탐지 (DFS)
		Set<Integer> visited = new HashSet<>();
		Set<Integer> onPath = new HashSet<>();

		for (int node : lastYear) {
			if (!visited.contains(node)) {
				if (hasCycle(combination, visited, onPath, node)) {
					return "IMPOSSIBLE";
				}
			}
		}

		// 순위 계산
		Map<Integer, Integer> teamToSize = new HashMap<>();
		for (int team : lastYear) {
			teamToSize.put(team, combination.getOrDefault(team, Set.of()).size());
		}

		List<Map.Entry<Integer, Integer>> sorted = teamToSize.entrySet()
			.stream()
			.sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
			.collect(Collectors.toList());

		Set<Integer> usedSizes = new HashSet<>();
		int[] answer = new int[n];

		for (int i = 0; i < sorted.size(); i++) {
			int size = sorted.get(i).getValue();
			int team = sorted.get(i).getKey();

			if (!usedSizes.add(size)) return "?"; // 중복된 순위
			answer[i] = team;
		}

		return Arrays.stream(answer)
			.mapToObj(String::valueOf)
			.collect(Collectors.joining(" "));
	}

	// DFS를 이용한 간접 사이클 탐지
	public static boolean hasCycle(Map<Integer, Set<Integer>> graph, Set<Integer> visited, Set<Integer> onPath, int node) {
		if (onPath.contains(node)) return true;     // 사이클 발생
		if (visited.contains(node)) return false;

		visited.add(node);
		onPath.add(node);

		for (int next : graph.getOrDefault(node, Set.of())) {
			if (hasCycle(graph, visited, onPath, next)) return true;
		}

		onPath.remove(node);
		return false;
	}

	public static HashMap<Integer, Set<Integer>> makeCombination(int[] year) {
		HashMap<Integer, Set<Integer>> combination = new HashMap<>();

		for (int i = 0; i < year.length; i++) {
			Set<Integer> nextYears = Arrays.stream(year, i + 1, year.length)
				.boxed()
				.collect(Collectors.toSet());

			combination.put(year[i], nextYears);
		}

		return combination;
	}
}