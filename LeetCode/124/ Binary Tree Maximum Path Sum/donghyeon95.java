import javax.swing.tree.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
	private int max_result = Integer.MIN_VALUE;
	public int maxPathSum(TreeNode root) {
		dfs(root);
		return max_result;
	}

	public int dfs(TreeNode node)  {
		if(node==null) return 0;

		int val = node.val;
		TreeNode left = node.left;
		TreeNode right = node.right;

		// 1. 왼쪽에서 올라오는 루트의 최대 + 내 값 ( 만약 음수면 0을 리턴)
		int leftResult = dfs(left);


		// 2. 오른쪽에서 올라오는 루트의 최대 + 내 값
		int rightResult = dfs(right);

		// 왼쪽 -> 나 -> 오른쪽 합치는 걸 Max에 반영
		max_result = Math.max(max_result, leftResult+rightResult+val);

		// return 하기전에 Max 반영
		int returnValue = Math.max(leftResult, rightResult) + val;
		max_result = Math.max(returnValue, max_result);

		// return
		if (returnValue < 0) return 0;
		else return returnValue;
	}
}