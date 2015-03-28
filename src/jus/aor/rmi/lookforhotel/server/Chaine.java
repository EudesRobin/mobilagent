package jus.aor.rmi.lookforhotel.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import jus.aor.rmi.lookforhotel.Hotel;
import jus.aor.rmi.lookforhotel._Chaine;

public class Chaine extends UnicastRemoteObject implements _Chaine{
	
	private static final long serialVersionUID = -4237212311180516187L;

	protected Chaine() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Hotel> get(String localisation) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}