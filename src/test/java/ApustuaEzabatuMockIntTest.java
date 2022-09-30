import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Question;
import exceptions.ApustuaEzDaEgin;
import test.dataAccess.TestDataAccess;

@RunWith(MockitoJUnitRunner.class)
public class ApustuaEzabatuMockIntTest {

    DataAccess dataAccess=Mockito.mock(DataAccess.class);
    
	BLFacade sut = new BLFacadeImplementation(dataAccess);
	
	static Apustua ap1, ap2, ap3, ap4, ap5;
	static Erabiltzailea e1, e2, e3, e4;
	
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	
	@BeforeClass
	public static void initialize() {
		ap1 = Mockito.mock(Apustua.class);
		ap2 = Mockito.mock(Apustua.class);
		ap3 = Mockito.mock(Apustua.class);
		ap4 = Mockito.mock(Apustua.class);
		ap5 = Mockito.mock(Apustua.class);
		
		e1 = Mockito.mock(Erabiltzailea.class);
		e2 = Mockito.mock(Erabiltzailea.class);
		e3 = Mockito.mock(Erabiltzailea.class);
		e4 = Mockito.mock(Erabiltzailea.class);
	}
    
	@Test
	public void apustuaEzabatuTest1() {
		
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(true).when(dataAccess).apustuaEzabatu(ap1, e1);
		
		boolean expected = true;
		boolean obtained = sut.apustuaEzabatu(ap1, e1);
		
		assertEquals(expected, obtained);
		
		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(), erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap1);
		assertEquals(erabiltzaileaCaptor.getValue(), e1);
	}
	
	@Test
	public void apustuaEzabatuMB1() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(true).when(dataAccess).apustuaEzabatu(ap1, e1);
		
		boolean expected = true;
		boolean obtained = sut.apustuaEzabatu(ap1, e1);
		
		assertEquals(expected, obtained);
		
		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(), erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap1);
		assertEquals(erabiltzaileaCaptor.getValue(), e1);
	}
	
	@Test
	public void apustuaEzabatuMB2() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap2, e1);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap2, e1);
		
		assertEquals(expected, obtained);
		
		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(), erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap2);
		assertEquals(erabiltzaileaCaptor.getValue(), e1);
	}
	
	@Test
	public void apustuaEzabatuMB3() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap5, e1);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap5, e1);
		
		assertEquals(expected, obtained);
		
		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(), erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap5);
		assertEquals(erabiltzaileaCaptor.getValue(), e1);
	}
	
	@Test
	public void apustuaEzabatuTest2() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(null, e1);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(null, e1);
		
		assertEquals(expected, obtained);

		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(),  erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), null);
		assertEquals(erabiltzaileaCaptor.getValue(), e1);
	}
	
	@Test
	public void apustuaEzabatuTest3() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap1, null);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap1, null);
		
		assertEquals(expected, obtained);

		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(),  erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap1);
		assertEquals(erabiltzaileaCaptor.getValue(), null);
	}
	
	@Test
	public void apustuaEzabatuTest4() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap2, e1);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap2, e1);
		
		assertEquals(expected, obtained);

		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(),  erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap2);
		assertEquals(erabiltzaileaCaptor.getValue(), e1);
	}
	
	@Test
	public void apustuaEzabatuTest5() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap3, e3);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap3, e3);
		
		assertEquals(expected, obtained);

		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(),  erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap3);
		assertEquals(erabiltzaileaCaptor.getValue(), e3);

	}
	
	@Test
	public void apustuaEzabatuTest6() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap1, e2);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap1, e2);
		
		assertEquals(expected, obtained);

		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(),  erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap1);
		assertEquals(erabiltzaileaCaptor.getValue(), e2);
	}
	
	@Test
	public void apustuaEzabatuTest7() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap4, e1);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap4, e1);
		
		assertEquals(expected, obtained);

		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(),  erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap4);
		assertEquals(erabiltzaileaCaptor.getValue(), e1);

	}
	
	@Test
	public void apustuaEzabatuTest8() {
		ArgumentCaptor<Apustua> apustuaCaptor = ArgumentCaptor.forClass(Apustua.class);
		ArgumentCaptor<Erabiltzailea> erabiltzaileaCaptor = ArgumentCaptor.forClass(Erabiltzailea.class);
		
		Mockito.doReturn(false).when(dataAccess).apustuaEzabatu(ap1, e4);
		
		boolean expected = false;
		boolean obtained = sut.apustuaEzabatu(ap1, e4);
		
		assertEquals(expected, obtained);

		Mockito.verify(dataAccess, Mockito.times(1)).apustuaEzabatu(apustuaCaptor.capture(),  erabiltzaileaCaptor.capture());
		
		assertEquals(apustuaCaptor.getValue(), ap1);
		assertEquals(erabiltzaileaCaptor.getValue(), e4);
	}

}
