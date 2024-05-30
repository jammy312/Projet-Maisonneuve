package forme;

import java.awt.Color;

/**
 * Cette classe repr�sente le ballon principal dans notre jeu
 *  @author James
 *  @author Jason
 */

public class Ballon extends Cercle  {

	private final double MASSE_INITIALE = 25;
	private final double TAILLE  = 15;
	private final Color COULEUR = new Color(255,69,0);
	private final Color COULEUR_EXTERIEUR = new Color(0,0,0);
	
	//James
	/**
	 * Constructeur de l'objet ballon
	 * @param x position en x de l'objet ballon
	 * @param y position en y de l'objet ballon
	 */
	public Ballon(double x, double y) {
		super(x, y);
		this.Influancable(true);
		this.setMasse(MASSE_INITIALE);
		this.setLargeurHauteur(TAILLE);

	}
	//Jason
	/**
	 * Cette m�thode permet de changer la couleur int�rieure du ballon 
	 */
	public Color getColor() {
		return COULEUR;
	} 
	
	//Jason
	/**
	 * Cette m�thode permet de changer la couleur ext�rieure du ballon 
	 */
	public Color getExterieurColor() {
		if (this.estDeplace()) {
			return super.getExterieurColor();
		}else {
			return COULEUR_EXTERIEUR;
		}	
	}

}
