import java.io.File;
import java.io.FileNotFoundException;

public class Test {

	public static void main(String[] arg) {

		System.out.println("\n");
		//skapOgSkriv("labyrinter/1.in");
		//skapOgSkriv("labyrinter/2.in");
		skapOgSkriv("labyrinter/3.in");
		// skapOgSkriv("labyrinter/4.in");
		// skapOgSkriv("labyrinter/5.in");
		// skapOgSkriv("labyrinter/6.in");
		// skapOgSkriv("labyrinter/7.in");
		
	}

	public static void skapOgSkriv(String filNavn) {
		
		System.out.println(filNavn);
		File fil = new File(filNavn);

		try {
			Labyrint lab = Labyrint.lesFraFil(fil);
			System.out.println(lab.toString());
			lab.finnUtveiFra(5,3);
			for (String løsning : lab.hentLøsninger()) {
				System.out.println(løsning);
			}
		} catch(FileNotFoundException e) {
			System.out.println("ugyldig filnanvn \n");
		}

		System.out.println("\n \n");
	}
}