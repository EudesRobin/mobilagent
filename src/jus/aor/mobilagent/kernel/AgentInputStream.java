package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 *  @author Morat
 * 
 */
class AgentInputStream extends ObjectInputStream{

	BAMAgentClassLoader loader;
	AgentInputStream(InputStream is, BAMAgentClassLoader cl) throws IOException{
		super(is);
		loader = cl;
	}

	protected Class<?> resolveClass(ObjectStreamClass cl) throws ClassNotFoundException, IOException{

		try {
			return loader.loadClass(cl.getName());
		} catch (Exception e) {
			/* echec "systématique" de l'instruction précédente, ça trouve jamais la classe... Why ?
			*  Heureusement, super.resolveClass est là pour nous sauver \o/
			*/
			return super.resolveClass(cl);
		}
	}
}