package dbservice;

import base.Address;
import base.DbService;
import base.MessageSystem;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
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
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseServiceImplTest extends Assert {

    @Mock MessageSystem          msgSystem;
    @Mock Configuration          config;
    @Mock ServiceRegistryBuilder builder;
    DbService                    dbService;


    @Before
    public void setUp() throws Exception {
        dbService = new DatabaseServiceImpl(msgSystem,config,builder);
    }


    @Test
    public void getMsgSystem(){
        assertTrue(dbService.getMessageSystem() instanceof MessageSystem);
    }

    @Test
    public void getAddress(){
        assertTrue(dbService.getAddress() instanceof Address);
    }

}
