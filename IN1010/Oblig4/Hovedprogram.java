

// 1) Metodene i denne obligen er relativt rett frem, og jeg synes derfor det er veldig
//    lite som trenger kommentering siden det er veldig lett å se hva som skjer (for det
//    meste konstruktører og getters). Har slengt inn noen kommentarer der jeg tror det kan
//    være behov.

// 2) Når det gjelder datastrukturtegningen har jeg tolket det som at det viktigste er å få
//    frem hvor ting skal peke, så jeg har droppet all kode (ville blitt mye ellers også) og
//    fokusert på atributter og pekere.

public class Hovedprogram {
	public static void main(String[] args) {

		System.out.println("\n");

		//lage en vanlig lege og skrive ut all info
		Lege allmen = new Lege("Nordmann, Ola");
		System.out.println(allmen.toString());

		//lage en spesialst og skriver ut all info
		Spesialist spes = new Spesialist("Nordame, Kari", 123);
		System.out.println(spes.toString());
		
		//Lage et vanlig legemiddel og skriver ut all info
		Vanlig vLegemiddel = new Vanlig("Metformin", 200, 500);
		System.out.println(vLegemiddel.toString());

		//Lage et ekstra vanlig legemiddel for PResepten. Fordi mer realistisk
		Vanlig forPResept = new Vanlig("Oralcon", 500, 10);

		//Lage et vanedannende legemiddel og skriver ut all info
		Vanedannende vaneLegemiddel = new Vanedannende("Paracet", 100, 500, 4000);
		System.out.println(vaneLegemiddel.toString());

		//lage et narkotisk legemiddel og skriver ut all info
		Narkotisk narkotiskLegemiddel = new Narkotisk("Kodein", 500, 25, 30);
		System.out.println(narkotiskLegemiddel.toString());

		//Lage en blå resept med det første vanlige legemiddelet, skrevet ut av spesialist
		BlåResept blaaResept = new BlåResept(vLegemiddel, spes, 0, 0);
		System.out.println(blaaResept.toString());

		//Lage en hvit resept med det vanedannende legemiddelet, skrevet ut av vanlig lege
		HvitResept hvitResept = new HvitResept(vaneLegemiddel, allmen, 1, 5);
		System.out.println(hvitResept.toString());

		//Lage en p-resept med det vanlige legemiddelet laget for denne resepten, skrevet ut av vanlig lege
		PResept pResept = new PResept(forPResept, allmen, 2);
		System.out.println(pResept.toString());

		//Lage en militærresept med et narkotisk legemedillen, skrevet ut av vanlig lege
		MResept mResept = new MResept(narkotiskLegemiddel, allmen, 3, 3);
		System.out.println(mResept.toString());
	}
}