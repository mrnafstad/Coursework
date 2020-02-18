
public class TestResepter {
	public static void main(String[] args) {
		Lege lege = new Lege("Nordmann, Ola");

		Narkotisk testMiddel = new Narkotisk("Kodein", 500, 25, 30);

		PResept t1 = new PResept(testMiddel, lege, 2);

		MResept t2 = new MResept(testMiddel, lege, 3, 2);

		HvitResept t3 = new HvitResept(testMiddel, lege, 4, 3);

		BlåResept t4 = new BlåResept(testMiddel, lege, 5, 5);

		testResept(t1, 0, "Hvit", 2, 3, "Nordmann, Ola", "Kodein", 500-108);
		testResept(t2, 1, "Hvit", 3, 2, "Nordmann, Ola", "Kodein", 0);
		testResept(t3, 2, "Hvit", 4, 3, "Nordmann, Ola", "Kodein", 500);
		testResept(t4, 3, "Blaa", 5, 5, "Nordmann, Ola", "Kodein", 500*0.25);
	} 

	static void testResept(Resept r, int forventetID, String farge, int pasientID, int antall, String legeNavn, String legemiddelNavn, double forventetPris) {
		// Metode som tester om verdiene til et Resept-objekt (første argument) matcher med de originale inputverdiene (resterende argumenter)
		testID(r, forventetID);
		testFarge(r, farge);
		testPasID(r, pasientID);
		testBruk(r, antall);
		testLegemiddel(r, legemiddelNavn);
		testLege(r, legeNavn);
		testPris(r, forventetPris);

		System.out.println("\n");
	}	

	static void testID(Resept r, int id) {
		// Hjelpemetode for å sjekke at faktisk id matcher forventet id
		if (r.hentId() == id) {
			System.out.println("Riktig 1");
		} else {
			System.out.println("Feil 1");
		}
	}

	static void testFarge(Resept r, String farge) {
		// Hjelpemetode for å sjekke at metoden farge() returnerer riktig verdi
		if (r.farge().equals(farge)) {
			System.out.println("Riktig 2");
		} else {
			System.out.println("Feil 2");
			System.out.println(r.farge());
		}
	}

	static void testPasID(Resept r, int id) {
		// Hjelpemetode for å sjekke at faktisk pasientId matcher forventet pasientId
		if (r.hentPasientId() == id) {
			System.out.println("Riktig 3");
		} else {
			System.out.println("Feil 3");
		}
	}

	static void testBruk(Resept r, int antall) {
		// Hjelpemetode for å sjekke at metoden bruk() virker som den skal
		// Først brukes resepten opp mens antall bruk telles. 
		// Så sjekkes det om inputverdien for bruk matcher med faktisk antall bruk (count == antall)
		// Så sjekkes det at bruk() returnerer false når resepten er brukt opp
		int count = 0;
		while (r.bruk()) {
			count ++;
		}
		if (count == antall) {
			System.out.println("Riktig 4");
		} else {
			System.out.println("Feil 4");
		}
		if (!r.bruk()) {
			System.out.println("Riktig 5");
		} else {
			System.out.println("Feil 5");
		}
	}

	static void testLegemiddel(Resept r, String legemiddelNavn) {
		// Hjelpemetode for å sjekke at faktisk legemiddelnavn matcher forventet legemiddelnavn
		if (r.hentLegemiddel().hentNavn().equals(legemiddelNavn)) {
			System.out.println("Riktig 6");
		} else {
			System.out.println("Feil 6");
		}
	}	

	static void testLege(Resept r, String legeNavn) {
		// Hjelpemetode for å sjekke at faktisk lege matcher forventet lege
		if (r.hentLege().hentNavn().equals(legeNavn)) {
			System.out.println("Riktig 7");
		} else {
			System.out.println("Feil 7");
		}
	}

	static void testPris(Resept r, double forventetPris) {
		// Hjelpemetode for å sjekke at faktisk pris matcher forventet pris
		if (r.prisAaBetale() == forventetPris) {
			System.out.println("Riktig 8");
		} else {
			System.out.println("Feil 8");
		}
	}
}