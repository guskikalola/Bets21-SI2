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
import domain.Pertsona;
import exceptions.MezuaEzDaZuzena;
import test.dataAccess.TestDataAccess;
import dataAccess.*;

public class MezuaBidaliDAWTest {

	static DataAccess dbManager;
	static Erabiltzailea e1;
	static Erabiltzailea e2;
	static Admin a1;
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
		e1 = (Erabiltzailea)dbManager.erregistratu("e1", "a", new Date());
		e2 = (Erabiltzailea)dbManager.erregistratu("e2", "a", new Date());
		a1 = (Admin)dbManager.getErabiltzailea("admin");
		testDA.close();

		try {
			dbManager.erabiltzaileaBlokeatu(a1, e2, "aaa");
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
		
		dbManager.close();
	}
	
	@Before
	public void open() {
		dbManager.open(false);
		e1 = (Erabiltzailea)dbManager.getErabiltzailea("e1");
		e2 = (Erabiltzailea)dbManager.getErabiltzailea("e2");
		a1 = (Admin)dbManager.getErabiltzailea("admin");
	}
	
	@After
	public void close() {
		dbManager.close();
	}
	
	@Test
	public void mezuaBidaliTest1() {
		try {
			dbManager.mezuaBidali(e1, a1, "k");
			fail();
		} catch (MezuaEzDaZuzena e) {
			String expected = "Short_message";
			String obtained = e.getMessage();
			assertEquals(expected, obtained);
		}
	}
	
	@Test
	public void mezuaBidaliTest2()  {
		try {
			dbManager.mezuaBidali(e1, a1, "awiofbkalbfklbailbfhoajibflasijbflaisbfailsdubfalibfasilbfasildubfaisubfasoiubfasldjibfsildbfdisdfbdkdj");
			fail();
		} catch (MezuaEzDaZuzena e) {
			String expected = "Long_message";
			String obtained = e.getMessage();
			assertEquals(expected, obtained);
		}
	}
	
	@Test
	public void mezuaBidaliTest3() {
		Mezua m = null;
		try {
			m = dbManager.mezuaBidali(e1, a1, "kaixo");			
			assertEquals(1, a1.jasotakoMezuakEskuratu(e1).size());
			Mezua eskuratuta = a1.jasotakoMezuakEskuratu(e1).get(0);
			assertEquals("kaixo", eskuratuta.getMezua());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		} finally {
			// Ezabatu mezua
			if ( m != null ) {
				testDA.open();
				boolean ezabatuta = testDA.mezuaEzabatu(e1,m.getMezuaZenbakia());
				testDA.close();
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}
	
	@Test
	public void mezuaBidaliTest4() {
		Mezua m = null;

		try {
			m = dbManager.mezuaBidali(e2, a1, "kaixo");
			assertEquals(1, a1.jasotakoMezuakEskuratu(e2).size());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		} finally {
			if ( m != null ) {
				testDA.open();
				boolean ezabatuta = testDA.mezuaEzabatu(e2,m.getMezuaZenbakia());
				testDA.close();
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}
	
	@Test
	public void mezuaBidaliTest5() {
		Mezua m = null;
		try {
			m = dbManager.mezuaBidali(e2, e1, "kaixo");
			assertEquals(0, e2.jasotakoMezuakEskuratu(e1).size());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		} finally {
			if ( m != null ) {
				testDA.open();
				boolean ezabatuta = testDA.mezuaEzabatu(e2,m.getMezuaZenbakia());
				testDA.close();
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}
}
