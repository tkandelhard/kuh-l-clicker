import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class InitGUI extends JFrame{
	// Buttons
    public JButton wieseUpgradeButton = new JButton("Wiese: ");
    public JButton partyhutUpgradeButton = new JButton("Partyhut: ");
	private JButton changeButtonBlue = new JButton("Blau");
	private JButton changeButtonGreen = new JButton("Gruen");
	private JButton changeButtonRed = new JButton("Rot");
	public JButton saveButton = new JButton("Save");

	// Panels
    public JPanel kuhPanel = new JPanel();
    private JPanel layoutPanel = new JPanel();
    private JPanel upgradePanel = new JPanel();
    private JPanel statsPanel = new JPanel();
    private JPanel optionPanel = new JPanel();

	// Upgradepanel soll eine Scrollbar besitzen damit sie durch weitere Upgrades erweiterbar ist
	private JScrollPane upgradeScrollBar;

	// JTabbedPane enthaellt die Grundstruktur unserer 3 Tabs fuer Ugrades, Stats und Optionen
	private JTabbedPane gameTabPane = new JTabbedPane();

	// Labels
    public JLabel milchLabel = new JLabel();
	public JLabel kuhLabel = new JLabel();
    private JLabel wieseLabel = new JLabel();
    private JLabel partyhutLabel = new JLabel();
    private JLabel incomeLabel = new JLabel("daily income");
    private JLabel statsLabel = new JLabel("Gekaufte Upgrades");
    public JLabel anzahlWieseUpgrade = new JLabel();
    public JLabel anzahlPartyhutUpgrade = new JLabel();
    public JLabel statMps = new JLabel();
    public JLabel statMpc = new JLabel();

	// HTML color-codes zum Anpassen der Themes und leichterem Nachbearbeiten
	private String colorRedBG = "#AA3939";
	private String colorRedPanel = "#D46A6A";
	private String colorBlueBG = "#009999";
	private String colorBluePanel = "#33CCCC";
	private String colorGreenBG = "#228751";
	private String colorGreenPanel = "#36DA82";
	private String setColor = "blue";

    // feste Fenstergroesse
    private final int SCREENWIDTH = 1000;
    private final int SCREENHEIGHT = 600;


	public void initGUI() {
		 // Layouts festlegen
	    upgradePanel.setLayout(new GridLayout(10, 2));
	    optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
	    kuhPanel.setLayout(new BoxLayout(kuhPanel, BoxLayout.Y_AXIS));
		layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.X_AXIS));

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
	    statsPanel.setBackground(Color.decode(colorBlueBG));
	    statsPanel.setLayout(new GridLayout(6, 1));
	    // upgrade stats
	    statsPanel.add(statsLabel);   
	    statsPanel.add(anzahlWieseUpgrade);
	    statsPanel.add(anzahlPartyhutUpgrade);
	    // income stats   
	    statsPanel.add(incomeLabel);
	    statsPanel.add(statMps);
	    statsPanel.add(statMpc);

		// Komponenten im Optionentab
		optionPanel.setBackground(Color.decode(colorBluePanel));
		optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		changeButtonBlue.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionPanel.add(changeButtonBlue);
		changeButtonGreen.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionPanel.add(changeButtonGreen);
		changeButtonRed.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionPanel.add(changeButtonRed);
		optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionPanel.add(saveButton);

	    // Kuhpanel
	    milchLabel.setText("x 0");
	    milchLabel.setIcon(new ImageIcon("resources/Milchkanne.png"));
	    milchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    kuhPanel.setBackground(Color.decode("#FFFFFF"));
	    kuhLabel.setPreferredSize(new Dimension(400, 400));
	    kuhLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    kuhPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	    kuhPanel.add(milchLabel);
	    kuhPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	    kuhPanel.add(kuhLabel);
	    kuhPanel.setPreferredSize(new Dimension(500, 500));

		// hinzufuegen der einzelnen Tabs in das JTabPane
		gameTabPane.addTab("Upgrades", upgradeScrollBar);
		gameTabPane.addTab("Stats", statsPanel);
		gameTabPane.addTab("Optionen", optionPanel);

		// layoutPanel ist Traeger der beiden Hauptpanels gameTabPane und kuhPanel
	    layoutPanel.setBackground(Color.decode(colorBlueBG));
	    layoutPanel.add(gameTabPane);
	    layoutPanel.add(kuhPanel);
		add(layoutPanel);

		// standard Settings, close/resize Operationen
        setVisible(true);
		setResizable(false);
        setTitle("Kuhlclicker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(SCREENWIDTH, SCREENHEIGHT);

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
}

