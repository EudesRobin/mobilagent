package jus.aor.rmi.lookforhotel.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import jus.aor.rmi.lookforhotel._Annuaire;
import jus.aor.rmi.lookforhotel._Chaine;

/**
 * @author eudes
 *
 */
public class RMIServer {

	/**
	 * 
	 */
	public RMIServer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		if(System.getSecurityManager()==null){
			System.setSecurityManager(new SecurityManager());
		}
		
		if(args.length!=3){
			throw new Exception("Missing parameters : <service_name> <filename> <port_serveur>");
		}
		
		if(args[0].equalsIgnoreCase("Chaine")){
			_Chaine skeleton = (_Chaine)UnicastRemoteObject.exportObject(new Chaine(args[1]),Integer.parseInt(args[2]));
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[2]));
			registry.rebind("Chaine",skeleton);
			
		}else if(args[0].equalsIgnoreCase("Annuaire")){
			_Annuaire skeleton = (_Annuaire)UnicastRemoteObject.exportObject(new Annuaire(args[1]),Integer.parseInt(args[2]));
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[2]));
			registry.rebind("Annuaire",skeleton);
		}
	}

}
