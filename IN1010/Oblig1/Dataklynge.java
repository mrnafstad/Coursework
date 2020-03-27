import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Dataklynge {
	// Klasse for Dataklynge.
	// Har igjen valgt ArrayList for å holde orden på racks siden det er dynamisk, men i dette tilfellet er det et åpenbart bedre valg enn
	// vanlig array, siden det ikke skal defineres noe maksimalt antall racks på klyngen. Altså er det gunstig
	// med ArrayList, slik at det kan lages nye racks når det trengs.
	private ArrayList<Rack> racks;
	private int noderPerRack;

	public Dataklynge(int noderPerRack) {
		// Dataklynge-konstrukter:
		// Initierer en ArrayList med Rack-objekter og setter antall noder per rack
		this.racks = new ArrayList<Rack>();
		this.noderPerRack = noderPerRack;
	}

	public Dataklynge(String filNavn) {
		this.racks = new ArrayList<Rack>();
		File fil = new File(filNavn);
		ArrayList<Integer> verdier = lesFil(fil);
		this.noderPerRack = verdier.get(0);
		for (int i = 1; i < verdier.size(); i += 3) {
			for (int j = 0; j < verdier.get(i); j++) {
				this.nyNode(new Node(verdier.get(i + 1), verdier.get(i+2)));
			}
		}
	}


	//funksjonalitet for å lese fil og lage nye klynger
	private static ArrayList<Integer> lesFil(File fil) {
		// Hjelpefunksjon som henter ut de aktuelle verdiene fra en gitt fil
		// Kun integers legges til i ArrayListen som returneres
		ArrayList<Integer> denneFil = new ArrayList<Integer>();
		try {
			Scanner myReader = new Scanner(fil);
			while (myReader.hasNext()) {
				if (myReader.hasNextInt()) {
					denneFil.add(myReader.nextInt());
				} else {
					myReader.next();
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fil ikke funnet");
		}
		return denneFil;
	}


	public int getAntallRacks() {
		// Henter ut antall racks i dataklyngen
		return this.racks.size();
	}

	public void nyNode(Node node) {
		// Legger til en ny node i dataklyngen. Først sjekkes det om det er ledig plass i et eksisterende rack
		// Dersom alle racks er fulle lages et nytt rack, og den aktuelle noden legges til i det nye racket.
		// En alternativ (og kanskje bedre) måte å håndtere dette på kunne vært å tillegne en private boolean isFull i
		// Rack klassen, og bare gjort en get på den. Men jeg holder meg til min originale løsning denne gangen.

		boolean added = false;

		for (int i = 0; i < this.racks.size(); i++) {
			if(this.racks.get(i).nyNode(node)) {
				added = true;
				break;
			}
		}

		if(!added) {
			this.racks.add(new Rack(this.noderPerRack));
			this.racks.get(this.racks.size() - 1).nyNode(node);
		}
	}

	public int antProsessorer() {
		// Henter antall prosessorer i dataklyngen ved å hente antall prosessorer i hvert enkelt Rack-objekt
		int sum = 0;
		for( int i = 0; i < this.racks.size(); i++){
			sum += this.racks.get(i).getProsessorAnt();
		}
		return sum;
	}

	public int noderMedNokMinne(int paakrevdMinne) {
		// Henter antall noder i dataklyngen med nok minne ved å summe antall noder med nok minne i hvert Rack-objekt
		int noder = 0;
		for( int i = 0; i < this.racks.size(); i++){
			noder += this.racks.get(i).getNodeMinne(paakrevdMinne);
		}
		return noder;
	}
}