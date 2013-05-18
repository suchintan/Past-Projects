package projectEuler;

public class Problem5 {
	public static void main(String[] args) {
		for(int c = 1; c <= 20; c++){
			long answer = smallestNumberDivisible(c);
			System.out.println(answer);
		}
	}

	private static long smallestNumberDivisible(int i) {
		long answer = 0;
		boolean done = false;
		while(!done){
			answer += i;
			boolean failed = false;
			for(int c = 1; c <= i; c++){
				if(answer % c != 0){
					failed = true;
				}
			}
//			System.out.println(failed);
			if(!failed){
				done = true;
			}
		}
		return answer;
	}
}
