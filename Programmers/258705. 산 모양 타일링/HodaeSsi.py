def solution(n, tops):
    MOD = 10007

    dp = [0] * n

    if tops[0] == 0:
        dp[0] = 3
    else:
        dp[0] = 4

    if tops[1] == 0:
        dp[1] = 3 * dp[0] - 1
    else:
        dp[1] = 4 * dp[0] - 1

    for i in range(2, n):
        if tops[i] == 0:
            dp[i] = (3 * dp[i - 1] % MOD - dp[i - 2] % MOD) % MOD
        else:
            dp[i] = (4 * dp[i - 1] % MOD - dp[i - 2] % MOD) % MOD

    return dp[n - 1]