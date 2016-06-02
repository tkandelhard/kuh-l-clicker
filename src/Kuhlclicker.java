import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    public JButton wiesenUpgrade = new JButton("Wiese");
    public JButton partyhutUpgrade = new JButton("Partyhut");
    public JLabel upgradeLabel = new JLabel("Upgrades");
    public JPanel upgradePanel = new JPanel();
    public JPanel kuhPanel = new JPanel();
    public ImageIcon backgroundWithoutUpgrades = new ImageIcon("Blablubb.jpg"); 
    public JLabel backgroundLabel = new JLabel();
    public int milch;
    public JLabel milchLabel = new JLabel();


    public Kuhlclicker(){

        renderer = new Renderer();
        add(renderer);
        initGUI();
        
        kuhPanel.addMouseListener(new MouseAdapter(){
      	  
      	    public void mouseClicked(MouseEvent e) {
      	    	getMilk();
      	    	milchLabel.setText("Milch: "+ milch);
      	        
      	       
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
        upgradePanel.add(wiesenUpgrade);
        upgradePanel.add(partyhutUpgrade);
    }

    public void getMilk(){
    	milch++;
    	System.out.println(milch);
    }


    public static void main(String[] args) {
        kuhlclicker = new Kuhlclicker();
    }

	



}


