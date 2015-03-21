/**
 * 
 */
package jus.aor.mobilagent.kernel;

import java.io.IOException;
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

	public AgentServer(String name,int port) throws MalformedURLException {
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

			while(true){
				// On accepte la connexion
				Socket agent = socket.accept();

				BAMAgentClassLoader bma = new BAMAgentClassLoader(new URL[]{},bms);

				InputStream input = agent.getInputStream();
				AgentInputStream agent_in = new AgentInputStream(input, bma);

				// creation de l'agent...
				try {
					// get jar first
					agent_in.loader.jarlib= (Jar) agent_in.readObject();
					// get object then
					Agent agent_object = (Agent) agent_in.readObject();
					// init agent
					agent_object.init(bma,this,name);

					agent_in.close();
					input.close();

					// l'agent effectue son action sur le srv
					new Thread(agent_object).start();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				socket.close();
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
