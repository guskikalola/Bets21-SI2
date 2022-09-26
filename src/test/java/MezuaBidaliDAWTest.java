
import static org.junit.Assert.*;

import java.util.Date;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import configuration.ConfigXML;
import domain.Admin;
import domain.Erabiltzailea;
import domain.Mezua;
import exceptions.MezuaEzDaZuzena;
import dataAccess.*;

public class MezuaBidaliDAWTest {

	static DataAccess dbManager;
	static Erabiltzailea p1;
	static Erabiltzailea p2;
	static Admin p3;
	
	@BeforeClass
	public static void initializeDB() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else 
			dbManager = new DataAccess();
		p1 = (Erabiltzailea)dbManager.erregistratu("e1", "a", new Date());
		p2= (Erabiltzailea)dbManager.erregistratu("e2", "a", new Date());
		p3 = (Admin)dbManager.getErabiltzailea("admin");
		dbManager.close();
	}
	
	@Before
	public void open() {
		dbManager.open(false);
		p1 = (Erabiltzailea)dbManager.getErabiltzailea("e1");
		p2 = (Erabiltzailea)dbManager.getErabiltzailea("e2");
		p3 = (Admin)dbManager.getErabiltzailea("admin");
	}
	
	@After
	public void close() {
		dbManager.close();
	}
	
	@Test
	public void mezuaBidaliTest() {
		Mezua m = null;
		try {
			m = dbManager.mezuaBidali(p1, p2, "kaixo");			
			assertEquals(1, p2.jasotakoMezuakEskuratu(p1).size());
			Mezua eskuratuta = p2.jasotakoMezuakEskuratu(p1).get(0);
			assertEquals("kaixo", eskuratuta.getMezua());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		} finally {
			// Ezabatu mezua
			if ( m != null ) {
				boolean ezabatuta = dbManager.mezuaEzabatu(p1,m.getMezuaZenbakia());
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}

	@Test
	public void mezuaBidaliTest2() {
		try {
			dbManager.mezuaBidali(p1, p2, "k");
			fail();
		} catch (MezuaEzDaZuzena e) {
			String expected = "Short_message";
			String obtained = e.getMessage();
			assertEquals(expected, obtained);
		}
	}
	
	@Test
	public void mezuaBidaliTest3()  {

		try {
			dbManager.mezuaBidali(p1, p2, "awiofbkalbfklbailbfhoajibflasijbflaisbfailsdubfalibfasilbfasildubfaisubfasoiubfasldjibfsildbfdisdfbdkdj");
			fail();
		} catch (MezuaEzDaZuzena e) {
			String expected = "Long_message";
			String obtained = e.getMessage();
			assertEquals(expected, obtained);
		}
	}
	
	@Test
	public void mezuaBidaliTest4() {
		Mezua m = null;
		try {
			dbManager.erabiltzaileaBlokeatu(p3, p1, "aaa");
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
		try {
			m = dbManager.mezuaBidali(p1, p3, "kaixo");
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
		assertEquals(1, p3.jasotakoMezuakEskuratu(p1).size());
		
		// Ezabatu mezua
		if ( m != null ) {
			boolean ezabatuta = dbManager.mezuaEzabatu(p1,m.getMezuaZenbakia());
			if(!ezabatuta) fail("ezin izan da ezabatu");
		}
	}
	
	@Test
	public void mezuaBidaliTest5() {
		try {
			dbManager.erabiltzaileaBlokeatu(p3, p1, "aaa");
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
		try {
			dbManager.mezuaBidali(p1, p2, "kaixo");
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
		assertEquals(0, p2.jasotakoMezuakEskuratu(p1).size());
	}
}
