package projectEuler;

public class Problem4 {
	public static void main(String[] args) {
		System.out.println(highestPalindrome(1000));
	}

	private static long highestPalindrome(int i) {
		long answer = 0;
		for(int c = i; c > 0; c--){
			for(int d = i; d > c; d--){
				if(isPalindrome(Integer.toString(c*d))){
					if(answer < c*d){
						answer = c*d;
					}
				}
			}
			
		}
		return answer;
	}

	private static boolean isPalindrome(String string) {
		for(int c = 0; c <= string.length()/2; c++){
			if(Integer.parseInt(string.substring(c,c+1)) != Integer.parseInt(string.substring(string.length()-c-1, string.length()-c))){
				return false;
			}
		}
		return true;
	}
}
