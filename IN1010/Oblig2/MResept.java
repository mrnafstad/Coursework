

public class MResept extends HvitResept {

	public MResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
		super(legemiddel, utskrivendeLege, pasientId, reit);
	}

	@Override
	public double prisAaBetale() {
		// Returnerer prisen pasienten må betale. Satt til 0 på MResept
		return 0.0;
	}

	@Override
	public String toString() {
		//Bruker Legger til resepttype og bruker super.toString() for å generere resten av informasjonen
		String info = "Militærresept \n" + super.toString();
		return info;
	}
}