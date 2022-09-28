import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
import exceptions.MezuaEzDaZuzena;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class ApustuaEzabatuDABTest {
	static Apustua ap1, ap2, ap3;
	static Erabiltzailea e1, e2, e3;
	static Kuota k1, k2, k3;
	static Event ev1;
	static Question q1, q2;

	// sut:system under test
	static DataAccess dbManager;

	// additional operations needed to execute the test
	static TestDataAccess testDA;

	@BeforeClass
	public static void initializeDB() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();

		testDA = new TestDataAccess();

		// Erabiltzaileak sortu
		e1 = (Erabiltzailea) dbManager.erregistratu("e1", "a", new Date());
		e2 = (Erabiltzailea) dbManager.erregistratu("e2", "a", new Date());
		e3 = (Erabiltzailea) dbManager.erregistratu("e3", "a", new Date());

		dbManager.diruaSartu(e1, "a", 22.0);
		dbManager.diruaSartu(e3, "a", 143.0);

		// Gertaera eskuratu
		ev1 = testDA.addEventWithQuestion("test", UtilDate.newDate(2023, 5, 17), "test", 1);
		testDA.close();

		// Galdera sortu
		try {
			q1 = dbManager.createQuestion(ev1, "probak", 0);
			q2 = dbManager.createQuestion(ev1, "probak2", 0);
		} catch (QuestionAlreadyExist e4) {
			e4.printStackTrace();
		}

		// Kuotak sortu
		k1 = dbManager.ipiniKuota(q1, "a", 1);
		k2 = dbManager.ipiniKuota(q2, "a", 1);
		k3 = dbManager.ipiniKuota(q2, "c", 1);

		try {
			// Apustuak sortu
			ap1 = dbManager.apustuaEgin(e1, k1, 10.0);
			ap2 = dbManager.apustuaEgin(e1, k2, 12.0);
			ap3 = dbManager.apustuaEgin(e3, k1, 143.0);
		} catch (ApustuaEzDaEgin e) {
			e.printStackTrace();
		}

		// e3 blokeatu behin apustua eginda. Bestela apustua ez da egiten eta hori ez da
		// nahi dugun egoera
		Admin admin = (Admin) dbManager.getErabiltzailea("admin");
		try {
			dbManager.erabiltzaileaBlokeatu(admin, e3, "probak");
		} catch (MezuaEzDaZuzena e5) {
			e5.printStackTrace();
		}

		// Emaitza ipini apustua ezin ezabatzeko
		try {
			dbManager.emaitzaIpini(q2, k3);
		} catch (EmaitzaEzinIpini e4) {
			e4.printStackTrace();
		}
		
		dbManager.close();
	}

	@AfterClass
	public static void ezabatu() {
		// Erabiltzaileak ezabatu
		testDA.open();
		testDA.removePertsona("e1");
		testDA.removePertsona("e2");
		testDA.removePertsona("e3");

		// Gertaera ezabatu
		testDA.removeEvent(ev1);

		testDA.close();
	}

	@Before
	public void open() {
		dbManager.open(false);
		e1 = (Erabiltzailea) dbManager.getErabiltzailea("e1");
		e2 = (Erabiltzailea) dbManager.getErabiltzailea("e2");
		e3 = (Erabiltzailea) dbManager.getErabiltzailea("e3");
	}

	@After
	public void close() {
		dbManager.close();
	}

	@Test
	public void apustuaEzabatuTest1() {
		boolean expected = true;
		boolean obtained = dbManager.apustuaEzabatu(ap1, e1);

		assertEquals(expected, obtained);

		// Hasierako egoerara bueltatu ( Apustua berriro sortu )
		try {
			ap1 = dbManager.apustuaEgin(e1, k1, 10.0);
			testDA.open();
			boolean ezabatutak = testDA.mugimenduGuztiakEzabatu(e1);
			testDA.close();
			if (!ezabatutak)
				fail("Ezin izan dira ezabatu mugimenduak");
		} catch (ApustuaEzDaEgin e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void apustuaEzabatuTest2() {
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(null, e1);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest3() {
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap1, null);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest4() {
		
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap2, e1);
		
		assertEquals(expected, obtained);
		
	}
	
	@Test
	public void apustuaEzabatuTest5() {
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap3, e3);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest6() {
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap1, e2);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest7() {
		Apustua ap4 = new Apustua(e1, 10.0, k1);
		ap4.setApustuZenbakia(12);
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap4, e1);
		
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest8() {
		Erabiltzailea e4 = new Erabiltzailea("e4","a",new Date());
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap1, e4);
		
		assertEquals(expected, obtained);
	}
}
