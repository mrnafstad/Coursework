
public class Aapning extends HvitRute implements Runnable {

	public Aapning(Labyrint lab, int rad, int kolonne) {
		super(lab, rad, kolonne);
	}

	public void run() {
		vei +=" (" + this.kolonne + "," + this.rad + ") Løst! \n";
		// Legge til strengen i labyrintens løsningsliste
		this.labyrint.laasIgjen();
		try {
			this.labyrint.hentLøsninger().leggTil(vei);
		}
		finally {
			this.labyrint.laasOpp();
		}
	}
}