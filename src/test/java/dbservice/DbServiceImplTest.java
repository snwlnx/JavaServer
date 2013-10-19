package dbservice;

import base.Address;
import base.LongId;
import base.MessageSystem;
import message.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import user.UserSession;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceImplTest extends Assert {
    @Mock MessageSystem messageSystem;
    DbServiceImpl dbService;


    @Before
    public void setUp() throws Exception {
        dbService = new DbServiceImpl(messageSystem);
    }

    @Test
    public void getMessageSystem(){
        assertTrue(dbService.getMessageSystem() instanceof MessageSystem);
    }

    @Test
    public void getAddress(){
        assertTrue(dbService.getAddress() instanceof Address);
    }


    @Test
	public void answerTest() {
		dbService.getUserIdAndAnswer(new LongId<UserSession>(1), "Test");
	}

    @Test
    public void getUsrId(){
        assertTrue(dbService.getUserId("Rus").get() == (new Long(2).longValue()));
    }

    @Test
	public void runTest() {
		Thread thread = new Thread(dbService);
		thread.start();
		thread.interrupt();
	}

}
