package message;

import base.Abonent;
import base.Address;
import base.Frontend;

public abstract class MessageToFrontend extends Message {
    public MessageToFrontend(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if(abonent instanceof Frontend) {
            exec((Frontend) abonent);
        }
    }

    public abstract void exec(Frontend frontend);
}
