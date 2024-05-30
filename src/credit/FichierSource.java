package credit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe permettant de lire le fichier de toutes nos sources
 * @author Estelle
 */
public class FichierSource {
	public static ArrayList<String> listeSources = new ArrayList<String>();
	
	/**
	 * Lit un document de sources et l'ajoute dans un tableau qui les contient toutes
	 */
	public static void lireSources() {
		String ligne = null;
				
		try {
			FileReader fichierLu = new FileReader("sources.txt");
			BufferedReader fluxEntree = new BufferedReader(fichierLu);
			do {
				ligne = fluxEntree.readLine();
				listeSources.add(ligne);
			} while (ligne != null);
			fluxEntree.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
