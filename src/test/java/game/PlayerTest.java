package game;

import base.Player;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest extends Assert {

	@BeforeClass
	public static void beforeClass() {
	   	Player.setSize(10, 20);
		Player.setHealthMax(100);
	}

	@Test
	public void constructorDefaultTest() {
		Player player1 = new Player(0, 0);
		Player player2 = new Player(-10, 20);
		Player player3 = new Player(10, -20);

		assertEquals(player1.getPositionX(), 0);
		assertEquals(player1.getPositionY(), 0);

		assertEquals(player2.getPositionX(), -10);
		assertEquals(player2.getPositionY(), 20);

		assertEquals(player3.getPositionX(), 10);
		assertEquals(player3.getPositionY(), -20);
	}

	@Test
	public void zeroVelocityTest() {
		Player player = new Player(0, 0, 0, 0);
		player.move();

		assertEquals(player.getPositionX(), 0);
		assertEquals(player.getPositionY(), 0);
	}

	@Test
	public void velocityTest() {
		Player player = new Player(10, 10, 20, -30);
		player.move();

		assertEquals(30, player.getPositionX());
		assertEquals(-20, player.getPositionY());
	}

	@Test
	public void collisionTest() {

		Player player = new Player(10, 10);

		assertTrue(player.collision(11, 11));
		assertTrue(player.collision(19, 29));
		assertTrue(player.collision(19, 11));
		assertTrue(player.collision(11, 29));
		assertTrue(player.collision(15, 20));
		assertFalse(player.collision(0, 0));
		assertFalse(player.collision(-1, 20));
		assertFalse(player.collision(20, -1));
		assertFalse(player.collision(100, 100));
	}

	@Test
	public void liveTest() {
		Player player = new Player();
		player.hurt(50);
		assertTrue(player.isAlive());

		player.hurt(50);
		assertFalse(player.isAlive());

		player = new Player();
		player.hurt(101);
		assertFalse(player.isAlive());
	}

	@Test
	public void toStringTest() {
		Player player = new Player(10, 134, 7, -45);
		player.hurt(15);
		assertEquals(player.toString(), "[" + 10 + "," + 134 + "," + 7 + "," + -45 + "," + 85 + ",\"Player\"]");
	}
}
