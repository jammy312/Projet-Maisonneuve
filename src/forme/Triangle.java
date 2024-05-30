package forme;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
/**
 * Cette classe représente le triangle dans notre jeu
 * @author Jason
 *
 */
public class Triangle extends Forme{
	Path2D.Double triangle;
	ArrayList<Line2D.Double> triangleSegment = new ArrayList<Line2D.Double>();

	/**
	 * C'est le constructeur de la classe Triangle
	 * @param positionX la position en x en haut à gauche
	 * @param positionY la position en y en haut à gauche
	 */
	public Triangle(double positionX, double positionY) {
		super(positionX, positionY);
	}

	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		triangle = new Path2D.Double();
		triangleSegment.clear();
		
		// Pour faire le triangle
		triangle.moveTo(getX(), getY()+getHauteur());
		triangle.lineTo(getX() + getLargeur()/2, getY());
		triangle.lineTo(getX()+ getLargeur(), getY() + getHauteur());
		triangle.lineTo(getX(), getY() + getHauteur());

		// pour faire en format Line2D des Rectangles
		triangleSegment.add(new Line2D.Double(getX(), getY()+getHauteur(),    getX() + getLargeur()/2, getY() ));
		triangleSegment.add(new Line2D.Double(getX() + getLargeur()/2, getY(),   getX()+ getLargeur(), getY() + getHauteur() ));
		triangleSegment.add(new Line2D.Double(getX()+ getLargeur(), getY() + getHauteur() ,getX(), getY() + getHauteur() ));
		this.setSegment(triangleSegment);
		
		//Pour dessiner le triangle
		this.dessinerForme(g2d, mat, new Area(triangle));
	}

}
