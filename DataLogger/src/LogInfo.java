import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class LogInfo {


	public static void main(String[] args) {

		String dropsListFileName = "drop table.txt";
		String totalNumOfDropsFileName = "number dropped.txt";
		String totalNumberOfKillsFileName = "total kills.txt";
		String saveRecordFileName = "totals.txt";
		String items[] = getItemsFromFile(dropsListFileName);
		Integer numOfItems[];
		Integer kills = 0;
		ArrayList<Integer> dummy = new ArrayList<Integer>();

		numOfItems = parseNumberDropped(dummy, totalNumOfDropsFileName);

		if(numOfItems.length != items.length){
			fillWithZeros(numOfItems, items, totalNumOfDropsFileName);
			numOfItems = parseNumberDropped(dummy, totalNumOfDropsFileName);
		}

		dummy.clear();

		kills = populateItemsArray(totalNumberOfKillsFileName, dummy);


		int maxValue = determineMaxValue(items);

		makeTableReadible(items, maxValue);
		try{
			int input = -1;
			while(input != 0){
				String display;
				int numOfMessageBoxes = 0;
				if(items.length%3==0){
					numOfMessageBoxes = items.length/3;
				}else{
					numOfMessageBoxes = items.length/3+1;
				}

				display = "Here are the list of items and corresponding numbers. 0 to exit:\n";
				for(int c = 0; c < numOfMessageBoxes; c++){

					for(int d = 0; (d < 3) && ((c*3+d) < items.length); d++){
						display += (c*3 + d + 1) + ": " + items[c*3+d] + "     ";
					}
					display+="\n";
				}
				int numOfDrops = Integer.parseInt(JOptionPane.showInputDialog("How many drops for the next kill?", 1));
				for(int c = 0; c < numOfDrops; c++){
					input = Integer.parseInt(JOptionPane.showInputDialog(display));
					if(input!=0){
						if(input < numOfItems.length){
							numOfItems[input-1]++;
						}else{
							JOptionPane.showMessageDialog(null, "You messed up; try again~");
						}
					}
				}
				if(input != 0){
					kills++;
				}			
			}
		}finally{
			saveData(kills, numOfItems, totalNumOfDropsFileName, totalNumberOfKillsFileName);
			saveTheRecords(kills, items, numOfItems, saveRecordFileName);
		}
	}

	private static void saveTheRecords(Integer kills, String[] items, Integer[] numOfItems, String path) {
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("The total number of kills are: " + kills);
			bw.newLine();
			bw.write("The drops achieved are: ");
			bw.newLine();
			for(int c = 0; c < items.length; c++){
				bw.write(items[c] + "    " + numOfItems[c]);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void makeTableReadible(String[] items, int maxValue) {
		for(int c = 0; c < items.length; c++){
			if(items[c].length() < maxValue){
				String tempString = items[c];
				for(int d = 0; d < maxValue-items[c].length(); d++){
					tempString+=" ";
				}
				items[c]=tempString;
			}
		}
	}

	private static void saveData(Integer kills, Integer[] numOfItems, String totalNumOfDropsFileName, String totalNumberOfKillsFileName) {
		saveData(totalNumberOfKillsFileName, new Integer[]{kills});
		saveData(totalNumOfDropsFileName, numOfItems);
	}

	private static void saveData(String filename, Integer... kills) {
		try{
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int c = 0; c < kills.length; c++){
				bw.write(Integer.toString(kills[c]));
				bw.newLine();
			}
			bw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int determineMaxValue(String[] items) {
		int maxValue = -1;
		int curValue = 0;
		for(int c = 0; c < items.length; c++){
			curValue = items[c].length();
			if(curValue > maxValue){
				maxValue = curValue;
			}
		}
		return maxValue;
	}

	private static Integer populateItemsArray(String totalNumberOfKillsFileName, ArrayList<Integer> dummy) {
		Integer kills;
		for(String s : getItemsFromFile(totalNumberOfKillsFileName)){
			dummy.add(Integer.parseInt(s));
		}
		if(dummy.size()==1){
			kills = dummy.get(0);
		}else{
			kills = 0;
			fillWithZeros(new Integer[0], new String[1], totalNumberOfKillsFileName);
		}
		return kills;
	}

	private static Integer[] parseNumberDropped(ArrayList<Integer> dummy, String filename) {
		Integer[] numOfItems;
		for(String s : getItemsFromFile(filename)){
			dummy.add(Integer.parseInt(s));
		}
		numOfItems = dummy.toArray(new Integer[0]);
		return numOfItems;
	}

	private static void fillWithZeros(Integer[] getCurNumbers, String[] items, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int c = 0; c < getCurNumbers.length; c++){
				bw.write(Integer.toString(getCurNumbers[c]));
				bw.newLine();
			}
			for(int c = 0; c < (items.length-getCurNumbers.length); c++){
				bw.write("0");
				if(c < items.length-getCurNumbers.length-1){
					bw.newLine();
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private static String[] getItemsFromFile(String filename) {
		ArrayList<String> items = new ArrayList<String>();
		boolean eof = false;

		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);

			while(!eof){
				String temp = br.readLine();
				if(temp == null){
					eof = true;
				}else{
					items.add(temp);
				}
			}
			br.close();
		}catch(IOException e){
			System.out.println(e.toString());
			return new String[0];
		}

		return items.toArray(new String[0]);
	}
}
