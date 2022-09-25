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

	static Erabiltzailea er1;

	static Erabiltzailea er2;

	static Erabiltzailea er3;

	static Erabiltzailea er4;
	static Admin ad1;
	static Blokeoa bl1;

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
		
				// Erabiltzaile honek ez erregitratua egon behar 
				er1 = new  Erabiltzailea();
				er1.setIzena("erab1");
			
				er2 = (Erabiltzailea) dbManager.erregistratu("erab2", "1234", new Date(2000, 01, 01));
				er3 = (Erabiltzailea) dbManager.erregistratu("erab3", "1234", new Date(2000, 01, 01));
				er4 = (Erabiltzailea) dbManager.erregistratu("erab4", "1234", new Date(2000, 01, 01));
			
				Admin ad1 = new Admin("admin2", "pass", new Date());

				try {
					dbManager.erabiltzaileaBlokeatu(ad1, er3, null);
				} catch (MezuaEzDaZuzena e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	public void diruaSarrtuTest1() {
		
		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er1, "1234", 2.0);
		assertEquals(expected, actual);
	
		
	}

	@Test
	public void diruaSartuTest2() {
		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er2, "12345", 2.0);
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
		
		boolean expected = true;
		boolean actual = dbManager.diruaSartu(er4, "1234", 22.0);

		assertEquals(expected, actual);
	
	}

}
