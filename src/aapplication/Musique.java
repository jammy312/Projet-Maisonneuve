package aapplication;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Classe qui permet de produire des sons
 * @author Estelle
 * @author James
 */
public class Musique {

	private Clip c;
	private static boolean avecSon = true;

	// Estelle
	/**
	 * Permet de jouer les sons
	 * @param titre le nom de fichier du son souhaité
	 */
	public void jouerMusique(String titre) {
		//On cherche le fichier de la musique
		File fichierSonCherche = new File(titre);
		if (avecSon) {
			try {
				//On le lit
				AudioInputStream audioEntree = AudioSystem.getAudioInputStream(fichierSonCherche);
				c = AudioSystem.getClip(); 
				c.open(audioEntree);
				//On le joue
				c.start();   
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	//James
	public Clip getClip() {
		return c;
	}

	//Estelle
	/**
	 * Permet d'activer ou de désactiver les sons de l'application
	 */
	public static void setMusique() {
		if (avecSon == true) {
			avecSon = false;
		} else {
			avecSon = true;
		}
	}
	
	//Par Estelle
	/**
	 * Permet de savoir si les sons sont activés ou pas
	 * @return avecSon le booléen qui indique si les sons sont activés ou non
	 */
	public static boolean getMusique() {
		return avecSon;
	}
}