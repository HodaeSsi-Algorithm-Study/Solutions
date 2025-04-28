// class Solution {
//
// 	public int divide(int dividend, int divisor) {
// 		// 몫이라는 건 몇번을 뺄 수 있는 지 여부
// 		// 6/2 = 3 => 3번뺄수 있다.
// 		// 21억 => 1 => 21억번 연산 (안될 거 같지만..)
// 		// 음수랑 양수는 다 치환
// 		long de = Math.abs((long)dividend);
// 		long d = Math.abs((long)divisor);
//
// 		long answer = 0;
// 		while(de >= d) {
// 			de -= d;
// 			answer++;
// 		}
//
// 		answer = ((dividend > 0 && divisor > 0) ||  (dividend < 0 && divisor < 0))? answer: (0-answer);
//
// 		if (answer > Integer.MAX_VALUE) return Integer.MAX_VALUE;
// 		if (answer < Integer.MIN_VALUE) return Integer.MIN_VALUE;
//
// 		return (int) answer;
//
// 	}
// }
// ==> -2147483648
// divisor = 1  이 경우는 통과
//
// dividend =
// 	2147483647
// divisor =
// 	2 이 경우는 	실패 ? (왜 ?? 내부 연산이 다른가? )

class Solution {

	public int divide(int dividend, int divisor) {
		if (dividend == Integer.MIN_VALUE && divisor == -1) {
			return Integer.MAX_VALUE;
		}

		long ldividend = Math.abs((long) dividend);
		long ldivisor = Math.abs((long) divisor);

		int answer = 0;
		long a = 0;

		for (int i = 31; i >= 0; i--) {
			long b = (ldividend >> i) & 1;
			a = (a << 1) + b;

			if (a >= ldivisor) {
				a -= ldivisor;
				answer = (answer << 1) | 1;
			} else {
				answer = answer << 1;
			}
		}


		if ((dividend < 0) ^ (divisor < 0)) {
			answer = -answer;
		}

		return answer;
	}
}