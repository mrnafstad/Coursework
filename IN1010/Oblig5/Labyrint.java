import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;

public class Labyrint {

	private Rute[][] ruter;
	private int rader;
	private int kolonner;

	private SortertLenkeliste<String> løsninger = new SortertLenkeliste<String>();

	private Labyrint(char[] rutene, int rader, int kolonner) {

		this.rader = rader;
		this.kolonner = kolonner;
		this.ruter = new Rute[rader][kolonner];
		
		int k = 0;

		// Sette opp rute-array
		for (int i = 0; i < rader; i++) {
			for (int j = 0; j < kolonner; j++) {
				// Sjekke om ruten er sort eller hvit
				if (rutene[k] == '.') {
					// Sjekke om ruten er på kanten
					if (j % (rader - 1) == 0 || i % (kolonner - 1) == 0) {
						ruter[i][j] = new Aapning(this, i, j);
						k ++;
					} else {
						ruter[i][j] = new HvitRute(this, i, j);	
						k ++;						
					}
				} else if (rutene[k] == '#') {
					ruter[i][j] = new SortRute(this, i, j);
					k ++;
				}

				if (k == rader*kolonner) break;
			}
		}

		this.setLinks();
	}


	private void setLinks() {
		// metode for å sette peker til retning for hver rute
		for (int i = 0; i < rader; i ++) {
			for (int j = 0; j < kolonner; j ++) {
				ruter[i][j].setRetninger();
			}
		}
	}

	public SortertLenkeliste<String> hentLøsninger() {
		return this.løsninger;
	}

	public int hentRader() {
		return this.rader;
	}

	public int hentKolonner() {
		return this.kolonner;
	}
	public Rute[][] hentRuter() {
		return ruter;
	}

	

	public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
		//metode for å lese labyrinten fra fil og sette opp labyrinten
		Scanner scanner = new Scanner(fil);
		String[] radKol = scanner.nextLine().split(" ");
		int rad = Integer.parseInt(radKol[0]);
		int kol = Integer.parseInt(radKol[1]);

		char[] ruter1 = new char[rad*kol];

		int k = 0;
		for (int i = 0; i < rad; i++) {
			if (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				for (int j = 0; j < kol; j++) {
					char type = line.charAt(j);
					ruter1[k] = type;
					k++;
					if (k == rad*kol) break;
				}
			} else break;
		}

		scanner.close();
		return new Labyrint(ruter1, rad, kol);
	}

	private void reset() {
		// Metode for å resette rutene mellom kall på løsninger
		for (int i = 0; i < rader; i++) {
			for (int j = 0; j < kolonner; j++) {
				ruter[i][j].res();
			}
		}

		this.løsninger = new SortertLenkeliste<String>();
	}


	public SortertLenkeliste<String> finnUtveiFra(int kol, int rad) {
		// Metode for å finne utveier. Først nullstilles gamle løsninger før vi finner nyew
		this.reset();
		this.ruter[rad][kol].finnUtvei();
		
		return this.løsninger;
	}


	public String toString() {
		String lab = "";
		for (int i = 0; i < rader; i++) {
			for (int j = 0; j < kolonner; j++) {
				lab += ruter[i][j].tilTegn();
			}
			lab += "\n";
		}
		return lab;
	}
}