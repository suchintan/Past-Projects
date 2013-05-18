package projectEuler;

public class Problem7 {
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
	
	public static void main(String[] args) {
		System.out.println(findTheNthPrime(10001));
	}

	private static long findTheNthPrime(int i) {
		if(i ==1){
			return 2l;
		}
		long curNum = 1;
		long curPoint = 1;
		while(curPoint!=i){
			curNum+=2;
			while(!isPrime(curNum)){
				curNum+=2;
			}
			curPoint++;
			
		}
		return curNum;
	}
}
