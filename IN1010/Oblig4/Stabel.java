
class Stabel <T> extends Lenkeliste<T> {


	public void leggPaa(T x) {
		// Her er det bare å bruke leggTil fra Lenkeliste
		leggTil(x);
	}

	public T taAv(){
		// Her er det bare å bruke fjern(pos) med pos lik størrelsen på stabelen - 1
		return fjern(stoerrelse()-1);
	}
}