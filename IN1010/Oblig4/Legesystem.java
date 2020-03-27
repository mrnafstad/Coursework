import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class Legesystem {
	static Lenkeliste<Pasient> pasienter = new Lenkeliste<Pasient>();
	static SortertLenkeliste<Lege> leger = new SortertLenkeliste<Lege>();
	static Lenkeliste<Legemiddel> legemidler = new Lenkeliste<Legemiddel>();
	static Lenkeliste<Resept> resepter = new Lenkeliste<Resept>();
	static Lenkeliste<Lenkeliste> komplett = new Lenkeliste<Lenkeliste>();

	public static void main(String[] arg) {

		lesFraFil("litenFil.txt");
		
		// System.out.println("\n");
		// for (Lege lege : leger) {
		// 	System.out.println(lege.toString());
		// 	for (Resept res : lege.hentResepter()) {
		// 		System.out.println(res.toString());
		// 	}
		// }

		meny();

	}

	public static void meny() {
		Scanner scanner = new Scanner(System.in);
		boolean quit = false;

		skrivMeny();

		while (!quit) {

			System.out.println("\n Gjør ett valg (5 for hovedmeny)");
			int action = scanner.nextInt();
			scanner.nextLine();

			switch(action) {
			case 0:
				System.out.println("Du valgte å avslutte");
				quit = true;
				break;
			case 1: 
				skrivOversikt();
				break;
			case 2:
				System.out.println("Vil du legge til: \n");
				System.out.println("1: Lege \n 2: Pasient \n 3: Legemiddel \n 4: Resept");
				String valg = scanner.nextLine();
				break;
			case 3:
				//brukResept();
				break;
			case 4: 
				//skrivStatistikk();
				break;
			case 5:
				//skrivTilFil();
			case 6:
				skrivMeny();
				break;
			}
		}
	}

	public static void skrivMeny() {

		System.out.println("0: Avslutt \n" +
			"1: Fullstendig oversikt \m" +
			"2: Ny lege/resept/pasient/legemiddel \n" +
			"3: Bruk resept \n" +
			"4: Skriv statistikk \n" +
			"5: Skriv data til fil \n");
	}

	public static void skrivOversikt() {
		System.out.println("Resepter: \n");
		for (Resept res : resepter) {
			System.out.println(res.toString);
		}
		System.out.println("Legemidler: \n");
		for (Legemiddel mdl : legemidler) {
			System.out.println(mdl.toString());
		}
		System.out.println("Leger: \n");
		for (Lege lege : leger) {
			System.out.println(lege.toString());
		}
		System.out.println("Pasienter: \n");
		for (Pasient pas : pasienter) {
			System.out.println(pas.toString());
		}
	}


	// For filinnlesing

	public static void lesFraFil(String filNavn) {
		File fil = new File(filNavn);
		ArrayList<String> denneFil = new ArrayList<String>();

		try {
			Scanner myReader = new Scanner(fil);
			myReader.useDelimiter("# ");
			while (myReader.hasNext()) {
				denneFil.add(myReader.next());
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fil ikke funnet");
		}

		//Teller for å holde styr på hvilke objekter som lages
		//0 = Pasienter, 1 = Legemidler, 2 = Leger og 3 = Resepter
		int teller = 0;

		for(String str : denneFil) {

			String[] mellom2 = str.split("\n", 0);
			if(teller == 0) {
				lagPasienter(mellom2);
				teller ++;
			} else if (teller == 1) {
				lagLegemidler(mellom2);
				teller ++;
			} else if (teller == 2) {
				lagLeger(mellom2);
				teller ++;
			} else if (teller == 3) {
				lagResepter(mellom2);
			}
		}
	}

	public static void lagPasienter(String[] str) {		
		for (int i = 1; i < str.length; i++) {
			String[] mellom1 = str[i].split(",", 0);

			pasienter.leggTil(new Pasient(mellom1[0], mellom1[1]));			
		}
	}

	public static void lagLeger(String[] str) {
		for (int i = 1; i < str.length; i++) {
			String[] mellom1 = str[i].split(",", 0);

			int kontrollID = Integer.parseInt(mellom1[1].trim());
			if (kontrollID != 0) {
				leger.leggTil(new Spesialist(mellom1[0], kontrollID));
			} else {
				leger.leggTil(new Lege(mellom1[0]));
			}
			
		}		
	}

	public static void lagLegemidler(String[] str) {
		for (int i = 1; i < str.length; i++) {
			String[] mellom1 = str[i].split(",", 0);

			String type = mellom1[1];
			String navn = mellom1[0];
			double pris = Double.parseDouble(mellom1[2]);
			double virkestroff = Double.parseDouble(mellom1[3]);

			if (type.compareTo("vanlig") == 0) {
				legemidler.leggTil(new Vanlig(navn, pris, virkestroff));
			} else if (type.compareTo("vanedannende") == 0) {
				int styrke = Integer.parseInt(mellom1[4].trim());
				legemidler.leggTil(new Vanedannende(navn, pris, virkestroff, styrke));
			} else if (type.compareTo("narkotisk") == 0) {
				int styrke = Integer.parseInt(mellom1[4].trim());
				legemidler.leggTil(new Narkotisk(navn, pris, virkestroff, styrke));
			}
			
		}
	}

	public static void lagResepter(String[] str) {
		for (int i = 1; i < str.length; i++) {
			String[] mellom1 = str[i].split(",", 0);

			int legemiddelID = Integer.parseInt(mellom1[0].trim());
			int pasientID = Integer.parseInt(mellom1[2].trim());
			int reit = 0;

			String legeNavn = mellom1[1];
			String reseptType = mellom1[3];


			if (mellom1.length == 5) {
				reit = Integer.parseInt(mellom1[4].trim());
			}
			for (Lege lege : leger) {
				if (legeNavn.compareTo(lege.hentNavn()) == 0) {
					if (reseptType.compareTo("hvit") == 0) {
						try {
							lege.skrivHvitResept(legemidler.hent(legemiddelID), pasienter.hent(pasientID), reit);
						} catch(UlovligUtskrift e) {
							System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
						}
					} else if (reseptType.compareTo("blaa") == 0) {
						try {
							lege.skrivBlaaResept(legemidler.hent(legemiddelID), pasienter.hent(pasientID), reit);
						} catch(UlovligUtskrift e) {
							System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
						}
					} else if (reseptType.compareTo("millitaer") == 0) {
						try {
							lege.skrivMillitaerResept(legemidler.hent(legemiddelID), pasienter.hent(pasientID), reit);
						} catch(UlovligUtskrift e) {
							System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
						}
					} else if (reseptType.compareTo("p") == 0) {
						try {
							lege.skrivPResept(legemidler.hent(legemiddelID), pasienter.hent(pasientID));
						} catch(UlovligUtskrift e) {
							System.out.println("Vanlig lege kan ikke skrive ut narkotiske legemidler");
						}
					}
				}
			}

			
		}		
	}

}