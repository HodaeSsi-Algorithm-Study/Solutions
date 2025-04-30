def solution(commands):
    N = 50

    parent = [[(i, j) for j in range(N)] for i in range(N)]
    # group_members[(r,c)] = { 모든 이 루트에 속한 셀 좌표 }
    group_members = {(i, j): {(i, j)} for i in range(N) for j in range(N)}
    # value_raw[(r,c)] = 이 루트 셀에 직접 저장된 문자열 (None 이면 EMPTY)
    value_raw = {(i, j): None for i in range(N) for j in range(N)}
    # value_to_roots[val] = { 이 값을 직접 갖고 있는 루트 셀들 }
    value_to_roots = {}

    def find(cell):
        r, c = cell
        while parent[r][c] != (r, c):
            r, c = parent[r][c]
        return (r, c)

    answer = []

    for cmd in commands:
        parts = cmd.split()

        if parts[0] == "UPDATE":
            # UPDATE r c value
            if len(parts) == 4:
                r, c, v = int(parts[1]) - 1, int(parts[2]) - 1, parts[3]
                root = find((r, c))

                # 이전 값이 있었다면 맵에서 제거
                old = value_raw[root]
                if old is not None:
                    s = value_to_roots[old]
                    s.remove(root)
                    if not s:
                        del value_to_roots[old]

                # 새 값을 저장하고 맵에도 추가
                value_raw[root] = v
                value_to_roots.setdefault(v, set()).add(root)

            # UPDATE value1 value2
            else:
                v1, v2 = parts[1], parts[2]
                if v1 == v2 or v1 not in value_to_roots:
                    continue

                # 직접 갖고 있는 루트들만 꺼내서 바꿔준다
                roots = list(value_to_roots[v1])
                for root in roots:
                    value_raw[root] = v2
                    value_to_roots.setdefault(v2, set()).add(root)

                # v1 집합은 완전히 비었으니 삭제
                del value_to_roots[v1]

        elif parts[0] == "MERGE":
            r1, c1, r2, c2 = map(lambda x: int(x) - 1, parts[1:5])
            root1, root2 = find((r1, c1)), find((r2, c2))
            if root1 == root2:
                continue

            raw1 = value_raw[root1]
            raw2 = value_raw[root2]
            # 둘 중 값이 있으면 root1 우선, 없으면 raw2
            new_raw = raw1 if raw1 is not None else raw2

            # group2 → group1 합치기
            for (rr, cc) in group_members[root2]:
                parent[rr][cc] = root1
                group_members[root1].add((rr, cc))
            del group_members[root2]

            # 루트2에 저장돼 있던 값 맵에서 제거
            if raw2 is not None:
                s2 = value_to_roots[raw2]
                s2.remove(root2)
                if not s2:
                    del value_to_roots[raw2]
            del value_raw[root2]

            # root1 값이 바뀌었다면 이전 맵에서 제거
            if raw1 is not None and raw1 != new_raw:
                s1 = value_to_roots[raw1]
                s1.remove(root1)
                if not s1:
                    del value_to_roots[raw1]

            # 새 값을 root1에 저장, 맵에도 추가
            value_raw[root1] = new_raw
            if new_raw is not None:
                value_to_roots.setdefault(new_raw, set()).add(root1)

        elif parts[0] == "UNMERGE":
            r, c = int(parts[1]) - 1, int(parts[2]) - 1
            root = find((r, c))
            prev_raw = value_raw[root]

            # 루트가 갖고 있던 맵 항목 먼저 제거
            if prev_raw is not None:
                s = value_to_roots[prev_raw]
                s.remove(root)
                if not s:
                    del value_to_roots[prev_raw]

            # 그룹 멤버 전부 각자 독립 루트로 분리
            members = list(group_members[root])
            for (mr, mc) in members:
                parent[mr][mc] = (mr, mc)
                group_members[(mr, mc)] = {(mr, mc)}
                value_raw[(mr, mc)] = None

            # 분리한 셀 중 (r,c)에만 이전 값 복원
            if prev_raw is not None:
                value_raw[(r, c)] = prev_raw
                value_to_roots.setdefault(prev_raw, set()).add((r, c))

        else:  # PRINT
            r, c = int(parts[1]) - 1, int(parts[2]) - 1
            root = find((r, c))
            v = value_raw[root]
            answer.append(v if v is not None else "EMPTY")

    return answer
