package evenement;

import java.util.EventListener;
/**
 * Cette interface permet de retouner la valeur que l'utilisateur doit pouvoir voir
 * @author Jason
 *
 */
public interface BallonListener extends EventListener{

	/**
	 * Il permet de donner la position en x et en y en unités réelles du ballon
	 * @param x la position en x du ballon
	 * @param y la position en y du ballon
	 */
	public void positionXY( double x, double y);

	/**
	 * Il permet de donner la vitesse en x et en y en unités réelles du ballon
	 * @param vX la vitesse en x du ballon
	 * @param vY la vitesse en y du ballon
	 */
	public void vitesseXY(double vX, double vY);

	/**
	 * Il permet de donner l'accélération en x et en y en unités réelles du ballon
	 * @param aX l'accélération en x du ballon
	 * @param aY l'accélération en y du ballon
	 */
	public void accelerationXY(double aX, double aY);

	/**
	 * Il permet de donner l'angle d'orientation du ballon selon sa vitesse
	 * @param degre l'angle du ballon
	 */
	public void degre(double degre);

	/**
	 * Il permet de savoir si le jeu a commencé ou non
	 * @param jeuCommence si le jeu commence ou non
	 */
	public void estCommence(boolean jeuCommence);
	
	/**
	 * Il permet de retourner le nombre de vies (essais) que l'utilisateur possède
	 * @param vie le nombre de vies que l'utilisateur possède
	 */
	public void getVie(int vie);
}
