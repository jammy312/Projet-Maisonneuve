package fichiers;

import java.util.ArrayList;

import forme.Aimant;
import forme.Canon;
import forme.Cercle;
import forme.Filet;
import forme.Rectangle;
import forme.Segment;
import forme.Triangle;

/**
 *	Cette classe regroupe tout ce qu'il faut pour créer un fichier "Niveau"
 * @author Estelle
 *
 */
public class Niveau extends GestionnaireDeFichiers {
	static int posVirgule = 0;
	static ArrayList<Integer> listePosVirgule = new ArrayList<Integer>();
	static double x, y, larg, rot;
	static boolean inf;
	static String donneeLue;

	/**
	 *Méthode qui permet de créer un objet selon une ligne de données dans un fichier de texte
	 *@param nomObjet le nom de l'objet créé
	 *@param donneesObjet les données telles que la taille, la rotation ou bien son influence face à la gravité
	 */
	public static void creerObjet (String nomObjet, String donneesObjet) {
		listePosVirgule.clear(); //liste qui renferme les positions des virgules dans une ligne 
		posVirgule = 0; //variable qui contient la position de la virgule
		do { //insere dans le tableau toutes les positions des virgules trouvees au cours de la ligne
			posVirgule = donneesObjet.indexOf(',',posVirgule+1);	
			listePosVirgule.add(posVirgule);
			//System.out.println(posVirgule);
		} while (posVirgule != donneesObjet.lastIndexOf(','));

		
		donneeLue = donneesObjet.substring(1, listePosVirgule.get(0)); //lit le nombre qui se trouve à partir du premier chiffre jusqu'à la premiere virgule
		x = Double.parseDouble(donneeLue);

		if (nomObjet.equals("Filet") || nomObjet.equals("Canon")) {
			donneeLue = donneesObjet.substring(listePosVirgule.get(0)+1, donneesObjet.length()-1); //lit le deuxième nombre qui se trouve entre la premiere virgule et le ]
			y = Double.parseDouble(donneeLue); 
			switch (nomObjet) {
			case "Filet":
				filet = new Filet(x,y);
				break;
			case "Canon":
				canon = new Canon(x,y);
				break;
			}
		} else {
			//on lit les nombres et données entre les virgules et on les passes aux variables correspondantes
			donneeLue = donneesObjet.substring(listePosVirgule.get(0)+1, listePosVirgule.get(1));
			y = Double.parseDouble(donneeLue);
			donneeLue = donneesObjet.substring(listePosVirgule.get(1)+1, listePosVirgule.get(2));
			larg = Double.parseDouble(donneeLue);
			switch (nomObjet) { //on ajoute dans chaque forme correspondante
			
			case "Cercle":
				donneeLue = donneesObjet.substring(listePosVirgule.get(2)+1, listePosVirgule.get(3));
				rot = Double.parseDouble(donneeLue);
				donneeLue = donneesObjet.substring(listePosVirgule.get(3)+1, donneesObjet.length()-1);
				Cercle cercleLu = new Cercle(x,y);
				cercleLu.setLargeurHauteur(larg);
				cercleLu.setRotation(rot);
				if (donneeLue.equals("true")) {
					cercleLu.Influancable(true);
				} else {
					cercleLu.Influancable(false);
				}
				cercle.add(cercleLu);
				break;

			case "Triangle":
				donneeLue = donneesObjet.substring(listePosVirgule.get(2)+1, donneesObjet.length()-1);
				rot = Double.parseDouble(donneeLue);
				Triangle triangleLu = new Triangle(x, y);
				triangleLu.setLargeurHauteur(larg);
				triangleLu.setRotation(rot);
				triangle.add(triangleLu);
				break;

			case "Segment":
				donneeLue = donneesObjet.substring(listePosVirgule.get(2)+1, donneesObjet.length()-1);
				rot = Double.parseDouble(donneeLue);
				Segment segmentLu = new Segment(x, y);
				segmentLu.setLargeurHauteur(larg);
				segmentLu.setRotation(rot);
				segment.add(segmentLu);
				break;

			case "Rectangle":
				donneeLue = donneesObjet.substring(listePosVirgule.get(2)+1, donneesObjet.length()-1);
				rot = Double.parseDouble(donneeLue);
				Rectangle rectangleLu = new Rectangle(x,y);
				rectangleLu.setLargeurHauteur(larg);
				rectangleLu.setRotation(rot);
				rectangle.add(rectangleLu);
				break;	

			case "Aimant":
				donneeLue = donneesObjet.substring(listePosVirgule.get(2)+1, donneesObjet.length()-1);
				rot = Double.parseDouble(donneeLue);
				Aimant aimantLu = new Aimant(x,y);
				aimantLu.setLargeurHauteur(larg);
				aimantLu.setRotation(rot);
				aimant.add(aimantLu);
				break;

			}
		}
	}
	
	
	


}







