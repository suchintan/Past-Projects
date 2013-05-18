package projectEuler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Problem18 {
    public static void main(String[] args) {
        int[][] triangle = getTriangle("triangle.txt");
    }

    private static int[][] getTriangle(String filename) {

        ArrayList<ArrayList<Integer>> triangle = new ArrayList<ArrayList<Integer>>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String data;
            boolean eof = false;

            while (!eof) {
                data = br.readLine();
                if (data != null) {
                    triangle.add(new ArrayList<Integer>());
                    while (data.length() > 0) {
                        String field = data.substring(0, 2);
                        data = data.substring(2, data.length());
                        triangle.get(triangle.size() - 1).add(Integer.parseInt(field));
                    }
                } else {
                    eof = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int data[][] = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];

        for (int c = 0; c < data.length; c++) {
            for (int d = 0; d < data[c].length; d++) {

            }
        }
        
        return null;
    }
}
