package grid;

//The Grid class is the container (and the container alone) for the minesweeper grid
public class Grid {
	
	private final int [][] mGrid;
	
	public Grid(){
		mGrid = new int[10][10];
	}
	
	public Grid(int x, int y){
		mGrid = new int[x][y];
	}
	
	public boolean setValue(int x, int y, int number){
		if(number >= 0 && number <= 9 && x < mGrid.length && y < mGrid[x].length){
			mGrid[x][y] = number;
			return true;
		} else {
			return false;
		}
	}
	
	public int[][] getGrid(){
		return mGrid;
	}
}
