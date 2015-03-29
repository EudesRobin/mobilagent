/**
 * 
 */
package jus.aor.courtage.kernel;

import java.net.URI;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author eudes
 *
 */
public class Registre implements _Registre {

	HashMap<String,LinkedList<Regfield>> registre = new HashMap<String,LinkedList<Regfield>>();

	@Override
	public void registerservice(String name, URI src) throws RemoteException {
		registerservice(name,src,true);
	}

	@Override
	public void registerservice(String name, URI src, boolean availability)
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
	public void updateservice(String name, URI src, boolean availability)
			throws RemoteException {
		for(Regfield r:registre.get(name)){
			if(r.serveur.equals(src)){
				r.av=availability;
			}
		}

	}

}
