import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {
	static int[][] moves = {{1,0}, {0,-1}, {-1,0}, {0,1}};
	static int[] rotates = {1, 2, 3, 0}; // 회전 시 방향

	// 좌표를 표현할 Point 클래스
	static class Point implements Comparable<Point> {
		int x, y;
		Point(int x, int y) { this.x = x; this.y = y; }

		@Override
		public int compareTo(Point o) {
			return (this.x != o.x)
				? Integer.compare(this.x, o.x)
				: Integer.compare(this.y, o.y);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Point)) return false;
			Point p = (Point) o;
			return x == p.x && y == p.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int[][] query = new int[N][4];
		ArrayList<Integer>[] dots = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			dots[i] = new ArrayList<>();
		}

		// 좌표 중복 제거 가능한 Set
		Set<Point> allDots = new HashSet<>();

		for (int i=0; i<N; i++) {
			query[i] = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			dots[i].add(query[i][2]); // 시작 방향
		}

		for (int i=0; i<N; i++) {
			int x = query[i][0];
			int y = query[i][1];
			int gen = query[i][3];
			ArrayList<Integer> dot = dots[i];

			// 드래곤 커브 방향 생성
			for (int j=0; j<gen; j++) {
				ArrayList<Integer> newdots = new ArrayList<>();
				for (int k=dot.size()-1; k>=0; k--) {
					int num = dot.get(k);
					newdots.add(rotates[num]);
				}
				dot.addAll(newdots);
			}

			// 좌표 기록
			allDots.add(new Point(x, y));
			for (int direction: dot) {
				int[] move = moves[direction];
				x += move[0];
				y += move[1];
				allDots.add(new Point(x, y));
			}
		}

		// 정사각형 개수 세기
		int count = 0;
		for (Point p : allDots) {
			Point right = new Point(p.x+1, p.y);
			Point down = new Point(p.x, p.y+1);
			Point diag = new Point(p.x+1, p.y+1);

			if (allDots.contains(right) && allDots.contains(down) && allDots.contains(diag)) {
				count++;
			}
		}

		System.out.println(count);
	}
}
