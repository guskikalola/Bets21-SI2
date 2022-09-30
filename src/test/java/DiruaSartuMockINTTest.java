import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Erabiltzailea;
import domain.Event;
import domain.Mugimendua;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DiruaSartuMockINTTest {
	
     DataAccess dataAccess = Mockito.mock(DataAccess.class);
     Event mockedEvent = Mockito.mock(Event.class);
	
	@InjectMocks
	 BLFacade sut = new BLFacadeImplementation(dataAccess);
	
	//sut.createQuestion:  The event has one question with a queryText. 

	
	@Test
	public void diruaSartuTest1() {

		Erabiltzaile er1 = new Erabiltzaile("erab1","1234",  new Date(2000, 01, 01) );

		
		
		Mockito.doReturn(true).when(sut).diruaSartu(er1, "1234", 1.0);
		
		
		
		// metodoaren irteera zuzena
		boolean expected = true;
		boolean actual = sut.diruaSartu(er1, "1234", 1.0);
		assertEquals(expected, actual);

		er1 = (Erabiltzailea) sut.getErabiltzailea("erab1");

		// Dirua modu egokian sartu zaio erabiltzaileari
		Double expectedSaldoa = 1.0;
		Double actualSaldoa = er1.getSaldoa();

		assertEquals(expectedSaldoa, actualSaldoa, 0);

		// mugimendua sortu dela eta ondo dagoela ziurtatu
		String expectedMezua = "dirua_sartu";
		Mugimendua m1 = er1.getMugimenduak().get(er1.getMugimenduak().size() - 1);

		// mugimendua zuzena dela ziurtatu
		assertTrue(m1.getErabiltzailea().getIzena().equals(er1.getIzena()));
		assertTrue(m1.getArrazoia().equals(expectedMezua));
		assertTrue(m1.getKantitatea() == 1.0);

		try {
			// Hasierako egoera utzi

			expected = testBL.mugimenduGuztiakEzabatu(er1);
			assertTrue(expected);
			//TODO setSaldoa konpondu
			er1.setSaldoa(0.0);
			assertTrue(sut.getErabiltzailea("erab1").getSaldoa() == 0.0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	@Test
	//sut.createQuestion:  The event has NOT a question with a queryText.
	public void test1() {
			try { 
				//define paramaters
				String queryText="proba galdera";
				Float betMinimum=new Float(2);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("05/10/2022");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				//configure Mock
				Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
				Mockito.doReturn(new Question(queryText, betMinimum,mockedEvent)).when(dataAccess).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));

				

				//invoke System Under Test (sut) 
				Question q=sut.createQuestion(mockedEvent, queryText, betMinimum);
				
				//verify the results
				//Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));
				
				
				ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
				ArgumentCaptor<String> questionStringCaptor = ArgumentCaptor.forClass(String.class);
				ArgumentCaptor<Float> betMinimunCaptor = ArgumentCaptor.forClass(Float.class);
				
				Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(eventCaptor.capture(),questionStringCaptor.capture(), betMinimunCaptor.capture());
				Float f=betMinimunCaptor.getValue();

				assertEquals(eventCaptor.getValue(),mockedEvent);
				assertEquals(questionStringCaptor.getValue(),queryText);
				assertEquals(betMinimunCaptor.getValue(),betMinimum);

			   } catch (QuestionAlreadyExist e) {
				// TODO Auto-generated catch block
				assertTrue(true);
				} catch (EventFinished e) {
				    fail();
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
	@Test
	//sut.createQuestion:  The event is null.
	public void test3() {
		try {
			//define paramaters
			String queryText="proba galdera";
			Float betMinimum=new Float(2);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.doReturn(null).when(dataAccess).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));

			

			//invoke System Under Test (sut) 
			Question q=sut.createQuestion(null, queryText, betMinimum);
			
			//verify the results
			Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));
			
			

			assertTrue(q==null);
			

		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			fail();
			} catch (EventFinished e) {
			    fail();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	@Test
	public void test7() {
		try {
			//define paramaters
			String queryText="proba galdera";
			Float betMinimum=new Float(2);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class))).thenThrow(QuestionAlreadyExist.class);
			

			//invoke System Under Test (sut) 
			sut.createQuestion(mockedEvent, queryText, betMinimum);
			
			//if the program continues fail
		    fail();
		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			   
			// if the program goes to this point OK
			assertTrue(true);
			} catch (EventFinished e) {
				// if the program goes to this point fail
			    fail();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	
	
	
	
		
}
