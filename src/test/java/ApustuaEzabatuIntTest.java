import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Question;
import exceptions.ApustuaEzDaEgin;
import exceptions.EmaitzaEzinIpini;
import exceptions.EventFinished;
import exceptions.MezuaEzDaZuzena;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

public class ApustuaEzabatuIntTest {

	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;

	static Apustua ap1, ap2, ap3;
	static Erabiltzailea e1, e2, e3;
	static Kuota k1, k2, k3;
	static Event ev1;
	static Question q1, q2;

	@BeforeClass
	public static void initialize() {
		// sut= new BLFacadeImplementation();

		// you can parametrize the DataAccess used by BLFacadeImplementation
		DataAccess da = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		// DataAccess da= new DataAccess();

		sut = new BLFacadeImplementation(da);

		testBL = new TestFacadeImplementation();

		// Erabiltzaileak sortu
		e1 = (Erabiltzailea) sut.erregistratu("e1", "a", new Date());
		e2 = (Erabiltzailea) sut.erregistratu("e2", "a", new Date());
		e3 = (Erabiltzailea) sut.erregistratu("e3", "a", new Date());

		sut.diruaSartu(e1, "a", 22.0);
		sut.diruaSartu(e3, "a", 143.0);

		// Gertaera eskuratu
		ev1 = testBL.addEventWithQuestion("test", UtilDate.newDate(2023, 5, 17), "test", 1);

		// Galdera sortu
		try {
			q1 = sut.createQuestion(ev1, "probak", 0);
			q2 = sut.createQuestion(ev1, "probak2", 0);
		} catch (QuestionAlreadyExist e4) {
			e4.printStackTrace();
		} catch (EventFinished e) {
			e.printStackTrace();
		}

		// Kuotak sortu
		k1 = sut.ipiniKuota(q1, "a", 1);
		k2 = sut.ipiniKuota(q2, "a", 1);
		k3 = sut.ipiniKuota(q2, "c", 1);

		try {
			// Apustuak sortu
			ap1 = sut.apustuaEgin(e1, k1, 10.0);
			ap2 = sut.apustuaEgin(e1, k2, 12.0);
			ap3 = sut.apustuaEgin(e3, k1, 143.0);
		} catch (ApustuaEzDaEgin e) {
			e.printStackTrace();
		}

		// e3 blokeatu behin apustua eginda. Bestela apustua ez da egiten eta hori ez da
		// nahi dugun egoera
		Admin admin = (Admin) testBL.getAdmin("admin");
		try {
			sut.erabiltzaileaBlokeatu(admin, e3, "probak");
		} catch (MezuaEzDaZuzena e5) {
			e5.printStackTrace();
		}

		// Emaitza ipini apustua ezin ezabatzeko
		try {
			sut.emaitzaIpini(q2, k3);
		} catch (EmaitzaEzinIpini e4) {
			e4.printStackTrace();
		}

	}
	
	@AfterClass
	public static void ezabatu() {
		// Erabiltzaileak ezabatu
		testBL.removePertsona("e1");
		testBL.removePertsona("e2");
		testBL.removePertsona("e3");

		// Gertaera ezabatu
		testBL.removeEvent(ev1);

	}

	@Before
	public void open() {
		e1 = (Erabiltzailea) sut.getErabiltzailea("e1");
		e2 = (Erabiltzailea) sut.getErabiltzailea("e2");
		e3 = (Erabiltzailea) sut.getErabiltzailea("e3");
	}

	@Test
	public void apustuaEzabatuTest1() {
		boolean expected = true;
		boolean obtained = sut.apustuaEzabatu(ap1, e1);
		
		// Hasierako egoerara bueltatu ( Apustua berriro sortu )
		try {
			ap1 = sut.apustuaEgin(e1, k1, 10.0);
			boolean ezabatutak = testBL.mugimenduGuztiakEzabatu(e1);
			if (!ezabatutak)
				fail("Ezin izan dira ezabatu mugimenduak");
		} catch (ApustuaEzDaEgin e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void apustuaEzabatuTest2() {
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(null, e1);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest3() {
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap1, null);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest4() {
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap2,e1);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest5() {
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap3, e3);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest6() {
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap1, e2);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest7() {
		Apustua ap4 = new Apustua(e1, 10, k1);
		ap4.setApustuZenbakia(10);
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap4, e1);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest8() {
		Erabiltzailea e4 = new Erabiltzailea("test", "a", new Date());
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap1, e4);
		
		assertEquals(expected, obtained);
	}

}
