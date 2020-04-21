
public class Aapning extends HvitRute {

	public Aapning(Labyrint lab, int rad, int kolonne) {
		super(lab, rad, kolonne);
	}

	@Override
	public void gaa(String vei) {
		vei +=" --> (" + this.kolonne + "," + this.rad + ") Løst! \n";
		// Legge til strengen i labyrintens løsningsliste
		this.labyrint.hentLøsninger().leggTil(vei);
	}
}