package jus.aor.rmi.lookforhotel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Définit une chaine d'hôtels et une fonctionnalité permettant d'obtenir l'ensemble des hotels de cette chaine
 * pour une localisation donnée.
 * @author Morat 
 */
public interface _Chaine extends Remote{
	/**
	 * Restitue la liste des hotels situés dans la localisation.
	 * @param localisation le lieu où l'on recherche des hotels
	 * @return la liste des hotels trouvés
	 * @throws RemoteException
	 */
	public abstract List<Hotel> get(String localisation) throws RemoteException;
}