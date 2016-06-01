import javax.swing.*;
import java.awt.*;

/**
 * Created by christianmaatz on 01.06.16.
 */
public class Renderer extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Kuhlclicker.kuhlclicker.repaint(g);
    }
}
