package test.businessLogic;

import java.util.Date;
import java.util.Iterator;

import configuration.ConfigXML;
import domain.Admin;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Mezua;
import domain.Pertsona;
import domain.Question;
import test.dataAccess.TestDataAccess;

public class TestFacadeImplementation {
	TestDataAccess dbManagerTest;

	public TestFacadeImplementation() {

		System.out.println("Creating TestFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();
		dbManagerTest = new TestDataAccess();
		dbManagerTest.close();
	}

	public boolean removeEvent(Event ev) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removeEvent(ev);
		dbManagerTest.close();
		return b;

	}

	public Event addEventWithQuestion(String desc, Date d, String q, float qty) {
		dbManagerTest.open();
		Event o = dbManagerTest.addEventWithQuestion(desc, d, q, qty);
		dbManagerTest.close();
		return o;

	}

	public boolean existQuestion(Event ev, Question q) {
		dbManagerTest.open();
		System.out.println(">> DataAccessTest: existQuestion");
		boolean b = dbManagerTest.existQuestion(ev, q);
		dbManagerTest.close();
		return b;

	}

	public boolean emaitzaEzabatu(Question q, Kuota k) {

		dbManagerTest.open();
		boolean b = dbManagerTest.emaitzaEzabatu(q, k);
		dbManagerTest.close();
		return b;
	}

	public Event getEventById(int id) {
		dbManagerTest.open();
		Event ev = dbManagerTest.getEventById(id);
		dbManagerTest.close();
		return ev;
	}

	public boolean mezuaEzabatu(Pertsona p2, int mezuaZenbakia) {
		dbManagerTest.open();
		boolean b = dbManagerTest.mezuaEzabatu(p2, mezuaZenbakia);
		dbManagerTest.close();
		return b;
	}

	public boolean mugimenduGuztiakEzabatu(Erabiltzailea e1) {

		dbManagerTest.open();
		boolean b = dbManagerTest.mugimenduGuztiakEzabatu(e1);
		dbManagerTest.close();
		return b;

	}

	public boolean removePertsona(String izena) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removePertsona(izena);
		dbManagerTest.close();
		return b;

	}

	public double saldoaAldatu(Erabiltzailea e, double saldoBerria) {
		dbManagerTest.open();
		Double d = dbManagerTest.saldoaAldatu(e, saldoBerria);
		dbManagerTest.close();
		return d;

	}

	public Admin getAdmin(String string) {
		dbManagerTest.open();
		Admin p = dbManagerTest.getAdmin(string);
		dbManagerTest.close();

		return p;
	}

}
