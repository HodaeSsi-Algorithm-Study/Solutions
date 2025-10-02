import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	// 1~8 방향 (상, 좌상, 좌, 좌하, 하, 우하, 우, 우상)
	static int[][] moves = {
		{},
		{0, -1}, {-1, -1}, {-1, 0}, {-1, 1},
		{0, 1}, {1, 1}, {1, 0}, {1, -1}
	};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] fishValue = new int[4][4]; // 물고기 번호
		int[][] fishMove = new int[4][4];  // 물고기 방향

		for (int j = 0; j < 4; j++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 4; i++) {
				int a = Integer.parseInt(st.nextToken()); // 물고기 번호
				int b = Integer.parseInt(st.nextToken()); // 방향
				fishValue[j][i] = a;
				fishMove[j][i] = b;
			}
		}

		System.out.println(dfs(fishValue, fishMove, 0, 0, 0));
	}

	public static int dfs(int[][] fishValue, int[][] fishMove, int sharkX, int sharkY, int sharkMove) {
		// 벽을 넘거나 물고기가 없을 때는 종료
		if (sharkX < 0 || sharkX > 3 || sharkY < 0 || sharkY > 3 || fishValue[sharkY][sharkX] == 0) {
			return 0;
		}

		// 상어가 먹는다
		int answer = fishValue[sharkY][sharkX];
		int newSharkMove = fishMove[sharkY][sharkX];
		fishValue[sharkY][sharkX] = 0;
		fishMove[sharkY][sharkX] = 0;

		// 물고기 이동
		for (int i = 1; i <= 16; i++) {
			int[] fish = findFish(fishValue, i);
			if (fish == null) continue;

			int move = fishMove[fish[1]][fish[0]];
			int newFishX = fish[0] + moves[move][0];
			int newFishY = fish[1] + moves[move][1];

			// 회전해서 갈 수 있는 자리 찾기
			for (int j = 0; j < 8; j++) {
				if (newFishX < 0 || newFishX > 3 || newFishY < 0 || newFishY > 3 || (newFishX == sharkX && newFishY == sharkY)) {
					move = (move == 8) ? 1 : move + 1; // 방향 회전
					newFishX = fish[0] + moves[move][0];
					newFishY = fish[1] + moves[move][1];
				} else break;
			}

			// 물고기 스왑
			int fishNum = fishValue[fish[1]][fish[0]];
			int tempValue = fishValue[newFishY][newFishX];
			int tempMove = fishMove[newFishY][newFishX];

			fishValue[newFishY][newFishX] = fishNum;
			fishMove[newFishY][newFishX] = move;
			fishValue[fish[1]][fish[0]] = tempValue;
			fishMove[fish[1]][fish[0]] = tempMove;
		}

		// 상어 이동 (최대 3칸)
		int result = 0;
		for (int i = 1; i <= 3; i++) {
			int newSharkX = sharkX + (moves[newSharkMove][0] * i);
			int newSharkY = sharkY + (moves[newSharkMove][1] * i);

			result = Math.max(result,
				dfs(cloneArr(fishValue), cloneArr(fishMove), newSharkX, newSharkY, newSharkMove));
		}

		return answer + result;
	}

	// 배열 깊은 복사
	public static int[][] cloneArr(int[][] arr) {
		int[][] newArr = new int[4][4];
		for (int i = 0; i < 4; i++) {
			newArr[i] = arr[i].clone();
		}
		return newArr;
	}

	// 특정 번호의 물고기 찾기
	public static int[] findFish(int[][] fishValue, int fishNum) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (fishValue[i][j] == fishNum) {
					return new int[]{j, i};
				}
			}
		}
		return null;
	}
}
