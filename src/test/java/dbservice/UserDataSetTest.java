package dbservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/17/13
 * Time: 12:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserDataSetTest extends Assert {
    UserDataSet set;
    @Before
    public void setUp() throws Exception {
        set = new UserDataSet(1,"test");
    }

    @Test
    public void getName(){
        assertTrue(set.getName().equals("test"));
    }

    @Test
    public void getId(){
        assertTrue(set.getId().get() == (new Long(1)).longValue());
    }
}
