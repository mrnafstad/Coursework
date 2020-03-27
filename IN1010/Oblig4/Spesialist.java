

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

	@Override
	public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
		
		HvitResept res = new HvitResept(legemiddel, this, pasient, reit);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}

	@Override
	public BlåResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
		
		BlåResept res = new BlåResept(legemiddel, this, pasient, reit);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}

	@Override
	public MResept skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
		
		MResept res = new MResept(legemiddel, this, pasient, reit);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}

	@Override
	public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
		
		PResept res = new PResept(legemiddel, this, pasient);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}
}