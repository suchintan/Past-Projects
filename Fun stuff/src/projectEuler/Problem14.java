package projectEuler;

public class Problem14 {
	public static void main(String[] args) {
		int numProducingLongestChain = determineLongestChain(1000000);
		System.out.println("The num that produces the longest chain is: " + numProducingLongestChain);
	}

	private static int determineLongestChain(int i) {
		int longestChainNum = 0;
		int longestChainLength = 0;
		for(int c = i; c > 1; c--){
			int chainLength = 0;
			long curNum = c;
			while(curNum > 1){
				if(curNum%2 == 0){
					curNum = doEven(curNum);
				}else{
					curNum = doOdd(curNum);
				}
				chainLength++;
			}
			chainLength++;
			if(chainLength > longestChainLength){
				System.out.println(longestChainNum);
				longestChainLength = chainLength;
				longestChainNum = c;
			}
		}
		
		return longestChainNum;
	}

	private static long doOdd(long curNum) {
		return curNum*3+1;
	}

	private static long doEven(long curNum) {
		return curNum/2;
	}
}
