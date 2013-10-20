package base;

import org.junit.Assert;
import org.junit.Test;


public class IdTests extends Assert {

        @Test
        public void equalityTest() {
            IntegerId<Object> id1 = new IntegerId<Object>(1);
            IntegerId<Object> id2 = new IntegerId<Object>(2);
            LongId<Object> id3 = new LongId<Object>(3);
            LongId<Object> id4 = new LongId<Object>(4);

            assertTrue(id1 != id2);
            assertFalse(id1.equals(id2));
            assertFalse(id1.equals(new Object()));
            assertTrue("1".equals(id2.toString()));

            assertTrue(id3 != id4);
            assertFalse(id3.equals(id4));
            assertFalse(id3.equals(new Object()));
            assertTrue("3".equals(id3.toString()));


        }

}
