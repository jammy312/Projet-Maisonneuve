package physique;

import java.awt.geom.Line2D;

import aapplication.Musique;
import forme.Aimant;
import forme.Cercle;
/**
 * Cette classe permet de calculer tout ce qui a rapport avec la physique
 *@author Jason
 *@author Estelle
 *@author James
 */
public class MethodePhysique {

	private final static double DELTA_T_ORIGINAL = 0.013;
	private  static double delaT = DELTA_T_ORIGINAL;
	private final static double LOI_DE_COULOMB = 9.00*Math.pow(10, 9);
	private static double e = 97.0/100.0;
	private static Musique musiqueAimant = new Musique();
	//Jason
	/**
	 * Il permet de calculer la collision entre un cercle et un cercle
	 * @param cercleA le premier cercle qui entre en collision
	 * @param cercleB le deuxième cercle qui entre en collision
	 */
	public static void collision(Cercle cercleA, Cercle cercleB) {
		System.out.println("Collision cercle-cercle effectué");

		//1- trouver la normale du cercleB
		Vecteur n = (cercleA.getVecteurPosition().soustrait(cercleB.getVecteurPosition())).multiplie(1.0 / Vecteur.module(cercleA.getVecteurPosition().soustrait(cercleB.getVecteurPosition())));

		try {
			n.normalise();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//2- trouver J
		final double J = n.prodScalaire(cercleA.getVecteurVitesse().soustrait(cercleB.getVecteurVitesse())) * ( -(1.0+e)/( 1.0/cercleB.getMasse() + 1.0/cercleA.getMasse()));

		//Afin que les balles ne restent pas collées
		if (cercleA.isInfluancable()) {
			cercleA.setVecteurPosition(cercleA.getVecteurPosition().additionne(n));
		}
		if (cercleB.isInfluancable()) {
			cercleB.setVecteurPosition(cercleB.getVecteurPosition().soustrait(n));
		}

		//3- modifier la vitesse des cercles
		cercleA.setVecteurVitesse(cercleA.getVecteurVitesse().additionne(n.multiplie(J/cercleA.getMasse())));
		cercleB.setVecteurVitesse(cercleB.getVecteurVitesse().soustrait(n.multiplie(J/cercleB.getMasse())));

		System.out.println("CercleA:"   + "\n" + "Vx:" + cercleA.getVecteurVitesse().getX() + "\n" + "Vy:" + -cercleA.getVecteurVitesse().getY());
		System.out.println("CercleB:"   + "\n" + "Vx:" + cercleB.getVecteurVitesse().getX() + "\n" + "Vy:" + -cercleB.getVecteurVitesse().getY());

		musiqueCollision();

	}

	//Jason
	/**
	 * Il permet de calculer la collision entre un segment immobile et un cercle influençable
	 * @param cercle Le cercle qui a touché le segment
	 * @param segment le segment qui est entré en contact avec le cercle
	 */
	public static void collision(Cercle cercle, Line2D.Double segment) {

		//1- trouver l'angle du segment
		//double orientation = Math.acos(((segment.getX2()-segment.getX1())/(Math.sqrt(Math.pow(segment.getX2()-segment.getX1(),2)+Math.pow(segment.getY2()-segment.getY1(),2)))));
		double orientation = Math.atan((segment.getY2() - segment.getY1())*1.0/ (segment.getX2() - segment.getX1())*1.0);

		// Pour les cas particuliers

		double a = (segment.getX2() - segment.getX1());
		double b = (segment.getY2() - segment.getY1());

		if (a>0) {
			if (b<0) {
				orientation+=Math.PI*2;
			}
		}else {
			orientation+=Math.PI;
		}
		System.out.println("Orientation du segment: " + Math.toDegrees(orientation));

		Vecteur nS = new Vecteur( Math.cos(orientation+Math.PI/2), Math.sin(orientation+Math.PI/2) ); // orientation dw la normal du segment
		Vecteur n;
		//Afin que la balle ne reste pas collée
		try {
			cercle.setVecteurPosition(cercle.getVecteurPosition().additionne(cercle.getVecteurVitesse().normalise().multiplie(-1 - cercle.getVecteurVitesse().module()/100.0)));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if ((cercle.getVecteurPosition().soustrait(new Vecteur(segment.getX1(),segment.getY1()))).prodScalaire(nS) > 0) {
			n = nS;
		} else {
			n = nS.multiplie(-1);
		}
		try {
			n.normalise();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Normal à la surface: " + n);

		try {
			n.normalise();
			Vecteur R = (cercle.getVecteurVitesse().normalise()).additionne(n.multiplie(2 * ((cercle.getVecteurVitesse().multiplie(-1).normalise()).prodScalaire(n))));
			if (R.module() != 1) {
				R.normalise();
			}
			cercle.setVecteurVitesse(R.multiplie(cercle.getVecteurVitesse().module()).multiplie(e));
		} catch (Exception e) {
			System.out.println("erreur entre segment-cercle");
			e.printStackTrace();
		}

		System.out.println("CercleA:"   + "\n" + "Vx:" + cercle.getVecteurVitesse().getX() + "\n" + "Vy:" + -cercle.getVecteurVitesse().getY());






		musiqueCollision();
	}


	//James
	/**
	 * Permet de faire la collision entre un cercle et un aimant
	 * @param cercle le cercle en collision
	 * @param aimant l'aimant avec lequel le cercle est en collision
	 */
	public static void collisionAimant (Cercle cercle, Aimant aimant) {
		System.out.println(" Collision cercle-Aimant effectué");
		System.out.println(" Intensité de l'aimant: " + aimant.getIntensite() );

		//on trouve d'abord la distance entre l'aimant et le cercle
		double distanceX = cercle.getVecteurPosition().getX() - aimant.getVecteurPosition().getX();		
		double distanceY =  cercle.getVecteurPosition().getY() - aimant.getVecteurPosition().getY();	
		Vecteur deplacement = new Vecteur (distanceX,distanceY);
		double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);	

		distance = distance - cercle.getHauteur()/2;

		if (distance <= 50 ) {
			distance = 50;
		}

		//ensuite on calcule la force de l'aimant sur le cercle
		Vecteur unitaire = deplacement.multiplie(Math.pow(distance, -1));
		Vecteur force = unitaire.multiplie((aimant.getIntensite() * Math.pow(10, -4) ) * LOI_DE_COULOMB * (-aimant.getChargeDeAimant() * Math.pow(10, -6)) * Math.pow(distance * distance, -1)  );

		//et on additionne la force de l'aimant + la force actuelle du cercle
		Vecteur forceCercle = cercle.getAcceleration().multiplie(cercle.getMasse());
		Vecteur nouveauForce = force.additionne(forceCercle);
		cercle.setForce(nouveauForce);

		System.out.println("CercleA:"   + "\n" + "Vx:" + cercle.getVecteurVitesse().getX() + "\n" + "Vy:" +  -cercle.getVecteurVitesse().getY());

		if (musiqueAimant.getClip() == null) {
			musiqueAimant.jouerMusique("aimant.wav");
		}else {
			if (!musiqueAimant.getClip().isRunning()) {
				musiqueAimant.jouerMusique("aimant.wav");
			}
		}
	} 


	//Estelle
	/**
	 * permet de faire le déplacement MUA des cercles qui se déplacent
	 * @param cercle n'importe quel cercle en mouvement
	 * @param gravite la gravité du système
	 */
	public static void Euler(Cercle cercle, double gravite) {
		Vecteur vitesse = new Vecteur();
		double vitesseX = cercle.getVecteurVitesse().getX();
		double accelerationX = cercle.getAcceleration().getX();
		double vitesseY = cercle.getVecteurVitesse().getY();
		double accelerationY = cercle.getAcceleration().getY() + gravite;
		vitesse.setX(vitesseX+(accelerationX*delaT));
		vitesse.setY(vitesseY+(accelerationY*delaT));
		double positionX = cercle.getVecteurPosition().getX()+(vitesseX*delaT);
		double positionY = cercle.getVecteurPosition().getY()+(vitesseY*delaT);
		cercle.setVecteurPosition(new Vecteur(positionX, positionY));
		cercle.setVecteurVitesse(vitesse);
	}

	//Jason
	/**
	 * Il permet de choisir entre 6 choix de collision d'inelasticité de la plus faible à la plus forte :
	 * <br> 1- les collisions seront élastiques
	 * <br> 2- les collisions ressembleront à une collision entre 2 aciers
	 * <br> 3- les collisions ressembleront à une collision entre 2 verres
	 * <br> 4- les collisions ressembleront à une collision entre 2 ivoires
	 * <br> 5- les collisions ressembleront à une collision entre 2 lièges
	 * <br> 6- les collisions ressembleront à une collision entre 2 bois
	 * <br> Si le choix n'est pas un de ces choix, alors il fera automatiquement une collision élastique
	 * @param choix le choix de la collision
	 */
	public static void changerInelasticite(int choix) {
		switch (choix) {
		case 2:
			e = 97/100.0;
			break;
		case 3:
			e = 15.0/16.0;
			break;
		case 4:
			e = 8.0/9.0;
			break;
		case 5:
			e =5.0/9.0;
			break;
		case 6:
			e =1.0/2.0;
			break;
		default:
			e =1.0;
			break;
		}
	}

	//Jason
	/**
	 * Il permet de modifier le deltaT du jeu
	 * @param nouvdeltaT le nouveau deltaT
	 */
	public static void setDeltaT(double nouvdeltaT) {
		delaT = nouvdeltaT;
	}

	//Jason
	/**
	 * Il permet de récupérer le deltaT original
	 * @return le deltaT original
	 */
	public static double getDeltaTOriginal() {
		return DELTA_T_ORIGINAL;
	}

	//Estelle
	/**
	 * Permet de produire un son quand un cercle est en collision avec un segment ou un autre cercle
	 */
	public static void musiqueCollision() {
		Musique musiqueObjet = new Musique();
		musiqueObjet.jouerMusique("collision.wav");
	}




}
