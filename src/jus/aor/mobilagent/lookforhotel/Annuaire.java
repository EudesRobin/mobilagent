/**
 * 
 */
package jus.aor.mobilagent.lookforhotel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jus.aor.mobilagent.kernel._Service;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * @author eudes
 *
 */
public class Annuaire implements _Annuaire,_Service<HashMap<String,Numero>> {

	String name, numero;
	HashMap<String,Numero> map_service = new HashMap<String,Numero>();
	
	public Annuaire(String... src) {

		/* Récupération de l'annuaire dans le fichier xml */
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new File(src[0]));

			
			NodeList list = doc.getElementsByTagName("Telephone");
			NamedNodeMap attrs;
			/* acquisition de toutes les entrées de l'annuaire */
			for(int i =0; i<list.getLength();i++) {
				attrs = list.item(i).getAttributes();
				name=attrs.getNamedItem("name").getNodeValue();
				numero=attrs.getNamedItem("numero").getNodeValue();
				// add val in hashmap
				map_service.put(name, new Numero(numero));
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public Numero get(String id) {
		return map_service.get(id);
		}

	@Override
	public HashMap<String, Numero> call(Object... params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
