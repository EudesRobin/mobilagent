package jus.aor.mobilagent.kernel;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * @author eudes
 *
 */
public class AgentServer extends Thread {

	private String name;
	private int port;
	private boolean alive = true;

	private HashMap<String,_Service<?>> services = new HashMap<String,_Service<?>>();
	private BAMServerClassLoader bms;


	public AgentServer(String name,int port) throws MalformedURLException{

		this.name=name;
		this.port=port;
		URL jar = new URL("file:///lib/MobilagentServer.jar");
		bms = new BAMServerClassLoader(new URL[]{jar},this.getClass().getClassLoader());

	}

	/**
	 * Get name of current server
	 * @return current name of the server
	 */
	public String get_srv_name(){
		return name;
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


	/**
	 * Bocle de réception du serveur d'agent.
	 */
	public void run() {

		try {
			ServerSocket socket = new ServerSocket(port);

			while(alive){
				Starter.get_logger().log(Level.FINE,"Serveur "+this.get_srv_name()+ " en attente...");
				// On accepte la connexion
				Socket agent = socket.accept();
				Starter.get_logger().log(Level.FINE,"Réception d'un agent");

				// prep Agent Loader
				BAMAgentClassLoader bma = new BAMAgentClassLoader(new URL[]{},bms);

				// On prépare la réception de l'agent
				InputStream input = agent.getInputStream();
				AgentInputStream agent_in = new AgentInputStream(input, bma);

				// On reçoit le jar des méthodes de l'agent
				Jar jarfile = (Jar)  agent_in.readObject();
				agent_in.loader.extractCode(jarfile);
				
				// On reçoit l'agent & on l'init.
				Agent agentob = (Agent) agent_in.readObject();
				agentob.init(agent_in.loader,this,this.get_srv_name());

				// Fin de la conec
				agent_in.close();
				
				Starter.get_logger().log(Level.FINE,"Fin de la réception de l'agent");

				// On lance l'agent !
				new Thread(agentob).start();

			}	
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
