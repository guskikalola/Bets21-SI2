package gui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Question;

public class GUI extends JFrame {

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

	public void gehituGaldera(Vector<Question> queries, DefaultTableModel defaultTableModel, ArrayList<Question> arrayList, JTable table) {
		for (domain.Question q : queries) {
			Vector<Object> row = new Vector<Object>();
	
			row.add(q.getQuestionNumber());
			row.add(q.getQuestion());
			row.add(q);
			defaultTableModel.addRow(row);
			arrayList.add(q);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(268);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(2)); // not shown in
	}

}