package error;

//This exception should be used when someone enters an invalid number for mines.
//Specifically, this should be used for inputs such as -1 or the number of mines exceeds the
//capacity of the grid (8x8 grid with 64 mines).
public class IllegalNumberOfMinesException extends Exception{
	public IllegalNumberOfMinesException(){
		
	}
	
	public IllegalNumberOfMinesException(String message){
		super(message);
	}
}
