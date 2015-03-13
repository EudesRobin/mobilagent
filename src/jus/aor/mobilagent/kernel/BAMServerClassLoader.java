/**
 * 
 */
package jus.aor.mobilagent.kernel;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

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
	 */
	public void addURL(String filename){
		try {
			super.addURL(new URL(filename));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
