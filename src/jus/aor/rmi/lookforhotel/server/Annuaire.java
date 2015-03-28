package jus.aor.rmi.lookforhotel.server;


import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.rmi.lookforhotel.Numero;
import jus.aor.rmi.lookforhotel._Annuaire;



/**
 * @author eudes
 *
 */
public class Annuaire extends UnicastRemoteObject implements _Annuaire {
	
	private static final long serialVersionUID = 7269239953815193065L;
	private HashMap<String,Numero> annuaire = new HashMap<String,Numero>();

	protected Annuaire(String filename) throws SAXException, IOException, ParserConfigurationException {
		
		String name, numero;
		/* Récupération de l'annuaire dans le fichier xml */
		DocumentBuilder docBuilder = null;
		Document doc=null;

		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = docBuilder.parse(new File(filename));


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

	}

	@Override
	public Numero get(String abonne) throws RemoteException {
		return annuaire.get(abonne);
	}


}