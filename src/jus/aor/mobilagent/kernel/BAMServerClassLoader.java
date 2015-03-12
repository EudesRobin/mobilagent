/**
 * 
 */
package jus.aor.mobilagent.kernel;

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
		// TODO Auto-generated constructor stub
	}

}
