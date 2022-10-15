package gui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.ResourceBundle;

public class GUIHilabetekoa extends GUI {

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

	public GUIHilabetekoa() throws HeadlessException {
		super();
	}

	public GUIHilabetekoa(GraphicsConfiguration gc) {
		super(gc);
	}

	public GUIHilabetekoa(String title) throws HeadlessException {
		super(title);
	}

	public GUIHilabetekoa(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

}