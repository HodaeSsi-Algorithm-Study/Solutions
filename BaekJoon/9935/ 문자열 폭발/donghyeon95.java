import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		String bomb = br.readLine();

		Stack<Character> stack = new Stack<>();
		int blen = bomb.length();

		for (char ch : s.toCharArray()) {
			stack.push(ch);

			// 폭탄 문자열 길이 이상일 때만 검사
			if (stack.size() >= blen) {
				boolean match = true;
				for (int i = 0; i < blen; i++) {
					if (stack.get(stack.size() - blen + i) != bomb.charAt(i)) {
						match = false;
						break;
					}
				}

				if (match) {
					for (int i = 0; i < blen; i++) {
						stack.pop();
					}
				}
			}
		}

		if (stack.isEmpty()) {
			System.out.println("FRULA");
		} else {
			StringBuilder sb = new StringBuilder(stack.size());
			for (char ch : stack) {
				sb.append(ch);
			}
			System.out.println(sb);
		}
	}
}
