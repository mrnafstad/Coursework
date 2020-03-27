
class Pasient {

	private static int idTeller = 0;

	private String navn;
	private String fødselsNummer;
	private int id;

	private Stabel<Resept> reseptListe;

	public Pasient(String navn, String fødselsNummer) {
		this.navn = navn;
		this.fødselsNummer = fødselsNummer;
		this.id = idTeller;
		idTeller ++;
		this.reseptListe = new Stabel<Resept>();
	}

	public void nyResept(Resept res) {
		this.reseptListe.leggPaa(res);
	}

	public Stabel<Resept> hentResepter() {
		return reseptListe;
	}

	public String hentNavn() {
		return navn;
	}

	public int hentID() {
		return id;
	}

	@Override
	public String toString() {
		String str = "Pasientens navn: " + this.navn + " \n"
					+ "PasientID: " + this.id + "\n"
					+ "Antall resepter: " this.reseptliste.stoerrelse();
		return str;
	}
}