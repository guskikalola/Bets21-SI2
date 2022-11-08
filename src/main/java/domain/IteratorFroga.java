package domain;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.swing.UIManager;

import businessLogic.BLFacade;
import businessLogic.BusinessFactory;
import configuration.ConfigXML;
import configuration.UtilDate;
import gui.MainGUI;

public class IteratorFroga {

	public static void main(String[] args) throws Exception {
		boolean isLocal = true;
	
		ConfigXML c = ConfigXML.getInstance();
		Locale.setDefault(new Locale(c.getLocale()));

		BusinessFactory bf = new BusinessFactory();
		BLFacade blFacade = bf.createBusiness(c);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;

	
		try {

			BLFacade appFacadeInterface = null;
			appFacadeInterface = bf.createBusiness(c);

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}
		
			System.out.println("Datuak sartzen... ");
			System.out.println("Sortutako event: "+	appFacadeInterface.sortuGertaera(UtilDate.newDate(year, month, 05),  "Atlético-Athletic").toString());
			System.out.println("Sortutako event: "+	appFacadeInterface.sortuGertaera(UtilDate.newDate(year, month, 05),  "Eibar-Barcelona").toString());
			System.out.println("Sortutako event: "+	appFacadeInterface.sortuGertaera(UtilDate.newDate(year, month, 05),  "Getafe-Celta").toString());
		
			date = (Date) sdf.parse("05/12/2022");
			ExtendedIterator<Event> i = appFacadeInterface.getEvents(date);

			domain.Event ev;
						
			System.out.println("Atzetik aurrera: ");
			i.goLast();
			while (i.hasPrevious()) {
				ev = i.previous();
				System.out.println(ev.toString());
			}
			
			System.out.println("Aurretik atzera: ");
			// Nahiz eta suposatu hasierara ailegatu garela, eragiketa egiten dugu.
			i.goFirst();
			while (i.hasNext()) {
				ev = (domain.Event) i.next();
				System.out.println(ev.toString());

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}