package projectEuler;

public class Problem6 {
	public static void main(String[] args) {
		long answer = squareSum(100) - sumSquare(100);
		System.out.println("The answer is: " + answer);
	}

	private static long squareSum(int nthNumber) {
		long answer = 0;
		long sum = 0;
		for(int c=0; c < nthNumber; c++){
			sum += (c+1);
		}
		answer = (long) Math.pow(sum, 2);
		return answer;
	}

	private static long sumSquare(int nthNumber) {
		long answer = 0;
		for(int c = 0; c < nthNumber; c++){
			answer += Math.pow(c+1, 2);
		}
		return answer;
	}
}
