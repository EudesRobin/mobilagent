package jus.aor.mobilagent.kernel;

import java.net.URI;
import java.net.URISyntaxException;

public class Agent implements _Agent {

	private static final long serialVersionUID = -5579606150122021510L;
	AgentServer srv;
	Route route;
	BAMAgentClassLoader bma;
	String srvname;
	


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
		// TODO
	}

}
