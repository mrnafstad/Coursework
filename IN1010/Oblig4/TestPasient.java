class TestPasient {

	public static void main(String[] arg) {
		Pasient pas = new Pasient("Nafstad, Halvor", "0198301982");
		System.out.println("Pasientens navn: " + pas.hentNavn());

		Lege lege = new Lege("Nordmann, Ola");
		Vanedannende testMiddel = new Vanedannende("Kodein", 500, 25, 30);
		BlåResept t4 = new BlåResept(testMiddel, lege, pas, 5);
		MResept t2 = new MResept(testMiddel, lege, pas, 2);
		pas.nyResept(t4);
		pas.nyResept(t2);

		try {
			lege.skrivHvitResept(testMiddel, pas, 5);
		} catch(UlovligUtskrift e) {
			System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
		}
		try {
			lege.skrivBlaaResept(testMiddel, pas, 5);
		} catch(UlovligUtskrift e) {
			System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
		}
		
		try {
			lege.skrivMillitaerResept(testMiddel, pas, 5);
		} catch(UlovligUtskrift e) {
			System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
		}

		try {
			lege.skrivPResept(testMiddel, pas);
		} catch(UlovligUtskrift e) {
			System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
		}

		try {
			lege.skrivHvitResept(testMiddel, pas, 5);
		} catch(UlovligUtskrift e) {
			System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
		}		
		for (Resept res : pas.hentResepter()) {
			System.out.println(res.toString());
		}

		System.out.println("----------");

		try {
			lege.skrivHvitResept(testMiddel, pas, 5);
		} catch(UlovligUtskrift e) {
			System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
		}
		for (Resept res : lege.hentResepter()) {
			System.out.println(res.toString());
		}

	}
}