package forme;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import physique.Vecteur;

/**
 *Cette classe est la mère de toute les formes qui peuvent influencer le ballon.
 *Elle possède des méthodes utiles et nécéssaires pour créer toutes les formes
 * @author Jason
 */
public abstract class Forme{
	// La x, la y, la largeur et la hauteur seront considérés selon la largeur du monde réel
	private double positionX;
	private double positionY;
	private double largeur;
	private double hauteur;
	private double rotation = 0;
	private final static double TAILLE_INITIALE = 50;
	private final Color COULEUR_FORME = new Color(143,94,26);
	private final Color COULEUR_FORME_EXTERIEUR = new Color(0,0,0);
	private final Color COULEUR_FORME_EXTERIEUR_DEPLACE = new Color(255,255,0);
	private ArrayList<Line2D.Double> formeSegment = new ArrayList<Line2D.Double>(); 
	private final int MULTIPLICATEUR_SEGMENT = 3;
	private final double TAILLE_MINI_CERCLE = 0.05;
	private boolean estDeplace = false;
	private final float OPACITE = 0.65F;
	private boolean visible = true;


	/**
	 * Constructeur de la classe Forme
	 * @param positionX sa position en X 
	 * @param positionY sa position en Y
	 */
	public Forme (double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.largeur = TAILLE_INITIALE;
		this.hauteur = TAILLE_INITIALE;
	}
	/**
	 * Constructeur de la classe Forme
	 */
	public Forme() {
		this.positionX = 0;
		this.positionY = 0;
		this.largeur = TAILLE_INITIALE;
		this.hauteur = TAILLE_INITIALE;	
	}

	/**
	 * Il permet de  récupérer la position en X en haut à droite de la forme
	 * @return la position en X de la forme
	 */
	public double getX() {
		return positionX;
	}

	/**
	 * Il permet de modifier la position en X en haut à droite de la forme
	 * @param positionX la nouvelle position en X
	 */
	public void setX(double positionX) {
		this.positionX = positionX;
	}

	/**
	 * Il permet de  récupérer la position en Y en haut à droite de la forme
	 * @return la position en Y de la forme
	 */
	public double getY() {
		return positionY;
	}

	/**
	 * Il permet de modifier la position en Y en haut à droite de la forme
	 * @param positionY la nouvelle position en Y
	 */
	public void setY(double positionY) {
		this.positionY = positionY;
	}

	/**
	 * Il permet de récupérer la largeur de la forme
	 * @return la taille en X de la forme
	 */
	public double getLargeur() {
		return largeur;
	}

	/**
	 * Il permet de récupérer la hauteur de la forme
	 * @return la taille en Y de la forme
	 */
	public double getHauteur() {
		return hauteur;
	}

	/**
	 * Il permet de modifier la largeur de l'objet
	 * @param tailleX la nouvelle taille en x de l'objet
	 */
	public void setLargeur(double tailleX) {
		this.largeur = tailleX;
	}

	/**
	 * Il permet de modifier la hauteur de l'objet
	 * @param tailleY la nouvelle taille en y de l'objet
	 */
	public void setHauteur(double tailleY) {
		this.hauteur = tailleY;
	}

	/**
	 * Il permet de modifier la rotation de l'objet en degrés
	 * @param rotation la nouvelle rotation de l'objet en degrés
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	/**
	 * Il permet de récupérer la rotation de l'objet en degrés
	 * @return la rotation de l'objet en degrés
	 */
	public double getRotation() {
		return this.rotation;
	}


	/**
	 * Il permet de récupérer la taille initiale des formes
	 * @return la taille initiale des formes
	 */
	public static double getTaillleInitiale() {
		return TAILLE_INITIALE;
	}

	/**
	 * Il permet de récupérer la couleur de la forme
	 * @return la couleur de la forme
	 */
	public Color getColor() {
		return COULEUR_FORME;
	}

	/**
	 * Il permet d'avoir la couleur extérieure de la forme
	 * @return la couleur extérieure de la forme
	 */
	public Color getExterieurColor() {
		if (estDeplace) {
			return COULEUR_FORME_EXTERIEUR_DEPLACE;
		}else {
			return this.COULEUR_FORME_EXTERIEUR;
		}
	}

	/**
	 * Il permet de connaitre le nom de la classe qui est utilisée
	 * Par exemple, si on utilise la classe cercle, il retourne "cercle"
	 * @return le nom de la classe
	 */
	public String getNomClasse() {
		String nomComplet = Forme.this.getClass().getName();
		return nomComplet.substring(Forme.class.getName().length()/2+1);
	}

	/**
	 * Il permet de récupérer l'ArrayList nécéssaire pour voir la collision entre un cercle et un segment de la forme
	 * @return la liste des segments de la forme
	 */
	public ArrayList<Line2D.Double> getSegment() {
		ArrayList<Line2D.Double> segmentOrientation = new ArrayList<Line2D.Double>();
		segmentOrientation.clear();
		for (int segment = 0; segment < formeSegment.size(); segment++) {
			segmentOrientation.add(this.orientationSegment(formeSegment.get(segment)));
		}
		return segmentOrientation;	
	}

	/**
	 * Il permet de modifier et de recevoir un segment qui a subi une rotation
	 * @param segment le segment qui va subir une rotation
	 * @return le segment après la rotation
	 */
	private Line2D.Double orientationSegment(Line2D.Double segment) {

		//point original
		Point2D p1 = new Point2D.Double(segment.getX1(),segment.getY1());
		Point2D p2 = new Point2D.Double(segment.getX2(),segment.getY2());

		AffineTransform mat = new AffineTransform();
		mat.rotate(Math.toRadians(getRotation()),this.getX()+this.getLargeur()/2,this.getY()+this.getHauteur()/2);

		Point2D p1AvecRotation = mat.transform(p1, null );
		Point2D p2AvecRotation = mat.transform(p2, null );

		return new Line2D.Double(p1AvecRotation.getX(),p1AvecRotation.getY(),p2AvecRotation.getX(),p2AvecRotation.getY() );

	}	

	/**
	 * Il permet d'avoir une série de mini-cercles qui se trouvent dans les coins des segments
	 * Utile lors d'une collision avec un cercle
	 * @return les cercles qui se trouvent dans les segments
	 */
	public ArrayList<Cercle> getMiniCercle() {
		ArrayList<Cercle> MiniCercle = new ArrayList<Cercle>();
		MiniCercle.clear();
		Cercle cercle;
		if (formeSegment == null || formeSegment.size() == 0) {
			return new ArrayList<Cercle>();
		}else {
			for (int segmentChoisi = 0; segmentChoisi < this.getSegment().size(); segmentChoisi++) {
				
				cercle = new Cercle();
				cercle.setLargeurHauteur(TAILLE_MINI_CERCLE);
				cercle.setVecteurPosition(new Vecteur(this.getSegment().get(segmentChoisi).getX2(),this.getSegment().get(segmentChoisi).getY2()));
				MiniCercle.add(cercle);	

			}
			return MiniCercle;
		}
	}


	/**
	 * Cette méthode permet de modifier l'ArrayList nécéssaire pour voir la collision
	 * @param formeSegment la nouvelle  liste des segments de la forme
	 */
	public void setSegment(ArrayList<Line2D.Double> formeSegment) {
		this.formeSegment = new ArrayList<Line2D.Double> (formeSegment);
	}

	/**
	 * Il retourne la position au centre de la forme en vecteur position
	 * @return la position du centre de la forme
	 */
	public Vecteur getVecteurPosition() {
		return new Vecteur(this.getX()+this.getLargeur()/2, this.getY()+this.getHauteur()/2);
	}


	/**
	 * Il permet de dessiner la forme du Shape
	 * @param g2d celui qui va dessiner la forme
	 * @param mat Il permet de le changer en mode réel
	 * @param forme la forme qui sera dessinée
	 */
	public void dessinerForme(Graphics2D g2d, AffineTransform mat, Shape forme) {
		AffineTransform g2dOriginal = g2d.getTransform();		
		AffineTransform matLocal = new AffineTransform(mat);
		Composite compositeOriginal = g2d.getComposite();
		matLocal.rotate(Math.toRadians(getRotation()),this.getX()+this.getLargeur()/2,this.getY()+this.getHauteur()/2);
		if (visible) {

			if (estDeplace) {
				g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, OPACITE ) ); // g2d dessinea avec 40% de transparence
			}

			g2d.setColor(getColor());
			g2d.fill(matLocal.createTransformedShape(forme));

			g2d.setStroke(new BasicStroke(MULTIPLICATEUR_SEGMENT));
			g2d.setColor(getExterieurColor());
			g2d.draw(matLocal.createTransformedShape(forme));
				
		}
		
		// pour récuperer la g2d originale et le mat original
		g2d.setTransform(g2dOriginal);
		if (estDeplace) {
			g2d.setComposite(compositeOriginal ); // g2d dessinea avec 40% de transparence
		}
		
	}

	/**
	 * Il permet de modifier la position du centre de la forme
	 * @param position la nouvelle position du centre de la forme
	 */
	public void setVecteurPosition( Vecteur position) {
		this.positionX = position.getX() - this.getLargeur()/2;
		this.positionY = position.getY() - this.getHauteur()/2;
	}

	/**
	 * Il permet de modifier la largeur et la hauteur de la forme pour qu'elles aient la même valeur
	 * @param largeurHauteur la nouvelle valeur en unités réelles
	 */
	public void setLargeurHauteur(double largeurHauteur) {
		this.largeur =  largeurHauteur;
		this.hauteur = largeurHauteur;
		//TAILLE_MINI_CERCLE = largeurHauteur/50.0;
	}

	/**
	 * Il permet de dessiner la forme
	 * @param g2d celui qui va dessiner la forme
	 * @param mat la modification de la forme en mode réelle
	 */
	public abstract void dessiner(Graphics2D g2d, AffineTransform mat);

	/**
	 * Il permet de savoir si la forme se fait déplacer par la souris ou non
	 * @param estDeplace il nous dit s'il est deplacé
	 */
	public void estDeplace(boolean estDeplace) {
		this.estDeplace = estDeplace;
	}

	/**
	 * Il permet de savoir si la forme se déplace ou non
	 * @return si elle se déplace ou non
	 */
	public boolean estDeplace() {
		return this.estDeplace;
	}

	/**
	 * Il permet de savoir si la ballon doit être visible ou non
	 * @param visible pour savoir si le ballon est visible ou non
	 */
	public void estVisible(boolean visible) {
		this.visible  = visible;
	}


	/**
	 * 
	 * @return la forme de l'objet en format Area
	 */
	public Area getForme() {
		if (this.getSegment().size() == 0) {
			return null;
		}else {
			Path2D.Double formeArea = new Path2D.Double();
			formeArea.moveTo(getSegment().get(0).getX1(), getSegment().get(0).getY1());
			for (int segment = 0; segment < this.getSegment().size(); segment++) {
				formeArea.lineTo(getSegment().get(segment).getX1(), getSegment().get(segment).getY1());
				formeArea.lineTo(getSegment().get(segment).getX2(), getSegment().get(segment).getY2());
			}
			return new Area(formeArea);
		}

	}
} 
