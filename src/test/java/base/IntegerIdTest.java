package base;

import org.junit.Assert;
import org.junit.Test;

public class IntegerIdTest extends Assert {

	@Test
	public void equalsTest() {
		IntegerId<Object> id1 = new IntegerId<Object>(1);
		IntegerId<Object> id2 = new IntegerId<Object>(2);
		IntegerId<Object> id3 = new IntegerId<Object>(1);
        IntegerId<Object> id4 = new IntegerId<Object>(1);

		assertTrue(id1 != id2);
		assertFalse(id1.equals(id2));
		assertTrue(id1.equals(id3));

		assertFalse(id1.equals(new Object()));
	}

	@Test
	public void toStringTest() {
		IntegerId<Object> id2 = new IntegerId<Object>(1);

		assertTrue("1".equals(id2.toString()));
	}

    @Test
    public void hashCodeTest() {
        Integer testValue = new Integer(1);
        IntegerId<Object> id1 = new IntegerId<Object>(testValue);

        assertEquals(id1.hashCode(), testValue.hashCode());
    }

}
