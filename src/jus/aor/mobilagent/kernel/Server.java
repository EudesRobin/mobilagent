package jus.aor.mobilagent.kernel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;


/**
 * Le serveur principal permettant le lancement d'un serveur d'agents mobiles et les fonctions permettant de déployer des services et des agents.
 * @author     Morat
 */
public final class Server {
	/** le nom logique du serveur */
	protected String name;
	/** le port où sera ataché le service du bus à agents mobiles. Pafr défaut on prendra le port 10140 */
	protected int port=10140;
	/** le server d'agent démarré sur ce noeud */
	protected AgentServer agentServer;

	/**
	 * Démarre un serveur de type mobilagent 
	 * @param port le port d'écoute du serveur d'agent 
	 * @param name le nom du serveur
	 */
	public Server(final int port, final String name){
		this.name=name;
		try {
			this.port=port;

			/* démarrage du server d'agents mobiles attaché à cette machine */
			agentServer = new AgentServer(name, port);
			
			/* temporisation de mise en place du server d'agents */
			Thread.sleep(1000);
			
			/* lancement du thread du serveur */
			agentServer.start();

		}catch(Exception ex){
			Starter.get_logger().log(Level.WARNING," erreur durant le lancement du serveur - server constr\n"+ex.toString(),ex);
			return;
		}
	}
	/**
	 * Ajoute le service caractérisé par les arguments
	 * @param name nom du service
	 * @param classeName classe du service
	 * @param codeBase codebase du service
	 * @param args arguments de construction du service
	 */
	public final void addService(String name, String classeName, String codeBase, Object... args) {
		try {
			// add codebase
			BAMServerClassLoader bms = new BAMServerClassLoader(new URL[]{},this.getClass().getClassLoader());
			bms.addURL(codeBase);

			/*
			 * thx javadoc !
			 * http://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#forName-java.lang.String-boolean-java.lang.ClassLoader
			 * We load the class
			 */
			Class<?> cl = Class.forName(classeName,true,this.getClass().getClassLoader());

			/*
			 * Instance of this class = our "service"
			 */
			_Service<?> service = (_Service<?>)cl.getConstructor(Object[].class).newInstance(new Object[]{args});

			// add service to hashmap
			agentServer.addService(name,service);
			
			bms.close();

		}catch(Exception ex){
			Starter.get_logger().log(Level.WARNING," erreur durant le lancement du serveur - add srv \n"+ex.toString(),ex);
			return;
		}
	}
	/**
	 * deploie l'agent caractérisé par les arguments sur le serveur
	 * @param classeName classe du service
	 * @param args arguments de construction de l'agent
	 * @param codeBase codebase du service
	 * @param etapeAddress la liste des adresse des étapes
	 * @param etapeAction la liste des actions des étapes
	 */
	public final void deployAgent(String classeName, Object[] args, String codeBase, List<String> etapeAddress, List<String> etapeAction) {
		try {
			BAMAgentClassLoader bma = new BAMAgentClassLoader(new URL[]{},this.getClass().getClassLoader());
			// add codebase of agent
			bma.extractCode(codeBase);
			// get class...
			Class<?> cl = (Class<?>)Class.forName(classeName, true, bma);
			// get constructor
			Constructor<?> cr = cl.getConstructor(Object[].class);
			// get instance of this class...
			Agent current_agent = (Agent) cr.newInstance(new Object[]{args});
			// On initialise notre agent
			current_agent.init(bma, agentServer,name);

			Starter.get_logger().log(Level.FINE,"Debut déploiement agent");
			
			// On ajoute les actions / etape !
			if(etapeAddress.size()!=etapeAction.size()){
				throw new Exception("Chaque etape doit avoir une action associée !");
			}else{
				for(int i=0;i<etapeAddress.size();i++){
					
					/*
					 * thx javadoc:
					 * http://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Field.html
					 */
					
					// on a le nom de l'action à exec, reste à recup la méthode associée...
					Field f = current_agent.getClass().getDeclaredField(etapeAction.get(i));
					f.setAccessible(true); // http://stackoverflow.com/questions/3567372/access-to-private-inherited-fields-via-reflection-in-java
					_Action action = (_Action) f.get(current_agent); // get field of current object
					
					current_agent.addEtape(new Etape(new URI(etapeAddress.get(i)),action));
					
					Starter.get_logger().log(Level.FINE,"add etape : "+etapeAddress.get(i)+ " - action " +etapeAction.get(i));
				}
			}
			if(etapeAddress.size()==0){
				Starter.get_logger().log(Level.FINE,"Aucune étape ajoutée pour cet agent");
			}
			Starter.get_logger().log(Level.FINE,"Deploiement agent terminé");
			
			new Thread(current_agent).start();

		}catch(Exception ex){
			Starter.get_logger().log(Level.WARNING," erreur durant le lancement du serveur - deploy agent \n"+ex.toString(),ex);
			return;
		}
	}

}
