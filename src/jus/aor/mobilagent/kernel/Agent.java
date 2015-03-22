package jus.aor.mobilagent.kernel;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class Agent implements _Agent {

	private static final long serialVersionUID = -5579606150122021510L;
	AgentServer srv;
	Route route;
	BAMAgentClassLoader bma;
	String srvname;
	boolean onsite=false;


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
		route.hasNext=true;
	}

	@Override
	public void run() {

		System.out.println("Serveur"+srvname);

		// Test prochaine étape
		while(route.hasNext()){

			if(onsite){
				System.out.println("Serveur"+srvname);
				// j'effectue mon action sur le site
				route.get().get_action().execute();
				// je me positionne sur ma prochaine étape
				route.next();

				onsite = false;
			}else{

				try {
					// notre agent part vers sa prochaine dest...
					System.out.println("Envoie vers "+route.get().server.getHost()+":"+route.get().server.getPort());

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

					onsite=true;

				} catch (Exception e) {
					e.printStackTrace();
				}

			}


		}

		// Action finale, on est revenu sur notre srv de départ
		route.get().get_action().execute();

	}

}
