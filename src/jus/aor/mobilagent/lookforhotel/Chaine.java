package jus.aor.mobilagent.lookforhotel;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import jus.aor.mobilagent.kernel._Service;

public class Chaine implements _Chaine,_Service<List<Hotel>> {
	
	List<Hotel> hotels = new LinkedList<Hotel>();

	public Chaine(Object...args) {
		/* récupération des hôtels de la chaîne dans le fichier xml passé en 1er argument */
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new File((String)args[0]));

			String name, localisation;
			NodeList list = doc.getElementsByTagName("Hotel");
			NamedNodeMap attrs;
			/* acquisition de toutes les entrées de la base d'hôtels */
			for(int i =0; i<list.getLength();i++) {
				attrs = list.item(i).getAttributes();
				name=attrs.getNamedItem("name").getNodeValue();
				localisation=attrs.getNamedItem("localisation").getNodeValue();
				hotels.add(new Hotel(name,localisation));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne la liste d'hotels matchant la loc
	 */
	@Override
	public List<Hotel> get(String localisation) {
		List<Hotel> l = new LinkedList<Hotel>();
		for(Hotel h:hotels){
			if(h.localisation.equalsIgnoreCase(localisation)){
				l.add(h);
			}
		}
		return l;
	}

	/**
	 * Action effectuée à l'appel du service
	 * Retourne le resultat de get
	 * un seul param, la loc
	 */
	@Override
	public List<Hotel> call(Object... params) throws IllegalArgumentException {
		return get(params[0].toString());
	}
}