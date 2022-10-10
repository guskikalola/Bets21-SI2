import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Mezua;
import exceptions.ApustuaEzDaEgin;
import exceptions.EmaitzaEzinIpini;
import exceptions.MezuaEzDaZuzena;
import test.businessLogic.TestFacadeImplementation;

public class MezuaBidaliIntTest {
	
	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;
	
	static Erabiltzailea e1, e2;
	static Admin a1;
	
	@BeforeClass
	public static void initialize() {
		DataAccess da = new DataAccess();

		sut = new BLFacadeImplementation(da);

		testBL = new TestFacadeImplementation();

		// Erabiltzaileak sortu
		e1 = (Erabiltzailea) sut.erregistratu("e1", "a", new Date());
		e2 = (Erabiltzailea) sut.erregistratu("e2", "a", new Date());
		a1 = (Admin) testBL.createAdmin("admin1","pass",new Date());
		
		// Blokeoa sortu
		try {
			sut.erabiltzaileaBlokeatu(a1, e2, "aaa");
		} catch (MezuaEzDaZuzena e3) {
			fail(e3.getMessage());
		}

	}
	
	@AfterClass
	public static void ezabatu() {
		
		testBL.removePertsona("e1");
		testBL.removePertsona("e2");
		testBL.removePertsona("admin1");

	}
	
	@Before
	public void open() {
		e1 = (Erabiltzailea) sut.getErabiltzailea("e1");
		e2 = (Erabiltzailea) sut.getErabiltzailea("e2");
		a1 = (Admin)testBL.getAdmin("admin1");
	}
	
	@After
	public void close() {
		sut.close();
	}
	
	@Test
	public void test1() {
		Mezua m = null;
		Erabiltzailea eDB = null;
		Admin aDB = null;
		try {
			m = sut.mezuaBidali(e1, a1, "kaixo");
			eDB = (Erabiltzailea) sut.getErabiltzailea("e1");
			aDB = (Admin)testBL.getAdmin("admin1");
			assertEquals(1, aDB.jasotakoMezuakEskuratu(eDB).size());
			assertEquals(1, eDB.getBidalitakoMezuak().size());
			Mezua eskuratuta = aDB.jasotakoMezuakEskuratu(eDB).get(0);
			assertEquals("kaixo", eskuratuta.getMezua());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}finally {
			// Ezabatu mezua
			if ( m != null ) {
				boolean ezabatuta = testBL.mezuaEzabatu(eDB, m.getMezuaZenbakia());
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}
	
	@Test
	public void test2() {
		Mezua obtained = null;
		try {
			obtained = sut.mezuaBidali(null, a1, "kaixo");
			assertEquals(null, obtained);
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test3() {
		Mezua obtained = null;
		try {
			obtained = sut.mezuaBidali(e1, null, "kaixo");
			assertEquals(null, obtained);
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		Mezua obtained = null;
		try {
			obtained = sut.mezuaBidali(e1, a1, null);
			assertEquals(null, obtained);
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test5() {
		try {
			sut.mezuaBidali(e1, a1, "k");
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
			sut.mezuaBidali(e1, a1, "kaixooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
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
			obtained = sut.mezuaBidali(e3, a1, "kaixo");
			assertEquals(null, obtained);
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	public void test8() {
		Admin a2 = new Admin("e3", "a", new Date());
		Mezua obtained = null;
		try {
			obtained = sut.mezuaBidali(e1, a2, "kaixo");
			assertEquals(null, obtained);
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test9() {
		Mezua m = null;
		Erabiltzailea e1DB = null;
		Erabiltzailea e2DB = null;
		try {
			m = sut.mezuaBidali(e2, e1, "kaixo");
			e1DB = (Erabiltzailea) sut.getErabiltzailea("e1");
			e2DB = (Erabiltzailea) sut.getErabiltzailea("e2");
			assertEquals(0, e1DB.jasotakoMezuakEskuratu(e2DB).size());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		} finally {
			if ( m != null ) {
				boolean ezabatuta = testBL.mezuaEzabatu(e2DB,m.getMezuaZenbakia());
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}
	
	@Test
	public void test10() {
		Mezua m = null;
		Erabiltzailea e2DB = null;
		Admin a1DB = null;
		try {
			m = sut.mezuaBidali(e2, a1, "kaixo");
			e2DB = (Erabiltzailea) sut.getErabiltzailea("e2");
			a1DB = testBL.getAdmin("admin1");
			assertEquals(1, a1DB.jasotakoMezuakEskuratu(e2).size());
			assertEquals(1, e2DB.getBidalitakoMezuak().size());
			Mezua eskuratuta = a1DB.jasotakoMezuakEskuratu(e2).get(0);
			assertEquals("kaixo", eskuratuta.getMezua());
		} catch (MezuaEzDaZuzena e) {
			fail(e.getMessage());
		}finally {
			// Ezabatu mezua
			if ( m != null ) {
				boolean ezabatuta = testBL.mezuaEzabatu(e2,m.getMezuaZenbakia());
				if(!ezabatuta) fail("ezin izan da ezabatu");
			}
		}
	}
}
