package jus.aor.rmi.lookforhotel.server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import jus.aor.rmi.lookforhotel.Numero;
import jus.aor.rmi.lookforhotel._Annuaire;



/**
 * @author eudes
 *
 */
public class Annuaire extends UnicastRemoteObject implements _Annuaire {

	protected Annuaire() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 7269239953815193065L;

	@Override
	public Numero get(String abonne) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


}