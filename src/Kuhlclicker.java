import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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


    public Kuhlclicker(){

        renderer = new Renderer();
        add(renderer);
        addMouseListener(this);
        setSize(SCREENWIDTH, SCREENHEIGHT);
        setVisible(true);
        setResizable(false);
        setTitle("Kuhlclicker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public void repaint(Graphics g){
        // Background
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);

        // Anzeige der stats
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, 200, 600);


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


