

public class Narkotisk extends Legemiddel {
	
	private int styrke;

	public Narkotisk(String navn, double pris, double virkemiddel, int styrke) {
		super(navn, pris, virkemiddel);
		this.styrke = styrke;
	}

	public int hentNarkotiskStyrke() {
		return this.styrke;
	}
	@Override
	public String toString() {
		// Legger til narkotisk styrke i den generelle informasjonsstrengen for Legemiddel
		String info = super.toString()
					+ "Narkotisk styrke: " + this.styrke + "\n";
		return info;
	}
}