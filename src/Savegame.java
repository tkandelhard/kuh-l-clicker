import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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
	private String levelWiese;
	private String levelPartyhut;
	private String milk;
	
	//hier bitte die IP des Servers zum speichern in der Cloud eintragen.
	private String host = "192.168.178.27";
	
	
	/**
	 * Constructor just creates the Object, handling of the file is done in the methods. If I know for sure how saving is handled this could change.
	 */
	public Savegame() {
		savegame = new File("savegame");
	}
	/**
	 * Method to save your current progress.
	 * 
	 * @param levelWiese level from the milk per click upgrade
	 * @param levelPartyhut level from the milk per second upgrade
	 * @param milk amount of milk saved
	 */
	public void writeSavegame(int levelWiese, int levelPartyhut, int milk) {
		
		try {
			FileWriter fOut = new FileWriter(savegame);
			PrintWriter pw = new PrintWriter(fOut);
			
			String savegameFile = "";
			savegameFile += levelWiese + "\n" + levelPartyhut + "\n" + milk;
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
				
				this.levelWiese = bufIn.readLine();
				this.levelPartyhut = bufIn.readLine();
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
	
	
	public void sendCloudSave(int lvlMilkUpgrades, int lvlMpsUpgrades, int milk) {
		
		try {
			// 1. Verbindung zum Server aufbauen
			Socket socket = new Socket(InetAddress.getByName(host), 12345);

			InputStream inStream = socket.getInputStream();

			// String zum speichern wird erstellt
			String message = lvlMilkUpgrades + "\n" + lvlMpsUpgrades + "\n" + milk;
			
			// 3. PrintWriter erzeugen
			OutputStream outStream = socket.getOutputStream(); 
			PrintWriter output = new PrintWriter(outStream); 
			output.println(message); 
						
			// 4. Nachricht an Server 
			output.flush(); 
			output.close();
			
			// 5. Daten vom Server empfangen
			Scanner input = new Scanner(inStream); 
			String messageServer = input.nextLine(); 

			// 5. Text vom Server lesen
			System.out.println("RECEIVED MESSAGE: " + messageServer);
			

			// 6. Verbindung schlieﬂen
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	

	public String getLevelWiese() {
		return levelWiese;
	}

	public void setLevelWiese(String lvlMilkUpgrades) {
		this.levelWiese = lvlMilkUpgrades;
	}

	public String getLevelPartyhut() {
		return levelPartyhut;
	}

	public void setLevelPartyhut(String lvlMpsUpgrades) {
		this.levelPartyhut = lvlMpsUpgrades;
	}

	public String getMilk() {
		return milk;
	}

	public void setMilk(String milk) {
		this.milk = milk;
	}
	
	
	

}
