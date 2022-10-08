import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Mezua;
import exceptions.MezuaEzDaZuzena;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MezuaBidaliMockIntTest {

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	
	@InjectMocks
	BLFacade sut = new BLFacadeImplementation(dataAccess);
	
	static Erabiltzailea e1;
	static Erabiltzailea e2;
	static Admin a1;
	static Blokeoa b1;
	
	@BeforeClass
	public static void initialize() {

		e1 = Mockito.mock(Erabiltzailea.class);
		e2 = Mockito.mock(Erabiltzailea.class);
		a1 = Mockito.mock(Admin.class);
		b1 = Mockito.mock(Blokeoa.class);

	}
	
	@Test
	public void test1() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Mezua m;
		try {
			m = sut.mezuaBidali(e1, a1, "kaixo");
			Mockito.doReturn(m).when(dataAccess).mezuaBidali(e1, a1, "kaixo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), e1);
			assertEquals(adminCaptor.getValue(), a1);
			assertEquals(mezuCaptor.getValue(), "kaixo");
			assertEquals(m, sut.mezuaBidali(e1, a1, "kaixo"));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test2() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Mezua m = null;
		try {
			m = sut.mezuaBidali(null, a1, "kaixo");
			Mockito.doReturn(null).when(dataAccess).mezuaBidali(null, a1, "kaixo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), null);
			assertEquals(adminCaptor.getValue(), a1);
			assertEquals(mezuCaptor.getValue(), "kaixo");
			assertEquals(null, sut.mezuaBidali(null, a1, "kaixo"));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e1, null, "kaixo");
			Mockito.doReturn(null).when(dataAccess).mezuaBidali(e1, null, "kaixo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), e1);
			assertEquals(adminCaptor.getValue(), null);
			assertEquals(mezuCaptor.getValue(), "kaixo");
			assertEquals(null, sut.mezuaBidali(e1, null, "kaixo"));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e1, a1, null);
			Mockito.doReturn(null).when(dataAccess).mezuaBidali(e1, a1, null);
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), e1);
			assertEquals(adminCaptor.getValue(), a1);
			assertEquals(mezuCaptor.getValue(), null);
			assertEquals(null, sut.mezuaBidali(e1, a1, null));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e1, a1, "k");
			Mockito.doThrow(new MezuaEzDaZuzena("Short_message")).when(dataAccess).mezuaBidali(e1, a1, "k");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
		} catch (MezuaEzDaZuzena e) {
			assertEquals(erabCaptor.getValue(), e1);
			assertEquals(adminCaptor.getValue(), a1);
			assertEquals(mezuCaptor.getValue(), "k");
			assertEquals("Short_message", e.getMessage());
		}
	}
	
	@Test
	public void test6() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e1, a1, "kaixooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
			Mockito.doThrow(new MezuaEzDaZuzena("Long_message")).when(dataAccess).mezuaBidali(e1, a1, "kaixoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
		} catch (MezuaEzDaZuzena e) {
			assertEquals(erabCaptor.getValue(), e1);
			assertEquals(adminCaptor.getValue(), a1);
			assertEquals(mezuCaptor.getValue(), "kaixooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
			assertEquals("Long_message", e.getMessage());
		}
		
	}
	
	@Test
	public void test7() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Erabiltzailea e3 = new Erabiltzailea("e3", "a", new Date());
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e3, a1, "kaixo");
			Mockito.doReturn(null).when(dataAccess).mezuaBidali(e3, a1, "kaixo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), e3);
			assertEquals(adminCaptor.getValue(), a1);
			assertEquals(mezuCaptor.getValue(), "kaixo");
			assertEquals(null, sut.mezuaBidali(e3, a1, "kaixo"));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test8() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Admin a2 = new Admin("e3", "a", new Date());
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e1, a2, "kaixo");
			Mockito.doReturn(null).when(dataAccess).mezuaBidali(e1, a2, "kaixo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), e1);
			assertEquals(adminCaptor.getValue(), a2);
			assertEquals(mezuCaptor.getValue(), "kaixo");
			assertEquals(null, sut.mezuaBidali(e1, a2, "kaixo"));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test9() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Erabiltzailea> erabCaptor2 = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e1, e2, "kaixo");
			Mockito.doReturn(m).when(dataAccess).mezuaBidali(e1, e2, "kaixo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), erabCaptor2.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), e1);
			assertEquals(erabCaptor2.getValue(), e2);
			assertEquals(mezuCaptor.getValue(), "kaixo");
			assertEquals(m, sut.mezuaBidali(e1, e2, "kaixo"));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test10() {
		ArgumentCaptor<Erabiltzailea> erabCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
		ArgumentCaptor<String> mezuCaptor = ArgumentCaptor.forClass(String.class);
		a1.blokeoaGehituListan(b1);
		Mezua m = null;
		try {
			m = sut.mezuaBidali(e2, a1, "kaixo");
			Mockito.doReturn(m).when(dataAccess).mezuaBidali(e2, a1, "kaixo");
			Mockito.verify(dataAccess, Mockito.times(1)).mezuaBidali(erabCaptor.capture(), adminCaptor.capture(), mezuCaptor.capture());
			
			assertEquals(erabCaptor.getValue(), e2);
			assertEquals(adminCaptor.getValue(), a1);
			assertEquals(mezuCaptor.getValue(), "kaixo");
			assertEquals(null, sut.mezuaBidali(e2, a1, "kaixo"));
		} catch (MezuaEzDaZuzena e) {
			e.printStackTrace();
		}
	}
}
