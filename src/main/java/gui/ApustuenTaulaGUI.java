package gui;

import java.awt.EventQueue;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

import domain.ErabiltzaileaApustuakAdapter;
import domain.ErabiltzaileaContainer;
import domain.Erabiltzailea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

public class ApustuenTaulaGUI extends JFrame {

	private JPanel contentPane;
	private JTable apustuakTaula;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApustuenTaulaGUI frame = new ApustuenTaulaGUI(new ErabiltzaileaApustuakAdapter(new ErabiltzaileaContainer(new Erabiltzailea("a","b",new Date()))));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param erabiltzaileaApustuakAdapter 
	 */
	public ApustuenTaulaGUI(ErabiltzaileaApustuakAdapter erabiltzaileaApustuakAdapter) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane apustuakPane = new JScrollPane();
		contentPane.add(apustuakPane);

		apustuakTaula = new JTable();
		apustuakTaula.setModel(erabiltzaileaApustuakAdapter);
		apustuakTaula.setFillsViewportHeight(true);
		apustuakPane.setViewportView(apustuakTaula);
	}
}
