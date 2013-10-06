package message;

import base.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystemImpl implements MessageSystem {


    private Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<Address, ConcurrentLinkedQueue<Message>>();
    private AddressService addressService = new AddressServiceImpl();

    public Address getAddress(Class<?> abonentType) {
        return addressService.getAddress(abonentType);
    }

    public void addService(Class<?> abonentType, Abonent abonent) {
        addressService.setAddress(abonentType, abonent);
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Message>());
    }

    public void sendMessage(Message message) {
        Queue<Message> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    public boolean execForAbonent(Abonent abonent) {
        Queue<Message> messageQueue = messages.get(abonent.getAddress());
        if (messageQueue == null) {
            return false;
        }
        while (!messageQueue.isEmpty()) {
            messageQueue.poll().exec(abonent);
        }
	    return true;
    }

    public AddressService getAddressService() {
        return addressService;
    }

}
