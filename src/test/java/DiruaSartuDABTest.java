import static org.junit.Assert.*;

import org.junit.Test;

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
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Mugimendua;
import domain.Pertsona;
import exceptions.MezuaEzDaZuzena;
import test.dataAccess.TestDataAccess;

public class DiruaSartuDABTest {

	static Erabiltzailea er1, er2, er3, er4;
	static Admin ad1;

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

		// Erabiltzailea sortu
		er1 = (Erabiltzailea) dbManager.erregistratu("erab1", "1234", new Date(2000, 01, 01));
		er2 = (Erabiltzailea) dbManager.erregistratu("erab2", "1234", new Date(2000, 01, 01));
		er3 = (Erabiltzailea) dbManager.erregistratu("erab3", "1234", new Date(2000, 01, 01));
		er4 = (Erabiltzailea) dbManager.erregistratu("erab4", "1234", new Date(2000, 01, 01));

		Admin ad1 = (Admin) dbManager.getErabiltzailea("admin");

		try {
			dbManager.erabiltzaileaBlokeatu(ad1, er4, null);
		} catch (MezuaEzDaZuzena e) {
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

	@AfterClass
	public static void ezabatu() {
		testDA.open();
		assertTrue(testDA.removePertsona("erab1"));
		assertTrue(testDA.removePertsona("erab2"));
		assertTrue(testDA.removePertsona("erab3"));
		assertTrue(testDA.removePertsona("erab4"));
		testDA.close();
	}

	@Test
	public void diruaSarrtuTest1() {

		// metodoaren irteera zuzena
		boolean expected = true;
		boolean actual = dbManager.diruaSartu(er1, "1234", 1.0);
		assertEquals(expected, actual);

		er1 = (Erabiltzailea) dbManager.getErabiltzaileaIzenarekin("erab1");

		// Dirua modu egokian sartu zaio erabiltzaileari
		Double expectedSaldoa = 1.0;
		Double actualSaldoa = er1.getSaldoa();

		assertEquals(expectedSaldoa, actualSaldoa, 0);

		// mugimendua sortu dela eta ondo dagoela ziurtatu
		String expectedMezua = "dirua_sartu";
		Mugimendua m1 = er1.getMugimenduak().get(er1.getMugimenduak().size() - 1);

		// mugimendua zuzena dela ziurtatu
		assertTrue(m1.getErabiltzailea().getIzena().equals(er1.getIzena()));
		assertTrue(m1.getArrazoia().equals(expectedMezua));
		assertTrue(m1.getKantitatea() == 1.0);

		try {
			// Hasierako egoera utzi

			testDA.open();
			expected = testDA.mugimenduGuztiakEzabatu(er1);
			testDA.close();
			assertTrue(expected);

			er1.setSaldoa(0.0);
			assertTrue(dbManager.getErabiltzaileaIzenarekin("erab1").getSaldoa() == 0.0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void diruaSartuTest2() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er2, "1234", 0.0);
		assertEquals(expected, actual);

		// Mugimendua sortu bada ezabatu eta saldoa 0.0-ra itzuli
		if (actual) {
			testDA.open();
			expected = testDA.mugimenduGuztiakEzabatu(er2);
			testDA.close();

			er2.setSaldoa(0.0);

		}

	}

	@Test
	public void diruaSartuTest3() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er3, "1234", -1.0);
		assertEquals(expected, actual);

		er1 = (Erabiltzailea) dbManager.getErabiltzaileaIzenarekin("erab3");
		// Mugimendua sortu bada ezabatu eta saldoa 0.0-ra itzuli
		if (actual) {

			testDA.open();
			expected = testDA.mugimenduGuztiakEzabatu(er3);
			testDA.close();

			er3.setSaldoa(0.0);

		}
	}

	@Test
	public void diruaSartuTest4() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(null, "1234", 1.0);
		assertEquals(expected, actual);
	}

	@Test
	public void diruaSartuTest5() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er4, null, 1.0);
		assertEquals(expected, actual);
	}
	
	@Test
	public void diruaSartuTest6() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er4, "1324", 1.0);
		assertEquals(expected, actual);
	}

}
