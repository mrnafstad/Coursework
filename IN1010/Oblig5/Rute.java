
public abstract class Rute {

	protected int rad;
	protected int kolonne;

	protected Labyrint labyrint;

	// verdi for å holde styr på om ruten er besøkt før eller ikke
	protected boolean besøkt = false;
	protected Rute nord;
	protected Rute syd;
	protected Rute øst;
	protected Rute vest;

	public Rute(Labyrint lab, int rad, int kolonne) {
		this.rad = rad;
		this.kolonne = kolonne;
		this.labyrint = lab;
	}

	public void setRetninger() {
		// Metode for å sette nord, sør, øst og vest. 
		// Er vi på en henholdsvis topplinje, bunnlinje, høyre kant eller venstre kant settes de til null.
		if (this.rad == 0) {
			this.nord = null;
		} else {
			this.nord = labyrint.hentRuter()[rad - 1][kolonne];
		}



		if (this.kolonne == 0) {
			this.vest = null;
		} else {
			this.vest = labyrint.hentRuter()[rad][kolonne - 1];
		}



		if (this.rad == this.labyrint.hentRader()-1) {
			this.syd = null;
		} else {
			this.syd = labyrint.hentRuter()[rad + 1][kolonne];
		}



		if (this.kolonne == this.labyrint.hentKolonner()-1) {
			this.øst = null;
		} else {
			this.øst = labyrint.hentRuter()[rad][kolonne + 1];
		}
	}

	public int hentRad() {
		return this.rad;
	}

	public int hentKolonne() {
		return this.kolonne;
	}

	public Rute hentNord() {
		return nord;
	}

	public Rute hentSyd() {
		return syd;
	}

	public Rute hentØst() {
		return øst;
	}

	public Rute hentVest() {
		return vest;
	}

	public void gaa(String vei) {
		vei += " (" + this.kolonne + "," + this.rad + ") -->";
		//System.out.println( "--> (" + this.kolonne + "," + this.rad + ")");
		//labyrint.setBesøk(this.rad, this.kolonne);
		this.besøkt = true;
		if (this.syd != null && !this.syd.besøkt) this.syd.gaa(vei);
		if (this.nord != null && !this.nord.besøkt) this.nord.gaa(vei);
		if (this.vest != null && !this.vest.besøkt) this.vest.gaa(vei);
		if (this.øst != null && !this.øst.besøkt) this.øst.gaa(vei);
	}

	public void res() {
		this.besøkt = false;
	}

	public void finnUtvei() {
		String vei = "";
		this.gaa(vei);
	}

	abstract public char tilTegn();
}