class Solution:
	def countNumbersWithUniqueDigits(self, n: int) -> int:
		if n == 0:
			return 1
		if n == 1:
			return 10
			
		result = 10  # n=1일 때의 결과
		available_digits = 9
		current = 9
		
		# (한자리수 unique 수) * (0~9중에, 첫 번째 자리에 사용된 수를 제외한 수)
		# (두자리수 unique 수) * (0~9중에, 첫 번째 자리와 두 번째 자리에 사용된 수를 제외한 수)
		# ...
		for i in range(2, n + 1):
			current *= available_digits
			result += current
			available_digits -= 1
			
		return result