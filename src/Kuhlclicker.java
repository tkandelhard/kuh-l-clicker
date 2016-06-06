import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

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
    public JButton wieseUpgradeButton = new JButton("Wiese: ");
    public JButton partyhutUpgrade = new JButton("Partyhut");
    public JLabel upgradeLabel = new JLabel("Upgrades");
    public JPanel upgradePanel = new JPanel();
    public JPanel kuhPanel = new JPanel();
    public ImageIcon backgroundWithoutUpgrades = new ImageIcon("Blablubb.jpg"); 
    public JLabel backgroundLabel = new JLabel();
    public JLabel milchLabel = new JLabel();
    
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
        // Background
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);

        // Anzeige der stats
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, 200, 600);


    }
    
    public void initGUI(){
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setVisible(true);
        setResizable(false);
        setTitle("Kuhlclicker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        upgradePanel.setLayout(new GridLayout(10,1));
        renderer.add(upgradePanel);
        renderer.add(kuhPanel);
        
        backgroundLabel.setIcon(backgroundWithoutUpgrades);
        backgroundLabel.setPreferredSize(new Dimension(800,750));
        
        kuhPanel.add(milchLabel);
        milchLabel.setText("Milch: 0");
        
        kuhPanel.add(backgroundLabel);
        kuhPanel.setPreferredSize(new Dimension(800,750));
        upgradePanel.setPreferredSize(new Dimension(300,750));
        upgradePanel.add(upgradeLabel);
        
        upgradePanel.add(wieseUpgradeButton);
        upgradePanel.add(partyhutUpgrade);
        
    }
    // immer aufrufen wenn sich was an der Milch anzahl verändert, sprich upgrades, clicks, etc 
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
			
			// setz den text Des Buttons auf den preis der nächsten stufe
			wieseUpgradeButton.setText("Wiese: " + Math.ceil(wieseUpgradeBaseCost * Math.pow(1.15,levelWiese+1))+ " Milch");
			
    		levelWiese++;
    		
    		// erhöht die mps 
    		mps = mps + wieseMpsUpgrade;
    	
    		// ziehe die kosten ab
    		milch =  (milch - (int)wieseUpgradeKosten);
    		
    		refreshMilchAnzeige();
    		
    		System.out.println("wiese +1");
    		
    		
    	}else
    	System.out.println("nicht genug Milch");
    	
    }
    // Sorgt dafür das man Milch die Sekunde bekommt
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


