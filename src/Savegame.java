import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author Till Kandelhard
 * @version v0.1
 * 
 * modified: 05.06.2016
 * 
 * Class to save your progress in a savegame-file.
 *
 */


public class Savegame {
	
	private File savegame;
	private String lvlMilkUpgrades;
	private String lvlMpsUpgrades;
	private String milk;
	
	
	/**
	 * Constructor just creates the Object, handling of the file is done in the methods. If I know for sure how saving is handled this could change.
	 */
	public Savegame() {
		savegame = new File("savegame");
	}
	/**
	 * Method to save your current progress.
	 * 
	 * @param lvlMilkUpgrades level from the milk per click upgrade
	 * @param lvlMpsUpgrades level from the milk per second upgrade
	 * @param milk amount of milk saved
	 */
	public void writeSavegame(int lvlMilkUpgrades, int lvlMpsUpgrades, int milk) {
		
		try {
			FileWriter fOut = new FileWriter(savegame);
			PrintWriter pw = new PrintWriter(fOut);
			
			String savegameFile = "";
			savegameFile += lvlMilkUpgrades + "\n" + lvlMpsUpgrades + "\n" + milk;
			pw.println(savegameFile);
			
			pw.close();
			fOut.close();
			
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This method loads the savefile and sets the instance Variables.
	 * If it does not exists it will create a new file.
	 * 
	 */
	
	public void loadSavegame() {
		if (savegame.exists() && !savegame.isDirectory()) {
			//open file reader and buffer and reads the lines for the upgrades and sets the instance variables
			try {
				
				FileReader reader = new FileReader(savegame);
				BufferedReader bufIn = new BufferedReader(reader);
				
				this.lvlMilkUpgrades = bufIn.readLine();
				this.lvlMpsUpgrades = bufIn.readLine();
				this.milk = bufIn.readLine();
				

				bufIn.close();
				reader.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		else {
			try {
				//new file where all values are 0
				FileWriter fOut = new FileWriter(savegame);
				PrintWriter pw = new PrintWriter(fOut);
				
				pw.println("0" + "\n" + "0" + "\n" + "0");
				
				pw.close();
				fOut.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	

	public String getLvlMilkUpgrades() {
		return lvlMilkUpgrades;
	}

	public void setLvlMilkUpgrades(String lvlMilkUpgrades) {
		this.lvlMilkUpgrades = lvlMilkUpgrades;
	}

	public String getLvlMpsUpgrades() {
		return lvlMpsUpgrades;
	}

	public void setLvlMpsUpgrades(String lvlMpsUpgrades) {
		this.lvlMpsUpgrades = lvlMpsUpgrades;
	}

	public String getMilk() {
		return milk;
	}

	public void setMilk(String milk) {
		this.milk = milk;
	}
	
	
	

}
