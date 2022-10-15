package gui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends JFrame {

	protected static ErabiltzaileGUI frame;
	protected String urtarrila = new String(ResourceBundle.getBundle("Etiquetas").getString("January"));
	protected String otsaila = new String(ResourceBundle.getBundle("Etiquetas").getString("February"));
	protected String martxoa = new String(ResourceBundle.getBundle("Etiquetas").getString("March"));
	protected String apirila = new String(ResourceBundle.getBundle("Etiquetas").getString("April"));
	protected String maiatza = new String(ResourceBundle.getBundle("Etiquetas").getString("May"));
	protected String ekaina = new String(ResourceBundle.getBundle("Etiquetas").getString("June"));
	protected String uztaila = new String(ResourceBundle.getBundle("Etiquetas").getString("July"));
	protected String abuztua = new String(ResourceBundle.getBundle("Etiquetas").getString("August"));
	protected String iraila = new String(ResourceBundle.getBundle("Etiquetas").getString("September"));
	protected String urria = new String(ResourceBundle.getBundle("Etiquetas").getString("October"));
	protected String azaroa = new String(ResourceBundle.getBundle("Etiquetas").getString("November"));
	protected String abendua = new String(ResourceBundle.getBundle("Etiquetas").getString("December"));

	public GUI() throws HeadlessException {
		super();
	}

	public GUI(GraphicsConfiguration gc) {
		super(gc);
	}

	public GUI(String title) throws HeadlessException {
		super(title);
	}

	public GUI(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public void atzeraButoiaSortu(JFrame frame) {
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		button.setBounds(21, 10, 41, 27);
		getContentPane().add(button);
	}

}