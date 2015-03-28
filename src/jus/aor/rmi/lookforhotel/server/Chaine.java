package jus.aor.rmi.lookforhotel.server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.rmi.lookforhotel.Hotel;
import jus.aor.rmi.lookforhotel._Chaine;

public class Chaine extends UnicastRemoteObject implements _Chaine{

	private static final long serialVersionUID = -4237212311180516187L;
	List<Hotel> hotels = new LinkedList<Hotel>();

	protected Chaine(String filename) throws ParserConfigurationException, SAXException, IOException {
		
		/* récupération des hôtels de la chaîne dans le fichier xml passé en 1er argument */
		DocumentBuilder docBuilder = null;
		Document doc=null;

		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = docBuilder.parse(new File(filename));

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

	}

	@Override
	public List<Hotel> get(String localisation) throws RemoteException {
		List<Hotel> l = new LinkedList<Hotel>();
		for(Hotel h:hotels){
			if(h.localisation.equalsIgnoreCase(localisation)){
				l.add(h);
			}
		}
		return l;
	}
}