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
 *          modified: 12.06.2016
 * 
 *          Class to save your progress in a savegame-file. The is constructed
 *          like this: levelWiese levelPartyhut milk
 *
 */

public class Savegame {

	// Savegame file
	private File savegame;

	// Saved level of the willow upgrade
	private String levelWiese;

	// Saved level of the hat upgrade
	private String levelPartyhut;

	// Saved value of milk
	private String milk;

	/**
	 * Constructor just creates the Object, handling of the file is done in the
	 * methods.
	 */
	public Savegame() {
		savegame = new File("savegame");
	}

	/**
	 * Method to save your current progress.
	 * 
	 * @param levelWiese
	 *            level from the milk per click upgrade
	 * @param levelPartyhut
	 *            level from the milk per second upgrade
	 * @param milk
	 *            amount of milk saved
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method loads the savefile and sets the instance Variables. If it
	 * does not exists it will create a new file.
	 * 
	 */

	public void loadSavegame() {
		// if no save file exists create a new one where all values are 0
		if (!savegame.exists()) {
			try {
				FileWriter fOut = new FileWriter(savegame);
				PrintWriter pw = new PrintWriter(fOut);

				String savegameFile = "";
				savegameFile += "0" + "\n" + "0" + "\n" + "0";
				pw.println(savegameFile);

				pw.close();
				fOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (savegame.exists() && !savegame.isDirectory()) {
			// open file reader and buffer and reads the lines for the upgrades
			// and sets the instance variables
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
	}

	/**
	 * Getter for all variables.
	 */

	public String getLevelWiese() {
		return levelWiese;
	}

	public String getLevelPartyhut() {
		return levelPartyhut;
	}

	public String getMilk() {
		return milk;
	}
}
