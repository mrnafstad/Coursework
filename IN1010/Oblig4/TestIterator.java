class TestIterator {

	public static void main(String[] arg) {
		Lenkeliste<Integer> lst = new Lenkeliste<Integer>();
		for (int i = 0; i < 10; i++) {
			lst.leggTil(i);
		}

		for (Integer j : lst) {
			System.out.println(j);
		}
		System.out.println(lst.stoerrelse());
	}
}