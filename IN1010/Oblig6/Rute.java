
public abstract class Rute implements Runnable {

	protected int rad;
	protected int kolonne;

	protected Labyrint labyrint;

	// verdi for å holde styr på om ruten er besøkt før eller ikke
	protected boolean besøkt = false;
	protected Rute nord;
	protected Rute syd;
	protected Rute øst;
	protected Rute vest;

	protected String vei;
	protected Thread tråden;

	public Rute(Labyrint lab, int rad, int kolonne) {
		this.rad = rad;
		this.kolonne = kolonne;
		this.labyrint = lab;
		this.vei = "";
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

	public void setVei(String veien) {
		this.vei = veien;
	}

	public void run() {
		this.vei += " (" + this.kolonne + "," + this.rad + ") -->";
		//System.out.println( "--> (" + this.kolonne + "," + this.rad + ")");
		//labyrint.setBesøk(this.rad, this.kolonne);

		// Det er nødvendig å lage de nye trådene før vi graver videre ned i eksisterende tråd,
		// ellers vil vi først grave oss til bunnen i eksisterende tråd før vi lager nye og 
		// programmet blir sekvensielt, ikke parallelt.

		this.besøkt = true;
		//System.out.println(vei);
		if (this.syd != null && !this.syd.besøkt) { 
			this.syd.setVei(this.vei);
			Thread tråd1 = new Thread(this.syd);
			tråd1.start();
			try {
				tråd1.join();
			} catch(InterruptedException e) {
				System.out.println("Tråd avbrutt");
			}
		} 
		if (this.nord != null && !this.nord.besøkt) {
			this.nord.setVei(this.vei);
			Thread tråd2 = new Thread(this.nord);
			tråd2.start();
			try {
				tråd2.join();
			} catch(InterruptedException e) {
				System.out.println("Tråd avbrutt");
			}
		}
		if (this.vest != null && !this.vest.besøkt) {
			this.vest.setVei(this.vei);
			Thread tråd3 = new Thread(this.vest);
			tråd3.start();
			try {
				tråd3.join();
			} catch(InterruptedException e) {
				System.out.println("Tråd avbrutt");
			}
		}
		if (this.øst != null && !this.øst.besøkt) {
			this.øst.setVei(vei);
			this.øst.run();
		}

		// Må håndtere blindvei!
	}

	public void res() {
		this.besøkt = false;
	}

	public void finnUtvei() throws InterruptedException {
		
		this.run();
	}

	abstract public char tilTegn();
}