package projectEuler;

public class Problem10 {
	public static void main(String[] args) {
		long answer = determineSumOfPrimes(2000000);
		System.out.println("The answer is: " + answer);
	}

	private static long determineSumOfPrimes(int i) {
		int curPoint = 3;
		long sum = 2;
		while(curPoint <= i){
			if(isPrime(curPoint)){
				sum+=curPoint;
			}
			curPoint+=2;
		}
		return sum;
	}

	private static boolean isPrime(long n){
		if (n%2==0) return false;
		if (n<=1) return false;
		if (n==2) return true;

		long m=Math.round(Math.sqrt(n));

		for (long i=3; i<=m; i+=2)
			if (n%i==0)
				return false;

		return true;
	}
}
