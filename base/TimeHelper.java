package base;

public class TimeHelper {
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED");
        }
    }
}
