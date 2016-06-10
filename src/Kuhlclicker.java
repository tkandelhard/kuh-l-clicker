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

    // rechen Stuff
    private int milch;

    private int levelWiese;
    private double wieseUpgradeKosten;

    private int levelPartyhut;
    private double partyhutUpgradeKosten;

    private double mpc;

    private int mps;// mps weil wegen isso (milch pro sekunde)
    private InitGUI gui = new InitGUI();


    public Kuhlclicker(){


        //ruft die MilchProSekundeRunnable jede sekunde auf

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(milchProSekunde, 0, 1, TimeUnit.SECONDS);

        wieseUpgrade();
        partyhutUpgrade();

        gui.initGUI();


        // Partyhut Upgrade
        gui.partyhutUpgradeButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                partyhutUpgrade();
            }
        });
        // Wiesen Upgrade
        gui.wieseUpgradeButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                wieseUpgrade();
                refreshMilchAnzeige();
            }
        });

        // Milch per Click auf die Kuh
        gui.kuhPanel.addMouseListener(new MouseAdapter(){

            public void mouseClicked(MouseEvent e) {
                getMilkPerClick();


            }
        });
    }

    // immer aufrufen wenn sich was an der Milch anzahl verï¿½ndert, sprich upgrades, clicks, etc
    public void refreshMilchAnzeige(){
        gui.milchLabel.setText("x "+ milch);
    }

    // regelt wie viel milch man per click bekommt
    public void getMilkPerClick(){
        mpc = 1;
        // erhöht die milch pro click um 10% der milch pro sekunde
        mpc = mpc + Math.ceil((mps*10)/100);
        milch = milch + (int)mpc;
        gui.statMpc.setText("Milch per Click: "+ mpc);
        refreshMilchAnzeige();

    }

    // regelt das komplette WiesenUpgrade
    public void wieseUpgrade(){


        double  wieseUpgradeBaseCost = 15;
        int wieseMpsUpgrade = 1;

        // falls noch kein upgrade gekauft wurde
        if(levelWiese == 0){
            gui.wieseUpgradeButton.setText("Wiese: " + wieseUpgradeBaseCost + " Milch");
        }

        // berechnung der upgrade kosten ( basekosten * 1,15^levelWiese
        wieseUpgradeKosten = wieseUpgradeBaseCost * Math.pow(1.15,levelWiese);

        // wenn genug milch vorhanden ist um das Upgrade zu kaufen
        if(wieseUpgradeKosten <= milch){

            // setz den text Des Buttons auf den preis der nï¿½chsten stufe
            gui.wieseUpgradeButton.setText("Wiese: " + Math.ceil(wieseUpgradeBaseCost * Math.pow(1.15,levelWiese+1))+ " Milch");

            levelWiese++;

            // erhoeht die mps
            mps = mps + wieseMpsUpgrade;

            // ziehe die kosten ab
            milch =  (milch - (int)wieseUpgradeKosten);

            refreshMilchAnzeige();
            // setzt die statsanzeige
            gui.anzahlWieseUpgrade.setText("Wiese: "+ levelWiese);
            System.out.println("wiese +1");

            // Anpassen des Kuhbildes
            if (levelWiese == 1){
                gui.kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgrade.png"));
            }

            if (levelWiese >= 2){
                gui.kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgradeFinal.png"));
            }


        }else
            System.out.println("nicht genug Milch");

    }
    // Komplette partyhut upgrade
    private void partyhutUpgrade() {

        double  partyhutUpgradeBaseCost = 100;
        int partyhutMpsUpgrade = 10;

        // falls noch kein upgrade gekauft wurde
        if(levelPartyhut == 0){
            gui.partyhutUpgradeButton.setText("Partyhut: " + partyhutUpgradeBaseCost+ " Milch");
        }
        // berechnung der upgrade kosten ( basekosten * 1,15^levelPartyhut
        partyhutUpgradeKosten = partyhutUpgradeBaseCost * Math.pow(1.15,levelPartyhut);

        // wenn genug milch vorhanden ist um das Upgrade zu kaufen
        if(partyhutUpgradeKosten <= milch){

            // setz den text Des Buttons auf den preis der nï¿½chsten stufe
            gui.partyhutUpgradeButton.setText("Partyhut: " + Math.ceil(partyhutUpgradeBaseCost * Math.pow(1.15,levelPartyhut+1))+ " Milch");

            levelPartyhut++;

            // erhï¿½ht die mps
            mps = mps + partyhutMpsUpgrade;

            // ziehe die kosten ab
            milch =  (milch - (int)partyhutUpgradeKosten);

            refreshMilchAnzeige();
            gui.anzahlPartyhutUpgrade.setText("Partyhut: " + levelPartyhut);
            System.out.println("Partyhut +1");


        }else
            System.out.println("nicht genug Milch");

    }
    // Sorgt dafï¿½r das man Milch die Sekunde bekommt
    Runnable milchProSekunde = new Runnable(){
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

