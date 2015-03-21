/**
 * 
 */
package jus.aor.mobilagent.kernel;

import java.util.HashMap;

/**
 * @author eudes
 *
 */
public class AgentServer extends Thread {

	private String name;
	private int port;
	private HashMap<String,_Service<?>> services = new HashMap<String,_Service<?>>();

	public AgentServer(String name,int port) {
		this.name=name;
		this.port=port;
	}

	/**
	 * Ajoute un service, s'il n'est pas déjà présent dans la Hashmap
	 * @param name nom du service
	 * @param service class du service
	 */
	public void addService(String name, _Service<?> service) {
		if(!services.containsKey(name)){
			services.put(name, service);
		}
		
	}
	/**
	 * Recupération d'un service
	 * @param name nom du service
	 * @return le service associé au nom
	 */
	public _Service<?> getService(String name){
		return services.get(name);
	}
	
	
	public void run() {
		// TODO
		// boucle de reception des agents..
	}

}
