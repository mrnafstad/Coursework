
public abstract class Legemiddel {
	private String navn;
	private double pris;
	private double virkestoff;

	//Jeg syntes oppgaveteksten var litt vag på dette med ID,
	//men jeg valgte å gi alle legemidler en unik ID fremfor å gi
	//unike IDer på de forskjellige legemiddeltypene.
	private static int count = 0;
	private int id;
	
	public Legemiddel(String navn, double pris, double virkestoff) {
		this.navn = navn;
		this.pris = pris;
		this.virkestoff = virkestoff;
		this.id = count;
		count ++;
	}

	public String hentNavn() {
		return this.navn;
	}

	public double hentPris() {
		return this.pris;
	}

	public double hentVirkestoff() {
		return this.virkestoff;
	}

	public void setNyPris(double nyPris) {
		this.pris = nyPris;
		System.out.println("Prisen for " + this.navn + " er satt til " + nyPris);
	}

	@Override
	public String toString() {
		String info = "Navn: " + hentNavn() +" \n"
						+ "Pris: " + hentPris() + "\n"
						+ "Virkestoff: " + hentVirkestoff() + "\n"
						+ "Legemiddel-ID: " + this.id + "\n";
		return info;
	}
}