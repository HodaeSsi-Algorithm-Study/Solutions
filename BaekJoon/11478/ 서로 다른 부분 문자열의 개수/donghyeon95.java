import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main{
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		Set<String> answer = new HashSet<>();
		for (int i=0; i<s.length(); i++) {
			for (int j=i+1; j<s.length()+1; j++) {
				String subStr = s.substring(i, j);
				answer.add(subStr);
			}
		}

		System.out.println(answer.size());
	}
}