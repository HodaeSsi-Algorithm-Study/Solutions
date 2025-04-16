# 풀이 방법 : 코인을 가장 적게 사용하는 방식으로 도달 가능한 최대 라운드 계산(greedy)
    # 코인에 대해 그리디하게 접근 하는 경우, 카드를 해당 라운드에서 사용하진 않지만 코인을 사용하여 킵 해 놓는 경우도 커버 됨
# 시간 복잡도 : O(n^2) (n은 카드의 개수)
def solution(coin, cards):
    answer = 0

    card_coin = {} # key - 카드 번호, value - [필요 코인 개수, 사용 여부]
    for i in range(len(cards) // 3):
        card_coin[cards[i]] = [0, False]

    for i in range(1, len(cards) // 3 + 1): # 최대 진행 가능 라운드는 cards 개수의 1/3
        find_pair = False
        card_coin[cards[len(cards) // 3 + 2 * (i - 1)]] = [1, False]
        card_coin[cards[len(cards) // 3 + 2 * (i - 1) + 1]] = [1, False]

        for j in range(len(cards) // 3 + 2 * (i - 1) + 2):
            left = card_coin.get(cards[j], [-100, False])
            right = card_coin.get(len(cards) + 1 - cards[j], [-100, False])
            if left[0] == -100 or left[1] or right[0] == -100 or right[1]:
                continue

            if left[0] == 0 + right[0] == 0:
                card_coin[cards[j]][1] = True
                card_coin[len(cards) + 1 - cards[j]][1] = True
                find_pair = True
                break

        if find_pair:
            answer = i
            continue
        if coin < 1:
            break

        for j in range(len(cards) // 3 + 2 * (i - 1) + 2):
            left = card_coin.get(cards[j], [-100, False])
            right = card_coin.get(len(cards) + 1 - cards[j], [-100, False])
            if left[0] == -100 or left[1] or right[0] == -100 or right[1]:
                continue

            if left[0] + right[0] == 1:
                card_coin[cards[j]][1] = True
                card_coin[len(cards) + 1 - cards[j]][1] = True
                find_pair = True
                coin -= 1
                break

        if find_pair:
            answer = i
            continue
        if coin < 2:
            break

        for j in range(len(cards) // 3 + 2 * (i - 1) + 2):
            left = card_coin.get(cards[j], [-100, False])
            right = card_coin.get(len(cards) + 1 - cards[j], [-100, False])
            if left[0] == -100 or left[1] or right[0] == -100 or right[1]:
                continue

            if left[0] + right[0] == 2:
                card_coin[cards[j]][1] = True
                card_coin[len(cards) + 1 - cards[j]][1] = True
                find_pair = True
                coin -= 2
                break

        if find_pair:
            answer = i
            continue
        else:
            break

    return answer + 1