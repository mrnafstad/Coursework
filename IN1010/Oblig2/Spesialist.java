

public class Spesialist extends Lege implements Godkjenningsfritak {

	private int kontrollID;

	public Spesialist(String navn, int kontrollID){
		super(navn);
		this.kontrollID = kontrollID;
	}

	public int hentKontrollID() {
		return this.kontrollID;
	}

	@Override
	public String toString() {
		// Legger til kontrollID på den generelle informasjonsstrengen for Lege
		String info = super.toString()
					+ "Kontroll ID: " + this.kontrollID + "\n";
		return info;
	}
}