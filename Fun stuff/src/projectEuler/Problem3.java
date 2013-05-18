package projectEuler;

public class Problem3 {
	public static void main(String[] args) {
		long num = Long.MAX_VALUE;
		long primeFactor = determinePrimeFactor(num);
		System.out.println("The Answer for the number " + num + " is: " + primeFactor);
	}
	
	private static long determinePrimeFactor(long num) {
		long i = num;
		long answer = 1;
		int curPoint = 1;
		if(i%2 == 0){
			i /=2;
			answer = 2;
		}
		while(!isPrime(i)){
			if(i%curPoint == 0 && isPrime(curPoint)){
				if(curPoint > answer){
					System.out.println("A prime factor is: " + curPoint);
					answer = curPoint;
				}
				i /= curPoint;
			}
			curPoint +=2;
		}
		if(i > answer){
			answer = i;
		}
		return answer;
	}

	private static boolean isPrime (long n){
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
