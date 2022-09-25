import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.*;
import domain.Admin;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Pertsona;
import domain.Question;
import exceptions.ApustuaEzDaEgin;
import exceptions.EmaitzaEzinIpini;
import exceptions.MezuaEzDaZuzena;
import exceptions.QuestionAlreadyExist;

public class ApustuaEzabatuDAWTest {

	static DataAccess dbManager;
	static Apustua ap1, ap2, ap3;
	static Erabiltzailea e1, e2, e3;
	static Kuota k1, k2;
	static Event ev1;
	static Question q1, q2;

	@BeforeClass
	public static void initializeDB() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		
		
		// Erabiltzaileak sortu
		e1 = (Erabiltzailea) dbManager.erregistratu("e1", "a", new Date());
		e2 = (Erabiltzailea) dbManager.erregistratu("e2", "a", new Date());
		e3 = (Erabiltzailea) dbManager.erregistratu("e3", "a", new Date());
		
	
		dbManager.diruaSartu(e1, "a", 22.0);
		dbManager.diruaSartu(e3, "a", 143.0);
		
		// Gertaera eskuratu
		ev1 = dbManager.getEventById(1);
		
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
	
	@Before
	public void open() {
		dbManager.open(false);
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
		boolean ezabatuta = dbManager.emaitzaEzabatu(q2,k1);
		if(!ezabatuta) fail("Ezin izan da emaitza ezabatu");
		
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
		
		// Hasierako egoerara bueltatu
		try {
			ap1 = dbManager.apustuaEgin(e1, k1, 10.0);
		} catch (ApustuaEzDaEgin e) {
			fail(e.getMessage());
		}
	}
	
}
