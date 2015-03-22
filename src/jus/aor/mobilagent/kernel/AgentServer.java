/**
 * 
 */
package jus.aor.mobilagent.kernel;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

/**
 * @author eudes
 *
 */
public class AgentServer extends Thread {

	private String name;
	private int port;
	private HashMap<String,_Service<?>> services = new HashMap<String,_Service<?>>();
	private BAMServerClassLoader bms;
	private boolean alive = true;
	
	public AgentServer(String name,int port) throws MalformedURLException{
			
			this.name=name;
			this.port=port;
			URL jar = new URL("file:///lib/MobilagentServer.jar");
			bms = new BAMServerClassLoader(new URL[]{jar},this.getClass().getClassLoader());

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

		try {
			ServerSocket socket = new ServerSocket(port);

			while(alive){
				// On accepte la connexion
				Socket agent = socket.accept();
				System.out.println(" services dispo " + services.entrySet());
				BAMAgentClassLoader bma = new BAMAgentClassLoader(new URL[]{},bms);

				InputStream input = agent.getInputStream();
				AgentInputStream agent_in = new AgentInputStream(input, bma);

				// creation de l'agent...
					
					Jar jarfile = (Jar)  agent_in.readObject();
					agent_in.loader.extractCode(jarfile);
					Agent agentob = (Agent) agent_in.readObject();
					agentob.init(agent_in.loader, this, this.getName());

					agent_in.close();
					
					new Thread(agentob).start();

			}	
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
