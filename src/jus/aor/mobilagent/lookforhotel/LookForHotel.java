package jus.aor.mobilagent.lookforhotel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel.Starter;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel._Service;

/**
 * Représente un client effectuant une requête lui permettant
 * d'obtenir les numéros de téléphone des hôtels répondant à son critère de choix.
 * @author  Morat
 */

public class LookForHotel extends Agent{

	private static final long serialVersionUID = -6297256119076338089L;
	/** le critère de localisaton choisi */
	private String localisation;
	private List<Hotel> hotels;
	private HashMap<String,Numero> res;
	
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(Object... args){
		super();
		localisation = (String)args[0];
		hotels =new LinkedList<Hotel>();
		res= new HashMap<String,Numero>();
	}
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() {
		return System.currentTimeMillis()-super.begin;
	}
	
	/**
	 * On peut interroger plusieurs serveurs pour avoir une liste d'hotels
	 */
	protected _Action get_hotels = new _Action(){

		private static final long serialVersionUID = 8258631604519690491L;

		@SuppressWarnings("unchecked")
		@Override
		public void execute() {
			_Service<List<Hotel>> service = (_Service<List<Hotel>>) srv.getService("Hotels");
			hotels.addAll(service.call(localisation));
		}
	};
	
	/**
	 * On interroge qu'un seul annuaire
	 */
	protected _Action get_nums = new _Action(){

		private static final long serialVersionUID = 6786941387813946982L;

		@SuppressWarnings("unchecked")
		@Override
		public void execute() {
			_Service<HashMap<String,Numero>> service = (_Service<HashMap<String, Numero>>) srv.getService("Telephones");
			res = service.call(hotels);	
		}		
	};
	
	protected _Action print = new _Action(){

		private static final long serialVersionUID = -5492276424526198603L;

		@Override
		public void execute() {	
			for(Hotel h:hotels){
				Starter.get_logger().log(Level.FINE,"hotel : "+ h.name + "num : " + res.get(h.name));
			}
			Starter.get_logger().log(Level.FINE,"durée parcours agent : " + call() + "ms");
		}
	};

}
