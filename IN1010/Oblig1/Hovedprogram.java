


import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Hovedprogram {
	public static void main(String[] args) {
		//Lage dataklyngen abel

		Dataklynge abel = new Dataklynge(12);
		leggTilNoder(abel, 650, 64, 1);
		leggTilNoder(abel, 16, 1024, 2);

		//Skrive ut diverse info om abel
		printAntallNoder(abel, 32);
		printAntallNoder(abel, 64);
		printAntallNoder(abel, 128);

		printAntallProsessorer(abel);

		System.out.println("Antall rack: " + abel.getAntallRacks());

		// Følgende skrives ut for abel:

		// Noder med minst 32 GB: 666
		// Noder med minst 64 GB: 666
		// Noder med minst 128 GB: 16
		// Antall prosessorer: 682
		// Antall rack: 56

		// Som obligtesten forutsier

		System.out.println("\n");

		//Lage og skrive ut info om dataklynger laget fra filene git på obligsiden 

		Dataklynge klynge1 = new Dataklynge("regneklynge.txt");
		printAntallNoder(klynge1, 32);
		printAntallNoder(klynge1, 64);
		printAntallNoder(klynge1, 128);

		printAntallProsessorer(klynge1);

		System.out.println("Antall rack: " + klynge1.getAntallRacks());

		System.out.println("\n");

		Dataklynge klynge2 = new Dataklynge("regneklynge2.txt");
		printAntallNoder(klynge2, 32);
		printAntallNoder(klynge2, 64);
		printAntallNoder(klynge2, 128);

		printAntallProsessorer(klynge2);

		System.out.println("Antall rack: " + klynge2.getAntallRacks());

		System.out.println("\n");

		Dataklynge klynge3 = new Dataklynge("regneklynge3.txt");
		printAntallNoder(klynge3, 32);
		printAntallNoder(klynge3, 64);
		printAntallNoder(klynge3, 128);

		printAntallProsessorer(klynge3);

		System.out.println("Antall rack: " + klynge3.getAntallRacks());

		System.out.println("\n");

		Dataklynge klynge4 = new Dataklynge("regneklynge4.txt");
		printAntallNoder(klynge4, 32);
		printAntallNoder(klynge4, 64);
		printAntallNoder(klynge4, 128);

		printAntallProsessorer(klynge4);

		System.out.println("Antall rack: " + klynge4.getAntallRacks());

	}

	public static void leggTilNoder(Dataklynge klynge, int antall, int minne, int prosessorAntall) {
		// En hjelpefunksjon for å legge til noder. Dette gjør psvm mer "ryddig"
		// Her gis enkelt og greit en dataklynge og antall noder som skal legges til med et gitt minne og prosessorAntall
		// som argumenter før de legges til
		for(int i = 0; i < antall; i++) {
			Node node = new Node(minne, prosessorAntall);
			klynge.nyNode(node);
		}
	}

	public static void printAntallNoder(Dataklynge klynge, int paaKrevd) {
		// Hjelpemetode for å skrive ut antall noder med nok minne på klyngen
		System.out.println("Noder med minst " + paaKrevd + " GB: " + klynge.noderMedNokMinne(paaKrevd));
	}

	public static void printAntallProsessorer(Dataklynge klynge) {
		// Hjelpefunksjon for å skrive ut antall prosessorer på klyngen
		System.out.println("Antall prosessorer: " + klynge.antProsessorer());
	}
}