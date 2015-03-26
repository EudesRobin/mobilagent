package jus.aor.mobilagent.lookforhotel;

import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel._Action;

/**
 * Représente un client effectuant une requête lui permettant
 * d'obtenir les numéros de téléphone des hôtels répondant à son critère de choix.
 * @author  Morat
 */

public class LookForHotel extends Agent{

	private static final long serialVersionUID = -6297256119076338089L;
	/** le critère de localisaton choisi */
	private String localisation;
	// ...
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String... args){
		localisation = args[0];
	}
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() {
		// ...
		return 0;
		
	}
	
	protected _Action get_hotels = new _Action(){

		private static final long serialVersionUID = 8258631604519690491L;

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	protected _Action get_nums = new _Action(){

		private static final long serialVersionUID = 6786941387813946982L;

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			
		}
		
	};

	// ...
}
