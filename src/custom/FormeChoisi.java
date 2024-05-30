package custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import forme.Aimant;
import forme.Ballon;
import forme.Cercle;
import forme.Forme;
import forme.Rectangle;
import forme.Segment;
import forme.Triangle;
import physique.ModeleAffichage;

/**
 * Cette classe permet de voir l'image de l'objet sélectionné.
 *@author James
 *@author Jason
 */

public class FormeChoisi extends JPanel {

	private static final long serialVersionUID = 1L;


	private ArrayList<Forme> forme = new ArrayList<Forme>() ;

	private final double LARGEUR_MONDE = 1;
	private int index = 0; 
	private boolean estBallon = false;

	// par James
	/**
	 * Il initialize la classe
	 * 
	 */
	public FormeChoisi() {
		//objet
		Cercle cercle = new Cercle(LARGEUR_MONDE / 2,0);
		Rectangle rectangle = new Rectangle(LARGEUR_MONDE / 2,0);
		Triangle triangle = new Triangle(LARGEUR_MONDE / 2,0);
		Aimant aimant = new Aimant (LARGEUR_MONDE / 2,0);
		Segment segment = new Segment (LARGEUR_MONDE / 2,0);
		Ballon ballon = new Ballon(LARGEUR_MONDE / 2,0);

		forme.add(cercle);
		forme.add(rectangle);
		forme.add(triangle);
		forme.add(aimant);
		forme.add(segment);
		forme.add(ballon);
	}


	@Override
	//James
	/**
	 * Permet de dessiner la forme de l'objet sélectionné par l'utilisateur.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		ModeleAffichage modele = new ModeleAffichage(this.getWidth(), this.getHeight(), LARGEUR_MONDE);

		double rayon = modele.getHautUnitesReelles();
		if (!estBallon) {
			//installation comme liste
			switch(index) {
			case 0:
				Cercle dessinerCercle =   (Cercle) forme.get(index);
				centrerObjet(rayon,dessinerCercle);

				dessinerCercle.dessiner(g2d, modele.getMatMC());
				break;
			case 1:
				Rectangle dessinerRectangle =   (Rectangle) forme.get(index);
				centrerObjet(rayon,dessinerRectangle);
				dessinerRectangle.dessiner(g2d, modele.getMatMC());
				break;	        
			case 2:
				Triangle dessinerTriangle =   (Triangle) forme.get(index);
				centrerObjet(rayon,dessinerTriangle);
				dessinerTriangle.dessiner(g2d, modele.getMatMC());	
				break;
			case 3:
				Aimant dessinerAimant =   (Aimant) forme.get(index);
				centrerObjet(rayon,dessinerAimant);
				dessinerAimant.dessiner(g2d, modele.getMatMC());
				break;
			case 4:
				Segment dessinerSegment =   (Segment) forme.get(index);
				centrerObjet(rayon,dessinerSegment);
				dessinerSegment.dessiner(g2d, modele.getMatMC());
			}
		}else {
			Ballon dessinerBallon = (Ballon) forme.get(forme.size()-1);
			centrerObjet(rayon,dessinerBallon);
			dessinerBallon.dessiner(g2d, modele.getMatMC());
		}
	}


	//James
	/**
	 * Permet de changer d'obstacle à prendre en appuyant sur une flèche gauche
	 * 
	 */
	public void gauche () {
		index++;
		if (index >forme.size() - 2) {
			index = 0;
		}
		repaint();	
	}

	//James
	/**
	 * Permet de changer d'obstacle à prendre en appuyant sur une flèche droite
	 * 
	 */ 
	public void droit () {
		index--;
		if (index < 0 ) {
			index = forme.size() - 2;
		}
		repaint();	
	}

	//James
	/**
	 * Permet de centrer un objet dans l'interface en haut
	 * @param rayon la hauteur et la largeur de l'objet Forme
	 * @param forme l'objet Forme
	 */
	private void centrerObjet (double rayon, Forme forme) {
		forme.setHauteur(rayon * 8/9);
		forme.setLargeur(rayon * 8/9);
		if (!forme.getNomClasse().equalsIgnoreCase("segment")) {

			forme.setX(LARGEUR_MONDE/2 - rayon/2 );
		}else {
			forme.setX(LARGEUR_MONDE/2  );
		}

		forme.setY(LARGEUR_MONDE/100);
	}

	//James
	/**
	 * Permet de prendre l'objet selectionné
	 * @return l'objet selectionné
	 */
	public Forme getObjet() {
		return forme.get(index);
	}

	//Jason
	/**
	 * Il permet d'afficher la forme qui est sélectionnée
	 * @param formeSelectionne la forme qui a été sélectionnée
	 */
	public void setObjet(Forme formeSelectionne) {
		boolean trouver = false;
		int typeForme = -1;
		while (!trouver) {
			typeForme++;
			if (formeSelectionne.getNomClasse().equals(forme.get(typeForme).getNomClasse())) {
				index = typeForme;
				trouver = true;
			}
		}
	}


	//James
	/**
	 * Il permet de modifier la couleur du fond d'écran
	 * @param couleur la nouvelle couleur du fond d'écran
	 */
	public void setCouleurFondEcran(Color couleur) {
		setBackground(couleur);
	}

	//James
	/**
	 * Permet de savoir si la forme qui doit être vue est une balle ou une autre forme
	 * @param valeur la valeur booléenne qui affirme si la forme qui doit être vue est une balle ou une autre forme
	 */
	public void estBallon (boolean valeur) {
		this.estBallon = valeur;
		repaint();
	}
}
