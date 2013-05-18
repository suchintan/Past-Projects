package projectEuler;

import java.math.BigInteger;

public class Problem20 {
	public static void main(String[] args) {
		BigInteger n = new BigInteger("100");
		BigInteger solution = determineFactorial(n);
		int sumOfDigits = determineSumOfDigits(solution);
		System.out.println(sumOfDigits);
	}

	private static int determineSumOfDigits(BigInteger solution) {
		String value = solution.toString();
		int sumOfDigits = 0;
		for(int c = 0; c < value.length(); c++){
			sumOfDigits += Integer.parseInt(value.substring(c, c+1));
		}
		return sumOfDigits;
	}

	private static BigInteger determineFactorial(BigInteger n) {
		BigInteger solution = BigInteger.ONE;
		
		for(int c = n.intValue(); c >= 1; c--){
			solution = solution.multiply(new BigInteger(Integer.toString(c)));
		}
		
		return solution;
	}
}
