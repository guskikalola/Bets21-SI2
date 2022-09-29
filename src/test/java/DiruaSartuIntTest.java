import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Mugimendua;
import test.businessLogic.TestFacadeImplementation;

public class DiruaSartuIntTest {

	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;

	static Erabiltzailea er1;
	static Admin ad1;
	static Blokeoa bl1;

	@BeforeClass
	public static void setUpClass() {
		//sut= new BLFacadeImplementation();
		
		// you can parametrize the DataAccess used by BLFacadeImplementation
		//DataAccess da= new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		DataAccess da= new DataAccess();

		sut=new BLFacadeImplementation(da);
		
		testBL= new TestFacadeImplementation();
		
		er1 = (Erabiltzailea) dbManager.erregistratu("erab1", "1234", new Date(2000, 01, 01));

		
	}
		// Erabiltzailea sortu


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
		assertEquals(expectedSaldoa, actualSaldoa, 0);

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
