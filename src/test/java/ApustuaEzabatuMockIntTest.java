import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;

@RunWith(MockitoJUnitRunner.class)
public class ApustuaEzabatuMockIntTest {

    DataAccess dataAccess=Mockito.mock(DataAccess.class);
	
	BLFacade sut = new BLFacadeImplementation(dataAccess);
	
    
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
