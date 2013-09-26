package base;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {
    private static AtomicInteger abonentIdCreator = new AtomicInteger(0);

    private final int abonentId;

    public Address() {
        abonentId = abonentIdCreator.incrementAndGet();
    }

    public int hashCode() {
        return abonentId;
    }

}
