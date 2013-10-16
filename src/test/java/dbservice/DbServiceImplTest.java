package dbservice;

import base.Address;
import base.MessageSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/17/13
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class DbServiceImplTest extends Assert {
    DbServiceImpl dbService;
    @Mock MessageSystem messageSystem;

    @Before
    public void setUp() throws Exception {
        dbService = new DbServiceImpl(messageSystem);
    }


    @Test
    public void getAddress(){
        assertTrue(dbService.getAddress() instanceof Address);
    }
    @Test
    public void getMsgSystem(){
        assertTrue(dbService.getMessageSystem() instanceof MessageSystem);
    }

    @Test
    public void getUsrId(){
        assertTrue(dbService.getUserId("Rus").get() == (new Long(2).longValue()));
    }

}
