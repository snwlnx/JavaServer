package message;

import base.Abonent;
import base.Address;
import base.Frontend;

public abstract class MessageToFrontend extends Message {
    public MessageToFrontend(Address from, Address to) {
        super(from, to);
    }

    public boolean exec(Abonent abonent) {
        if(abonent instanceof Frontend) {
            exec((Frontend) abonent);
	        return true;
        }
	    return false;
    }

    public abstract void exec(Frontend frontend);
}
