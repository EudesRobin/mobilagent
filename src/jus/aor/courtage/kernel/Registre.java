/**
 * 
 */
package jus.aor.courtage.kernel;

import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author eudes
 *
 */
public class Registre extends UnicastRemoteObject implements _Registre {

	private static final long serialVersionUID = -5380643427313088389L;
	HashMap<String,LinkedList<Regfield>> registre;

	protected Registre() throws RemoteException {
		super();
		registre=new HashMap<String,LinkedList<Regfield>>();
	}


	@Override
	public synchronized void registerservice(String name, URI src) throws RemoteException {
		registerservice(name,src,true);
	}

	@Override
	public synchronized void registerservice(String name, URI src, boolean availability)
			throws RemoteException {
		/**
		 * Si le service n'a jamais été add, on créé l'entrée
		 */
		if(!registre.containsKey(name)){
			registre.put(name,new LinkedList<Regfield>());
		}
		// On ajoute le serveur proposant ce service à la liste
		registre.get(name).add(new Regfield(src,availability));
	}

	@Override
	public synchronized void updateservice(String name, URI src, boolean availability)
			throws RemoteException {
		for(Regfield r:registre.get(name)){
			if(r.serveur.equals(src)){
				r.av=availability;
			}
		}

	}


	@Override
	public synchronized LinkedList<URI> getservice(String name) throws RemoteException {
		LinkedList<URI> tmp = new LinkedList<URI>();
		for(Regfield r:registre.get(name)){
			if(r.av){
				tmp.add(r.serveur);
			}
		}
		return tmp;
	}

}
