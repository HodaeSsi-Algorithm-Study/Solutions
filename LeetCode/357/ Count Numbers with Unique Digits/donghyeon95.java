import java.util.ArrayList;

class Solution {
	public int countNumbersWithUniqueDigits(int n) {
		// N은 depth
		int answer = 0;

		if (n==0) return 1;
		if (n==1) return 10;

		for (int i=1; i<10; i++) {
			ArrayList<Integer> arr = new ArrayList<>();
			arr.add(i);
			answer += dfs(n, 1, arr);
		}

		return answer+1;
	}

	public int dfs(int n, int depth, ArrayList<Integer> visited) {
		if (n <= depth) return 1;

		int cnt = 0;

		for (int i=0; i<10; i++) {
			if(!visited.contains(i)) {
				ArrayList<Integer> newVisited = new ArrayList<>(visited);
				newVisited.add(i);
				cnt += dfs(n, depth+1, newVisited);
			}
		}

		return cnt+1;
	}
}