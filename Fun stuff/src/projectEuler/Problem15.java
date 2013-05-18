package projectEuler;


public class Problem15 {
    public static void main(String[] args) {
        long routes = 0;
        for (int c = 1; c <= 1; c++) {
            System.out.println("solving for " + c);
            routes += determineNumberOfRoutes(0, 0, 20, 20);
            System.out.println("The number of roots for " + c + " are: " + routes);
        }
    }

    private static long determineNumberOfRoutes(int i, int j, int sizex, int sizey) {
        if (i >= sizex && j >= sizey) {
            return 1;
        }
        long numRoutes = 0;
        if (i >= sizex) {
            numRoutes += determineNumberOfRoutes(i, j + 1, sizex, sizey);
        } else if (j >= sizey) {
            numRoutes += determineNumberOfRoutes(i + 1, j, sizex, sizey);
        } else {
            numRoutes += determineNumberOfRoutes(i + 1, j, sizex, sizey);
            numRoutes += determineNumberOfRoutes(i, j + 1, sizex, sizey);
        }
        return numRoutes;
    }
}
