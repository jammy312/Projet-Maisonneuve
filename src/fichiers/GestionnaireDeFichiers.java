package fichiers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import custom.BacASable;
import forme.Aimant;
import forme.Canon;
import forme.Cercle;
import forme.Filet;
import forme.Forme;
import forme.Rectangle;
import forme.Segment;
import forme.Triangle;
/**
 * Cette classe permet de sauvegarder et de récupérer les niveaux édits dans le bac à sable
 * @author Caroline Houle
 * @author Estelle
 * @author Jason
 * @author James
 *
 */
public class GestionnaireDeFichiers {	


	static String sousDossierSurBureau = "Niveaux";

	static String nomFichTexteNom = "customNom.txt";
	static ArrayList<String> listeNoms = new ArrayList<String>();
	static ArrayList<Cercle> cercle = new ArrayList<Cercle>();
	static ArrayList<Aimant> aimant = new ArrayList<Aimant>();
	static ArrayList<Segment> segment = new ArrayList<Segment>();
	static ArrayList<Triangle> triangle = new ArrayList<Triangle>();
	static ArrayList<Rectangle> rectangle = new ArrayList<Rectangle>();
	static ArrayList<Forme> formeTout = new ArrayList<Forme>();
	static Filet filet = new Filet(0,0);
	static Canon canon = new Canon(0,0);
	static String nomJeux, nomTeste;
	static int nbreEssai = 0;

	//Estelle
	/**
	 * crée un fichier de texte contenant les données du bac à sable sauvegardées dans un dossier du bureau de l'utilisateur
	 * @param nom le nom du fichier sauvegardé
	 * @param ensembleForme le tableau contenant toutes les formes présentes dans le bac à sable au moment de la sauvegarde
	 * @param nbEssais le nombre d'essais accordés pour ce niveau sauvegardé
	 * @param etiquetteNom l'étiquette dans laquelle l'utilisateur a placé le nom du niveau
	  */
	public static void ecrireFichierTexteBureau(String nom, ArrayList<Forme> ensembleForme, int nbEssais, JTextField etiquetteNom) {
		// chemin d'acces au dossier
		File dossier = new File(System.getProperty("user.home"), "Desktop" + "\\" + sousDossierSurBureau);

		// on cree le dossier s'il n'existe pas
		if (dossier.mkdir()) {
			JOptionPane.showMessageDialog(null,
					"Le dossier " + dossier.toString() + " a été créé car il n'existait pas !");
		}

		// chemin d'acces complet au fichier de travail
		File fichierDeTravail = new File(dossier + "\\" + nomFichTexteNom);
		PrintWriter fluxSortie = null;

		try {
			
			//On vérifie si le nom existe déjà et s'il faut le remplacer ou le changer
			if (listeNoms.contains(nom)) {
				if (demanderRemplacer(nom)) {
					supprimer(nom);
					ecrireFichierTexteBureau(nom, ensembleForme, nbEssais, etiquetteNom);
				} else { 
					//Toujours s'assurer à ce qu'il se trouve un nom dans l'étiquette
					JOptionPane.showMessageDialog(null, "Veuillez inscrire le nouveau nom");
					etiquetteNom.setBackground(Color.yellow);
					if (etiquetteNom.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Tâche impossible à faire. Il faut mettre un nom pour ton custom !");
						etiquetteNom.setBackground(Color.yellow);
					} 							
				} 
			}
			else {
				//On ajoute le niveau
				fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(fichierDeTravail,true)));
				fluxSortie.println(nom);	
				ecrireFichierForme(nom, ensembleForme, nbEssais, dossier);
				JOptionPane.showMessageDialog(null, "Votre niveau a été ajouté dans "
						+ fichierDeTravail.toString());
			}


		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur d'écriture!");
			e.printStackTrace();


		} finally {
			if (fluxSortie != null)
				fluxSortie.close();
		}

	}


	//Estelle
	/**
	 * vérifie si le nom entré pour la sauvegarde existe déjà ou non et permet de le remplacer ou de donner un nouveau nom
	 * @param nomQuiExiste le nom qui existe déjà
	 * @return un booléen qui affirme que la personne veut remplacer ou qui indique que la personne ne veut pas et veut donner un autre nom
	 * 
	 */
	public static boolean demanderRemplacer(String nomQuiExiste) {
		String[] choix = {"Oui, je veux remplacer le fichier existant", "Non, je vais donner un autre nom"};
		int boiteChoix = JOptionPane.showOptionDialog(null, "Un autre fichier porte déjà ce nom, voulez-vous l'écraser et le remplacer par votre bac à sable?", 
				"Fichier déjà existant", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, null);
		if ((boiteChoix != JOptionPane.YES_OPTION) && (boiteChoix != JOptionPane.NO_OPTION)) {
			demanderRemplacer(nomQuiExiste);
		}
		return (boiteChoix == JOptionPane.YES_OPTION);
	}

	//Estelle
	/**
	 * vérifie si le fichier texte qui contient le nom de toutes les sauvegardes existe dans le dossier de l'utilisateur
	 * @return un booléen qui affirme ou non l'existence de ce fichier
	 * 
	 */
	public static boolean fichierExiste() {
		// chemin d'acces au fichier de travail, qui sera sur le Bureau
		File fichierDeTravail = new File(System.getProperty("user.home"),
				"Desktop" + "\\" + sousDossierSurBureau + "\\" + nomFichTexteNom);

		// on test si le fichier  lire existe 
		return fichierDeTravail.exists();
	}

	//Par Estelle
	/**
	 * vérifie si un fichier particulier que l'utilisateur cherche existe dans son dossier
	 * @param fichierCherche un String qui contient le fichier cherché
	 * @return un booléen qui affirme ou non l'existence de ce fichier
	 * 
	 */
	public static boolean fichierExiste(String fichierCherche) {
		String nomFichier = fichierCherche+".txt";
		// chemin d'acces au fichier de travail, qui sera sur le Bureau
		File fichierDeTravail = new File(System.getProperty("user.home"),
				"Desktop" + "\\" + sousDossierSurBureau + "\\" + nomFichier);

		// on teste si le fichier à lire existe 
		return fichierDeTravail.exists();
	}

	//Estelle
	/**
	 * Lit un fichier de texte contenant les noms de sauvegardes et les enregistre dans un tableau
	 * 
	 */
	public static void lireFichierTexteBureau() {
		BufferedReader fluxEntree = null;
		String nom = null;

		// chemin d'acces au fichier de travail, qui sera sur le Bureau
		File fichierDeTravail = new File(System.getProperty("user.home"),
				"Desktop" + "\\" + sousDossierSurBureau + "\\" + nomFichTexteNom);

		if(fichierDeTravail.exists()) {
			try {
				fluxEntree = new BufferedReader(new FileReader(fichierDeTravail));
				listeNoms.clear(); // toujours vider avant pour éviter une erreur de lecture ou d'ajout
				do { // répéter tant que la fin de fichier n'est pas rencontrée
					nom = fluxEntree.readLine();
					if (nom != null) {
						listeNoms.add(nom);
					}
				} while (nom != null);
			} // fin try



			catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Incapable de lire le fichier  " + fichierDeTravail.toString());
				System.exit(0);
			}

			catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
				e.printStackTrace();
				System.exit(0);
			}

			finally {
				// on exécutera toujours ceci, erreur ou pas
				try {
					fluxEntree.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Erreur rencontre lors de la fermeture!");
				}
			} // fin finally
		}
	}

	//Estelle
	/**
	 * Il permet de lire un niveau déjà fait et d'y jouer
	 * @param numNiveau le numéro du niveau
	 * @param bac le bac qui sera utilisé pour créer le niveau
	 */
	public static void lireNiveau(int numNiveau, BacASable bac){
		viderTableauxFormes();
		FileReader fichierLu = null;
		BufferedReader fluxEntree = null;
		String nomFichier = "Niveau" + numNiveau;

		try {
			fichierLu = new FileReader(nomFichier + ".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lireEtCreer(fichierLu, nomFichier, fluxEntree, bac);		
	}


	//Jason
	/**
	 * Il permet d'écrire le bac à sable de la personne dans un fichier binaire
	 * @param nom le nom du jeu
	 * @param ensembleForme l'ensemble des formes que l'utilisateur utilise
	 * @param nbEssais le nombre d'essais permis
	 * @param dossier l'endroit oú le fichier sera déposé
	 */
	private static void ecrireFichierForme(String nom, ArrayList<Forme> ensembleForme, int nbEssais, File dossier) {
		File fichierDeTravail = new File(dossier + "\\" + nom + ".txt");
		PrintWriter fluxEntre = null;
		// 1- trier les formes et crires le
		organiserForme(ensembleForme);
		nomJeux = nom;
		nbreEssai = nbEssais;

		//2- écrire les formes et le nombre d'Essai
		try {
			fluxEntre = new PrintWriter(new BufferedWriter(new FileWriter(fichierDeTravail)));
			fluxEntre.println(nbEssais);			
			String valeur;	
			// 3- écrire les formes
			ecrireForme(new ArrayList<Forme>(cercle), fluxEntre);
			ecrireForme(new ArrayList<Forme>(aimant), fluxEntre);
			ecrireForme(new ArrayList<Forme>(segment), fluxEntre);
			ecrireForme(new ArrayList<Forme>(triangle), fluxEntre);
			ecrireForme(new ArrayList<Forme>(rectangle), fluxEntre);

			//écrire le filet
			fluxEntre.println("Filet");
			valeur = "[" + filet.getX( )+ ","+filet.getY() + "]";
			fluxEntre.println(valeur);

			//écrire le Canon
			fluxEntre.println("Canon");
			valeur = "[" + canon.getX( )+ ","+canon.getY() + "]";
			fluxEntre.println(valeur);
		}  // fin try

		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Incapable de lire le fichier  " + fichierDeTravail.toString());
			System.exit(0);
		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
			e.printStackTrace();
			System.exit(0);
		}

		finally {
			fluxEntre.close();
		} // fin finally


	}
	//Jason
	/**
	 * Il permet d'organiser les formes entre eux
	 * @param ensembleForme toutes les formes dans le custom qui doivent être organisées
	 */
	private static void organiserForme(ArrayList<Forme> ensembleForme) {
		viderTableauxFormes();
		// 1- trier les formes

		for (int forme = 0; forme < ensembleForme.size(); forme++) {
			ensembleForme.get(forme).estDeplace(false);
			switch (ensembleForme.get(forme).getNomClasse()) {

			case "Cercle":
				cercle.add((Cercle) ensembleForme.get(forme));
				break;

			case "Aimant":
				aimant.add((Aimant) ensembleForme.get(forme));
				break;

			case "Segment":
				segment.add((Segment) ensembleForme.get(forme));
				break;

			case "Rectangle":
				rectangle.add((Rectangle) ensembleForme.get(forme));
				break;

			case "Filet":
				filet = new Filet(ensembleForme.get(forme).getX(),ensembleForme.get(forme).getY());
				break;

			case "Canon":
				canon = new Canon(ensembleForme.get(forme).getX(),ensembleForme.get(forme).getY());
				break;

			case "Triangle":
				triangle.add((Triangle) ensembleForme.get(forme));
				break;
			}
		}
	}

	//Par Jason
	/**
	 * Il permet d'écrire les formes dans un fichier qu'on enregistrera dans le bureau
	 * @param forme les formes qui doivent être mises dans le fichier
	 * @param fluxEntre le fichier dans lequel les fichiers seront mis
	 */
	private static void ecrireForme(ArrayList<Forme> forme, PrintWriter fluxEntre) {

		if (forme.size()!=0) {
			String nom = forme.get(0).getNomClasse();
			String valeur;
			fluxEntre.println(nom);
			for (int indiceForme = 0; indiceForme < forme.size(); indiceForme++) {
				valeur = "[" + forme.get(indiceForme).getX( )+ ","+forme.get(indiceForme).getY() + "," + forme.get(indiceForme).getHauteur()+"," + forme.get(indiceForme).getRotation();
				if (forme.get(0).getNomClasse().equalsIgnoreCase("Cercle")) {
					valeur = valeur + "," + cercle.get(indiceForme).isInfluancable() + "]";
				}else {
					valeur = valeur + "]";
				}

				fluxEntre.println(valeur);

			}

		}
	}

	//Estelle
	/**
	 * Permet de récupérer un bac à sable en lisant le contenu du fichier texte correspondant
	 * @param nomEdite le nom du bac que l'utilisateur recherche
	 * @param bac le BacASable dans lequel ajouter les valeurs
	 */
	public static void lireContenuBac(String nomEdite, BacASable bac) {
		viderTableauxFormes();
		BufferedReader fluxEntree = null;
		String fichierCherche = nomEdite+".txt";

		// chemin d'acces au fichier de travail, qui sera sur le Bureau
		File fichierDeTravail = new File(System.getProperty("user.home"),
				"Desktop" + "\\" + sousDossierSurBureau + "\\" + fichierCherche);

		try {
			lireEtCreer(new FileReader(fichierDeTravail), nomEdite, fluxEntree, bac);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 

	//Estelle
	/**
	 * Il permet de créer le niveau sélectionné
	 * @param fichierLecture le niveau séléectionné
	 * @param nomFichier le nom du niveau
	 * @param fluxEntree il permet de lire le fichier du niveau
	 * @param bacCree afin de le déposer dans un bac à sable
	 */
	public static void lireEtCreer(FileReader fichierLecture, String nomFichier, BufferedReader fluxEntree, BacASable bacCree) {
		String ligne = null;		
		int nbLigne = 1; 
		String formeLue = null;

		try {
			fluxEntree = new BufferedReader(fichierLecture);
			do { // répéter tant que la fin de fichier n'est pas rencontre
				ligne = fluxEntree.readLine();
				if (nbLigne == 1) { //cette ligne indique le nb d'essais
					nbreEssai = Integer.parseInt(ligne);

				} else {
					if (ligne != null) {
						if (ligne.charAt(0)=='[') {
							Niveau.creerObjet(formeLue, ligne); //formeLue indique le nom de la forme (Cercle, etc.) et ligne indique la ligne des donnees ([...])
						} else { 
							formeLue = ligne;
						} 
					}
				}
				nbLigne++;
			} while (ligne != null); 
			nomJeux = nomFichier;
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Incapable de lire le fichier  " + fichierLecture.toString());
			System.exit(0);
		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
			e.printStackTrace();
			System.exit(0);
		}

		finally {
			// on exécutera toujours ceci, erreur ou pas
			try {
				fluxEntree.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontre lors de la fermeture!");
			}
		}
		bacCree.setSauvegarde();
		bacCree.setNomSauvegarde(nomFichier);
		bacCree.setNbEssai(nbreEssai);
	}



	//Jason
	/**
	 * Il permet de récupérer les variables nécessaires sans pour autant les sauvegarder dans le bureau
	 * @param nom le nom du jeu
	 * @param ensembleForme l'ensemble des formes que l'utilisateur utilise
	 * @param nbEssais le nombre d'essais permis
	 */
	public static void sauvegardeTemporaire(String nom, ArrayList<Forme> ensembleForme, int nbEssais) {
		organiserForme(ensembleForme);
		formeTout = ensembleForme;
		nomJeux = nom;
		nbreEssai = nbEssais;
	}

	//Estelle
	/**
	 * Il permet de vider tous les arrayList
	 */
	private static void viderTableauxFormes() {
		cercle.clear();
		aimant.clear();
		segment.clear();
		rectangle.clear();
		triangle.clear();
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste de cercles quand le fichier est sauvegardé ou récupéré
	 * @return la liste de cercles quand le fichier est sauvegardé ou récupéré
	 */
	public static ArrayList<Cercle> getListeCercle() {
		return cercle;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste d'aimants quand le fichier est sauvegardé ou récupéré
	 * @return la liste d'aimants quand le fichier est sauvegardé ou récupéré
	 */
	public static ArrayList<Aimant> getListeAimant() {
		return aimant;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste de segments quand le fichier est sauvegardé ou récupéré
	 * @return la liste de segments quand le fichier est sauvegardé ou récupéré
	 */
	public static ArrayList<Segment> getListeSegment() {
		return segment;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste de rectangles quand le fichier est sauvegardé ou récupéré
	 * @return la liste de rectangles quand le fichier est sauvegardé ou récupéré
	 */
	public static ArrayList<Rectangle> getListeRectangle() {
		return rectangle;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste de triangles quand le fichier est sauvegardé ou récupéré
	 * @return la liste de triangles quand le fichier est sauvegardé ou récupéré
	 */
	public static ArrayList<Triangle> getListeTriangle() {
		return triangle;
	}

	//Estelle
	/**
	 * Il permet d'obtenir le filet mis quand le fichier est sauvegardé ou récupéré
	 * @return le filet quand le fichier est sauvegardé ou récupéré
	 */
	public static Filet getFilet() {
		return filet;
	}

	//Estelle
	/**
	 * Il permet d'obtenir le canon quand le fichier est sauvegardé ou récupéré
	 * @return le canon quand le fichier est sauvegardé ou récupéré
	 */
	public static Canon getCanon() {
		return canon;
	}
	
	//Jason
	/**
	 * Il permet d'obtenir le nom du jeu quand le fichier est sauvegardé ou récupéré
	 * @return le nom du jeu quand le fichier est sauvegardé ou récupéré
	 */
	public static String getNom() {
		return nomJeux;
	}

	//Jason
	/**
	 * Il permet d'avoir le nombre d'essais quand le fichier est sauvegardé ou récupéré
	 * @return le nombre d'essais quand le fichier est sauvegardé ou récupéré
	 */
	public static int getNbreEssai() {
		return nbreEssai;
	}

	//Jason
	/**
	 * Il permet de retourner toute les formes présentes dans un ArrayList
	 * @return toutes les formes du jeu
	 */
	public static ArrayList<Forme> getForme(){
		formeTout.clear();

		//Pour récupérer les filets
		formeTout.add(filet);

		//Pour récupérer les canons
		formeTout.add(canon);		

		//Pour récupérer les cercles
		for (int listeCercle = 0; listeCercle < cercle.size(); listeCercle++) {
			formeTout.add(cercle.get(listeCercle));
		}

		//Pour récupérer les aimants
		for (int listeAimant = 0; listeAimant < aimant.size(); listeAimant++) {
			formeTout.add(aimant.get(listeAimant));

		}	

		//Pour récupérer les rectangles
		for (int listeRectangle = 0; listeRectangle < rectangle.size(); listeRectangle++) {
			formeTout.add(rectangle.get(listeRectangle));

		}		

		//Pour récupérer les triangles
		for (int listeTriangle = 0; listeTriangle < triangle.size(); listeTriangle++) {
			formeTout.add(triangle.get(listeTriangle));

		}	

		//Pour récupérer les segments
		for (int listeSegment = 0; listeSegment < segment.size(); listeSegment++) {
			formeTout.add(segment.get(listeSegment));

		}	

		return  formeTout;
	}


	//James
	/**
	 * Méthode qui permet de prendre tous les noms de tous les fichiers sauvegardés
	 * @return le tableau qui contient tous les noms de fichiers sauvegardés
	 */
	public static ArrayList<String> getNomSauvegarde() {
		lireFichierTexteBureau();
		return listeNoms;
	}


	//James
	/**
	 * Il permet de supprimer un niveau
	 * @param nomFichier le nom du fichier à supprimer
	 * @return si le fichier est supprimé
	 */
	public static boolean supprimer(String nomFichier) {
		//System.out.println("le nom du fichier est :" + nomFichier + ".txt");

		//1) on regarde si le fichier existe
		File dossier = new File(System.getProperty("user.home"), "Desktop" + "\\" + sousDossierSurBureau + "\\" + nomFichier + ".txt");

		if (dossier.exists()) {

			BufferedReader fluxEntree = null;
			PrintWriter fluxSortie = null;


			//2) on fait un chemin d'acces au fichier de travail, qui sera sur le Bureau
			File fichierDeTravail = new File(System.getProperty("user.home"),
					"Desktop" + "\\" + sousDossierSurBureau + "\\" + nomFichTexteNom);

			try {
				fluxEntree = new BufferedReader(new FileReader(fichierDeTravail));
				ArrayList<String> essai = new ArrayList<String>();

				//3) on fait une nouvelle arrayList listeNoms ou le fichier qui doit être supprimé ne doit pas être là
				String ligne="";
				while (ligne  != null) {
					ligne = fluxEntree.readLine();
					if(!nomFichier.equals(ligne) && ligne !=null) {
						//fluxSortie.println(ligne);
						essai.add(ligne);
					}
				}
				fluxEntree.close();
				listeNoms.clear();
				listeNoms = essai;

				//4) on réecrit le fichier de la liste des noms pour que le fichier supprimé ne soit pas là
				fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(fichierDeTravail)));
				for (int lise = 0; lise < listeNoms.size(); lise++) {
					fluxSortie.println(listeNoms.get(lise));
				}
				fluxSortie.close();

				// 5) et on supprime le fichier
				dossier.delete();

			} // fin try

			catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Incapable de lire le fichier  " + fichierDeTravail.toString());
				System.exit(0);
			}

			catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
				e.printStackTrace();
				System.exit(0);
			}

			finally {
				// on exécutera toujours ceci, erreur ou pas
				try {
					fluxEntree.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Erreur rencontre lors de la fermeture!");
				}

			} // fin finally

			return true;			
		}else {
			return false;
		}
	}

}
