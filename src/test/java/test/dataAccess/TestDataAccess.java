package test.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Mezua;
import domain.Pertsona;
import domain.Question;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.DoesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
		
		public boolean emaitzaEzabatu(Question q, Kuota k) {
			db.getTransaction().begin();
			
			Question qDB = db.find(Question.class, q.getQuestionNumber());
			qDB.setResult(null);
			
			db.getTransaction().commit();
			
			return !qDB.emaitzaDu();
		}
		
		public Event getEventById(int id) {
			Event ev = db.find(Event.class, id);
			return ev;
		}
		
		public boolean mezuaEzabatu(Pertsona p2, int mezuaZenbakia) {
			db.getTransaction().begin();
			Iterator<Mezua> it = p2.getBidalitakoMezuak().iterator();
			boolean ezabatuta = false;
			Mezua ezabatutakoa = null;
			while(it.hasNext() && !ezabatuta) {
				Mezua m = it.next();
				if(m.getMezuaZenbakia().equals(mezuaZenbakia)) {
					it.remove();
					ezabatuta = true;
					ezabatutakoa = m;				
				}
			}
			
			Iterator<Mezua> it2 = ezabatutakoa.getNori().getJasotakoMezuak().iterator();
			boolean ezabatuta2 = false;
			Mezua ezabatutakoa2 = null;
			while(it2.hasNext() && !ezabatuta2) {
				Mezua m = it2.next();
				if(m.getMezuaZenbakia().equals(mezuaZenbakia)) {
					it2.remove();
					ezabatuta2 = true;
					ezabatutakoa2 = m;				
				}
			}
			
			db.getTransaction().commit();
			return ezabatuta && ezabatuta2;
		}
		
		public boolean mugimenduGuztiakEzabatu(Erabiltzailea e1) {
			db.getTransaction().begin();
			
			Erabiltzailea eDB = db.find(Erabiltzailea.class, e1.getIzena());
			eDB.getMugimenduak().clear();
			
			db.getTransaction().commit();
			
			return eDB.getMugimenduak().size() == 0;
		}
		
		public boolean removePertsona(String izena) {
			db.getTransaction().begin();
			
			Pertsona p = db.find(Pertsona.class, izena);
			db.remove(p);
			
			db.getTransaction().commit();
			
			Pertsona pDago = db.find(Pertsona.class, izena);
			
			return pDago == null;
			
		}
		
		public double saldoaAldatu(Erabiltzailea e, double saldoBerria) {
			db.getTransaction().begin();
			
			Erabiltzailea eDB = db.find(Erabiltzailea.class, e.getIzena());
			
			eDB.saldoaAldatu(saldoBerria);
			
			db.getTransaction().commit();
			
			return eDB.getSaldoa();
		}
		
}

