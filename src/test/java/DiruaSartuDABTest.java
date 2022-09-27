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

	static Erabiltzailea er1;
	static Admin ad1;
	static Blokeoa bl1;

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

		// Erabiltzailea sortu

		er1 = (Erabiltzailea) dbManager.erregistratu("erab1", "1234", new Date(2000, 01, 01));		
		
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
		testDA.close();
				
	}

	@Test
	public void diruaSarrtuTest1() {
		
		// metodoaren irteera zuzena
		boolean expected = true;
		boolean actual = dbManager.diruaSartu(er1, "1234", 2.0);
		assertEquals(expected, actual);
		
		// Dirua modu egokian sartu zaio erabiltzaileari
		Double expectedSaldoa = 2.0;
		Double actualSaldoa = dbManager.getErabiltzaileaIzenarekin("erab1").getSaldoa();
		assertEquals(expectedSaldoa, actualSaldoa,0);
		
		String expectedMezua = "dirua_sartu";
		String actualMezua;
		
		er1 = (Erabiltzailea) dbManager.getErabiltzailea("erab1");

		Mugimendua m1 = er1.getMugimenduak().get(er1.getMugimenduak().size() - 1);
		actualMezua = m1.getArrazoia();
		
		assertTrue(expectedMezua.equals(actualMezua));
		

	}

	@Test
	public void diruaSartuTest11() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er1, "12345", 0.0);
		assertEquals(expected, actual);
	}

	@Test
	public void diruaSartuTest12() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er1, "12345", -1.0);
		assertEquals(expected, actual);
	}

	@Test
	public void diruaSartuTest2() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(null, "12345", 2.0);
		assertEquals(expected, actual);
	}

	@Test
	public void diruaSartuTest3() {

		boolean expected = false;
		boolean actual = dbManager.diruaSartu(er1, null, 2.0);
		assertEquals(expected, actual);
	}

}
