package base;

import message.Message;

public interface MessageSystem {
    public void addService(Class<?> abonentType, Abonent abonent);

    public void sendMessage(Message message);

    public void execForAbonent(Abonent abonent);

    public AddressService getAddressService();

    public Address getAddress(Class<?> abonentType);
}
