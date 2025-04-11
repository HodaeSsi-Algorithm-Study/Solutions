import java.util.Arrays;

class Solution {
    public String solution(long n, String[] bans) {
        String answer = "";

        Arrays.sort(bans, (s1, s2) -> {
            if (s1.length() == s2.length()) {
                return s1.compareTo(s2);
            }
            return Integer.compare(s1.length(), s2.length());
        });

        for (String ban : bans) {
            String nString = numberToString(n);
            if (ban.length() < nString.length() ||
                    (ban.length() == nString.length() && ban.compareTo(nString) <= 0)) {
                n++;
            }
        }
        answer = numberToString(n);

        return answer;
    }

    private String numberToString(long n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            long remained = n % 26;
            n /= 26;
            if (remained == 0) {
                n--;
                sb.append('z');
            } else {
                sb.append((char)('a' + remained - 1));
            }
        }
        return sb.reverse().toString();
    }
}