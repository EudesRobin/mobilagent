package jus.aor.mobilagent.hello;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel.Agent;

/**
 * Classe de test élémentaire pour le bus à agents mobiles
 * @author  Morat
 */
public class Hello extends Agent{


	private static final long serialVersionUID = 3882387847447156013L;

	/**
	  * construction d'un agent de type hello.
	  * @param args aucun argument n'est requis
	  */
	 public Hello(Object... args) {
	 }
	 
	 /**
	 * l'action à entreprendre sur les serveurs visités  
	 */
	protected _Action doIt = new _Action(){
		/**
		 * 
		 */
		private static final long serialVersionUID = -9129644307555501553L;

		@Override
		public void execute() {
			System.out.println("Hello ~");	
		}
	};
	
	/* (non-Javadoc)
	 * @see jus.aor.mobilagent.kernel.Agent#retour()
	 */
	//@Override (  ça override rien du tout...)
	protected _Action retour(){
		return doIt;
	}
}
