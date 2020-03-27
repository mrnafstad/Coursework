

public class PResept extends HvitResept {

	private int rabatt = 108;
	//private int reit = 3;

	public PResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId) {
		super(legemiddel, utskrivendeLege, pasientId, 3);
	}

	@Override
	public double prisAaBetale() {
		// Henter original pris og trekker fra rabatten (108). Hvis dette er større en null returneres det,
		// ellers returneres 0 (når ny pris er under 0)
		double pris =  this.hentLegemiddel().hentPris() - this.rabatt;
		if (pris >= 0) {
			return pris;
		}
		return 0.0;
	}

	@Override
	public String toString() {
		//Bruker Legger til resepttype og bruker super.toString() for å generere resten av informasjonen
		String info = "P-resept \n" + super.toString();
		return info;
	}
}