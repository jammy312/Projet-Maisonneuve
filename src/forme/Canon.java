package forme;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Cette classe sert à dessiner le canon.
 * @author Estelle
 * @author James
 * @author Jason
 *
 */
public class Canon extends Forme  {
	private Image imgCanon = null;
	private final double DIAMETRE = 75;
	private final float OPACITE = 0.7f;

	//Estelle
	/**
	 * Constructeur de la classe canon
	 * @param posX la position en x initiale  du canon
	 * @param posY la position en y initiale du canon
	 */
	public Canon(double posX,double posY) {
		super(posX,posY);
		//image
		URL sourceCanon = getClass().getClassLoader().getResource("canon.png");

		if (sourceCanon == null){
			System.out.println("Impossible");
		}
		try {
			imgCanon = ImageIO.read(sourceCanon);
		} 
		catch (IOException e) {
			System.out.println("Erreur");
		}

	}

	//Estelle et James
	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {;
	//Estelle
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	AffineTransform matLocale = new AffineTransform(mat);
	Composite compositeOriginal = g2d.getComposite();
	this.setHauteur(DIAMETRE);
	this.setLargeur(DIAMETRE);
	matLocale.rotate(Math.toRadians(this.getRotation()),this.getVecteurPosition().getX(),this.getVecteurPosition().getY());

	//James
	double fact = DIAMETRE / (double)imgCanon.getWidth(null);
	matLocale.translate(this.getX() , this.getY());
	matLocale.scale(fact, fact);
	if (this.estDeplace()) {
		g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, OPACITE  ) ); // g2d dessinera avec 40% de transparence
	}
	g2d.drawImage(imgCanon, matLocale, null);
	matLocale.scale(-fact, -fact);

	if (this.estDeplace()) {
		g2d.setComposite(compositeOriginal); // si le canon est déplacé
	}

	}

	//James
	/**
	 * Permet de savoir si le canon touche une autre forme
	 * @param forme la forme qui touche le canon
	 * @return si la forme touche le canon
	 */
	public boolean contain (Forme forme) {
		boolean contain = false;
		if (!forme.getNomClasse().equalsIgnoreCase("Canon")) {
			Area rectangleForme = forme.getForme();

			Area rectangleCanon1 = this.getForme();
			Area rectangleCanon2 = this.getForme();

			rectangleCanon1.subtract(rectangleForme);

			if(!rectangleCanon1.equals(rectangleCanon2) ) {
				contain = true;
			}
		}
		return contain;	

	}

	//James
	/**
	 * Permet de retourner l'image du canon
	 * @return retourne l'image du canon
	 */
	public Image getImageCanon() {
		return imgCanon;
	}


	//Jason
	@Override
	/**
	 * Il permet de prendre la forme du canon
	 */
	public Area getForme() {
		return new Area( new Rectangle2D.Double(this.getX(),this.getY(),this.getLargeur(),this.getHauteur())  );

	}

}
