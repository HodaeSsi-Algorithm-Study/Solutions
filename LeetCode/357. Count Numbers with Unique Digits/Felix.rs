struct Solution;

impl Solution {
    pub fn count_numbers_with_unique_digits(n: i32) -> i32 {
        let combinations = [
            [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            [1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            [1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0],
            [1, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0],
            [1, 4, 6, 4, 1, 0, 0, 0, 0, 0, 0],
            [1, 5, 10, 10, 5, 1, 0, 0, 0, 0, 0],
            [1, 6, 15, 20, 15, 6, 1, 0, 0, 0, 0],
            [1, 7, 21, 35, 35, 21, 7, 1, 0, 0, 0],
            [1, 8, 28, 56, 70, 56, 28, 8, 1, 0, 0],
            [1, 9, 36, 84, 126, 126, 84, 36, 9, 1, 0],
            [1, 10, 45, 120, 210, 252, 210, 120, 45, 10, 1],
        ];

        let factorials = [1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800];

        let mut dp = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        dp[0] = 1;

        for d in 1..10 {
            dp[d] = dp[d - 1] + combinations[10][d] * factorials[d]
                - combinations[9][d - 1] * factorials[d - 1]
        }

        dp[n as usize]
    }
}

fn main() {
    assert_eq!(91, Solution::count_numbers_with_unique_digits(2));
    assert_eq!(0, Solution::count_numbers_with_unique_digits(1));
}
