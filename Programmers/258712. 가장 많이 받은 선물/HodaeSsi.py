# 시간복잡도 : O(n^2)
def solution(friends, gifts):
    answer = 0

    # 자료구조 hashmap - key : 친구이름, value : {선물준횟수 - 선물 받은횟수(기본0), hashmap - key: 선물준사람, value: 선물준횟수}
    friend_map = {friend: [0, {}] for friend in friends}

    for gift in gifts:
        giver, receiver = gift.split()
        friend_map[giver][0] += 1
        friend_map[giver][1][receiver] = friend_map[giver][1].get(receiver, 0) + 1

        friend_map[receiver][0] -= 1

    for friend_one in friends:
        temp = 0
        for friend_two in friends:
            if friend_one != friend_two:
                one_to_two = friend_map[friend_one][1].get(friend_two, 0)
                two_to_one = friend_map[friend_two][1].get(friend_one, 0)

                if one_to_two > two_to_one:
                    temp += 1
                elif one_to_two == two_to_one:
                    if friend_map[friend_one][0] > friend_map[friend_two][0]:
                        temp += 1

        if temp > answer:
            answer = temp

    return answer

# Test call
if __name__ == "__main__":
    friends = ["muzi", "ryan", "frodo", "neo"]
    gifts = ["muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"]
    print(solution(friends, gifts))  # Expected output: 2