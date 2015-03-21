/**
 * 
 */
package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author eudes
 *
 */
public class BAMAgentClassLoader extends URLClassLoader {

	HashMap<String,byte[]> lib = new HashMap<String,byte[]>();
	Jar jarlib;
	
	public BAMAgentClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);	
	}
	
	/**
	 * Extrait les classes du Jar
	 * @param jar nom du jar à récup & extraire
	 */
	public void extractCode(String jar){
		try {
			jarlib = new Jar(jar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for( Entry<String, byte[]> rsc: jarlib.classIterator()){
			IntegrateCode(rsc.getKey(),rsc.getValue());
		}
	}
	
	/**
	 * Ajoute la classe ds la HashMap
	 * @param name nom de la classe
	 * @param code binaire de la classe
	 */
	private void IntegrateCode(String name,byte[] code){
		lib.put(name, code);
	}
	
	/**
	 * Retourne une instance de la classe stokée ds la hashmap.
	 * @param name le nom de la classe
	 * @return une instance de la classe
	 */
	public Class<?> findClass(String name){
		byte[] class_bin = lib.get(name);
		/* thx javadoc :
		* http://docs.oracle.com/javase/7/docs/api/java/lang/ClassLoader.html
		*/
		return defineClass(name,class_bin,0,class_bin.length);
	}


}
