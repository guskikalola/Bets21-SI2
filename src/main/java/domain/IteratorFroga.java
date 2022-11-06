package domain;

import java.awt.Color;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.UIManager;

import businessLogic.BLFacade;
import businessLogic.BusinessFactory;
import configuration.ConfigXML;
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

		

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			BLFacade appFacadeInterface = null;
			appFacadeInterface = bf.createBusiness(c);

			
			date = (Date) sdf.parse("15/11/2022");
			ExtendedIterator<Event> i = appFacadeInterface.getEvents(date);

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