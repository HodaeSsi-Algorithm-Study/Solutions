from sys import stdin
input = stdin.readline

programs, memory_requirement = map(int, input().split())

program_memories = list(map(int, input().split()))

kill_cost = list(map(int, input().split()))

# cost 몇에 얼마의 memory를 추가할 수 있는가
next_dp = [0] * (10001)


for memory, cost in zip(program_memories, kill_cost):
    curr_dp = next_dp
    next_dp = [0] * (10001)

    if cost == 0:
        pass

    for next_cost in range(cost):
        next_dp[next_cost] = curr_dp[next_cost]

    for next_cost in range(cost, 10001):
        next_dp[next_cost] =\
            max(curr_dp[next_cost],
                curr_dp[next_cost-cost] + memory)


for cost, memory in enumerate(next_dp):
    if memory_requirement <= memory:
        print(cost)
        break
