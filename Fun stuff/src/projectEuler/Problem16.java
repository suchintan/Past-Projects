package projectEuler;

import java.math.BigInteger;

public class Problem16 {
	public static void main(String[] args) {
		int answer = determineSumOfDigits(1000);
		System.out.println(answer);
	}

	private static int determineSumOfDigits(int i) {
		int sum = 0;
		BigInteger num = new BigInteger("2");
		String result = num.pow(i).toString();
		for(int c = 0; c < result.length(); c++){
			sum += Integer.parseInt(result.substring(c, c+1));
		}
		return sum;
	}
}
