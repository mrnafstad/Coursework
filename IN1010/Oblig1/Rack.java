import java.util.ArrayList;

public class Rack {
	// Klasse for racks. Jeg har valgt å bruke ArrayList for å ha kontroll på nodene, siden ArrayLists er dynamiske. 
	// Strengt tatt kunne jeg valgt vanlige arrays her, siden vi har et maksimalt antall noder per rack, men vi
	// sparer kanskje litt plass dersom noen racks ikke fylles helt opp.
	private ArrayList<Node> noder;
	private int maksNoder;

	public Rack(int maksNoder) {
		// Konstruktør for racks, initierer en ArrayList som kan fylles med Node-objekter
		// Setter også et maks antall noder for racket
		this.noder = new ArrayList<Node>();
		this.maksNoder = maksNoder;
	}


	public boolean nyNode(Node node) {
		// Legger til et nytt Node-objekt i racket hvis det er ledig (og returnerer true)
		// Dersom racket er fullt returneres false, dette boolean håndteres i Dataklynge-klassen
		if (this.noder.size() >= this.maksNoder) {
			return false;
		}
		this.noder.add(node);
		return true;
	}

	public int getAntall() {
		// Henter antall noder i racket
		return this.noder.size();
	}

	public int getProsessorAnt() {
		// Henter antall prosessorer i racket ved å summe antall prosessorer i hver enkelte node
		int sum = 0;
		for(int i = 0; i < this.noder.size(); i++) {
			sum += this.noder.get(i).getProsessorAntall();
		}
		return sum;
	}

	public int getNodeMinne( int krevdMinne) {
		// Henter totalt minne i racket ved å summe minnet til hver enkelt node
		int sum = 0;
		for (int i = 0; i < this.noder.size(); i++) {
			if(this.noder.get(i).getMinne() >= krevdMinne){
				sum++;
			}
		}
		return sum;
	}

}