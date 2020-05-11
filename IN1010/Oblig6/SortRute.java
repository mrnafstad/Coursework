
public class SortRute extends Rute implements Runnable {
	private char tegn = '#';

	public SortRute(Labyrint lab, int rad, int kolonne) {
		super(lab, rad, kolonne);
	}

	public char tilTegn() {
		return this.tegn;
	}

	public void run() {
		//Sørge for at vi ikke går videre når vi havner på en sort rute
	}
}