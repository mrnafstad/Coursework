
public abstract class Rute {

	protected int rad;
	protected int kolonne;

	protected Labyrint labyrint;


	protected Rute opp;
	protected Rute ned;
	protected Rute høyre;
	protected Rute venstre;

	public Rute(int rad, int kolonne) {
		this.rad = rad;
		this.kolonne = kolonne;
	}

	public Rute hentOpp() {
		if (this.rad == 0) {
			return null;
		}
		return Labyrint[rad - 1][kolonne];
	}

	public Rute hentNed() {
		if (this.rad == this.Labyrint.hentRader()) {
			return null;
		}

		return Labyrint[rad + 1][kolonne];
	}

	public Rute hentHøyre() {
		if (this.kolonne == 0) {
			return null;
		} 
		return Labyrint[rad][kolonne - 1];
	}

	public Rute hentVenstre() {
		if (this.kolonne == this.Labyrint.hentKolonner()) {
			return null;
		} 
		return Labyrint[rad][kolonne + 1];
	}

	abstract public char tilTegn();
}