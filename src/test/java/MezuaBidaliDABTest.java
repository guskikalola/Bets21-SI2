import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Mezua;
import exceptions.MezuaEzDaZuzena;
import test.dataAccess.TestDataAccess;

public class MezuaBidaliDABTest {

	static DataAccess dbManager;
	static Erabiltzailea e1;
	static Erabiltzailea e2;
	static Admin a1;
	static TestDataAccess testDA;
	
	@BeforeClass
	public static void initializeDB() {
		dbManager = new DataAccess();
		testDA = new TestDataAccess();
		
		e1 = (Erabiltzailea)dbManager.erregistratu("e1", "a", new Date());
		e2 = (Erabiltzailea)dbManager.erregistratu("e2", "a", new Date());
		a1 = testDA.createAdmin("admin1","pass",new Date());
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
		a1 = (Admin)dbManager.getErabiltzailea("admin1");
	}
	
	@After
	public void close() {
		dbManager.close();
	}
	
	@AfterClass
	public static void ezabatu() {
		// Erabiltzaileak ezabatu
		testDA.open();
		
		testDA.removePertsona("e1");
		testDA.removePertsona("e2");
		testDA.removePertsona("admin1");

		testDA.close();
	}
	
	@Test
	public void test1() {
		Mezua m = null;
		try {
			m = dbManager.mezuaBidali(e1, a1, "kaixo");
			assertEquals(1, a1.jasotakoMezuakEskuratu(e1).size());
			assertEquals(1, e1.getBidalitakoMezuak().size());
			Mezua eskuratuta = a1.jasotakoMezuakEskuratu(e1).get(0);
			assertEquals("kaixo", eskuratuta.getMezua());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}finally {
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
	public void test2() {
		Mezua obtained = null;
		try {
			obtained = dbManager.mezuaBidali(null, a1, "kaixo");
			fail();
		} catch (MezuaEzDaZuzena e) {
			assertEquals(null, obtained);
		}
	}

	@Test
	public void test3() {
		Mezua obtained = null;
		try {
			obtained = dbManager.mezuaBidali(e1, null, "kaixo");
			fail();
		} catch (MezuaEzDaZuzena e) {
			assertEquals(null, obtained);
		}
	}
	
	@Test
	public void test4() {
		Mezua obtained = null;
		try {
			obtained = dbManager.mezuaBidali(e1, a1, null);
			fail();
		} catch (MezuaEzDaZuzena e) {
			assertEquals(null, obtained);
		}
	}
	
	@Test
	public void test5() {
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
	public void test6() {
		try {
			dbManager.mezuaBidali(e1, a1, "kaixooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
			fail();
		} catch (MezuaEzDaZuzena e) {
			String expected = "Long_message";
			String obtained = e.getMessage();
			assertEquals(expected, obtained);
		}
	}
	
	@Test
	public void test7() {
		Erabiltzailea e3 = new Erabiltzailea("e3", "a", new Date());
		Mezua obtained = null;
		try {
			obtained = dbManager.mezuaBidali(e3, a1, "kaixo");
			fail();
		} catch (MezuaEzDaZuzena e) {
			assertEquals(null, obtained);
		}

	}
	
	@Test
	public void test8() {
		Admin a2 = new Admin("e3", "a", new Date());
		Mezua obtained = null;
		try {
			obtained = dbManager.mezuaBidali(e1, a2, "kaixo");
			fail();
		} catch (MezuaEzDaZuzena e) {
			assertEquals(null, obtained);
		}
	}
	
	@Test
	public void test9() {
		Mezua m = null;
		try {
			m = dbManager.mezuaBidali(e2, e1, "kaixo");
			assertEquals(0, e1.jasotakoMezuakEskuratu(e2).size());
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
	public void test10() {
		Mezua m = null;
		try {
			m = dbManager.mezuaBidali(e2, a1, "kaixo");
			assertEquals(1, a1.jasotakoMezuakEskuratu(e2).size());
			assertEquals(1, e2.getBidalitakoMezuak().size());
			Mezua eskuratuta = a1.jasotakoMezuakEskuratu(e2).get(0);
			assertEquals("kaixo", eskuratuta.getMezua());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}finally {
			// Ezabatu mezua
			if ( m != null ) {
				testDA.open();
				boolean ezabatuta = testDA.mezuaEzabatu(e2,m.getMezuaZenbakia());
				testDA.close();
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}
}
