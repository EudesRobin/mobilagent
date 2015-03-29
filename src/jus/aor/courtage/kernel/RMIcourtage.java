package jus.aor.courtage.kernel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import jus.aor.courtage.kernel.IOHandler;


/**
 * @author eudes
 *
 */
public class RMIcourtage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.util.logging.Level level;
		level=java.util.logging.Level.ALL;
		Logger logger=null;
		String loggerName;

		try {
			loggerName = "jus/aor/courtage/"+InetAddress.getLocalHost().getHostName()+args[1];
			logger = Logger.getLogger(loggerName);
			logger.addHandler(new IOHandler());
			logger.setLevel(level);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if(System.getSecurityManager()==null){
			System.setSecurityManager(new SecurityManager());
		}
		
		if(args.length!=2){
			logger.log(Level.WARNING,"Wrong parameters : <service_name> <port_serveur>");
		}
		
		try {
			if(args[0].equalsIgnoreCase("Courtage")){
				_Registre skeleton;
				// exportobject déjà réalisé car la classe extends UnicastRemoteObject
				skeleton = (_Registre)new Registre();
				Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[1]));
				registry.rebind("Courtage",skeleton);
				logger.log(Level.FINE,"Service Courtage disponible");
			}else{
				logger.log(Level.WARNING,"Service demandé inconnu");
			}
		} catch (IOException e) {
			logger.log(Level.WARNING,e.toString());
		}

	}

}
