package jus.aor.mobilagent.kernel;

import java.io.Serializable;
import java.net.URI;

/**
 * Définit une étape de la feuille de route du parcours d'un agent.
 * @author  Morat
 */
public class Etape implements Serializable{
	private static final long serialVersionUID = 4102055378099993883L;
	/** l'adresse du serveur de l'étape */
	protected URI server;
	/** l'action à réaliser à cette étape */
	protected _Action action;
	
	/**
	 * Création d'une étape à partir d'une adresse de serveur et d'une action.
	 * @param server le serveur de l'étape
	 * @param action l'action à exécuter
	 */
	public Etape(URI server, _Action action){
		this.server = server;
		this.action = action;
	}

	
	@Override
	public String toString() {
		return server+"("+action+")";
	}
	
	/**
	 * get action of this step
	 * @return action to do...
	 */
	_Action get_action(){
		return action;
	}
}
