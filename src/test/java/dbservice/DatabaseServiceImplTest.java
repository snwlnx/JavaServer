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

@RunWith(MockitoJUnitRunner.class)
public class DatabaseServiceImplTest extends Assert {

    @Mock MessageSystem          messageSystem;
    @Mock Configuration          configuration;
    @Mock ServiceRegistryBuilder builder;
    DbService                    dbService;


    @Before
    public void setUp() throws Exception {
        dbService = new DatabaseServiceImpl(messageSystem, configuration,builder);
    }


    @Test
    public void getAddress(){
        assertTrue(dbService.getAddress() instanceof Address);
    }

    @Test
    public void getMsgSystem(){
        assertTrue(dbService.getMessageSystem() instanceof MessageSystem);
    }


}
