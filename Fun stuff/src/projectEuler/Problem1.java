package projectEuler;

public class Problem1 {
	
	
	public static void main (String args[]){
		long total = sumOfAll(3, 100000, 0);
		total += sumOfAll(5, 100000, 0);
		System.out.println("The total is: " + total);
	}

	private static long sumOfAll(int base, int maxVal, int currentPoint) {
		if(base * currentPoint >= maxVal){
			System.out.println(base*currentPoint);
			return 0;
		}else{
			int total = base * currentPoint;
			if(base == 5 && ((base*currentPoint)%3) == 0){
				total = 0;
			}
			return total + sumOfAll(base, maxVal, ++currentPoint);
		}
		
	}
}
