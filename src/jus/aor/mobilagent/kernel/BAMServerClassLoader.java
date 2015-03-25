/**
 * 
 */
package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarException;

/**
 * @author eudes
 *
 */
public class BAMServerClassLoader extends URLClassLoader {
	
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
		super.addURL(new URL(filename));
	}
	
}
