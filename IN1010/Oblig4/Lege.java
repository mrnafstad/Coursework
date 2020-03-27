
public class Lege implements Comparable<Lege>{

	protected String navn;
	protected Lenkeliste<Resept> utskrevedeResepter;

	public Lege(String navn) {
		this.navn = navn;
		this.utskrevedeResepter = new Lenkeliste<Resept>();
	}

	public String hentNavn() {
		return this.navn;
	}

	public String toString() {
		// Genererer en string med legens navn
		String info = "Legens navn: " + this.navn + "\n";
		return info;
	}

	public int compareTo(Lege annenLege) {
		int comp = this.navn.compareTo(annenLege.hentNavn());
		if (comp > 0) {
			return 1;
		} else if (comp < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public Lenkeliste<Resept> hentResepter() {
		return this.utskrevedeResepter;
	}

	public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel);

		HvitResept res = new HvitResept(legemiddel, this, pasient, reit);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}

	public BlåResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel);

		BlåResept res = new BlåResept(legemiddel, this, pasient, reit);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}

	public MResept skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel);

		MResept res = new MResept(legemiddel, this, pasient, reit);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}

	public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel);

		PResept res = new PResept(legemiddel, this, pasient);
		pasient.nyResept(res);
		this.utskrevedeResepter.leggTil(res);
		return res;
	}
}