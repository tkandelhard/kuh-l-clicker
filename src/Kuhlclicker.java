import java.awt.*;
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

    public static Kuhlclicker kuhlclicker;
    private final int SCREENWIDTH = 1000;
    private final int SCREENHEIGHT = 600;
    private Renderer renderer;
    
    // Gui stuff
    private JButton wieseUpgradeButton = new JButton("Wiese: ");
    private JButton partyhutUpgradeButton = new JButton("Partyhut: ");
    private JButton settingsButton = new JButton("Settings");
    private JButton saveButton = new JButton("Save");
    private JButton loadButton = new JButton("Load");
    private JButton otherButton = new JButton("Other");
    private JScrollPane upgradeScrollBar;
    private JPanel kuhPanel = new JPanel();      // clickable Kuh
    private JTabbedPane gameTabPane = new JTabbedPane();     // hier sind die 3 Buttons und deren Interaktionen drin

    private JPanel layoutPanel = new JPanel();
    private JPanel upgradePanel = new JPanel();
    private JPanel statsPanel = new JPanel();
    private JPanel optionPanel = new JPanel();

    private JLabel backgroundLabel = new JLabel();
    private JLabel milchLabel = new JLabel();
    private JLabel wieseLabel = new JLabel();
    private JLabel partyhutLabel = new JLabel();
    private JLabel incomeLabel = new JLabel("daily income");
    private JLabel statsLabel = new JLabel("Gekaufte Upgrades");
    private JLabel anzahlWieseUpgrade = new JLabel();
    private JLabel anzahlPartyhutUpgrade = new JLabel();
    private JLabel statMps = new JLabel();
    private JLabel statMpc = new JLabel();


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

        renderer = new Renderer();
        add(renderer);
        initGUI();
        wieseUpgrade();
        partyhutUpgrade();
        
        
        // Partyhut Upgrade
        partyhutUpgradeButton.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e){
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
    }

    public void repaint(Graphics g){

        // Background
        g.setColor(Color.decode("#5EC4CD"));
        g.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);

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
        upgradePanel.setBackground(Color.decode("#028E9B"));
        upgradePanel.add(wieseUpgradeButton);
        upgradePanel.add(wieseLabel);
        upgradePanel.add(Box.createRigidArea(new Dimension(0, 60)));
        upgradePanel.add(partyhutUpgradeButton);
        upgradePanel.add(partyhutLabel);
        upgradePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Scrollbar fuer Upgrades
        upgradeScrollBar = new JScrollPane(upgradePanel);
        upgradeScrollBar.setPreferredSize(new Dimension(400, 400));

        // Komponenten im Stats Bereich
        statsPanel.setBackground(Color.decode("#028E9B"));
        statsPanel.setLayout(new GridLayout(6,1));
        // upgrade stats
        statsPanel.add(statsLabel);   
        statsPanel.add(anzahlWieseUpgrade);
        statsPanel.add(anzahlPartyhutUpgrade);
        // income stats   
        statsPanel.add(incomeLabel);
        statsPanel.add(statMps);
        statsPanel.add(statMpc);

        // Komponenten im Optionentab (wenn zeit dann extra methode statt DRY)
        optionPanel.setBackground(Color.decode("#028E9B"));
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(settingsButton);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(saveButton);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(loadButton);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        otherButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(otherButton);

        gameTabPane.add(upgradeScrollBar, "Upgrades");
        gameTabPane.addTab("Stats", statsPanel);
        gameTabPane.addTab("Options", optionPanel);

        // Kuhpanel
        milchLabel.setText("x 0");
        milchLabel.setIcon(new ImageIcon("resources/Milchkanne.png"));
        milchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        kuhPanel.setBackground(Color.decode("#FFFFFF"));
        backgroundLabel.setIcon(new ImageIcon("resources/Kuh.png"));
        backgroundLabel.setPreferredSize(new Dimension(400, 400));
        backgroundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        kuhPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        kuhPanel.add(milchLabel);
        kuhPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        kuhPanel.add(backgroundLabel);
        kuhPanel.setPreferredSize(new Dimension(500, 500));

        layoutPanel.setBackground(Color.decode("#5EC4CD"));
        layoutPanel.add(gameTabPane);
        layoutPanel.add(kuhPanel);
        renderer.add(layoutPanel);
/*
        renderer.add(gameTabPane);
        renderer.add(kuhPanel);
*/
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setVisible(true);
        setResizable(false);
        setTitle("Kuhlclicker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // immer aufrufen wenn sich was an der Milch anzahl verï¿½ndert, sprich upgrades, clicks, etc 
    public void refreshMilchAnzeige(){
    	milchLabel.setText("x "+ milch);
    }
    
    // regelt wie viel milch man per click bekommt 
    public void getMilkPerClick(){
    	mpc = 1;
    	// erhöht die milch pro click um 10% der milch pro sekunde
    	mpc = mpc + Math.ceil((mps*10)/100);
    	milch = milch + (int)mpc;
    	statMpc.setText("Milch per Click: "+ mpc);
    	
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
			
			// setz den text Des Buttons auf den preis der nï¿½chsten stufe
			wieseUpgradeButton.setText("Wiese: " + Math.ceil(wieseUpgradeBaseCost * Math.pow(1.15,levelWiese+1))+ " Milch");
			
    		levelWiese++;
    		
    		// erhï¿½ht die mps 
    		mps = mps + wieseMpsUpgrade;
    	
    		// ziehe die kosten ab
    		milch =  (milch - (int)wieseUpgradeKosten);
    		
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
			
			// setz den text Des Buttons auf den preis der nï¿½chsten stufe
			partyhutUpgradeButton.setText("Partyhut: " + Math.ceil(partyhutUpgradeBaseCost * Math.pow(1.15,levelPartyhut+1))+ " Milch");
			
    		levelPartyhut++;
    		
    		// erhï¿½ht die mps 
    		mps = mps + partyhutMpsUpgrade;
    	
    		// ziehe die kosten ab
    		milch =  (milch - (int)partyhutUpgradeKosten);
    		
    		refreshMilchAnzeige();
    		anzahlPartyhutUpgrade.setText("Partyhut: " + levelPartyhut);
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
		statMps.setText("Milch Pro Sekunde: " + mps);
		}
    };
    

    public static void main(String[] args) {
        kuhlclicker = new Kuhlclicker();
    }

	



}


