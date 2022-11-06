package domain;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.UIManager;

import businessLogic.BLFacade;
import businessLogic.BusinessFactory;
import configuration.ConfigXML;
import configuration.UtilDate;
import gui.MainGUI;

public class IteratorFroga {

	public static void main(String[] args) throws Exception {
		boolean isLocal = true;
		// Facade objektua lortu lehendabiziko ariketa erabiliz
		// BLFacade facadeInterface=.......... 

		//BLFacade blFacade = (new BLFactory()).getBusinessLogicFactory(isLocal);
		//BLFacade blFacade = (BLFacade) new BusinessFactory().createBusiness(null);
		
		
		ConfigXML c = ConfigXML.getInstance();
		Locale.setDefault(new Locale(c.getLocale()));

		BusinessFactory bf = new BusinessFactory();
		BLFacade blFacade = bf.createBusiness(c);

	/*	
		
		try {
			BLFacade appFacadeInterface = null;
			appFacadeInterface = bf.createBusiness(c);
	
		} catch (Exception e) {

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		// a.pack();

		
*/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}
			date = (Date) UtilDate.newDate(year, month, 17);
			
			ExtendedIterator<Event> i = blFacade.getEvents(date);

			Event ev;
			i.goLast();
			while (i.hasPrevious()) {
				ev = i.previous();
				System.out.println(ev.toString());
			}
			// Nahiz eta suposatu hasierara ailegatu garela, eragiketa egiten dugu.
			i.goFirst();
			while (i.hasNext()) {
				ev = (Event) i.next();
				System.out.println(ev.toString());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}