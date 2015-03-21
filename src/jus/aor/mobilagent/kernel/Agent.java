package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class Agent implements _Agent {

	private static final long serialVersionUID = -5579606150122021510L;
	AgentServer srv;
	Route route;
	BAMAgentClassLoader bma;
	String srvname;
	boolean done = false;


	@Override
	public void init(AgentServer agentServer, String serverName) {
		srv=agentServer;
		srv.setName(serverName);
		srvname = serverName;
		
		
		try {
			//init, on créé une route, étape -> action "nihil"
			route = new Route(new Etape(new URI(serverName), _Action.NIHIL));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void init(BAMAgentClassLoader loader, AgentServer server,
			String serverName) {
		bma = loader;
		init(server,serverName);

	}

	@Override
	public void addEtape(Etape etape) {
		route.add(etape);

	}

	@Override
	public void run() {
		
		// On a une action a effectuer
		if(!done){
			route.next().get_action().execute();
		}
		
		// On en a pas terminé ;)
		if(route.hasNext()){
			try {
				// notre agent part vers sa prochaine dest...
				done=true;
				System.out.println("Envoie de l'agent vers "+route.get().server.getHost()+":"+route.get().server.getPort());
				
				// construction du socket
				Socket socket_agent = new Socket(route.get().server.getHost(),route.get().server.getPort());
				
				// construction de l'output stream
				ObjectOutputStream output = new ObjectOutputStream(socket_agent.getOutputStream());
				
				// envoi jar
				output.writeObject(bma.jarlib);
				
				// envoie de l'agent
				output.writeObject(this);
				
				// close
				output.close();
				socket_agent.close();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
