package projectEuler;

public class Problem12 {
    public static void main(String[] args) {
        long triangleNumber = determineTriangleNumber(500);
        System.out.println("The triangle number is: " + triangleNumber);
    }

    private static long determineTriangleNumber(int i) {
        long triangleNumber = 0;
        int numOfDivisors = 0;
        int nthTriangleNumber = 0;
        long prevTriangleNumber = 0;
        while (numOfDivisors <= i) {
            nthTriangleNumber++;
            prevTriangleNumber = triangleNumber;
            triangleNumber = prevTriangleNumber + nthTriangleNumber;
            numOfDivisors = determineNumOfDivisors(triangleNumber);
        }
        return triangleNumber;
    }

    private static int determineNumOfDivisors(long triangleNumber) {
        int numOfDivisors = 0;
        for (int c = 1; c <= Math.sqrt(triangleNumber); c++) {
            if (triangleNumber % c == 0) {
                numOfDivisors += 2;
            }
        }
        if (Math.sqrt(triangleNumber) == (int) Math.sqrt(triangleNumber)) {
            numOfDivisors--;
        }
        return numOfDivisors;
    }
}
