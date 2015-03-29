package jus.aor.courtage.kernel;

import java.net.URI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface _Registre extends Remote{
	
	/**
	 * Ajoute le service dans le registre, par defaut disponible
	 * @param name nom du servic
	 * @param src serveur proposant le service
	 * @throws RemoteException
	 */
	public void registerservice(String name,URI src) throws RemoteException;
	/**
	 * Ajoute le service dans le registre
	 * @param name nom du service
	 * @param src serveur proposant le service
	 * @param availability disponibilité du service
	 * @throws RemoteException
	 */
	public void registerservice(String name,URI src,boolean availability) throws RemoteException;
	/**
	 * Actualisation de la disponibilité du service
	 * @param name nom du service
	 * @param src serveur concernée
	 * @param availabiity disponibilitée souhaitée du service
	 * @throws RemoteException
	 */
	public void updateservice(String name,URI src,boolean availability) throws RemoteException;
	
	/**
	 * Retourne la liste des serveurs disponibles pour le service recherché
	 * @param name service recherché
	 * @return liste des serveurs disponibles
	 * @throws RemoteException
	 */
	public LinkedList<URI> getservice(String name) throws RemoteException;
	
}
