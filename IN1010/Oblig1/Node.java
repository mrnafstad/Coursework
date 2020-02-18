

public class Node {
	//Klasse for noder
	private int minne;
	private int prosessorAntall;

	public Node(int minne, int prosessorAntall) {
		//Node-konstruktør, setter minne og prosessorantall til noden
		this.minne = minne;
		this.prosessorAntall = prosessorAntall;
	}

	public int getMinne() {
		//Hente nodens minne
		return this.minne;
	}
	
	public int getProsessorAntall() {
		//Hente antall prosessorer i noden
		return this.prosessorAntall;
	}
}