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
    public final int SCREENWIDTH = 1200;
    public final int SCREENHEIGHT = 800;
    public Renderer renderer;
    
    // Gui stuff
    public JButton upgradeButton = new JButton("Upgrades");
    public JButton statsButton = new JButton("Stats");
    public JButton optionsButton = new JButton("Options");
    public JButton wieseUpgradeButton = new JButton("Wiese: ");
    public JButton partyhutUpgradeButton = new JButton("Partyhut");
    public JButton settingsButton = new JButton("Settings");
    public JButton saveButton = new JButton("Save");
    public JButton loadButton = new JButton("Load");
    public JButton otherButton = new JButton("Other");



    public JPanel kuhPanel = new JPanel();      // clickable Kuh
    public JTabbedPane gameTabPane = new JTabbedPane();     // hier sind die 3 Buttons und deren Interaktionen drin
    public JPanel upgradePanel = new JPanel();
    public JPanel statsPanel = new JPanel();
    public JPanel optionPanel = new JPanel();

    public ImageIcon backgroundWithoutUpgrades = new ImageIcon("resources/Blablubb.jpg");
    public ImageIcon wieseIcon = new ImageIcon ("resources/Wiese.jpg");
    public ImageIcon partyhutIcon = new ImageIcon("resources/Partyhut.jpg");

    public JLabel backgroundLabel = new JLabel();
    public JLabel milchLabel = new JLabel();
    public JLabel wieseLabel = new JLabel();
    public JLabel partyhutLabel = new JLabel();

    // rechen Stuff
    public int milch;
    public int levelWiese;
    public double wieseUpgradeKosten;
    
    public int mps;// mps weil wegen isso (milch pro sekunde)
   


    public Kuhlclicker(){

    	
    	//ruft die MilchProSekundeRunnable jede sekunde auf 
        
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(milchProSekunde, 0, 1, TimeUnit.SECONDS);
        
        
        renderer = new Renderer();
        add(renderer);
        initGUI();
        wieseUpgrade();
        
        // Wiesen Upgrade
        wieseUpgradeButton.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e){
        		wieseUpgrade();		
        	}
        });
        
        // Milch per Click auf die Kuh
        kuhPanel.addMouseListener(new MouseAdapter(){
      	  
      	    public void mouseClicked(MouseEvent e) {
      	    	getMilkPerClick();
      	    	refreshMilchAnzeige();
      	    	       
      	    }
      });
    }

    public void repaint(Graphics g){

        // Background dunkelgruen
        g.setColor(Color.decode("#238B49"));
        g.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);

        /*
        // Anzeige der stats
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, 200, 600);
        */
    }
    
    public void initGUI(){
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setVisible(true);
        setResizable(false);
        setTitle("Kuhlclicker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Layouts festlegen
        upgradePanel.setLayout(new GridLayout(10, 2, 4, 6));
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

        wieseLabel.setIcon(wieseIcon);
        wieseLabel.setPreferredSize(new Dimension(40, 40));
        partyhutLabel.setIcon(partyhutIcon);
        partyhutLabel.setPreferredSize(new Dimension(40, 40));

        upgradePanel.add(wieseUpgradeButton);
        upgradePanel.add(wieseLabel);
        upgradePanel.add(partyhutUpgradeButton);
        upgradePanel.add(partyhutLabel);
        //upgradePanel.setPreferredSize(new Dimension(400, 400));

        // Komponenten im Optionentab (wenn zeit dann extra methode statt DRY)
        // createRigidArea laesst Platz zwischen Komponenten, damit es besser aussieht
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
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));


        JScrollPane upgradeScrollBar = new JScrollPane(upgradePanel);
        upgradeScrollBar.setPreferredSize(new Dimension(400, 400));

        gameTabPane.add(upgradeScrollBar, "Upgrades");
        gameTabPane.addTab("Stats", statsPanel);
        gameTabPane.addTab("Options", optionPanel);

        renderer.add(gameTabPane);
        renderer.add(kuhPanel);
        
        backgroundLabel.setIcon(backgroundWithoutUpgrades);
        backgroundLabel.setPreferredSize(new Dimension(800, 750));
        
        kuhPanel.add(milchLabel);
        milchLabel.setText("Milch: 0");

        kuhPanel.add(backgroundLabel);
        kuhPanel.setPreferredSize(new Dimension(500,500));

        
    }
    // immer aufrufen wenn sich was an der Milch anzahl ver�ndert, sprich upgrades, clicks, etc 
    public void refreshMilchAnzeige(){
    	milchLabel.setText("Milch: "+ milch);	
    }
    
    // regelt wie viel milch man per click bekommt 
    public void getMilkPerClick(){
    	
    	milch++;
    	
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
    		
    		refreshMilchAnzeige();
    		
    		System.out.println("wiese +1");
    		
    		
    	}else
    	System.out.println("nicht genug Milch");
    	
    }
    // Sorgt daf�r das man Milch die Sekunde bekommt
    Runnable milchProSekunde = new Runnable(){
	@Override
	public void run() {
		
		milch = milch + mps;
		refreshMilchAnzeige();
		}
    };
    

    public static void main(String[] args) {
        kuhlclicker = new Kuhlclicker();
    }

	



}


