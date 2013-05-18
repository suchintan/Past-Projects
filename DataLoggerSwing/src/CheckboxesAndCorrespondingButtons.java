import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CheckboxesAndCorrespondingButtons extends JPanel implements ActionListener{
	
	JCheckBox mItemsCheckBox[];

    

    StringBuffer choices;
    CheckBoxListener myListener = null;
    Integer mKills;
    Integer mNumberOfItems[];
    boolean mSelected[];
    String mItems[];
    static JButton  mButtonAddKill;
    String dropsListFileName = "drop table.txt";
	String totalNumOfDropsFileName = "number dropped.txt";
	String totalNumberOfKillsFileName = "total kills.txt";
	String saveRecordFileName = "totals.txt";
    
    public CheckboxesAndCorrespondingButtons(String items[], Integer numbers[], Integer kills) {
    	mKills = kills;
    	mNumberOfItems = numbers;
    	mItems = items;
    	
    	mButtonAddKill = new JButton("Click to add kill");
    	mButtonAddKill.setVerticalAlignment(AbstractButton.CENTER);
    	mButtonAddKill.setHorizontalAlignment(AbstractButton.LEADING);
    	mButtonAddKill.setActionCommand("addKill");
    	mButtonAddKill.setToolTipText("Click this button to update kill count with drops");
    	mButtonAddKill.addActionListener(this);
    	
    	
    	myListener = new CheckBoxListener();
        
        mSelected = new boolean[items.length];
        mItemsCheckBox = new JCheckBox[items.length];
        for(int c = 0; c < items.length; c++){
        	mSelected[c] = false;
        	mItemsCheckBox[c] = new JCheckBox(items[c] + " " + numbers[c]);
        	mItemsCheckBox[c].setSelected(false);
        	mItemsCheckBox[c].addItemListener(myListener);
        }
               		

        JPanel jplCheckBox = new JPanel();
        jplCheckBox.setLayout(new GridLayout(0,4));  //0 rows, 1 Column
        
        for(int c = 0; c < mItemsCheckBox.length;c++){
        	jplCheckBox.add(mItemsCheckBox[c]);
        }

        setLayout(new BorderLayout());
        add(jplCheckBox, BorderLayout.WEST);
        add(mButtonAddKill, BorderLayout.SOUTH);
        //setBorder(BorderFactory.createEmptyBorder(50,items.length / 10 + 1 * 200,50,items.length / 10 + 1 * 200));
    }


    //Listens to the check boxes events
    class CheckBoxListener implements ItemListener {
        @Override
		public void itemStateChanged(ItemEvent e) {
            int index = 0;
            
            Object source = e.getSource();
            
            boolean found = false;
            int c = -1;
            while(!found){
            	c++;
            	if(source == mItemsCheckBox[c]){
            		found=true;
            	}
            }
            mSelected[c] = true;

            if (e.getStateChange() == ItemEvent.DESELECTED){
                mSelected[c] = false;
            }
        }
    }
    
    
    public static void createGUI(Integer kills, String[] items, Integer[] numOfItems){
    	JFrame frame = new JFrame("Checkboxes and stuff");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	CheckboxesAndCorrespondingButtons window = new CheckboxesAndCorrespondingButtons(items, numOfItems, kills);
        window.setOpaque(true);
        frame.getRootPane().setDefaultButton(mButtonAddKill);
        frame.setContentPane(window);
        frame.pack();
        frame.setVisible(true);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean killOccured = false;
		if(e.getActionCommand().equals("addKill")){
			for(int c = 0; c < mItems.length; c++){
				if (mSelected[c]){
					mItemsCheckBox[c].setSelected(false);
					killOccured = true;
					mNumberOfItems[c]++;
				}
			}
		}
		if(killOccured){
			mKills++;
			updateCheckboxLabels();
			JOptionPane.showMessageDialog(null, "You have added a kill - data has been saved. Killcount:" + mKills);
			saveData(mKills, mNumberOfItems, totalNumOfDropsFileName, totalNumberOfKillsFileName);
			saveTheRecords(mKills, mItems, mNumberOfItems, saveRecordFileName);
		}
	}
	
	private void updateCheckboxLabels() {
		 for(int c = 0; c < mItemsCheckBox.length; c++){
	        	mItemsCheckBox[c].setText(mItems[c] + " " + Integer.toString(mNumberOfItems[c]));
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

}
