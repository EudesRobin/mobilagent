package jus.aor.mobilagent.kernel;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class Agent implements _Agent  {

	private static final long serialVersionUID = -5579606150122021510L;
	protected transient AgentServer srv;
	Route route;
	protected transient BAMAgentClassLoader bma;
	protected transient String srvname;
	boolean todo=false;


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


		if(todo){
			System.out.println("Serveur"+srvname);

			route.next().get_action().execute();
		}
		
		if(route.hasNext()){
				try {
					todo=true;
					
					// notre agent part vers sa prochaine dest...
					System.out.println("Envoie vers "+route.get().server.getHost()+":"+route.get().server.getPort());

					// construction du socket
					Socket socket_agent = new Socket(route.get().server.getHost(),route.get().server.getPort());

					// construction de l'output stream
					ObjectOutputStream output = new ObjectOutputStream(socket_agent.getOutputStream());

					if(bma.jarlib == null ){
						System.out.println("NULL JARFILE !");
					}else{
						System.out.println(" hashmap ... " + bma.lib.entrySet());
					}
					// envoi jar
					output.writeObject(bma.jarlib);
					System.out.println("jar envoyé");

					// envoie de l'agent
					output.writeObject(this);
					System.out.println("agent envoyé");
					// close
					output.close();
					socket_agent.close();

					
					
					System.out.println("Envoie terminé");

				} catch (Exception e) {
					e.printStackTrace();
					
				}
		}


	}

}
