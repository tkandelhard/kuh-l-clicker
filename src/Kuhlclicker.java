import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

/**
 * Created by christianmaatz on 01.06.16.
 *
 *
 */
public class Kuhlclicker extends JFrame {

	public Kuhlclicker kuhlclicker;

	private int milch;

	private int levelWiese;
	private int wieseUpgradeKosten;

	private int levelPartyhut;
	private int partyhutUpgradeKosten;

	private int wieseUpgradeBaseCost = 15;
	private int wieseMpsUpgrade = 1;

	private int partyhutUpgradeBaseCost = 100;
	private int partyhutMpsUpgrade = 10;

	private int mpc;

	private int mps;
	private InitGUI gui = new InitGUI();

	public Kuhlclicker() {
		// Auslagern der GUI über Thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui.initGUI();
			}
		});

		// Savegame wird geladen
		final Savegame savegame = new Savegame();
		savegame.loadSavegame();
		levelWiese = Integer.parseInt(savegame.getLevelWiese());
		levelPartyhut = Integer.parseInt(savegame.getLevelPartyhut());
		milch = Integer.parseInt(savegame.getMilk());
		mps = levelWiese * wieseMpsUpgrade + levelPartyhut * partyhutMpsUpgrade;
		// Anpassen der Wiesengrafik je nach Upgradestufe
			// erste Ausbaustufe
			if (levelWiese == 1) {
				gui.kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgrade.png"));
			}
			// zweite Ausbaustufe
			else if (levelWiese > 1) {
				gui.kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgradeFinal.png"));
			}
			// keine Ausbaustufe
			else if (levelWiese == 0) {
				gui.kuhLabel.setIcon(new ImageIcon("resources/Kuh.png"));
			}


		if (levelWiese != 0) {
			gui.wieseUpgradeButton
					.setText("Wiese: " + (int) Math.ceil(wieseUpgradeBaseCost * Math.pow(1.15, levelWiese)) + " Milch");
		}

		if (levelPartyhut != 0) {
			gui.partyhutUpgradeButton.setText(
					"Partyhut: " + (int) Math.ceil(partyhutUpgradeBaseCost * Math.pow(1.15, levelPartyhut)) + " Milch");
		}

		// ruft die MilchProSekundeRunnable jede sekunde auf
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(milchProSekunde, 0, 1, TimeUnit.SECONDS);

		wieseUpgrade();
		partyhutUpgrade();

		// Partyhut Upgrade
		gui.partyhutUpgradeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				partyhutUpgrade();
			}
		});

		// Wiesen Upgrade
		gui.wieseUpgradeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				wieseUpgrade();
				refreshMilchAnzeige();
			}
		});

		// Click auf die Kuh generiert Milchresource
		gui.kuhPanel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				getMilkPerClick();

			}
		});

		// Speichert den momentanen Spielstand in einem Savegame Objekt und schreibt den Stand in einen File
		gui.saveButton.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				savegame.writeSavegame(levelWiese, levelPartyhut, milch);

			}
		});

	}

	/*
	immer aufrufen wenn sich was an der Milchmenge veraendert, sprich
	 Upgrades, Clicks, etc.
	*/
	public void refreshMilchAnzeige() {
		gui.milchLabel.setText("x " + milch);
	}

	// regelt wie viel milch man per click bekommt
	public void getMilkPerClick() {
		mpc = 1;
		// erhoeht die Milch pro Click um 10% der Milch pro Sekunde
		mpc = mpc + (int) Math.ceil((mps * 10) / 100);
		milch = milch + (int) mpc;
		gui.statMpc.setText("Milch per Click: " + mpc);
		refreshMilchAnzeige();

	}

	// regelt das komplette WiesenUpgrade
	public void wieseUpgrade() {

		// falls noch kein Upgrade gekauft wurde
		if (levelWiese == 0) {
			gui.wieseUpgradeButton.setText("Wiese: " + wieseUpgradeBaseCost + " Milch");
		}

		// Berechnung der Upgrade Kosten ( Basiskosten * 1,15^levelWiese
		wieseUpgradeKosten = wieseUpgradeBaseCost * (int) Math.pow(1.15, levelWiese);

		// wenn genug Milch vorhanden ist um das Upgrade zu kaufen
		if (wieseUpgradeKosten <= milch) {

			// setz den Text des Buttons auf den Preis der naechsten Stufe
			gui.wieseUpgradeButton.setText(
					"Wiese: " + (int) Math.ceil(wieseUpgradeBaseCost * Math.pow(1.15, levelWiese + 1)) + " Milch");

			levelWiese++;

			// erhoeht den mps-Wert
			mps = mps + wieseMpsUpgrade;

			// ziehe die Kosten ab
			milch = (milch - (int) wieseUpgradeKosten);

			refreshMilchAnzeige();
			// setzt die Statsanzeige
			gui.anzahlWieseUpgrade.setText("Wiese: " + levelWiese);

			// Anpassen des Kuhbildes erste Ausbaustufe
			if (levelWiese == 1) {
				gui.kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgrade.png"));
			}
			// Anpassen des Kuhbildes zweite Ausbaustufe
			if (levelWiese >= 2) {
				gui.kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgradeFinal.png"));
			}

		}
	}

	// Komplettes Partyhut Upgrade
	private void partyhutUpgrade() {

		// falls noch kein Upgrade gekauft wurde
		if (levelPartyhut == 0) {
			gui.partyhutUpgradeButton.setText("Partyhut: " + partyhutUpgradeBaseCost + " Milch");
		}
		// Berechnung der Upgrade Kosten ( basekosten * 1,15^levelPartyhut
		partyhutUpgradeKosten = partyhutUpgradeBaseCost * (int) Math.pow(1.15, levelPartyhut);

		// wenn genug Milch vorhanden ist um das Upgrade zu kaufen
		if (partyhutUpgradeKosten <= milch) {

			// setzt den Text des Buttons auf den Preis der naechsten Stufe
			gui.partyhutUpgradeButton.setText("Partyhut: "
					+ (int) Math.ceil(partyhutUpgradeBaseCost * Math.pow(1.15, levelPartyhut + 1)) + " Milch");

			levelPartyhut++;

			// erhoeht den mps-Wert
			mps = mps + partyhutMpsUpgrade;

			// ziehe die Kosten ab
			milch = (milch - (int) partyhutUpgradeKosten);

			refreshMilchAnzeige();
			gui.anzahlPartyhutUpgrade.setText("Partyhut: " + levelPartyhut);

		}

	}

	// Sorgt dafuer das man Milch die Sekunde bekommt
	Runnable milchProSekunde = new Runnable() {
		@Override
		public void run() {

			milch = milch + mps;
			refreshMilchAnzeige();
			gui.statMps.setText("Milch Pro Sekunde: " + mps);
		}
	};

	public static void main(String[] args) {
		Kuhlclicker kuhlclicker = new Kuhlclicker();
	}

}
