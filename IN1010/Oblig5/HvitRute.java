
public class HvitRute extends Rute {
	protected char type = '.';

	public HvitRute(Labyrint lab, int rad, int kolonne) {
		super(lab, rad, kolonne);
	}

	public char tilTegn() {
		return this.type;
	}
}