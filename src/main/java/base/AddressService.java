package base;

public interface AddressService {
    public Address getAddress(Class< ? > abonentType);
    public void setAddress(Class<?> abonentType, Abonent abonent);
}
