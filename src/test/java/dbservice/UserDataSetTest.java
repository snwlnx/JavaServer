package dbservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDataSetTest extends Assert {
    UserDataSet userDataSet;

    @Before
    public void setUp() throws Exception {
        userDataSet = new UserDataSet(1,"test");
    }


        @Test
    public void getId(){
        assertTrue(userDataSet.getId().get() == (new Long(1)).longValue());
    }

    @Test
    public void getName(){
        assertTrue(userDataSet.getName().equals("test"));
    }

    @Test
    public void setName(){
        userDataSet.setName("testName");
        assertTrue(userDataSet.getName().equals("testName"));
    }





}
