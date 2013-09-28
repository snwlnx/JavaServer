package message;

import base.Abonent;
import base.Address;
import base.AddressService;

import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class AddressServiceImpl implements AddressService {

    private Map<Class<?>, Vector<Address>> addresses = new HashMap<Class<?>, Vector<Address>>();
    private Random rnd = new Random();

    public Address getAddress(Class<?> abonentType) {
        Vector<Address> addressVector = addresses.get(abonentType);
        return addressVector.get(rnd.nextInt(addressVector.size()));
    }

    public void setAddress(Class<?> abonentType, Abonent abonent) {
        Vector<Address> addressVector = addresses.get(abonentType);
        if (addressVector == null) {
            addressVector = new Vector<Address>();
            addresses.put(abonentType, addressVector);
        }
        addressVector.addElement(abonent.getAddress());
    }
}
