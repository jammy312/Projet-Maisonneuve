package forme;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
/**
 * C'est la classe du rectangle dans notre jeu
 * @author Jason
 *
 */
public class Rectangle extends Forme{
	Path2D.Double rectangle;
	ArrayList<Line2D.Double> rectangleSegment = new ArrayList<Line2D.Double>();

	/**
	 * Constructeur de la classe rectangle
	 * @param positionX la position en x en haut à droite du rectangle
	 * @param positionY la position en y en haut à droite du rectangle
	 */
	public Rectangle(double positionX, double positionY) {
		super(positionX, positionY);
	}

	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		rectangleSegment.clear();

		// pour faire en format Line2D des Rectangles
		rectangleSegment.add(new Line2D.Double(getX(), getY(),getX() + getLargeur(), getY() ));
		rectangleSegment.add(new Line2D.Double(getX() + getLargeur(), getY(),getX() + getLargeur(), getY() + getHauteur()));
		rectangleSegment.add(new Line2D.Double(getX() + getLargeur(), getY() + getHauteur(),getX(), getY() + getHauteur()));
		rectangleSegment.add(new Line2D.Double(getX(), getY() + getHauteur() ,getX(), getY()));
		this.setSegment(rectangleSegment);

		// Pour dessiner le rectangle
		this.dessinerForme(g2d, mat, new Area(new Rectangle2D.Double(this.getX(), this.getY(), this.getLargeur(), this.getHauteur())));
	}
}
