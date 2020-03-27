

class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {


	@Override
	public void leggTil(T x) {
		// Lage nodekopi og hente størrelse
		Node p = super.hentStart();
		int size = super.stoerrelse();
		if (size == 0 || p == null) {
			// Dersom lenkelisten er tom
			super.leggTil(x);
		} else {
			// Telle oss frem til indeks der x er større enn p.data
			int count = 0;
			while (p != null && p.data.compareTo(x) < 0) {
				p = p.neste;
				count++;
			}
			// Legge til element på funnet indeks
			super.leggTil(count, x);
		}
	}

	@Override
	public void leggTil(int pos, T x) {
		// Håndtering av bruk av ugyldig metode
		throw new UnsupportedOperationException();
	}

	@Override
	public void sett(int pos, T x) {
		// Håndtering av bruk av ugyldig metode
		throw new UnsupportedOperationException();
	}

	@Override
	public T fjern() {
		// Siden siste element skal fjernes er det bare å bruke fjern(pos) fra lenkeliste med
		// pos lik størrelsen på listen -1
		return fjern(stoerrelse() - 1);
	}
}