package domain;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class IteratorFroga {

	public static void main(String[] args) {
		boolean isLocal=true;
		//Facade objektua lortu lehendabiziko ariketa erabiliz
		//BLFacade facadeInterface=..........
	
		BLFacade blFacade = (new BLFactory()).getBusinessLogicFactory(isLocal);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = (Date) sdf.parse("15/11/2022"); 
			ExtendedIterator<Event> i = blFacade.getEvents(date);		
		
			Event ev;
			i.goLast();
			while (i.hasPrevious()){
				ev=i.previous();
				ev.print();
			}
			//Nahiz eta suposatu hasierara ailegatu garela, eragiketa egiten dugu.
			i.goFirst();
			while (i.hasNext())){
				ev=(Event) i.next();
				ev.print();
		}

}
}