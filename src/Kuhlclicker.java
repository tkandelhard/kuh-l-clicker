import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

/**
 * Created by christianmaatz on 01.06.16.
 */

public class Kuhlclicker extends JFrame {

    public static Kuhlclicker kuhlclicker;
    private final int SCREENWIDTH = 1000;
    private final int SCREENHEIGHT = 600;
    
    // Gui stuff
    private JButton wieseUpgradeButton = new JButton("Wiese: ");
    private JButton partyhutUpgradeButton = new JButton("Partyhut: ");
    private JButton changeButtonBlue = new JButton("Blau");
    private JButton changeButtonGreen = new JButton("Gruen");
    private JButton changeButtonRed = new JButton("Rot");
    private JButton loadButton = new JButton("Load");
    private JScrollPane upgradeScrollBar;
    private JPanel kuhPanel = new JPanel();      // clickable Kuh
    private JTabbedPane gameTabPane = new JTabbedPane();     // hier sind die 3 Buttons und deren Interaktionen drin

    private JPanel layoutPanel = new JPanel();
    private JPanel upgradePanel = new JPanel();
    private JPanel statsPanel = new JPanel();
    private JPanel optionPanel = new JPanel();

    private JLabel backgroundLabel = new JLabel();
    private JLabel kuhLabel = new JLabel();
    private JLabel milchLabel = new JLabel();
    private JLabel wieseLabel = new JLabel();
    private JLabel partyhutLabel = new JLabel();
    private JLabel incomeLabel = new JLabel("daily income");
    private JLabel statsLabel = new JLabel("Gekaufte Upgrades");
    private JLabel anzahlWieseUpgrade = new JLabel();
    private JLabel anzahlPartyhutUpgrade = new JLabel();
    private JLabel statMps = new JLabel();
    private JLabel statMpc = new JLabel();

    private String colorRedBG = "#AA3939";
    private String colorRedPanel = "#D46A6A";
    private String colorBlueBG = "#009999";
    private String colorBluePanel = "#33CCCC";
    private String colorGreenBG = "#3C7113";
    private String colorGreenPanel = "#5E9732";
    private String setColor = "blue";



    // rechen Stuff
    private int milch;
    
    private int levelWiese;
    private double wieseUpgradeKosten;
    
    private int levelPartyhut;
    private double partyhutUpgradeKosten;
    
    private double mpc;
    
    private int mps;// mps weil wegen isso (milch pro sekunde)
   


    public Kuhlclicker(){

    	
    	//ruft die MilchProSekundeRunnable jede sekunde auf 
        
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(milchProSekunde, 0, 1, TimeUnit.SECONDS);

        initGUI();
        wieseUpgrade();
        partyhutUpgrade();
        
        
        // Partyhut Upgrade
        partyhutUpgradeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                partyhutUpgrade();
            }
        });
        // Wiesen Upgrade
        wieseUpgradeButton.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e){
        		wieseUpgrade();
        		refreshMilchAnzeige();	
        	}
        });
        
        // Milch per Click auf die Kuh
        kuhPanel.addMouseListener(new MouseAdapter(){
      	  
      	    public void mouseClicked(MouseEvent e) {
      	    	getMilkPerClick();
      	    	
      	    	       
      	    }
        });

        // Click auf Blau aendert thema auf blau
        changeButtonBlue.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setColor = "blue";
                layoutPanel.setBackground(Color.decode(colorBlueBG));
                upgradePanel.setBackground(Color.decode(colorBluePanel));
                statsPanel.setBackground(Color.decode(colorBluePanel));
                optionPanel.setBackground(Color.decode(colorBluePanel));

            }
        });
        // Click auf Gruen aendert thema auf gruen
        changeButtonGreen.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setColor = "green";
                layoutPanel.setBackground(Color.decode(colorGreenBG));
                upgradePanel.setBackground(Color.decode(colorGreenPanel));
                statsPanel.setBackground(Color.decode(colorGreenPanel));
                optionPanel.setBackground(Color.decode(colorGreenPanel));
            }
        });
        // Click auf Rot aendert thema auf rot
        changeButtonRed.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setColor = "red";
                layoutPanel.setBackground(Color.decode(colorRedBG));
                upgradePanel.setBackground(Color.decode(colorRedPanel));
                statsPanel.setBackground(Color.decode(colorRedPanel));
                optionPanel.setBackground(Color.decode(colorRedPanel));
            }
        });

    }

    public void initGUI(){

        // Layouts festlegen
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.X_AXIS));
        upgradePanel.setLayout(new GridLayout(10, 2));
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        kuhPanel.setLayout(new BoxLayout(kuhPanel, BoxLayout.Y_AXIS));

        // Komponenten im Upgrade Bereich
        wieseLabel.setIcon(new ImageIcon("resources/WieseIcon.png"));
        partyhutLabel.setIcon(new ImageIcon("resources/Partyhut.png"));

        upgradePanel.setBackground(Color.decode(colorBluePanel));
        upgradePanel.add(wieseUpgradeButton);
        upgradePanel.add(wieseLabel);
        upgradePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        upgradePanel.add(partyhutUpgradeButton);
        upgradePanel.add(partyhutLabel);
        upgradePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Scrollbar fuer Upgrades
        upgradeScrollBar = new JScrollPane(upgradePanel);
        upgradeScrollBar.setPreferredSize(new Dimension(400, 400));

        statsPanel.setBackground(Color.decode(colorBluePanel));
        statsPanel.setLayout(new GridLayout(6, 1));
        // upgrade stats
        statsPanel.add(statsLabel);   
        statsPanel.add(anzahlWieseUpgrade);
        statsPanel.add(anzahlPartyhutUpgrade);
        // income stats   
        statsPanel.add(incomeLabel);
        statsPanel.add(statMps);
        statsPanel.add(statMpc);


        // Komponenten im Optionentab (wenn zeit dann extra methode statt DRY)
        optionPanel.setBackground(Color.decode(colorBluePanel));
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        changeButtonBlue.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(changeButtonBlue);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        changeButtonGreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(changeButtonGreen);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        changeButtonRed.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(changeButtonRed);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(loadButton);

        gameTabPane.add(upgradeScrollBar, "Upgrades");
        gameTabPane.addTab("Stats", statsPanel);
        gameTabPane.addTab("Options", optionPanel);

        // Kuhpanel
        milchLabel.setText("x 0");
        milchLabel.setIcon(new ImageIcon("resources/Milchkanne.png"));
        milchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        kuhPanel.setBackground(Color.decode("#FFFFFF"));
        kuhLabel.setIcon(new ImageIcon("resources/Kuh.png"));
        kuhLabel.setPreferredSize(new Dimension(400, 400));
        kuhLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        kuhPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        kuhPanel.add(milchLabel);
        kuhPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        kuhPanel.add(kuhLabel);
        kuhPanel.setPreferredSize(new Dimension(500, 500));

        layoutPanel.setBackground(Color.decode(colorBlueBG));
        layoutPanel.add(gameTabPane);
        layoutPanel.add(kuhPanel);
        add(layoutPanel);

        setSize(SCREENWIDTH, SCREENHEIGHT);
        setVisible(true);
        setResizable(false);
        setTitle("Kuhlclicker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    // immer aufrufen wenn sich was an der Milch anzahl ver�ndert, sprich upgrades, clicks, etc 
    public void refreshMilchAnzeige(){
    	milchLabel.setText("x "+ milch);
    }
    
    // regelt wie viel milch man per click bekommt 
    public void getMilkPerClick(){
    	mpc = 1;
    	// erh�ht die milch pro click um 10% der milch pro sekunde
    	mpc = mpc + Math.ceil((mps*10)/100);
    	milch = milch + (int)mpc;
    	statMpc.setText("Milch per Click: "+ mpc);
    	refreshMilchAnzeige();
    	
    }
    
    // regelt das komplette WiesenUpgrade
    public void wieseUpgrade(){
    	
    	
    	double  wieseUpgradeBaseCost = 15;
    	int wieseMpsUpgrade = 1;
    	
    	// falls noch kein upgrade gekauft wurde 
    	if(levelWiese == 0){
    		wieseUpgradeButton.setText("Wiese: " + wieseUpgradeBaseCost+ " Milch");
    	}
    	// berechnung der upgrade kosten ( basekosten * 1,15^levelWiese
    	wieseUpgradeKosten = wieseUpgradeBaseCost * Math.pow(1.15,levelWiese);
    	
    	// wenn genug milch vorhanden ist um das Upgrade zu kaufen
		if(wieseUpgradeKosten <= milch){
			
			// setz den text Des Buttons auf den preis der n�chsten stufe
			wieseUpgradeButton.setText("Wiese: " + Math.ceil(wieseUpgradeBaseCost * Math.pow(1.15,levelWiese+1))+ " Milch");
			
    		levelWiese++;
    		
    		// erh�ht die mps 
    		mps = mps + wieseMpsUpgrade;
    	
    		// ziehe die kosten ab
    		milch =  (milch - (int)wieseUpgradeKosten);

            // Anpassen des Kuhbildes
            if (levelWiese == 1){
                kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgrade.png"));
            }
            else if (levelWiese >= 2){
                kuhLabel.setIcon(new ImageIcon("resources/KuhWieseUpgradeFinal.png"));
            }



    		refreshMilchAnzeige();
    		// setzt die statsanzeige
    		anzahlWieseUpgrade.setText("Wiese: "+ levelWiese);
    		System.out.println("wiese +1");
    		
    		
    	}else
    	System.out.println("nicht genug Milch");
    	
    }
    // Komplette partyhut upgrade
    private void partyhutUpgrade() {
    	
    	double  partyhutUpgradeBaseCost = 100;
    	int partyhutMpsUpgrade = 10;
    	
    	// falls noch kein upgrade gekauft wurde 
    	if(levelPartyhut == 0){
    		partyhutUpgradeButton.setText("Partyhut: " + partyhutUpgradeBaseCost+ " Milch");
    	}
    	// berechnung der upgrade kosten ( basekosten * 1,15^levelPartyhut
    	partyhutUpgradeKosten = partyhutUpgradeBaseCost * Math.pow(1.15,levelPartyhut);
    	
    	// wenn genug milch vorhanden ist um das Upgrade zu kaufen
		if(partyhutUpgradeKosten <= milch){
			
			// setz den text Des Buttons auf den preis der n�chsten stufe
			partyhutUpgradeButton.setText("Partyhut: " + Math.ceil(partyhutUpgradeBaseCost * Math.pow(1.15,levelPartyhut+1))+ " Milch");
			
    		levelPartyhut++;
    		
    		// erh�ht die mps 
    		mps = mps + partyhutMpsUpgrade;
    	
    		// ziehe die kosten ab
    		milch =  (milch - (int)partyhutUpgradeKosten);
    		
    		refreshMilchAnzeige();
    		anzahlPartyhutUpgrade.setText("Partyhut: " + levelPartyhut);
    		System.out.println("Partyhut +1");
    		
    		
    	}else
    	System.out.println("nicht genug Milch");
		
	}
    // Sorgt daf�r das man Milch die Sekunde bekommt
    Runnable milchProSekunde = new Runnable(){
	@Override
	public void run() {
		
		milch = milch + mps;
		refreshMilchAnzeige();
		statMps.setText("Milch Pro Sekunde: " + mps);
		}
    };
    

    public static void main(String[] args) {
        kuhlclicker = new Kuhlclicker();
    }
}


