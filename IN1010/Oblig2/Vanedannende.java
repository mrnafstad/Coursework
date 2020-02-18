

public class Vanedannende extends Legemiddel {
	private int styrke;
		
	public Vanedannende(String navn, double pris, double virkemiddel, int styrke) {
		super(navn, pris, virkemiddel);
		this.styrke = styrke;
	}


	public int hentVanedannendeStyrke() {
		return this.styrke;
	}

	@Override
	public String toString() {
		// Legger til vanedannende styrke i den generelle informasjonsstrengen for Legemiddel
		String info = super.toString()
					+ "Vanedannende styrke: " + this.styrke + "\n";
		return info;
	}
}