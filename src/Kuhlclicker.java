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

        // renderer Klasse wird erstellt und GUI wird initiiert
        renderer = new Renderer();
        add(renderer);
        initGUI();

        // Mouselistener fuer das Kuhpanel
        kuhPanel.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                getMilk();
                milchLabel.setText("Milch: " + milch);


            }
        });
    }

    public void repaint(Graphics g){
        // Background settings
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);

        // Anzeige der stats
        g.setColor(Color.WHITE);
        g.fillRect(50, 50, 200, 600);

    }
    
    public void initGUI(){


        backgroundLabel.setIcon(backgroundWithoutUpgrades);
        backgroundLabel.setPreferredSize(new Dimension(800, 750));

        // Panel fuer die Upgrades
        upgradePanel.setLayout(new GridLayout(10, 1));
        upgradePanel.setPreferredSize(new Dimension(300, 750));
        upgradePanel.add(upgradeLabel);
        upgradePanel.add(wiesenUpgrade);
        upgradePanel.add(partyhutUpgrade);
        renderer.add(upgradePanel);

        // Panel fuer Kuh
        kuhPanel.add(milchLabel);
        milchLabel.setText("Milch: 0");
        kuhPanel.add(backgroundLabel);
        kuhPanel.setPreferredSize(new Dimension(800, 750));
        renderer.add(kuhPanel);

        // Standard GUI-Settings
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setVisible(true);
        setResizable(false);
        setTitle("Kuhlclicker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    // click auf Kuh generiert Resource Milch
    public void getMilk(){
    	milch++;
    	System.out.println(milch);
    }


    public static void main(String[] args) {
        kuhlclicker = new Kuhlclicker();
    }



}


