import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.*;
import domain.Admin;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Mugimendua;
import domain.Pertsona;
import domain.Question;
import exceptions.ApustuaEzDaEgin;
import exceptions.EmaitzaEzinIpini;
import exceptions.MezuaEzDaZuzena;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class ApustuaEzabatuDAWTest {

	static Apustua ap1, ap2, ap3;
	static Erabiltzailea e1, e2, e3;
	static Kuota k1, k2;
	static Event ev1;
	static Question q1, q2;
	
	 //sut:system under test
	 static DataAccess dbManager;
	 
	 //additional operations needed to execute the test 
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
		
		testDA=new TestDataAccess();
		
		// Erabiltzaileak sortu
		e1 = (Erabiltzailea) dbManager.erregistratu("e1", "a", new Date());
		e2 = (Erabiltzailea) dbManager.erregistratu("e2", "a", new Date());
		e3 = (Erabiltzailea) dbManager.erregistratu("e3", "a", new Date());
		
	
		dbManager.diruaSartu(e1, "a", 22.0);
		dbManager.diruaSartu(e3, "a", 143.0);
		
		// Gertaera eskuratu
		ev1 = testDA.addEventWithQuestion("test",  UtilDate.newDate(2023, 5, 17), "test", 1);
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
		
		try {
			// Apustuak sortu
			ap1 = dbManager.apustuaEgin(e1, k1, 10.0);
			ap2 = dbManager.apustuaEgin(e1, k2, 12.0);
			ap3 = dbManager.apustuaEgin(e3, k1, 143.0);
		} catch (ApustuaEzDaEgin e) {
			e.printStackTrace();
		}
		
		// e3 blokeatu behin apustua eginda. Bestela apustua ez da egiten eta hori ez da nahi dugun egoera
		Admin admin = (Admin) dbManager.getErabiltzailea("admin");
		try {
			dbManager.erabiltzaileaBlokeatu(admin, e3, "probak");
		} catch (MezuaEzDaZuzena e5) {
			e5.printStackTrace();
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
		Apustua ap4 = new Apustua(e1, 1, k1);
		ap4.setApustuZenbakia(4);
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap4, e1);
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest2() {
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap1, e2);
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest3() {
		// Emaitza ipini apustua ezin ezabatzeko
		try {
			dbManager.emaitzaIpini(q2, k1);
		} catch (EmaitzaEzinIpini e4) {
			e4.printStackTrace();
		}
		
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap2, e1);
		
		// Emaitza ezabatu aurreko egoerara bueltatzeko
		testDA.open();
		boolean ezabatuta = testDA.emaitzaEzabatu(q2,k1);
		if(!ezabatuta) fail("Ezin izan da emaitza ezabatu");
		testDA.close();
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest4() {
		boolean expected = false;
		boolean obtained = dbManager.apustuaEzabatu(ap3, e3);
		assertEquals(expected, obtained);
	}
	
	@Test
	public void apustuaEzabatuTest5() {
		boolean expected = true;
		boolean obtained = dbManager.apustuaEzabatu(ap1, e1);
		assertEquals(expected, obtained);	
		
		Erabiltzailea eDB = (Erabiltzailea)dbManager.getErabiltzailea("e1");
		
		Mugimendua m = eDB.getMugimenduak().get(eDB.getMugimenduak().size() - 1);
		
		String expectedMessage = "apustua_ezabatuta";
		String obtainedMessage = m.getArrazoia();
		
		assertEquals(expectedMessage, obtainedMessage);
		
		// Hasierako egoerara bueltatu
		try {
			ap1 = dbManager.apustuaEgin(e1, k1, 10.0);
			testDA.open();
			boolean ezabatutak = testDA.mugimenduGuztiakEzabatu(e1);
			testDA.close();
			assertTrue(ezabatutak);
			System.out.println("Size:"+e1.getMugimenduak().size());
		} catch (ApustuaEzDaEgin e) {
			fail(e.getMessage());
		}
	}
	
}
