import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.*;
import domain.Admin;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Pertsona;
import exceptions.MezuaEzDaZuzena;


public class DiruaSartuDAWTest {

	static DataAccess dbManager;

	Erabiltzailea er1, er2, er3, er4;
	Admin ad1;
	Blokeoa bl1;

	@BeforeClass
	public static void initializeDB() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		dbManager.close();
	}

	@Before
	public void open() {

		dbManager.open(false);
		// Erabiltzaileak sortu
		
		// Erabiltzaile honek ez erregitratua egon behar 
		er1 = new  Erabiltzailea();
		er1.setIzena("erab1");
	
		er2 = (Erabiltzailea) dbManager.erregistratu("erab2", "1234", new Date(2000, 01, 01));
		er3 = (Erabiltzailea) dbManager.erregistratu("erab3", "1234", new Date(2000, 01, 01));
		er4 = (Erabiltzailea) dbManager.erregistratu("erab4", "1234", new Date(2000, 01, 01));

		Admin ad1 = new Admin("admin2", "pass", new Date());

		
		//dbManager.diruaSartu(er2, "1234", 10.0);
		
	/*	Pertsona  erab3= dbManager.getErabiltzailea("erab3");
		try {
			dbManager.erabiltzaileaBlokeatu(ad1, (Erabiltzailea)erab3, null);
		} catch (MezuaEzDaZuzena e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		//		ad1 = new Admin("ad1", "1324", new Date(2000, 01, 01));

		//Pertsona erab3 = 
		
		//bl1 = new Blokeoa(ad1, er3, "lapurreta");
			//	er3.blokeoaGehitu(bl1);
		
	/*	try {
		
		dbManager.erabiltzaileaBlokeatu(ad1, er3, "lapurreta");
		
		} catch(MezuaEzDaZuzena e) {
			
			e.printStackTrace();
			
		}*/
		
		
		/*
		 * ad1.blokeoaGehituListan(bl1);
		 * 
		 * er3.blokeoaGehitu(bl1);
		 */
		/*
		 * dbManager.diruaSartu(erab1, "a", 10.0); dbManager.diruaSartu(erab2, "a",
		 * 12.0); dbManager.diruaSartu(erab3, "a", 143.0);
		 */

		/*
		 * // Gertaera eskuratu Event ev1 = dbManager.getEventById(1);
		 * 
		 * // Galdera sortu try { q1 = dbManager.createQuestion(ev1, "probak", 0); }
		 * catch (QuestionAlreadyExist e4) { e4.printStackTrace(); }
		 */
		// Kuotak sortu
		/*
		 * k1 = dbManager.ipiniKuota(q1, "a", 1);
		 * 
		 * try { // Apustuak sortu ap1 = dbManager.apustuaEgin(e1, k1, 10.0); ap2 =
		 * dbManager.apustuaEgin(e2, k1, 12.0); ap3 = dbManager.apustuaEgin(e3, k1,
		 * 143.0); } catch (ApustuaEzDaEgin e) { e.printStackTrace(); }
		 */
	}

	@After
	public void close() {
		dbManager.close();
	}

	@Test
	public void diruaSarrtuTest1() {
		
		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er1, "1234", 2.0);
		
		assertEquals(expected, actual);
	
		
	}

	@Test
	public void diruaSartuTest2() {
		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er2, "12345", 2.0);
		//System.out.println("emaitza da " + actual);
		assertEquals(expected, actual);

	}

	@Test
	public void diruaSartuTest3() {
		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er3, "1234", 2.0);

		assertEquals(expected, actual);

	}

	@Test
	public void diruaSartuTest4() {
		
		System.out.println(dbManager.getErabiltzailea("erab4").getIzena());

		boolean expected = true;
		boolean actual = dbManager.diruaSartu(er4, "12345", 2.0);

		Double expected2 = 2.0;

		assertEquals(expected, actual);
		Double actual2 = er4.getSaldoa();

		assertEquals(expected2, actual2);

	}

}
