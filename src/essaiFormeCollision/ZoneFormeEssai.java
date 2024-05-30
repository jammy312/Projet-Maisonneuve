package essaiFormeCollision;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import forme.Cercle;
import forme.Rectangle;
import forme.Segment;
import forme.Triangle;
import physique.ModeleAffichage;
/**
 * Cette classe permet de vérifier si nos formes se dessinent
 * @author Jason
 */
public class ZoneFormeEssai extends JPanel {
	
	private static final long serialVersionUID = 1L;
	Cercle cercle;
	Rectangle rectangle;
	Triangle triangle;
	Segment segment;
	private final double LARGEUR_MONDE = 500;
	private final double rotation = 130;


	/**
	 * Permet de tester le dessin des formes
	 * @param g pour faire le graphique
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ModeleAffichage modele = new ModeleAffichage(this.getWidth(), this.getHeight(), LARGEUR_MONDE);
		Graphics2D g2d = (Graphics2D) g;

		cercle = new Cercle(modele.getLargUnitesReelles()/2, modele.getHautUnitesReelles()/2);
		rectangle = new Rectangle(modele.getLargUnitesReelles()/2, 0);
		triangle = new Triangle(modele.getLargUnitesReelles()/3, modele.getHautUnitesReelles()/2);
		segment = new Segment(modele.getHautUnitesReelles()/2, modele.getLargUnitesReelles()/3);


		cercle.dessiner(g2d, modele.getMatMC());
		rectangle.dessiner(g2d, modele.getMatMC());
		triangle.dessiner(g2d, modele.getMatMC());
		segment.dessiner(g2d, modele.getMatMC());

		triangle.setX(modele.getLargUnitesReelles()/2);
		triangle.setY(modele.getHautUnitesReelles()/4);

		triangle.setRotation(rotation);

		rectangle.setX(rectangle.getX() + rectangle.getLargeur() *1.3);
		rectangle.setLargeur(modele.getLargUnitesReelles()/2);
		rectangle.setHauteur(modele.getHautUnitesReelles()/2);

		segment.setX(0);
		segment.setY(0);
		segment.setLargeur(modele.getLargUnitesReelles());
		segment.setHauteur(modele.getHautUnitesReelles());


		triangle.dessiner(g2d, modele.getMatMC());
		segment.dessiner(g2d, modele.getMatMC());
		rectangle.dessiner(g2d, modele.getMatMC());
	}

}



;