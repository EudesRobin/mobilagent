package jus.aor.rmi.lookforhotel.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import jus.aor.rmi.lookforhotel.IOHandler;
import jus.aor.rmi.lookforhotel._Annuaire;
import jus.aor.rmi.lookforhotel._Chaine;

/**
 * @author eudes
 *
 */
public class RMIServer {

	/**
	 * @param args 3 args requis : <service_name> <filename> <port_serveur>
	 * @throws Exception 
	 */
	public static void main(String[] args){

		java.util.logging.Level level;
		level=java.util.logging.Level.ALL;
		Logger logger=null;
		String loggerName;

		try {
			loggerName = "jus/aor/rmi/"+InetAddress.getLocalHost().getHostName()+args[1];
			logger = Logger.getLogger(loggerName);
			logger.addHandler(new IOHandler());
			logger.setLevel(level);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if(System.getSecurityManager()==null){
			System.setSecurityManager(new SecurityManager());
		}

		if(args.length!=3){
			logger.log(Level.WARNING,"Wrong parameters : <service_name> <filename> <port_serveur>");
		}
		
		try {
			if(args[0].equalsIgnoreCase("Chaine")){
				_Chaine skeleton;
				// exportobject déjà réalisé car la classe extends UnicastRemoteObject
				skeleton = (_Chaine)new Chaine(args[1]);
				Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[2]));
				registry.rebind("Chaine",skeleton);
				logger.log(Level.FINE,"Service Chaine disponible");
			}else if(args[0].equalsIgnoreCase("Annuaire")){
				_Annuaire skeleton = (_Annuaire)new Annuaire(args[1]); // idem
				Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[2]));
				registry.rebind("Annuaire",skeleton);
				logger.log(Level.FINE,"Service Annuaire disponible");
			}else{
				logger.log(Level.WARNING,"Service demandé inconnu");
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.log(Level.WARNING,e.toString());
		}
	}

}
