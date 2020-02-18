

public class HvitResept extends Resept {

	public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
		super(legemiddel, utskrivendeLege, pasientId, reit);
	}

	@Override
	public String farge() {
		String hvit = "Hvit";
		return hvit;
	}

	@Override
	public double prisAaBetale() {
		// Henter og returnerer den originale prisen på legemiddelet
		return this.hentLegemiddel().hentPris();
	}

	@Override
	public String toString() {
		//Bruker Legger til resepttype og bruker super.toString() for å generere resten av informasjonen
		String info = "Hvit resept \n" + super.toString();
		return info;
	}
}