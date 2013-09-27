import base.Address;
import base.DbService;
import base.Frontend;
import base.MessageSystem;
import dbservice.DbServiceImpl;
import frontend.FrontendImpl;
import message.MessageSystemImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressServiceImplTest {


        final int                  n = 10;
        final int                  nmax = 1000;
        final int                  Eps  =  10;
        MessageSystem messageSystem  = new MessageSystemImpl();
        int[]                  array = new int[n];

        @Before
        public void setUp(){
            for(int i = 0 ; i < n; i++){
                array[i] = 0;
                DbService dbService = new DbServiceImpl(messageSystem);
                //(new Thread(dbService)).start();
            }

        }

//        @Test
//        public void testAddresServ(){
//            Address address;
//            for(int i = 0 ; i < nmax; i++){
//                address = messageSystem.getAddress(DbService.class);
//                array[address.hashCode()-1]++;
//            }
//            for(int i = 0; i < n; i++){
//                Assert.assertTrue(Math.abs(array[i] - nmax/n) < Eps );
//            }
//        }
  }


