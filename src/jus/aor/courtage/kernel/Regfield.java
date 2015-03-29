package jus.aor.courtage.kernel;

import java.net.URI;

public class Regfield {
	
	URI serveur;
	boolean av;

	public Regfield(URI ur,boolean availability) {
		serveur=ur;
		av=availability;
	}

}
