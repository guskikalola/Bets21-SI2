import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
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
import gui.ErabiltzaileaBlokeatuGUI;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DiruaSartuMockINTTest {

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	// Event mockedEvent = Mockito.mock(Event.class);

	@InjectMocks
	BLFacade sut = new BLFacadeImplementation(dataAccess);

	static Erabiltzailea er1, er2, er3, er4;

	// sut.createQuestion: The event has one question with a queryText.

	@BeforeClass
	public static void initialize() {

		er1 = Mockito.mock(Erabiltzailea.class);
		er2 = Mockito.mock(Erabiltzailea.class);
		er3 = Mockito.mock(Erabiltzailea.class);
		er4 = Mockito.mock(Erabiltzailea.class);

	}

	@Test
	public void diruaSartuTest1() {

		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<String> pasahitzaCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Double> kantitateaCaptor = ArgumentCaptor.forClass(Double.class);

		Mockito.doReturn(true).when(dataAccess).diruaSartu(er1, "1234", 1.0);

		boolean expected = true;
		boolean actual = sut.diruaSartu(er1, "1234", 1.0);

		Mockito.verify(dataAccess, Mockito.times(1)).diruaSartu(erabiltzaileaCaptor.capture(),
				pasahitzaCaptor.capture(), kantitateaCaptor.capture());

		// emitza zuena izan dela frogatu
		assertEquals(expected, actual);

		// metodoari deia paramaetro egokiekin egin zaiela ziurtatu
		assertEquals(er1, erabiltzaileaCaptor.getValue());
		assertEquals("1234", pasahitzaCaptor.getValue());
		assertEquals(1.0, kantitateaCaptor.getValue(), 0);

	}

	@Test
	public void diruaSartuTest2() {

		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<String> pasahitzaCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Double> kantitateaCaptor = ArgumentCaptor.forClass(Double.class);

		Mockito.doReturn(false).when(dataAccess).diruaSartu(er2, "1234", 0.0);

		boolean expected = false;
		boolean actual = sut.diruaSartu(er2, "1234", 0.0);

		Mockito.verify(dataAccess, Mockito.times(1)).diruaSartu(erabiltzaileaCaptor.capture(),
				pasahitzaCaptor.capture(), kantitateaCaptor.capture());

		// emitza zuena izan dela frogatu
		assertEquals(expected, actual);

		// metodoari deia paramaetro egokiekin egin zaiela ziurtatu
		assertEquals(er2, erabiltzaileaCaptor.getValue());
		assertEquals("1234", pasahitzaCaptor.getValue());
		assertEquals(0.0, kantitateaCaptor.getValue(), 0);

	}

	@Test
	public void diruaSartuTest3() {

		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<String> pasahitzaCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Double> kantitateaCaptor = ArgumentCaptor.forClass(Double.class);

		Mockito.doReturn(false).when(dataAccess).diruaSartu(er3, "1234", -1.0);

		boolean expected = false;
		boolean actual = sut.diruaSartu(er3, "1234", -1.0);

		Mockito.verify(dataAccess, Mockito.times(1)).diruaSartu(erabiltzaileaCaptor.capture(),
				pasahitzaCaptor.capture(), kantitateaCaptor.capture());

		// emitza zuena izan dela frogatu
		assertEquals(expected, actual);

		// metodoari deia paramaetro egokiekin egin zaiela ziurtatu
		assertEquals(er3, erabiltzaileaCaptor.getValue());
		assertEquals("1234", pasahitzaCaptor.getValue());
		assertEquals(-1.0, kantitateaCaptor.getValue(), 0);

	}

	@Test
	public void diruaSartuTest4() {

		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<String> pasahitzaCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Double> kantitateaCaptor = ArgumentCaptor.forClass(Double.class);

		Mockito.doReturn(false).when(dataAccess).diruaSartu(null, "1234", 1.0);

		boolean expected = false;
		boolean actual = sut.diruaSartu(null, "1234", 1.0);

		Mockito.verify(dataAccess, Mockito.times(1)).diruaSartu(erabiltzaileaCaptor.capture(),
				pasahitzaCaptor.capture(), kantitateaCaptor.capture());

		// emitza zuena izan dela frogatu
		assertEquals(expected, actual);

		// metodoari deia paramaetro egokiekin egin zaiela ziurtatu
		assertEquals(null, erabiltzaileaCaptor.getValue());
		assertEquals("1234", pasahitzaCaptor.getValue());
		assertEquals(1.0, kantitateaCaptor.getValue(), 0);

	}

	@Test
	public void diruaSartuTest5() {
		
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<String> pasahitzaCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Double> kantitateaCaptor = ArgumentCaptor.forClass(Double.class);

		Mockito.doReturn(false).when(dataAccess).diruaSartu(er3, null, 1.0);

		boolean expected = false;
		boolean actual = sut.diruaSartu(er3, null, 1.0);

		Mockito.verify(dataAccess, Mockito.times(1)).diruaSartu(erabiltzaileaCaptor.capture(),
				pasahitzaCaptor.capture(), kantitateaCaptor.capture());

		// emitza zuena izan dela frogatu
		assertEquals(expected, actual);

		// metodoari deia paramaetro egokiekin egin zaiela ziurtatu
		assertEquals(er3, erabiltzaileaCaptor.getValue());
		assertEquals(null, pasahitzaCaptor.getValue());
		assertEquals(1.0, kantitateaCaptor.getValue(), 0);
	}

	@Test
	public void diruaSartuTest6() {

		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<String> pasahitzaCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Double> kantitateaCaptor = ArgumentCaptor.forClass(Double.class);

		Mockito.doReturn(false).when(dataAccess).diruaSartu(er4, "1234", 1.0);

		boolean expected = false;
		boolean actual = sut.diruaSartu(er4, "1234", 1.0);

		Mockito.verify(dataAccess, Mockito.times(1)).diruaSartu(erabiltzaileaCaptor.capture(),
				pasahitzaCaptor.capture(), kantitateaCaptor.capture());

		// emitza zuena izan dela frogatu
		assertEquals(expected, actual);

		// metodoari deia paramaetro egokiekin egin zaiela ziurtatu
		assertEquals(er4, erabiltzaileaCaptor.getValue());
		assertEquals("1234", pasahitzaCaptor.getValue());
		assertEquals(1.0, kantitateaCaptor.getValue(), 0);

	}

}
