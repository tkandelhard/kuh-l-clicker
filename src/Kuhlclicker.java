import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
public class Kuhlclicker extends JFrame implements ActionListener, MouseListener{

    public static Kuhlclicker kuhlclicker;
    public final int SCREENWIDTH = 1200;
    public final int SCREENHEIGHT = 800;
    public Renderer renderer;
    public JButton wiesenUpgrade = new JButton("Wiese");
    public JButton partyhutUpgrade = new JButton("Partyhut");
    public JLabel upgradeLabel = new JLabel("    Upgrades");
    public JPanel upgradePanel = new JPanel();


    public Kuhlclicker(){

        renderer = new Renderer();
        add(renderer);
        addMouseListener(this);
        initGUI();


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
        upgradePanel.setLayout(new GridLayout(3,1));
        renderer.add(upgradePanel);
        upgradePanel.add(upgradeLabel);
        upgradePanel.add(wiesenUpgrade);
        upgradePanel.add(partyhutUpgrade);
    }

    public void getMilk(){

    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getMilk();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public static void main(String[] args) {
        kuhlclicker = new Kuhlclicker();
    }

}


