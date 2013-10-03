package base;

public class Player implements Movable, Cloneable {
    static private int healthMax;
    static private int width, height;

    private int x, y,
                velocityX, velocityY,
                helthCurrent;

    public Player() {
		setPosition(0, 0);
	    this.velocityX = 0;
	    this.velocityX = 0;
	    helthCurrent = healthMax;
    }
    public Player(int x, int y) {
        setPosition(x, y);
        this.velocityX = 0;
        this.velocityX = 0;
        helthCurrent = healthMax;
    }

    public Player(int x, int y, int vX, int vY) {
        setPosition(x, y);
        velocityX = vX;
        velocityY = vY;
        helthCurrent = healthMax;
    }


    public int getHealthMax() {
        return healthMax;
    }

    public static void setHealthMax(int healthMax) {
        Player.healthMax = healthMax;
    }

    public static void setSize(int width, int height) {
        Player.height = height;
        Player.width = width;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(int x, int y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    public void setPosition(Player pl) {
        this.x = pl.x;
        this.y = pl.y;
    }

    public int getPositionX() {
        return x;
    }
    public int getPositionY() {
        return y;
    }

    public void move() {
        x += velocityX;
        y += velocityY;
    }

    public boolean collision(int x, int y) {
        int x2 = this.x + width,
            y2 = this.y + height;
        if(x > this.x && x < x2) {
            if(y > this.y && y < y2) {
                return true;
            }
        }
        return false;
    }

    public void setHealth(int h) {
        helthCurrent = h;
    }

    public int hurt(int h) {
        helthCurrent -= h;
        return helthCurrent;
    }

    public boolean isAlive() {
        return helthCurrent > 0;
    }

    public String toString() {
        return "[" + x + "," + y + "," + velocityX + "," + velocityY + "," + helthCurrent + ",\"Player\"]";
    }

    public Player clone() {
        Player pl = new Player(x, y, velocityX, velocityY);
        return pl;
    }
}
