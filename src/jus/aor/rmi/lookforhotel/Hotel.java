package jus.aor.rmi.lookforhotel;

import java.io.Serializable;

/**
 * Un hotel qui est caractérisé par son nom et sa localisation.
 * @author Morat 
 */
public class Hotel implements Serializable {

	private static final long serialVersionUID = -3396563769444283693L;
	/** la localisation de l'hôtel */
	public String localisation;
	/** le nom de l'hôtel */
	public String name;
	/**
	 * Définition d'un hôtel par son nom et sa localisation.
	 * @param name le nom de l'hôtel
	 * @param localisation la localisation de l'hôtel
	 */
	public Hotel(String name, String localisation) {
		this.name=name; this.localisation=localisation;
	}
	@Override
	public String toString() {
		return "Hotel{"+name+","+localisation+"}";
	}
}
