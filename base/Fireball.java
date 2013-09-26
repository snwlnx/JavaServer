package base;

public class Fireball implements Movable, Cloneable {
    private static int damage;

    private float x, y;
    private float velocityX, velocityY;
    private int moveCounter = 0;

    public Fireball(float x, float y, float vX, float vY) {
        this.x = x;
        this.y = y;
        velocityX = vX;
        velocityY = vY;
    }

    public static void setDamage(int dmg) {
        damage = dmg;
    }

    public static int getDamage() {
        return damage;
    }


    public void move() {
        x += velocityX;
        y += velocityY;
        ++moveCounter;
    }
    
    public int getPositionX() {
        return (int)x;
    }

    public int getPositionY() {
        return (int)y;
    }

    public String toString() {
        return "[" + getPositionX() + "," + getPositionY() + "," + velocityX + "," + velocityY  + ", \"Fireball\"]";
    }

    public Fireball clone() {
        Fireball fb = new Fireball(x, y, velocityX,  velocityY);
        return fb;
    }
}
