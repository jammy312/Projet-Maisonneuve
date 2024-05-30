package forme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import physique.MethodePhysique;
import physique.Vecteur;
/**
 * Cette classe représente le cercle dans notre jeu
 *@author Jason
 *@author James
 */
public class Cercle extends Forme  {
	private final double MASSE_INITIALE = 50;
	private double masse = MASSE_INITIALE;
	private boolean influancable = false;
	private Vecteur vitesse = new Vecteur();
	private Vecteur acceleration = new Vecteur();
	private final double MASSE_ENORME = Double.POSITIVE_INFINITY;
	private boolean fleche;
	private final double TAILLE_PETITE_FLECHES = 6.4/10.0;
	private final double VITESSE_COULEUR_BALLE = 100;
	private double ancienOrientation = 0;
	private final int MULTIPLICATEUR_FLECHE = 3;

	//Jason
	/**
	 * Constructeur de la classe cercle
	 * @param x sa position en X
	 * @param y sa position en Y
	 */
	public Cercle(double x, double y) {
		super(x, y);
		fleche = true;
	}

	//Jason
	/**
	 * Constructeur de la classe cercle
	 */
	public Cercle() {
		super();
	}

	//Jason
	/**
	 * Constructeur qui permet de copier les valeurs d'un autre cercle
	 * @param cercle le cercle dont on copie les valeurs
	 */
	public Cercle(Cercle cercle) {
		super(cercle.getX(),cercle.getY());
		this.setRotation(cercle.getRotation());
		this.setVecteurVitesse(cercle.getVecteurVitesse());
		this.setAcceleration(cercle.getAcceleration());
		this.setLargeurHauteur(cercle.getLargeur());
		this.setMasse(cercle.getMasse());
		this.Influancable(cercle.isInfluancable());
		this.voirVitesse(cercle.isVoirVitesse());
		this.setVecteurPosition(cercle.getVecteurPosition());

	}

	//Jason
	/**
	 * Pour dessiner les cercles et ses données
	 * @param g2d pour dessiner la forme
	 * @param mat pour la modifer en unité réelle
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		this.dessinerForme(g2d, mat, new Area(this.getCercle()));

		// Pour dessiner la fleche de la vitesse 
		if (influancable && fleche) {
			AffineTransform g2dOriginal = g2d.getTransform();	
			Color couleurOriginale = g2d.getColor();
			AffineTransform matLocal = new AffineTransform(mat);
			g2d.setStroke(new BasicStroke(MULTIPLICATEUR_FLECHE));

			// Pour modifier la couleur selon la vitesse de la balle
			if (this.getVecteurVitesse().module() >=VITESSE_COULEUR_BALLE) {
				g2d.setColor(new Color(255,0,0));
			}else {
				g2d.setColor(new Color(255,255 -(int)(this.getVecteurVitesse().module() * 255 / VITESSE_COULEUR_BALLE),0));
			}

			// Pour dessiner la flèche
			Line2D.Double segmentVitesse = new Line2D.Double(this.getVecteurPosition().getX(),this.getVecteurPosition().getY(),vitesse.getX()/1.5 + this.getVecteurPosition().getX(), vitesse.getY()/1.5 + this.getVecteurPosition().getY()); 
			g2d.draw(matLocal.createTransformedShape(segmentVitesse));

			//Pour dessiner les deux petites flèches

			segmentVitesse = new Line2D.Double((vitesse.getX()/1.5 + this.getVecteurPosition().getX()), (vitesse.getY()/1.5 + this.getVecteurPosition().getY()), (vitesse.getX()/1.5)*TAILLE_PETITE_FLECHES + this.getVecteurPosition().getX(), (vitesse.getY()/1.5)*TAILLE_PETITE_FLECHES + this.getVecteurPosition().getY()); 
			int rotationFleche  = 26;
			matLocal.rotate(rotationFleche, (vitesse.getX()/1.5 + this.getVecteurPosition().getX()), (vitesse.getY()/1.5 + this.getVecteurPosition().getY()));
			g2d.draw(matLocal.createTransformedShape(segmentVitesse));

			matLocal.rotate(-(rotationFleche*2), (vitesse.getX()/1.5 + this.getVecteurPosition().getX()), (vitesse.getY()/1.5 + this.getVecteurPosition().getY()));
			g2d.draw(matLocal.createTransformedShape(segmentVitesse));



			g2d.setTransform(g2dOriginal);
			g2d.setColor(couleurOriginale);
		}
	}

	//Jason
	/**
	 * Il permet de vérifier s'il y a eu une collision entre un segment et un cercle. S'il y a une colliison, il fera le calcul des deux cercles.
	 * @param cercle le cercle qui pourrait rentrer en collision
	 * @return  s'il y a une collision
	 */
	public boolean collision(Cercle cercle) {
		Area cercleInitiale = new Area(this.getCercle());
		Area cercleCollision = new Area(cercle.getCercle());
		cercleInitiale.intersect(cercleCollision);

		if (!cercleInitiale.isEmpty()  ) {
			MethodePhysique.collision(cercle,this);
			return true;
		}else {
			return false;
		}
	}

	//Jason et James
	/**
	 * Il permet de voir s'il y a une collision entre ce cercle et les segments de la forme. S'il y a une collision, il fera le calcul de ces collisions.
	 * @param forme la forme qui possède les segments 
	 * @return s'il y a eu une collision
	 */
	public boolean collision(Forme forme) {
		if (this.isInfluancable() && this.isCollision(forme)) {
			boolean collision = false;
			if (!isAimant(forme) ) {
				//jason
				//Pour vérifier s'il y a une collision entre le cercle et un coin d'un segment
				for (int MiniCercle = 0; MiniCercle < forme.getMiniCercle().size(); MiniCercle++) {
					if (!collision) {
						Cercle cercleCollision = forme.getMiniCercle().get(MiniCercle);
						if (this.collision(cercleCollision)) {
							collision = true;
						}
					}
				}

				//Pour vérifier s'il y a une collision entre le cercle et un coté du segment
				for (int segment = 0; segment < forme.getSegment().size(); segment++) {
					if (!collision) {
						if (this.isCollision(forme.getSegment().get(segment))) {
							System.out.println(" Collision cercle-" + forme.getNomClasse() + " effectué");
							MethodePhysique.collision(this,forme.getSegment().get(segment));
							collision = true;

						}
					}
				}




				if (collision) {

					return true;
				}else {
					return false;
				}

			}else {
				//James
				return  collisionAimant((Aimant)forme);
			}
		}else {
			return false;
		}
	}


	//Jason
	/**
	 * Permet de savoir si ce segment à touché le cercle
	 * @param segment le segment à vérifier
	 * @return s'ils se touchent
	 */
	public boolean isCollision(Line2D.Double segment) {
		if (segment.ptSegDist(this.getVecteurPosition().getX(), this.getVecteurPosition().getY()) <= this.getHauteur()/2) {
			return true;
		}else {
			return false;
		}
	}

	//Jason
	/**
	 * Permet de savoir si cette forme a touché le cercle
	 * @param forme la forme à vérifier
	 * @return si la forme a touché le cercle
	 */
	public boolean isCollision(Forme forme) {
		Area cercle = this.getForme();
		Area formeArea = forme.getForme();

		for (int i = 0; i < forme.getMiniCercle().size(); i++) {
			formeArea.add(new Area(forme.getMiniCercle().get(i).getForme()));
		}

		cercle.intersect(formeArea);
		if (!cercle.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}

	//Jason
	/**
	 * Il permet de modifier la vitesse du Cercle
	 * @param vitesse La vitesse du cercle
	 */
	public void setVecteurVitesse(Vecteur vitesse) {
		this.vitesse = new Vecteur(vitesse.getX(),vitesse.getY());
	}

	//Jason
	/**
	 * Il permet de récupérer le vecteur de la vitesse du cercle. Si celui-ci n'est pas influençable, alors il retournera une vitesse de 0m/s
	 * @return La vitesse du cercle en vecteur
	 */
	public Vecteur getVecteurVitesse() {
		if (influancable) {
			return new Vecteur(this.vitesse.getX(),this.vitesse.getY());
		}else {
			return new Vecteur();
		}
	}

	//Jason
	/**
	 * Il permet de récupérer la masse du cercle en kg. Si celui-ci n'est pas influençable, alors il retournera une masse énorme
	 * @return La masse du cercle en kg
	 */
	public double getMasse(){
		if (influancable) {
			return this.masse;
		}else {
			return MASSE_ENORME;
		}
	}

	//Jason
	/**
	 * Il permet de récupérer l'accélération de ce cercle en vecteur. Si celui-ci n'est pas influençable, alors il retournera une acceleration de 0m/s<sup>2</sup>
	 * @return L'accélération du cercle en vecteur
	 */
	public Vecteur getAcceleration() {
		if (influancable) {
			return new Vecteur(this.acceleration.getX(),this.acceleration.getY());
		}else {
			return new Vecteur();
		}	}

	//Jason
	/**
	 * Il permet de modifier la valeur de l'accélération du cercle en Vecteur 
	 * @param acceleration La nouvelle valeur de l'accélération
	 */
	public void setAcceleration(Vecteur acceleration) {
		this.acceleration = new Vecteur(acceleration.getX(),acceleration.getY());
	}

	//Jason
	/**
	 * Il permet de savoir si ce cercle est influençable par la gravité, la collision, etc.
	 * @return Si le cercle est influençable
	 */
	public boolean isInfluancable() {
		return this.influancable;
	}

	//Jason
	/**
	 * Il permet de modifier si le cercle est influençable ou non
	 * @param influancable si le cercle sera influençable ou non
	 */
	public void Influancable(boolean influancable) {
		this.influancable = influancable;
	}

	//Jason
	/**
	 * Il permet de modifier la masse du cerle en Kg
	 * @param masse la nouvelle masse du cercle en Kg
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}

	//Jason
	/**
	 * Il permet de récupérer la valeur en forme Ellipse2D.Double du cercle
	 * @return la valeur en forme Ellipse2D.Double du cercle
	 */
	public Ellipse2D.Double getCercle() {

		return new Ellipse2D.Double(this.getX(),this.getY(),this.getLargeur(),this.getHauteur());
	}

	//James
	/**
	 * Il permet de faire la collision entre un cercle et un aimant
	 * @param aimant aimant avec lequel le cercle sera en collision
	 * @return un booléen si la collision à été faite ou non
	 * 
	 */
	private boolean collisionAimant(Aimant aimant) {
		Area cercleInitiale = new Area(this.getCercle());
		Area cercleCollision = new Area(aimant.getAimant());
		cercleInitiale.intersect(cercleCollision);

		if ( !cercleInitiale.isEmpty() && this.isInfluancable() && aimant.getIntensite() != 0) {
			MethodePhysique.collisionAimant(this, aimant);
			return true;
		}else {
			return false;
		}
	}
	//James
	/**
	 * Méthode qui permet de savoir si le cercle est en collision avec l'aimant.
	 * @param forme objet avec lequel le cercle est en collision
	 * @return la valeur vraie ou fausse pour savoir si le cercle est en collision avec l'aimant.
	 */
	private boolean isAimant(Forme forme) {
		return forme.getNomClasse().equalsIgnoreCase("Aimant");
	}


	//James
	/**
	 * Il permet de modifier la force de la balle
	 * @param force la force de la balle
	 */
	public void setForce(Vecteur force){
		this.setAcceleration(force.multiplie(1/masse));
	}


	//Jason
	@Override
	/**
	 * Permet de récupérer la liste des minis cercles d'une forme.
	 */
	public ArrayList<Cercle> getMiniCercle() {
		return new ArrayList<Cercle>();
	}

	//Jason
	@Override
	/**
	 * Permet de récupérer les segments d'une forme.
	 */
	public ArrayList<Line2D.Double> getSegment() {
		return new ArrayList<Line2D.Double>();
	}

	//Jason
	/**
	 * Permet de récupérer le degré du ballon.
	 * @return Le degré du ballon.
	 */
	public double getDegre() {
		if (this.getVecteurVitesse().module() == 0) {
			return ancienOrientation;
		} else {


			double orientation = Math.toDegrees(Math.atan((-this.getVecteurVitesse().getY())/this.getVecteurVitesse().getX()));

			// Pour les cas particuliers

			double a = this.getVecteurVitesse().getX();
			double b = -this.getVecteurVitesse().getY();

			//1-
			if (a>0) {
				if (b>0) {
					ancienOrientation = orientation;
					return orientation;
				}else {
					ancienOrientation = orientation + 360;
					return orientation+360;
				}
			}else {
				ancienOrientation = orientation + 180;
				return orientation+180;
			}

		}
	}

	//Jason
	@Override
	/**
	 * Permet de récupérer la forme du cercle.
	 */
	public Area getForme() {
		return new Area(this.getCercle());

	}

	//James
	/**
	 * Permet de savoir si la collision d'un ballon est dans le filet
	 * @param forme le filet en question qui est en collision
	 * @return si la forme est dans le filet
	 */
	public boolean collisionFilet (Filet forme) {
		boolean contain = false;

		if(this.isInfluancable()) {

			// Il vérifie si c'est un filet
			if (forme.getNomClasse().equalsIgnoreCase("Filet")) {
				Filet filet = (Filet) forme;

				// Il vérifie si c'est un ballon et non un cercle influençable
				if(this.getNomClasse().equalsIgnoreCase("Ballon")) {
					Ballon ballon = (Ballon) this;
					if(filet.collision(ballon)) {
						contain = true;
					}
				}
			}
			this.collision(forme);
		}
		return contain;
	}


	//Jason
	/**
	 * Il permet de savoir si nous voulons voir la flèche de vitesse ou non
	 * @param fleche pour savoir s'il faut voir la flèche de la vitesse ou non
	 */
	public void voirVitesse(boolean fleche) {
		this.fleche = fleche;
	}

	//Jason
	/**
	 * Il permet de savoir si on peut voir la vitesse de la balle
	 * @return si on peut voir la vitesse de la balle
	 */
	public boolean isVoirVitesse() {
		return fleche;
	}

}




