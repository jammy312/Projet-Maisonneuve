package custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import forme.Aimant;
import forme.Canon;
import forme.Cercle;
import forme.Filet;
import forme.Forme;
import forme.Rectangle;
import forme.Segment;
import forme.Triangle;
import physique.ModeleAffichage;



/**Cette classe permet de créer les niveaux desirés
 * @author James
 */
public class ModeEditer extends JPanel {

	private static final long serialVersionUID = 1L;


	private ArrayList<Forme> forme = new ArrayList<Forme>() ;
	private final double LARGEUR_MONDE = 1000;
	// pour bouger un objet
	private boolean clicke = false;
	private int nbClicke = -1 ;
	private  double pixelParUniteX ;
	private  double pixelParUniteY ;
	private double hauteurUniteReel;
	private double largeurUniteReel;
	private double xPrecedent;
	private double yPrecedent;
	private double largeurPixel;
	private double hauteurPixel;
	private Image fondEcran = null;
	private String FOND_ECRAN_NOM = "fondEcranBac.jpg";
	private Filet filet = new Filet(LARGEUR_MONDE / 2,LARGEUR_MONDE / 2);
	private Canon canon = new Canon(0,LARGEUR_MONDE / 2);
	private int nbEssai;
	private int formeSelectionne;

	/**
	 * Constructeur de cette classe
	 */
	public ModeEditer() {
		forme.clear();
		setBackground(Color.white);

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (clicke) {
					deplacement( largeurUniteReel / largeurPixel * e.getX(), hauteurUniteReel / hauteurPixel * e.getY()  ,forme.get(nbClicke));

				}

			}		
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// on initialise tous les formes pour qu'on sache qu'aucune ne soit déplacée
				for (int nb = 0; nb <= forme.size() - 1; nb ++) {
					forme.get(nb).estDeplace(false);
				}
				//ensuite, on regarde si une forme est sélectionnée
				for (int nb = 0; nb <= forme.size() - 1; nb ++) {
					if (forme.get(nb).getForme().contains(e.getX()/pixelParUniteX, e.getY()/pixelParUniteY)) {
						nbClicke = nb;
						clicke = true;
						canon = (Canon) forme.get(1);
						filet = (Filet) forme.get(0);					
						xPrecedent = forme.get(nb).getX();
						yPrecedent = forme.get(nb).getY();
						forme.get(formeSelectionne).estDeplace(false);
						formeSelectionne = nb;
						forme.get(nb).estDeplace(true);
					}

				}
				//si aucune forme n'est sélectionnée
				if (!clicke) {
					nbClicke = -1;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clicke = false;	
				boolean contain = false;
				if(nbClicke != -1) {
					for (int index = 0; index < forme.size() ; index ++) {
						if(canon.contain(forme.get(index)) || filet.contain(forme.get(index))) {
							contain = true;
						}
						if (formeSelectionne == 1 || formeSelectionne == 0) {
							forme.get(formeSelectionne).estDeplace(false);
						}
					}
					if(contain) {
						deplacement ( xPrecedent + forme.get(nbClicke).getLargeur()/2,yPrecedent +  forme.get(nbClicke).getHauteur() / 2,forme.get(nbClicke));

					}
				}
				repaint();
			}
		});

		forme.add(filet);
		forme.add(canon);
	}
	@Override
	/**
	 * Permet de dessiner chaque forme que l'utilsateur décide de mettre pour son niveau(le filet et le canon sont déja dessinés)
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		ModeleAffichage modele = new ModeleAffichage(this.getWidth(), this.getHeight(), LARGEUR_MONDE);

		pixelParUniteX = modele.getPixelsParUniteX();
		pixelParUniteY = modele.getPixelsParUniteY();
		hauteurUniteReel = modele.getHautUnitesReelles();
		largeurUniteReel = modele.getLargUnitesReelles();
		largeurPixel = modele.getLargPixels();
		hauteurPixel = modele.getHautPixels();
		trierArrayList(forme);
		recupereImage();
		g2d.drawImage(fondEcran,modele.getMatMC(),null);
		for (int nb = 0; nb <= forme.size() - 1; nb ++) {		
			objetDessiner(forme.get(nb),modele, g2d);

		}
		g2d.setColor(Color.black);
		g2d.drawString((int)modele.getHautUnitesReelles() + "",10, 12);
		g2d.drawString((int)modele.getLargUnitesReelles() + "",this.getWidth() - 35, this.getHeight() - 10);
	}
	/**
	 * Permet d'ajouter un nouvel objet dans la classe
	 * @param objet l'objet à ajouter dans la classe
	 */
	public void nouveauObjet (Forme objet) {

		switch(objet.getNomClasse()) {
		case "Cercle":
			Cercle objetCercle = new Cercle(LARGEUR_MONDE / 2, LARGEUR_MONDE / 100);
			forme.add(objetCercle);
			break;
		case "Rectangle":
			Rectangle objetRectangle = new Rectangle(LARGEUR_MONDE / 2,LARGEUR_MONDE / 100);
			forme.add(objetRectangle);
			break;	        
		case "Triangle":
			Triangle objetTriangle = new Triangle(LARGEUR_MONDE / 2,LARGEUR_MONDE / 100);
			forme.add(objetTriangle);
			break;
		case "Aimant":
			Aimant objetAimant = new Aimant(LARGEUR_MONDE / 2,LARGEUR_MONDE / 100);
			forme.add(objetAimant);
			break;
		case "Segment":
			Segment objetSegment = new Segment(LARGEUR_MONDE / 2,LARGEUR_MONDE / 100);
			forme.add(objetSegment);
		}
		repaint();
	}

	/**
	 * Méthode qui dessine l'objet dans la liste
	 * @param objet
	 * @param modele
	 * @param g2d
	 */
	private void  objetDessiner (Forme objet, ModeleAffichage modele ,	Graphics2D g2d) {
		switch(objet.getNomClasse()) {
		case "Cercle":
			Cercle objetCercle = (Cercle) objet;
			objetCercle.dessiner(g2d, modele.getMatMC());
			break;
		case "Rectangle":
			Rectangle objetRectangle = (Rectangle) objet;
			objetRectangle.dessiner(g2d, modele.getMatMC());
			break;	        
		case "Triangle":
			Triangle objetTriangle = (Triangle) objet;
			objetTriangle.dessiner(g2d, modele.getMatMC());	
			break;
		case "Aimant":
			Aimant objetAimant = (Aimant) objet;
			objetAimant.dessiner(g2d, modele.getMatMC());		
			break;
		case "Segment":
			Segment objetSegment = (Segment) objet;
			objetSegment.dessiner(g2d, modele.getMatMC());	
			break;
		case "Canon":
			Canon objetCanon = (Canon) objet;
			objetCanon.dessiner(g2d, modele.getMatMC());
			break;
		case "Filet":
			Filet objetFilet = (Filet) objet;
			objetFilet.dessiner(g2d, modele.getMatMC());

		}


	}

	/**
	 * Permet de déplacer l'objet quand il est cliqué
	 * @param x la position x de la souris
	 * @param y la position y de la souris
	 * @param forme la forme déplacée
	 */
	private void deplacement(double x, double y, Forme forme) {
		if (forme.getNomClasse().equalsIgnoreCase("Canon")){

		}
		forme.setX( x - forme.getLargeur() /2);
		forme.setY(y - forme.getHauteur() / 2);
		repaint();
	}

	/**
	 * Permet de prendre la forme cliquée. S'il n'y a aucune forme sélectionnée, il renverra null.
	 * @return la forme selectionnée par le curseur
	 */
	public Forme getFormeSelectionne () {

		if (nbClicke == -1) {
			return null;
		}else {
			return	forme.get(nbClicke);

		}

	}

	/**
	 * Retourne la liste de formes
	 * @return la liste de formes
	 */
	public ArrayList<Forme> getListForme() {
		return forme;
	}

	/**
	 * Il permet de modifier le nombre d'essais souhaités
	 * @param nbEssai le nouveau nombre d'essais souhaités
	 */
	public void setNbEssaie (int nbEssai) {
		this.nbEssai = nbEssai;
	}

	/**
	 * Il permet de retourner le nombre d'essais souhaités
	 * @return le nombre d'essais souhaités
	 */
	public int getNbEssaie () {
		return nbEssai;
	}

	/**
	 * Méthode qui fait le tri dans l'ArrayList pour que les cercles et la balle soient en avant des autres formes
	 * @param forme les formes à trier
	 */
	private  void trierArrayList (ArrayList<Forme> forme) {
		int nb = 0;

		//on regarde d'abord combien il y a de cercles
		for(int i = 0; i< forme.size(); i++) {
			if(forme.get(i).getNomClasse().equalsIgnoreCase("Cercle")) {		
				nb++;
			}
		}

		int nbCercle[] = new int[nb] ;	


		//après on regarde quelles formes sont des cercles

		int nombreCercle = 0;

		for(int i = 0; i< forme.size(); i++) {
			if(forme.get(i).getNomClasse().equalsIgnoreCase("Cercle")) {		
				nbCercle[nombreCercle] = i;
				nombreCercle++;
			}
		}


		//ensuite on fait une nouvelle Arraylist ou toutes les formes exceptées les cercles seront en premier
		ArrayList<Forme> nouveauForme = new ArrayList<Forme>() ;

		for(int nbForme = 0; nbForme < forme.size(); nbForme ++) {
			boolean cercle = false;
			for(int trie = 0; trie < nbCercle.length; trie++) {
				if(nbForme == nbCercle[trie]) {
					cercle = true;
				}
			}
			if(!cercle) {
				nouveauForme.add(forme.get(nbForme));	
			}

		}


		//et on met les cercles en dernier
		for(int nbForme = 0; nbForme < forme.size(); nbForme ++) {
			boolean cercle = false;
			for(int trie = 0; trie < nbCercle.length; trie++) {
				if(nbForme == nbCercle[trie]) {
					cercle = true;
				}
			}
			if(cercle) {
				nouveauForme.add(forme.get(nbForme));	
			}

		}

		forme.clear();
		for (int i = 0; i <  nouveauForme.size() ; i ++) {
			forme.add(nouveauForme.get(i));			

		}

	}

	/**
	 * Il permet de récupérer les formes 
	 * @param forme les formes à récupérer
	 */
	public void setForme (ArrayList<Forme> forme) {
		this.forme = forme;
		repaint();
	}

	/**
	 * Il permet de récupérer le fond d'écran de l'animation
	 */
	private void recupereImage() {
		if (fondEcran == null) {		
			java.net.URL urlImage = getClass().getClassLoader().getResource(FOND_ECRAN_NOM);
			if (urlImage == null) {
				JOptionPane.showMessageDialog(null, "Fichier " + FOND_ECRAN_NOM + " introuvable");
			}
			try {
				fondEcran = ImageIO.read(urlImage);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur pendant la lecture du fichier d'image");
			}

			// redimensionner l'image de la même grandeur que le le composant
			Image imgRedim = fondEcran.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

			fondEcran = imgRedim;
		}
	}

	/**
	 * Permet de changer la couleur du fond de l'écran
	 * @param couleur la couleur du fond de l'écran
	 */
	public void CouleurFond(Color couleur) {
		setBackground(couleur);
	}

}
