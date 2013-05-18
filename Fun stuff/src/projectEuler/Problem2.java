package projectEuler;

public class Problem2 {
	
	
	
	public static void main(String[] args) {
		long fibonacciAnswer = doEvenFibonacci(0, 1, 4000000);
		System.out.println("The answer is: " + fibonacciAnswer);
	}

	private static long doEvenFibonacci(int prevValue, int curValue, int maxValue) {
		int newCurValue = prevValue+curValue;
		if(newCurValue >= maxValue){
			return 0;
		}else{
			
			if(newCurValue%2 == 0){
				return newCurValue + doEvenFibonacci(curValue, newCurValue, maxValue);
			}
			return doEvenFibonacci(curValue, newCurValue, maxValue);
		}
	}
}
