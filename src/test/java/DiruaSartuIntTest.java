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
import exceptions.MezuaEzDaZuzena;
import test.businessLogic.TestFacadeImplementation;

public class DiruaSartuIntTest {

	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;

	static Erabiltzailea er1, er2, er3, er4;
	static Admin ad1;

	@BeforeClass
	public static void setUpClass() {

		// you can parametrize the DataAccess used by BLFacadeImplementation
		DataAccess da = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));

		sut = new BLFacadeImplementation(da);
		testBL = new TestFacadeImplementation();

		// Erabiltzailea sortu
		er1 = (Erabiltzailea) sut.erregistratu("erab1", "1234", new Date(2000, 01, 01));
		er2 = (Erabiltzailea) sut.erregistratu("erab2", "1234", new Date(2000, 01, 01));
		er3 = (Erabiltzailea) sut.erregistratu("erab3", "1234", new Date(2000, 01, 01));
		er4 = (Erabiltzailea) sut.erregistratu("erab4", "1234", new Date(2000, 01, 01));

		Admin ad1 = testBL.getAdmin("admin");

		try {
			sut.erabiltzaileaBlokeatu(ad1, er4, null);
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public static void ezabatu() {
		assertTrue(testBL.removePertsona("ereab1"));
		assertTrue(testBL.removePertsona("ereab2"));
		assertTrue(testBL.removePertsona("ereab3"));
		assertTrue(testBL.removePertsona("ereab4"));
	}

	@Test
	public void diruaSarrtuTest1() {

		// metodoaren irteera zuzena
		boolean expected = true;
		boolean actual = sut.diruaSartu(er1, "1234", 1.0);
		assertEquals(expected, actual);

		er1 = (Erabiltzailea) sut.getErabiltzailea("erab1");

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

			expected = testBL.mugimenduGuztiakEzabatu(er1);
			assertTrue(expected);
			// TODO setSaldoa konpondu
			er1.setSaldoa(0.0);
			assertTrue(sut.getErabiltzailea("erab1").getSaldoa() == 0.0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void diruaSartuTest2() {

		boolean expected = false;
		boolean actual = sut.diruaSartu(er2, "1234", 0.0);
		assertEquals(expected, actual);

		// Mugimendua sortu bada ezabatu eta saldoa 0.0-ra itzuli
		if (actual) {
			expected = testBL.mugimenduGuztiakEzabatu(er2);

			er2.setSaldoa(0.0);

		}

	}

	@Test
	public void diruaSartuTest3() {

		boolean expected = false;
		boolean actual = sut.diruaSartu(er3, "1234", -1.0);
		assertEquals(expected, actual);

		er1 = (Erabiltzailea) sut.getErabiltzailea("erab3");
		// Mugimendua sortu bada ezabatu eta saldoa 0.0-ra itzuli
		if (actual) {

			expected = testBL.mugimenduGuztiakEzabatu(er3);

			er3.setSaldoa(0.0);

		}
	}

	@Test
	public void diruaSartuTest4() {

		boolean expected = false;
		boolean actual = sut.diruaSartu(null, "1234", 1.0);
		assertEquals(expected, actual);
	}

	@Test
	public void diruaSartuTest5() {

		boolean expected = false;
		boolean actual = sut.diruaSartu(er4, null, 1.0);
		assertEquals(expected, actual);
	}

	@Test
	public void diruaSartuTest6() {

		boolean expected = false;
		boolean actual = sut.diruaSartu(er4, "1324", 1.0);
		assertEquals(expected, actual);
	}

}
