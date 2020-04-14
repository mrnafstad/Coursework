
public class Labyrint {

	private Rute[][] ruter;
	private int rader;
	private int kolonner;

	private Labyrint(ArrayList<Rute> ruter, int rader, int kolonner) {
		this.rader = rader;
		this.kolonner = kolonner;
		this.ruter = new Rute[rader][kolonner];
		for (int k = 0; k < ruter.size(); k++) {
			for (int i = 0; i < rader; i++) {
				for (int j = 0; j < kolonner; j++) {
					this.ruter[i][j] = 
				}
			}
		}
	}

	public int hentRader() {
		return this.rader;
	}

	public int hentKolonner() {
		return this.kolonner;
	}

	public String toString() {
		String lab = "";
		for (int i = 0; i < rader; i++) {
			for (int j = 0; j < kolonner; j++) {
				lab += rader[i][j].tilTegn();
			}
			lab += "\n";
		}
		return lab;
	}
}