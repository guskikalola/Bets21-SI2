package domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ErabiltzaileaApustuakAdapter extends AbstractTableModel {
	private Erabiltzailea erabiltzailea;
	private List<ApustuaContainerLuzatuta> apustuak;
	private String[] columnNames = {"Event","Question","EventDate","Bet (€)"};
	
	public ErabiltzaileaApustuakAdapter() {
		this.erabiltzailea = null;
		this.apustuak = new ArrayList<>();
	}
	public ErabiltzaileaApustuakAdapter(ErabiltzaileaContainer erab) {
		this.erabiltzailea = erab.getE();
		this.apustuak = erab.getApustuak();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ApustuaContainerLuzatuta apCL = this.apustuak.get(rowIndex);
		Apustua ap = apCL.getApustua();

		QuestionContainer qC = apCL.getKuotak().get(0).getQuestion();
		Question q = qC.getQuestion();
		Event ev = qC.getEvent();
		
		switch(columnIndex) {
		case 0: // Event
			return ev.getDescription();
		case 1: // Question
			return q.getQuestion();
		case 2: // EventDate
			return ev.getEventDate();
		case 3: // Bet (money)
			return Double.toString(ap.getDiruKop());
		default:
			throw new IndexOutOfBoundsException(columnIndex);
		}
	}
	@Override
	public int getRowCount() { return apustuak.size(); }
	@Override
	public int getColumnCount() { return columnNames.length; }
	@Override
	public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }
	@Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }
}
