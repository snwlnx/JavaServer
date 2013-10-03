package message;

import base.Abonent;
import base.Address;
import base.DbService;

public abstract class MessageToDbService extends Message
{
	public MessageToDbService(Address from, Address to) {
		super(from, to);
	}
	
	public boolean exec(Abonent abonent) {
		if( abonent instanceof DbService) {
			exec((DbService)abonent);
			return true;
		}
		return false;
	}
	
	public abstract void exec(DbService accountService);
}
