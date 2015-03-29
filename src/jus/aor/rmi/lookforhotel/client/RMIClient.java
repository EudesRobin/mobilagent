package jus.aor.rmi.lookforhotel.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import jus.aor.rmi.lookforhotel.IOHandler;
import jus.aor.rmi.lookforhotel.Hotel;
import jus.aor.rmi.lookforhotel.Numero;
import jus.aor.rmi.lookforhotel._Annuaire;
import jus.aor.rmi.lookforhotel._Chaine;

/**
 * @author eudes
 *
 */
public class RMIClient {

	/**
	 * @param args <serveur name> <serveur port> <stub name> [<localisation>]
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

		// au min, on interroge une chaine, un annuaire. description = 4 params pour chaine, 3 pour l'annuaire 
		if(args.length<7 || (args.length%4)-3!=0){
			logger.log(Level.WARNING,"Missing parameters : une chaine/un annuaire min, description : <serveur name> <serveur port> <stub name>");
		}

		int count = 0;
		List<Hotel> hotels = new LinkedList<Hotel>();
		HashMap<String,Numero> annuaire = new HashMap<String,Numero>();
		long begin = System.currentTimeMillis();
		/**
		 * On peut contacter n chaines d'hotels, décrite par 4 params :  <serveur name> <serveur port> "Chaine" <localisation>
		 * Et on termine par 1 unique annuaire décrit par 3 params :  <serveur name> <serveur port> "Annuaire"
		 */
		while(count<args.length){
			try {
				if(args[count+2].equalsIgnoreCase("Chaine")){
					Registry registry;
					registry = LocateRegistry.getRegistry(InetAddress.getByName(args[count]).getHostAddress(),Integer.parseInt(args[count+1]));
					_Chaine stub = (_Chaine) registry.lookup(args[count+2]);
					hotels.addAll(stub.get(args[count+3]));
					count+=4;
				}else if(args[count+2].equalsIgnoreCase("Annuaire")){
					Registry registry = LocateRegistry.getRegistry(InetAddress.getByName(args[count]).getHostAddress(),Integer.parseInt(args[count+1]));
					_Annuaire stub = (_Annuaire) registry.lookup(args[count+2]);
					for(Hotel h:hotels){
						annuaire.put(h.name,stub.get(h.name));
					}
					count+=3;
				}
			} catch (NumberFormatException | RemoteException
					| UnknownHostException | NotBoundException e ) {
				logger.log(Level.WARNING,e.toString());
			}
		}
		long end = System.currentTimeMillis();

		/**
		 * On affiche le résultat final :)
		 */
		Set<?> s = annuaire.entrySet();
		Iterator<?> it = s.iterator();
		while(it.hasNext()){
			@SuppressWarnings("unchecked")
			Map.Entry<String,Numero> ent = (Map.Entry<String,Numero>)it.next();
			logger.log(Level.FINE,ent.getKey()+" : "+ent.getValue());
		}
		logger.log(Level.FINE,"Durée interrogation (hors affichage) :"+(end-begin)+" ms");
		logger.log(Level.FINE,"Nombre d'hotels : " + hotels.size());

	}

}
