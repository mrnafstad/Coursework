
public class TestLegemiddel {
	public static void main(String[] args){
		System.out.println("\n");
		Vanlig m1 = new Vanlig("Metformin", 200, 500);
		testVanlig(m1, "Metformin", 200, 500);

		Vanedannende v1 = new Vanedannende("Paracet", 100, 500, 4000);
		testVanedannende(v1, "Paracet", 100, 500, 4000);

		Narkotisk n1 = new Narkotisk("Kodein", 500, 25, 30);
		testNarkotisk(n1, "Kodein", 500, 25, 30);
	}

	// static void test(Legemiddel v, String navn, double pris, double virkestoff) {

	// 	if (v instanceof Vanlig) {
	// 		System.out.println("Dette er et vanlig legemiddel");
	// 		System.out.println("Info:");
	// 		System.out.println(v.toString());
	// 		System.out.println("Test:");
	// 		testVanlig(v);
	// 	} else if (v instanceof Vanedannende) {
	// 		System.out.println("Dette er et vanedannende legemiddel");
	// 		System.out.println("Info:");
	// 		System.out.println(v.toString());
	// 		System.out.println("Test:");
	// 		testVanedannende((Vanedannende)v);
	// 	} else if (v instanceof Narkotisk) {
	// 		System.out.println("Dette er et narkotisk legemiddel");
	// 		System.out.println("Info:");
	// 		System.out.println(v.toString());
	// 		System.out.println("Test:");
	// 		testNarkotisk((Narkotisk)v);
	// 	}
	//}

	static void testVanlig(Legemiddel v, String navn, double pris, double virkestoff) {
		// Denne metoden tar et legemiddel samt det forventet navn, pris og virkestoff for objektet
		// Bruker så hjelpemetoder for å sjekke at forventet og faktisk verdi stemmer
		testNavn(v, navn);
		testPris(v, pris);
		testVirkestoff(v, virkestoff);
		testNyPris(v, 1.25);
		System.out.println("\n");
	}

	static void testVanedannende(Vanedannende v, String navn, double pris, double virkestoff, int styrke) {
		// Denne metoden tar et vanedannende legemiddel samt forventet navn, pris, virkestoff og styrke for objektet
		// Bruker så hjelpemetoder for å sjekke at forventet og faktisk verdi stemmer 
		testVanlig(v, navn, pris, virkestoff);
		testStyrke(v, styrke);
		System.out.println("\n");
	}

	static void testNarkotisk(Narkotisk v, String navn, double pris, double virkestoff, int styrke) {
		// Denne metoden tar et narkotisk legemiddel samt forventet navn, pris, virkestoff og styrke for objektet
		// Bruker så hjelpemetoder for å sjekke at forventet og faktisk verdi stemmer 
		testVanlig(v, navn, pris, virkestoff);
		testStyrke(v, styrke);
		System.out.println("\n");
	}

	static void testNavn(Legemiddel v, String navn) {
		// Hjelpemetode for å sjekke at faktisk navn og forventet navn stemmer
		if (v.hentNavn().equals(navn)) {
			System.out.println("Riktig 1");
		} else {
			System.out.println("Feil 1");
		}
	}

	static void testPris(Legemiddel v, double pris) {
		// Hjelpemetode for å sjekke at faktisk pris matcher forventet pris
		if (v.hentPris() == pris) {
			System.out.println("Riktig 2");
		} else {
			System.out.println("Feil 2");
		}
	}

	static void testVirkestoff(Legemiddel v, double virkestoff) {
		// Hjelpemetode for å sjekke at faktisk virkestoff matcher forventet virkestoff
		if (v.hentVirkestoff() == virkestoff ) {
			System.out.println("Riktig 3");
		} else {
			System.out.println("Feil 3");
		}
	}


	static void testNyPris(Legemiddel v, double endring) {
		// Hjelpemetode for å sjekke at metoden setNyPris virker som den skal
		double gammelPris =v.hentPris();
		v.setNyPris(v.hentPris()*endring);
		if (v.hentPris() == gammelPris*endring) {
			System.out.println("Riktig 4");
		} else {
			System.out.println("Feil 4");
		}
	}

	static void testStyrke(Narkotisk v, int styrke) {
		// Hjelpemetode for å sjekke at faktisk styrke matcher forventet styrke
		if (v.hentNarkotiskStyrke() == styrke) {
			System.out.println("Riktig 5");
		} else {
			System.out.println("Feil 5");
		}
	}

	static void testStyrke(Vanedannende v, int styrke) {
		// Hjelpemetode for å sjekke at faktisk styrke matcher forventet styrke
		if (v.hentVanedannendeStyrke() == styrke) {
			System.out.println("Riktig 6");
		} else {
			System.out.println("Feil 6");
		}
	}
}