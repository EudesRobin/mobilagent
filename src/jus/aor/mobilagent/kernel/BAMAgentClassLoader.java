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
public class BAMAgentClassLoader extends URLClassLoader {

	public BAMAgentClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
		// TODO Auto-generated constructor stub
		
	}


}
