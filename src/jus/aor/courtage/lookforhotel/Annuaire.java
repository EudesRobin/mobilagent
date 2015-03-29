package jus.aor.courtage.lookforhotel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jus.aor.mobilagent.kernel._Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;



/**
 * @author eudes
 *
 */
public class Annuaire implements _Annuaire,_Service<HashMap<String,Numero>> {
	
	private HashMap<String,Numero> annuaire = new HashMap<String,Numero>();

	public Annuaire(Object... src) {

		String name, numero;
		/* Récupération de l'annuaire dans le fichier xml */
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new File((String)src[0]));

			NodeList list = doc.getElementsByTagName("Telephone");
			NamedNodeMap attrs;
			/* acquisition de toutes les entrées de l'annuaire */
			for(int i =0; i<list.getLength();i++) {
				attrs = list.item(i).getAttributes();
				name=attrs.getNamedItem("name").getNodeValue();
				numero=attrs.getNamedItem("numero").getNodeValue();
				// add val in hashmap
				annuaire.put(name, new Numero(numero));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * renvoie le numero matchant l'id ( nom de l'hotel )
	 */
	@Override
	public Numero get(String id) {
		return annuaire.get(id);
	}

	/**
	 * Action effectuée à l'appel de ce service
	 * param : liste d'hotels
	 * return hashmap hotel , numero
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Numero> call(Object... params) throws IllegalArgumentException {
		
		List<Hotel> hotels = (List<Hotel>) params[0];
		HashMap<String,Numero> cmp = new HashMap<String,Numero>();

		for(Hotel h:hotels){
			cmp.put(h.name,get(h.name));
		}

		return cmp;
	}
}