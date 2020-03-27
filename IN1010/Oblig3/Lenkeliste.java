

class Lenkeliste<T> implements Liste<T> {

	// Jeg har hentet inspirasjon fra forelesningsnotatene til enkelte prosedyrer
	// Har også lagt inn en ekstraprossedyre hentStart()

	class Node {
		Node neste = null;
		T data;
		Node(T x) {
			data = x;
		}
	}



	private Node start = null;
	private int count = 0;

	public Node hentStart() {
		return start;
	}

	public int stoerrelse() {
		//Tellingen blir tatt hånd om hver gang det legges til eller fjernes et element
		return this.count;
	}

	public T hent(int pos) {
		// Hvis startnode er null eller gitt index er < 0 kastes UgyldigListeIndeks

		if (start == null) throw new UgyldigListeIndeks(-1);
		if (pos < 0) throw new UgyldigListeIndeks(-1);
		// Lage kopi av startnode for iterasjon
		Node p = start;
		// for-loop som leter seg frem til elementet på gitt indeks, etter elementet er funnet
		// returneres data på gitt indeks.
		for (int i = 0; i < pos; i++) {
			if (p.neste == null) throw new UgyldigListeIndeks(pos);
			p = p.neste;
		}
		T n = p.data;
		return n;
	}

	public void leggTil(T x) {
		if (start == null) {
			// Hvis startnoden er null settes startnoden til å være en ny node med data x
			// Det legges til 1 på count
			start = new Node(x);
			count ++;
		} else {
			// Startnode-kopi for itterasjon
			// while-løkke som graver ned til siste element
			// Ny node legges til bakerst
			// legges til 1 på count
			Node p = start;
			while (p.neste != null) {
				p = p.neste;
			}
			p.neste = new Node(x);
			count ++;
		}
	}

	public void leggTil(int pos, T x) {
		if (start == null && pos == 0) {
			// Håndtering av spesialtilfelle der startnoden er null og gitt indeks er 0
			start = new Node(x);
			count ++;
		} else if (start == null && pos > 0) {
			// Håndtering av tilfelle dersom gitt indeks er større enn null og startnode er null
			throw new UgyldigListeIndeks(-1);
		} else if (pos < 0) {
			// Håndtering av tilfelle dersom indeks er mindre en 0
			throw new UgyldigListeIndeks(-1);
		} else {
			Node ny = new Node(x);
			if (pos == 0) {
				// Dersom gitt indeks er 0 settes ny.neste til å være den eksisterende lenkelisten
				ny.neste = start;
				start = ny;
				count ++;
			} else {
				// Finner elementet før gitt indeks og setter ny.neste til å være lenkelisten fra
				// og med gitt indeks. Deretter settes gitt indeks til å være denne nye lenkelisten
				Node p = start;
				for (int i = 0; i < pos-1; i++)  {
					if (p.neste == null) {
						throw new UgyldigListeIndeks(pos);
					}
					p = p.neste;
				}
				ny.neste = p.neste;
				p.neste = ny;
				count ++;
			}
		}
	}


	public void sett(int pos, T x) {
		if (start == null || pos < 0) {
			// Håndtering av indeks mindre enn 0 og at start er null
			throw new UgyldigListeIndeks(-1);
		} else {

			if (pos == 0) {
				// Dersom gitt indeks er 0 settes start.data til x
				start.data = x;
			} else {
				// Her graver vi oss ned til riktig indeks og setter data på gitt indeks til x
				Node p = start;
				for (int i = 0; i < pos; i++)  {
					if (p.neste == null) {
						throw new UgyldigListeIndeks(pos);
					}
					p = p.neste;
				}
				p.data = x;
			}
		}
	}

	public T fjern(int pos) {
		if (start == null) {
			// Behandling av fjerning fra tom liste
			throw new UgyldigListeIndeks(pos);
		} else if (pos < 0) {
			// Behandling av fjerning av negativ indeks
			throw new UgyldigListeIndeks(-1);
		} else {
			Node p = start;
			if (pos == 0) {
				// Fjerne og returnere første element
				Node n = p;
				p = p.neste;
				count --;
				return n.data;
			}
			// Grave ned til elementet før gitt indeks
			for (int i = 0; i < pos - 1; i++) {
				p = p.neste;
				if (p.neste == null) {
					throw new UgyldigListeIndeks(pos);
				}
			}
			// Sette p.neste = p.neste.neste (tilsvarende, om det var et metodekall)
			Node n = p.neste;
			p.neste = n.neste;
			count --;
			return n.data;
		}
	}

	public T fjern() {
		// Behandling av forsøk på å fjerne fra tom liste
		if (start == null) throw new UgyldigListeIndeks(-1);
		// Fjerne og returnere første element i lenkeliste
		Node p = start;
		Node n = p.neste;
		T data = p.data;
		start = n;
		count --;
		return data;
	}
}