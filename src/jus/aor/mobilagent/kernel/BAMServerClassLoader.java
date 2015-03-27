/**
 * 
 */
package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.jar.JarException;

/**
 * @author eudes
 *
 */
public class BAMServerClassLoader extends URLClassLoader {
	
	HashMap<String,byte[]> lib = new HashMap<String,byte[]>();
	private Jar jarlib;
	
	public BAMServerClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	/**
	 * Appends the specified URL to the list of URLs to search for classes and resources.
	 * If the URL specified is null or is already in the list of URLs, or if this loader is closed, then invoking this method has no effect.
	 * http://http://docs.oracle.com/javase/8/docs/api/java/net/URLClassLoader.html#addURL-java.net.URL-
	 * 
	 * @param filename url à ajouter au repository
	 * @throws IOException 
	 * @throws JarException 
	 */
	public void addURL(String filename) throws JarException, IOException{
		addjar(filename);
	}
	
	/**
	 *  ajoute le contenu du jar au repository
	 * @param jar jar contenant les classes...
	 * @throws JarException
	 * @throws IOException
	 */
	public void addjar(String jar) throws JarException, IOException{
		jarlib = new Jar(jar);
		extractCode(jar);
	}
	
	/**
	 * Extrait le contenu du jar
	 * @param jar jar contenant les classes
	 */
	public void extractCode(String jar){
		try {
			jarlib = new Jar(jar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for( Entry<String, byte[]> rsc: jarlib.classIterator()){
			IntegrateCode(rsc.getKey(),rsc.getValue());
		}
	}
	
	/**
	 * Add class dans la hashmap
	 * @param name key
	 * @param code  value
	 */
	private void IntegrateCode(String name,byte[] code){
		if(lib==null){
			lib = new HashMap<String,byte[]>();
		}
		lib.put(name, code);
	}
	
	public Class<?> findClass(String name){
		byte[] class_bin = lib.get(name);
		
		/* thx javadoc :
		* http://docs.oracle.com/javase/7/docs/api/java/lang/ClassLoader.html
		*/
		return defineClass(name,class_bin,0,class_bin.length);
	}
	
}
