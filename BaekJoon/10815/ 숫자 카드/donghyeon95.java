import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Main{
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int[] card = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		Set<Integer> cardSet = Arrays.stream(card).boxed().collect(Collectors.toSet());

		int M = Integer.parseInt(br.readLine());
		int[] numbers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int[] answer = new int[M];

		for (int i=0; i<M; i++) {
			if (cardSet.contains(numbers[i])) {
				answer[i]=1;
			}
		}

		for (int i=0; i<M; i++) {
			System.out.print(answer[i]+ " ");
		}
	}
}