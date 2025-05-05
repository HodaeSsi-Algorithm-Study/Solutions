from typing import Optional, Tuple


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class Solution:
    def maxPathSum(self, root: Optional[TreeNode]) -> int:
        max_node = -1001

        def solve(node: Optional[TreeNode]) -> Tuple[int, int, int]:
            # 날 포함해 양쪽을 연결한 최대값, 날 포함해 한 쪽만 연결한 최대값, 날 포함하지 않을 때의 최대값
            if node is None:
                return (0, 0, 0)

            nonlocal max_node
            max_node = max(max_node, node.val)

            left = solve(node.left)
            right = solve(node.right)

            return (
                node.val + max(left[1], 0) + max(right[1], 0),
                node.val + max(left[1], right[1], 0),
                max(*left, *right)
            )

        ret = max(solve(root))
        return ret if max_node >= 0 else max_node
