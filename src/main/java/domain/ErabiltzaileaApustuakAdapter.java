package domain;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ErabiltzaileaApustuakAdapter extends AbstractTableModel {

	private Erabiltzailea erabiltzailea;
	private List<ApustuaContainerLuzatuta> apustuak;
	private String[] columnNames = {"Event","Question","EventDate","Bet (€)"};
	
	public ErabiltzaileaApustuakAdapter(ErabiltzaileaContainer container) {
		this.erabiltzailea = container.getE();
		this.apustuak = container.getApustuak();
	}

	@Override
	public int getRowCount() {
		return apustuak.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ApustuaContainerLuzatuta apC = this.apustuak.get(rowIndex);
		Double diruKop = apC.getDiruKop();
		QuestionContainer qC = apC.getKuotak().get(0).getQuestion();
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
			return Double.toString(diruKop);
		default:
			throw new IndexOutOfBoundsException(columnIndex);
		}
	}

}
