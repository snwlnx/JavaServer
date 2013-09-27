import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class UserTestCase extends Assert {

	private int x;

	@Before
	public void prework() {
		x = 10;
	}

	@Test
	public void testXvalue() {
		assertTrue(x == 10);
	}
}
