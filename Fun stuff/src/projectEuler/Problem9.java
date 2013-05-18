package projectEuler;

public class Problem9 {
	public static void main(String[] args) {
		int productOfABC = determineProduct(1000);
		System.out.println("The product is: " + productOfABC);
	}

	private static int determineProduct(int i) {
		int a = 3;
		int b = 4;
		int c = 5;
		while(a + b + c != 1000){
			c = 0;
			a++;
			if(a >= b){
				a = 1;
				b++;
			}
			boolean isNatural = determineNatural(a, b);
			if(isNatural){
				c = determineC(a, b);
			}
		}
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		return a*b*c;
	}

	private static boolean determineNatural(int a, int b) {
		if(determineC(a,b) == Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))){
			return true;
		}
		return false;
	}

	private static int determineC(int a, int b) {
		return (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}
}
