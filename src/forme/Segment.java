package forme;

import java.awt.geom.Line2D;
import java.util.ArrayList;
/**
 * Cette classe représente le segment dans notre jeu
 * @author Jason
 *
 */
public class Segment extends Rectangle {
	Line2D.Double segment;
	ArrayList<Line2D.Double> segmentList =  new ArrayList<Line2D.Double>();
	/**
	 * C'est le constructeur de la classe Segment
	 * @param positionX la position en x en haut à gauche
	 * @param positionY la position en y en haut à gauche
	 */
	public Segment(double positionX, double positionY) {
		super(positionX, positionY);
		this.setLargeur(getTaillleInitiale());
	}
	
	@Override
	/**
	 * Permet de changer la largeur du segment.
	 */
	public void setLargeur(double largeur) {
		super.setLargeur(largeur/10);
	}
	
	@Override
	/**
	 * Permet de changer la largeur et la hauteur du segment.
	 */
	public void setLargeurHauteur(double largeurHauteur) {
		this.setHauteur(largeurHauteur);
		this.setLargeur(largeurHauteur);
	}
	
}
