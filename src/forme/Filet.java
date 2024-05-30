package forme;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Cette classe sert à dessiner le filet, afin de l'ajouter dans un niveau
 *@author Estelle
 *@author James
 */

public class Filet extends Forme{
	private Image imgFilet = null;
	private final int DIAMETRE = 85;
	ArrayList<Line2D.Double> filetSegment = new ArrayList<Line2D.Double>();
	private final float OPACITE = 0.7f;


	//Estelle
	/**
	 * Constructeur pour le filet
	 * @param posX la position en X
	 * @param posY la position en Y
	 */
	public Filet(double posX, double posY) {
		super(posX,posY);
		//image
		URL sourceFilet = getClass().getClassLoader().getResource("filet.png");

		if (sourceFilet == null){
			System.out.println("Impossible");
			return;
		}
		try {
			imgFilet = ImageIO.read(sourceFilet);
		} 
		catch (IOException e) {
			System.out.println("Erreur");
		}

	}

	//Estelle et James
	/**
	 * Sert à dessiner le filet grâce à une image, sa position et le diamètre de l'image (taille)
	 * @param g2d pour dessiner la forme
	 * @param mat pour la modifer en unité réelle
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		//Estelle
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform matLocale = new AffineTransform(mat);
		Composite compositeOriginal = g2d.getComposite();

		this.setHauteur(DIAMETRE *96/100);
		this.setLargeur(DIAMETRE);
		if (this.estDeplace()) {
			g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, OPACITE   ) ); // g2d dessinera avec 40% de transparence
		}
		
		//James
		double fact = DIAMETRE / (double)imgFilet.getWidth(null);
		matLocale.translate(this.getX() , this.getY());
		matLocale.scale(fact, fact);
		g2d.drawImage(imgFilet, matLocale, null);
		filetSegment.clear();
		filetSegment.add(new Line2D.Double(getX(), getY(),getX() + getLargeur(), getY() ));
		filetSegment.add(new Line2D.Double(getX() + getLargeur(), getY(),getX() + getLargeur(), getY() + getHauteur()));
		filetSegment.add(new Line2D.Double(getX() + getLargeur(), getY() + getHauteur(),getX(), getY() + getHauteur()));
		filetSegment.add(new Line2D.Double(getX(), getY() + getHauteur() ,getX(), getY()));
		this.setSegment(filetSegment);
		matLocale.scale(-fact, -fact);
		if (this.estDeplace()) {
			g2d.setComposite(compositeOriginal);
		}

	}

	//James
	/**
	 * Permet de savoir si le filet touche une autre forme
	 * @param forme la forme qui touche le filet
	 * @return si la forme touche le filet
	 */
	public boolean contain (Forme forme) {
		boolean contain = false;
		if (!forme.getNomClasse().equalsIgnoreCase("Filet")) {
			Area rectangleForme = forme.getForme();

			Area rectangleFilet1 = this.getForme();
			Area rectangleFilet2 = this.getForme();

			rectangleFilet1.subtract(rectangleForme);


			if(!rectangleFilet1.equals(rectangleFilet2) ) {
				contain = true;
			}
		}
		return contain;
	}

	//James
	/**
	 * Il permet de retourner l'image du filet
	 * @return retourne l'image du filet
	 */
	public Image getImageFilet() {
		return imgFilet;
	}

	//James
	/**
	 * Permet de savoir si le ballon touche en haut du filet.
	 * @param ballon le ballon avec lequel le filet entre en contact
	 * @return si le ballon touche le haut du filet
	 */
	public boolean collision(Ballon ballon) {
		boolean collision = false;

		if (ballon.isCollision(this.getSegment().get(0)) ) {
			collision = true;
		}	
		return collision;

	}



}
