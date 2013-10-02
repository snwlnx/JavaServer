package game;

import base.Fireball;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FireballTest extends Assert {

	private static final int damageValue = 10;

	@BeforeClass
	public static void beforeClass() {
		Fireball.setDamage(damageValue);
	}

	@Test
	public void damageTest() {
		assertEquals(damageValue, Fireball.getDamage());
	}

	@Test
	public void constructorTest() {
		Fireball fb = new Fireball(10, 20, 0, 0);
		assertEquals(10, fb.getPositionX());
		assertEquals(20, fb.getPositionY());
	}

	@Test
	public void moveTest() {
		Fireball fb = new Fireball(10, 20, 1, 2);
		fb.move();

		assertEquals(11, fb.getPositionX());
		assertEquals(22, fb.getPositionY());

	}

	@Test
	public void toStringTest() {

		Fireball fb = new Fireball(40, 50, 3, 10);
		String plainObject = "[" + 40 + "," + 50 + "," + 3.0 + "," + 10.0 + ", \"Fireball\"]";

		assertTrue(plainObject.equals(fb.toString()));
	}

	@Test
	public void cloneTest() {
		Fireball fb = new Fireball(1, 2, 3, 4);
		Fireball fb2 = fb.clone();

		assertTrue(fb != fb2);
		assertTrue(fb.toString().equals(fb2.toString()));
	}


}
