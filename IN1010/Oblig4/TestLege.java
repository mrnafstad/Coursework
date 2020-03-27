class TestLege {
	public static void main(String[] arg) {
		Lege l1 = new Lege("Nafstad");
		Lege l2 = new Lege("Ahmad");
		Lege l3 = new Lege("Nadvig");
		System.out.println(l1.compareTo(l2));
		System.out.println(l2.compareTo(l1));
		System.out.println(l1.compareTo(l1));
		SortertLenkeliste<Lege> sLst = new SortertLenkeliste<Lege>();
		sLst.leggTil(l1);
		sLst.leggTil(l2);
		sLst.leggTil(l3);
		for (Lege l : sLst) {
			System.out.println(l.hentNavn());
		}
	}
}