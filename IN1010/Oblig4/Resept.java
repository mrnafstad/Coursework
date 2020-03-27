

public abstract class Resept {

	private static int count = 0;
	private int id;

	private int reit;
	private Pasient pasient;

	private Legemiddel legemiddel;
	private Lege utskrivendeLege;

	public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		this.id = count;
		count ++;

		this.legemiddel = legemiddel;
		this.utskrivendeLege = utskrivendeLege;
		this.pasient = pasient;
		this.reit = reit;
	}

	public int hentId() {
		return this.id;
	}

	public Pasient hentPasient() {
		return this.pasient;
	}

	public int hentReit() {
		return this.reit;
	}

	public Legemiddel hentLegemiddel() {
		return this.legemiddel;
	}

	public Lege hentLege() {
		return this.utskrivendeLege;
	}

	public boolean bruk() {
		// Sjekker om det er flere uttak igjen (reit < 0). Hvis ja, trekk fra én på reit og returner true
		// ellers returner false
		if (this.reit > 0) {
			this.reit --;
			return true;
		}
		return false;
	}

	abstract public String farge();

	abstract public double prisAaBetale();

	@Override
	public String toString() {
		//Lager en enkel streng med generell reseptinformasjon
		String info = "Resept-ID: " + this.id + "\n"
					+ "Pasient-ID: " + this.pasient.hentNavn() + "\n"
					+ "Bruk igjen: " + this.reit + "\n"
					+ "Legemiddel: " + this.legemiddel.hentNavn() + "\n"
					+ "Utskrivende lege: " + this.utskrivendeLege.hentNavn() + "\n";
		return info;
	}
}