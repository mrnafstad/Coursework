
public class Lege {

	private String navn;

	public Lege(String navn) {
		this.navn = navn;
	}

	public String hentNavn() {
		return this.navn;
	}

	public String toString() {
		// Genererer en string med legens navn
		String info = "Legens navn: " + this.navn + "\n";
		return info;
	}
}