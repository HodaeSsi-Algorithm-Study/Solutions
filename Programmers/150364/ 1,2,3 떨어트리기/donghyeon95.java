import java.util.*;

class Solution {
	public int[] solution(int[][] edges, int[] target) {
		HashMap<Integer, ArrayList<Integer>> graphs = new HashMap<>();
		HashMap<Integer, Integer> route = new HashMap<>(); // sort된 index를 저장
		int leafLen =  (int) Arrays.stream(target).filter(v -> v != 0).count();

		for (int[] edge: edges) {
			ArrayList<Integer> arr = graphs.getOrDefault(edge[0], new ArrayList<>());
			graphs.put(edge[0], arr);
			arr.add(edge[1]);
		}

		for (int i=0; i<target.length; i++) {
			ArrayList<Integer> arr = graphs.getOrDefault(i, null);

			if (arr == null) continue;

			arr.sort(Comparator.naturalOrder());
			route.put(i, 0);
		}

		// route를 따라서 leafNode까지 갈거임
		// LeafNode 패턴을 찾는다.
		int node = 1;
		int sum = Arrays.stream(target).sum();
		int[] clonedTarget = target.clone();
		ArrayList<Integer> pattern = new ArrayList<>();
		while (true) {
			if (sum == 0) break;
			if (route.get(node) == null) {
				if (clonedTarget[node-1] <= 0) break;

				clonedTarget[node-1] -= 1;
				sum-=1;

				pattern.add(node);
				node = 1;
				continue;
			}

			ArrayList<Integer> arr = graphs.get(node);
			int index = route.get(node);
			int nextIndex = (index+1 >= arr.size())? 0: index+1;
			route.put(node, nextIndex);
			node = arr.get(index);
		}

		System.out.println(pattern);

		// 패턴안에 성공한 경우
		int[] answer = new int[pattern.size()];;
		for (int i=0; i<pattern.size(); i++) {
			answer[i] = 1;
		}

		if (sum != 0) { // 패턴 안에 실패 한 경우
			for (int i=0; i<target.length; i++) {
				int remain = clonedTarget[i];
				if (remain == 0) continue;

				// pattern의 뒤에서 부터 최대한 넣어간다. greedy
				int index = pattern.size();
				while (remain > 0) {
					index = findIndex(pattern, index, i+1);
					if (index < 0) return new int[]{-1};

					if (remain >= 2) {
						answer[index] += 2;
						remain-=2;
					} else if (remain ==1) {
						answer[index] += 1;
						remain-=1;
					}
				}
			}
		}
		System.out.println(Arrays.toString(answer));

		// 재분배가 가능한 지 확인
		int index = answer.length-1;
		while (index > 0) {
			// 하나씩 줄여보기
			if (!distribte(pattern, answer, index)) break;
			index--;
		}
		int last = answer.length - 1;
		while (last >= 0 && answer[last] == 0) last--;

		return Arrays.copyOfRange(answer, 0, last + 1);

	}

	// 재분배
	public boolean distribte(ArrayList<Integer> pattern, int[] answer, int index) {
		int node = pattern.get(index);
		int cnt = answer[index];
		int[] clonedAnswer = answer.clone();

		clonedAnswer[index] = 0;
		int remaining = cnt;

		int i = index - 1;
		while (remaining > 0 && i >= 0) {
			if (pattern.get(i) != node || clonedAnswer[i] >= 3) {
				i--;
				continue;
			}

			int gap = 3 - clonedAnswer[i];
			int give = Math.min(gap, remaining);
			clonedAnswer[i] += give;
			remaining -= give;

			i--;
		}

		if (remaining > 0) return false;

		System.arraycopy(clonedAnswer, 0, answer, 0, answer.length);
		return true;
	}

	// index 보다 같거나 작은 애들 중에 제일 빠른 애.
	public int findExcept3Index(ArrayList<Integer> pattern, int[] answer, int index, int number) {
		for (int i=index; i>-1; i--) {
			if (pattern.get(i) == number && answer[i] < 3) return i;
		}

		return -1;
	}

	// index 보다 같거나 작은 애들 중에 제일 빠른 애.
	public int findIndex(ArrayList<Integer> pattern, int index, int number) {
		for (int i=index-1; i>-1; i--) {
			if (pattern.get(i) == number) return i;
		}

		return -1;
	}

	public void fillArr(ArrayList<Integer> pattern, int[] answer, int remain, int cnt, int number) {
		boolean first = false;
		if (remain == 0) first = true;
		for (int i=0; i<pattern.size(); i++) {
			if (pattern.get(i) != number) continue;

			if (!first) {answer[i] = remain; first = true;}
			else answer[i] = 3;
		}
	}

	public int cntNumber(ArrayList<Integer> pattern, int number) {
		return (int) pattern.stream()
			.filter(s -> s == number)
			.count();
	}
}