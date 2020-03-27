

public class Vanlig extends Legemiddel {
	private static int count = 0;
	private int id;
	
	public Vanlig(String navn, double pris, double virkemiddel) {
		super(navn, pris, virkemiddel);
	}

	@Override
	public String toString() {
		String info = super.toString();
		return info;
	}
}