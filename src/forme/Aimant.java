package forme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Cette classe permet de créer l'objet aimant
 * @author James
 * @author Jason
 * @author Estelle
 */
public class Aimant  extends Forme{

	private double intensite = 0;
	private Ellipse2D.Double aimant = new Ellipse2D.Double();
	private final double CHARGE_AIMANT = 6000;
	private final Color COULEUR_AIMANT = new Color(65,105,225);
	
	
	//James
	/**
	 * Constructeur pour faire l'aimant
	 * @param x la position en x de l'aimant
	 * @param y la position en y de l'aimant
	 */
	public Aimant (double x, double y){
		super(x,y);
	}

	//James
	/**
	 *  Constructeur pour faire l'aimant mais on peut changer l'intensité de l'aimant
	 * @param x la position en x de l'aimant
	 * @param y la position en y de l'aimant
	 * @param intensite l'intensité de l'aimant
	 */
	public Aimant(double x, double y, double intensite) {
		super(x,y);
		this.intensite = intensite;
	}

	//James
	/**
	 * Permet de dessiner l'aimant
	 * @param g2d pour dessiner la forme
	 * @param mat pour la modifer en unité réelle
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		this.dessinerForme(g2d, mat, new Area(this.getAimant()));
	}

	//James
	/**
	 * Permet de prendre l'intensité de l'aimant
	 * @return l'intensité de l'aimant
	 */
	public double getIntensite() {
		return intensite;
	}

	//James
	/**
	 * Permet de changer l'intensité de l'aimant
	 * @param intensite l'intensité de l'aimant
	 */
	public void setIntensite(double intensite) {
		this.intensite = intensite;
	}

	//James
	/**
	 * Permet de savoir si l'aimant est en collision avec un ballon
	 * @param balle la balle en collision avec l'aimant
	 * @return une valeur booléenne pour savoir s'il est en collision ou non
	 */
	public boolean collision (Cercle balle) {
		if( this.aimant.intersects(aimant.getX(), aimant.getY(), aimant.getWidth(), aimant.getHeight())) {

			return true;
		}else {
			return false;
		}

	}

	//James
	/**
	 * Il permet de prendre la charge de l'aimant.
	 * @return chargeAimant la charge de l'aimant.
	 */
	public double getChargeDeAimant() {
		return CHARGE_AIMANT;
	}

	//James
	/**
	 * Donne la forme du cercle
	 * @return la forme du cercle
	 */
	public Ellipse2D.Double getAimant() {

		return new Ellipse2D.Double(this.getX(),this.getY(),this.getLargeur(),this.getHauteur());
	}

	//Jason
	/**
	 * Donne la surface de l'aimant
	 * @return la surface de l'aimant
	 */
	public Area getForme() {
		return new Area(this.getAimant());

	}
	
	//Estelle
	/**
	 * Donne la couleur de l'aimant
	 * @return la couleur de l'aimant
	 */
	public Color getColor() {
		return COULEUR_AIMANT;
	}

	
}
