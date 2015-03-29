package jus.aor.courtage.kernel;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class Agent implements _Agent  {

	private static final long serialVersionUID = -5579606150122021510L;
	private boolean todo=false;
	protected Route route;

	private transient BAMAgentClassLoader bma;
	private transient String srvname;
	protected transient AgentServer srv;
	
	protected long begin;



	@Override
	public void init(AgentServer agentServer, String serverName) {
		srv=agentServer;
		srv.setName(serverName);
		srvname = serverName;
		begin = System.currentTimeMillis();


		try {
			// Pour rentrer @home 
			if(route==null){
				route = new Route(new Etape(new URI(serverName), _Action.NIHIL));
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(BAMAgentClassLoader loader, AgentServer server,String serverName) {
		bma = loader;
		init(server,serverName);
	}

	@Override
	public void addEtape(Etape etape) {
		route.add(etape);
	}

	/**
	 * Boucle de l'agent : action à exec ou envoi vers sa prochaine étape
	 */
	@Override
	public void run() {

		Starter.get_logger().log(Level.FINE,"Agent sur serveur "+srvname);

		if(todo){
			route.next().get_action().execute();
			Starter.get_logger().log(Level.FINE,"Job DONE");
		}

		if(route.hasNext()){
			try {
				Starter.get_logger().log(Level.FINE,"Envoi vers "+ route.get().server.getHost()+":"+route.get().server.getPort());

				todo=true;

				// construction du socket
				Socket socket_agent = new Socket(route.get().server.getHost(),route.get().server.getPort());

				// construction de l'output stream
				ObjectOutputStream output = new ObjectOutputStream(socket_agent.getOutputStream());

				// envoi du repository de l'agent
				output.writeObject(bma.getjar());
				// envoi de l'agent
				output.writeObject(this);

				output.close();
				socket_agent.close();

				Starter.get_logger().log(Level.FINE,"Envoi terminé");

			} catch (Exception e) {
				// serveur injoignable
				Starter.get_logger().log(Level.WARNING,"Serveur" + route.get().server.getHost()+":"+route.get().server.getPort()+ " injoignable");
				todo=true;
			}
		}
	}
}