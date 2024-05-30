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
 * Cette classe permet de sauvegarder et de r�cup�rer les niveaux �dits dans le bac � sable
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
	 * cr�e un fichier de texte contenant les donn�es du bac � sable sauvegard�es dans un dossier du bureau de l'utilisateur
	 * @param nom le nom du fichier sauvegard�
	 * @param ensembleForme le tableau contenant toutes les formes pr�sentes dans le bac � sable au moment de la sauvegarde
	 * @param nbEssais le nombre d'essais accord�s pour ce niveau sauvegard�
	 * @param etiquetteNom l'�tiquette dans laquelle l'utilisateur a plac� le nom du niveau
	  */
	public static void ecrireFichierTexteBureau(String nom, ArrayList<Forme> ensembleForme, int nbEssais, JTextField etiquetteNom) {
		// chemin d'acces au dossier
		File dossier = new File(System.getProperty("user.home"), "Desktop" + "\\" + sousDossierSurBureau);

		// on cree le dossier s'il n'existe pas
		if (dossier.mkdir()) {
			JOptionPane.showMessageDialog(null,
					"Le dossier " + dossier.toString() + " a �t� cr�� car il n'existait pas !");
		}

		// chemin d'acces complet au fichier de travail
		File fichierDeTravail = new File(dossier + "\\" + nomFichTexteNom);
		PrintWriter fluxSortie = null;

		try {
			
			//On v�rifie si le nom existe d�j� et s'il faut le remplacer ou le changer
			if (listeNoms.contains(nom)) {
				if (demanderRemplacer(nom)) {
					supprimer(nom);
					ecrireFichierTexteBureau(nom, ensembleForme, nbEssais, etiquetteNom);
				} else { 
					//Toujours s'assurer � ce qu'il se trouve un nom dans l'�tiquette
					JOptionPane.showMessageDialog(null, "Veuillez inscrire le nouveau nom");
					etiquetteNom.setBackground(Color.yellow);
					if (etiquetteNom.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "T�che impossible � faire. Il faut mettre un nom pour ton custom !");
						etiquetteNom.setBackground(Color.yellow);
					} 							
				} 
			}
			else {
				//On ajoute le niveau
				fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(fichierDeTravail,true)));
				fluxSortie.println(nom);	
				ecrireFichierForme(nom, ensembleForme, nbEssais, dossier);
				JOptionPane.showMessageDialog(null, "Votre niveau a �t� ajout� dans "
						+ fichierDeTravail.toString());
			}


		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur d'�criture!");
			e.printStackTrace();


		} finally {
			if (fluxSortie != null)
				fluxSortie.close();
		}

	}


	//Estelle
	/**
	 * v�rifie si le nom entr� pour la sauvegarde existe d�j� ou non et permet de le remplacer ou de donner un nouveau nom
	 * @param nomQuiExiste le nom qui existe d�j�
	 * @return un bool�en qui affirme que la personne veut remplacer ou qui indique que la personne ne veut pas et veut donner un autre nom
	 * 
	 */
	public static boolean demanderRemplacer(String nomQuiExiste) {
		String[] choix = {"Oui, je veux remplacer le fichier existant", "Non, je vais donner un autre nom"};
		int boiteChoix = JOptionPane.showOptionDialog(null, "Un autre fichier porte d�j� ce nom, voulez-vous l'�craser et le remplacer par votre bac � sable?", 
				"Fichier d�j� existant", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, null);
		if ((boiteChoix != JOptionPane.YES_OPTION) && (boiteChoix != JOptionPane.NO_OPTION)) {
			demanderRemplacer(nomQuiExiste);
		}
		return (boiteChoix == JOptionPane.YES_OPTION);
	}

	//Estelle
	/**
	 * v�rifie si le fichier texte qui contient le nom de toutes les sauvegardes existe dans le dossier de l'utilisateur
	 * @return un bool�en qui affirme ou non l'existence de ce fichier
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
	 * v�rifie si un fichier particulier que l'utilisateur cherche existe dans son dossier
	 * @param fichierCherche un String qui contient le fichier cherch�
	 * @return un bool�en qui affirme ou non l'existence de ce fichier
	 * 
	 */
	public static boolean fichierExiste(String fichierCherche) {
		String nomFichier = fichierCherche+".txt";
		// chemin d'acces au fichier de travail, qui sera sur le Bureau
		File fichierDeTravail = new File(System.getProperty("user.home"),
				"Desktop" + "\\" + sousDossierSurBureau + "\\" + nomFichier);

		// on teste si le fichier � lire existe 
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
				listeNoms.clear(); // toujours vider avant pour �viter une erreur de lecture ou d'ajout
				do { // r�p�ter tant que la fin de fichier n'est pas rencontr�e
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
				// on ex�cutera toujours ceci, erreur ou pas
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
	 * Il permet de lire un niveau d�j� fait et d'y jouer
	 * @param numNiveau le num�ro du niveau
	 * @param bac le bac qui sera utilis� pour cr�er le niveau
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
	 * Il permet d'�crire le bac � sable de la personne dans un fichier binaire
	 * @param nom le nom du jeu
	 * @param ensembleForme l'ensemble des formes que l'utilisateur utilise
	 * @param nbEssais le nombre d'essais permis
	 * @param dossier l'endroit o� le fichier sera d�pos�
	 */
	private static void ecrireFichierForme(String nom, ArrayList<Forme> ensembleForme, int nbEssais, File dossier) {
		File fichierDeTravail = new File(dossier + "\\" + nom + ".txt");
		PrintWriter fluxEntre = null;
		// 1- trier les formes et crires le
		organiserForme(ensembleForme);
		nomJeux = nom;
		nbreEssai = nbEssais;

		//2- �crire les formes et le nombre d'Essai
		try {
			fluxEntre = new PrintWriter(new BufferedWriter(new FileWriter(fichierDeTravail)));
			fluxEntre.println(nbEssais);			
			String valeur;	
			// 3- �crire les formes
			ecrireForme(new ArrayList<Forme>(cercle), fluxEntre);
			ecrireForme(new ArrayList<Forme>(aimant), fluxEntre);
			ecrireForme(new ArrayList<Forme>(segment), fluxEntre);
			ecrireForme(new ArrayList<Forme>(triangle), fluxEntre);
			ecrireForme(new ArrayList<Forme>(rectangle), fluxEntre);

			//�crire le filet
			fluxEntre.println("Filet");
			valeur = "[" + filet.getX( )+ ","+filet.getY() + "]";
			fluxEntre.println(valeur);

			//�crire le Canon
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
	 * @param ensembleForme toutes les formes dans le custom qui doivent �tre organis�es
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
	 * Il permet d'�crire les formes dans un fichier qu'on enregistrera dans le bureau
	 * @param forme les formes qui doivent �tre mises dans le fichier
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
	 * Permet de r�cup�rer un bac � sable en lisant le contenu du fichier texte correspondant
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
	 * Il permet de cr�er le niveau s�lectionn�
	 * @param fichierLecture le niveau s�l�ectionn�
	 * @param nomFichier le nom du niveau
	 * @param fluxEntree il permet de lire le fichier du niveau
	 * @param bacCree afin de le d�poser dans un bac � sable
	 */
	public static void lireEtCreer(FileReader fichierLecture, String nomFichier, BufferedReader fluxEntree, BacASable bacCree) {
		String ligne = null;		
		int nbLigne = 1; 
		String formeLue = null;

		try {
			fluxEntree = new BufferedReader(fichierLecture);
			do { // r�p�ter tant que la fin de fichier n'est pas rencontre
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
			// on ex�cutera toujours ceci, erreur ou pas
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
	 * Il permet de r�cup�rer les variables n�cessaires sans pour autant les sauvegarder dans le bureau
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
	 * Il permet d'obtenir la liste de cercles quand le fichier est sauvegard� ou r�cup�r�
	 * @return la liste de cercles quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static ArrayList<Cercle> getListeCercle() {
		return cercle;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste d'aimants quand le fichier est sauvegard� ou r�cup�r�
	 * @return la liste d'aimants quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static ArrayList<Aimant> getListeAimant() {
		return aimant;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste de segments quand le fichier est sauvegard� ou r�cup�r�
	 * @return la liste de segments quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static ArrayList<Segment> getListeSegment() {
		return segment;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste de rectangles quand le fichier est sauvegard� ou r�cup�r�
	 * @return la liste de rectangles quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static ArrayList<Rectangle> getListeRectangle() {
		return rectangle;
	}

	//Estelle
	/**
	 * Il permet d'obtenir la liste de triangles quand le fichier est sauvegard� ou r�cup�r�
	 * @return la liste de triangles quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static ArrayList<Triangle> getListeTriangle() {
		return triangle;
	}

	//Estelle
	/**
	 * Il permet d'obtenir le filet mis quand le fichier est sauvegard� ou r�cup�r�
	 * @return le filet quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static Filet getFilet() {
		return filet;
	}

	//Estelle
	/**
	 * Il permet d'obtenir le canon quand le fichier est sauvegard� ou r�cup�r�
	 * @return le canon quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static Canon getCanon() {
		return canon;
	}
	
	//Jason
	/**
	 * Il permet d'obtenir le nom du jeu quand le fichier est sauvegard� ou r�cup�r�
	 * @return le nom du jeu quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static String getNom() {
		return nomJeux;
	}

	//Jason
	/**
	 * Il permet d'avoir le nombre d'essais quand le fichier est sauvegard� ou r�cup�r�
	 * @return le nombre d'essais quand le fichier est sauvegard� ou r�cup�r�
	 */
	public static int getNbreEssai() {
		return nbreEssai;
	}

	//Jason
	/**
	 * Il permet de retourner toute les formes pr�sentes dans un ArrayList
	 * @return toutes les formes du jeu
	 */
	public static ArrayList<Forme> getForme(){
		formeTout.clear();

		//Pour r�cup�rer les filets
		formeTout.add(filet);

		//Pour r�cup�rer les canons
		formeTout.add(canon);		

		//Pour r�cup�rer les cercles
		for (int listeCercle = 0; listeCercle < cercle.size(); listeCercle++) {
			formeTout.add(cercle.get(listeCercle));
		}

		//Pour r�cup�rer les aimants
		for (int listeAimant = 0; listeAimant < aimant.size(); listeAimant++) {
			formeTout.add(aimant.get(listeAimant));

		}	

		//Pour r�cup�rer les rectangles
		for (int listeRectangle = 0; listeRectangle < rectangle.size(); listeRectangle++) {
			formeTout.add(rectangle.get(listeRectangle));

		}		

		//Pour r�cup�rer les triangles
		for (int listeTriangle = 0; listeTriangle < triangle.size(); listeTriangle++) {
			formeTout.add(triangle.get(listeTriangle));

		}	

		//Pour r�cup�rer les segments
		for (int listeSegment = 0; listeSegment < segment.size(); listeSegment++) {
			formeTout.add(segment.get(listeSegment));

		}	

		return  formeTout;
	}


	//James
	/**
	 * M�thode qui permet de prendre tous les noms de tous les fichiers sauvegard�s
	 * @return le tableau qui contient tous les noms de fichiers sauvegard�s
	 */
	public static ArrayList<String> getNomSauvegarde() {
		lireFichierTexteBureau();
		return listeNoms;
	}


	//James
	/**
	 * Il permet de supprimer un niveau
	 * @param nomFichier le nom du fichier � supprimer
	 * @return si le fichier est supprim�
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

				//3) on fait une nouvelle arrayList listeNoms ou le fichier qui doit �tre supprim� ne doit pas �tre l�
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

				//4) on r�ecrit le fichier de la liste des noms pour que le fichier supprim� ne soit pas l�
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
				// on ex�cutera toujours ceci, erreur ou pas
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
