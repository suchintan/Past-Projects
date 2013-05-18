package ui;
import java.awt.*;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import javax.swing.*;

/**
 * @author Nikhil Malhotra
 */
public class GameWindow extends JFrame{
	
	private JFrame gameFrame;
	private JLabel[] gameLabels;
	private JButton[] gameButtons;
	
	public GameWindow(){
		gameFrame.setTitle("Game Frame");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end GameWindow
	
	public GameWindow(String title){
		gameFrame.setTitle(title);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end GameWindow
	
	public void putThisOnTheWindow(String style, String item, int numItemsLong,int numItemsWide, int location[][], int size[][]){
		if(item == "Button"){
			gameButtons = new JButton[numItemsLong];
		}else if(item == "Image"){
			
		}else if(item == "Text Field"){
			
		}else if(item == "Timer"){
			
		}else if(item == "Label"){
			gameLabels = new JLabel[numItemsLong];

	        gameLabels[0].setPreferredSize(new Dimension(size[0][0], size[0][0]));
	        gameFrame.getContentPane().add(gameLabels[0], BorderLayout.CENTER);
		}//end if
	}//end putThisInTheWindow
	
	public void putThisOn(String item,int lX,int lY, String suit, String filePath){
		
	}// end putThisOn
	
	public void showTheWindow(){
		gameFrame.pack();
		gameFrame.setVisible(true);
	}//end showTheWindow
	
	public JFrame giveMeTheWindow(){
		return gameFrame;
	}//end giveMeTheWindow
	
}//end class GameWindow
