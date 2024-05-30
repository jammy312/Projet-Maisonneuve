package essaiFormeCollision;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import forme.Aimant;
import forme.Ballon;
import forme.Cercle;
import forme.Filet;
import forme.Forme;
import forme.Rectangle;
import forme.Segment;
import forme.Triangle;
import physique.MethodePhysique;
import physique.ModeleAffichage;
import physique.Vecteur;


/**
 * C'est la classe qui nous permet de dessiner nos formes et de tester nos collisions
 * @author Jason
 * @author James
 */
public class ZoneCollisionEssai extends JPanel implements Runnable {
	public ZoneCollisionEssai() {
	}
	private boolean enCourAnimation = false, premiereFois=true;
	private Cercle cercle, cercle2, cercle3;
	private Ballon ballon;
	private Cercle collision, collision2;
	private final double LARGEUR_MONDE = 500;
	private final double VITESSE_X_INITIALE= 15;
	private final double VITESSE_Y_INITIALE = 5;
	ModeleAffichage modele;
	private final long ATTENTE = 2;
	ArrayList<Cercle> cercleInfluancable = new ArrayList<Cercle>();
	ArrayList<Cercle> cercleNonInfluancable = new ArrayList<Cercle>();
	ArrayList<Forme> formeNonInfluancable = new ArrayList<Forme>();
	private boolean prochain = false;
	private final double ROTATION = 243;
	private final double GRAVITE = 5;
	private boolean fleche;


	private static final long serialVersionUID = 1L;

	//Jason
	/**
	 * Il permet de démarrer l'animation
	 */
	public void start() {
		if (!enCourAnimation && !prochain) {	
			Thread processusAnimation = new Thread(this);	
			processusAnimation.start();
			enCourAnimation = true;
		} else {
			enCourAnimation = false;
			prochain = true;
			prochainImage();
		}
	}
	//Jason
	/**
	 * Il permet d'animer l'animation une image à la fois
	 */
	private void prochainImage() {
		for (int i = 0; i < cercleInfluancable.size(); i++) {
			cercleInfluancable.get(i).estDeplace(false);
			for (int j = i+1; j < cercleInfluancable.size(); j++) {
				cercleInfluancable.get(i).collision(cercleInfluancable.get(j));

			}


			for (int j = 0; j < cercleNonInfluancable.size(); j++) {
				cercleInfluancable.get(i).collision(cercleNonInfluancable.get(j));
			}

			for (int j = 0; j < formeNonInfluancable.size(); j++) {
				cercleInfluancable.get(i).collision( formeNonInfluancable.get(j));
			}


		}

		for (int i = 0; i < cercleInfluancable.size(); i++) {
			Cercle cerclePris = cercleInfluancable.get(i);
			MethodePhysique.Euler(cerclePris, GRAVITE);
		}

		this.repaint();
	}

	@Override

	//Jason
	/**
	 * Thread qui permet de faire l'animation
	 */
	public void run() {
		while (enCourAnimation) {
			MethodePhysique.changerInelasticite(2);
			for (int i = 0; i < cercleInfluancable.size(); i++) {
				cercleInfluancable.get(i).estDeplace(true);
				cercleInfluancable.get(i).setAcceleration(new Vecteur());
				for (int j = i+1; j < cercleInfluancable.size(); j++) {
					if (j != i) {
						cercleInfluancable.get(i).collision(cercleInfluancable.get(j));
					}
				}


				for (int j = 0; j < cercleNonInfluancable.size(); j++) {
					cercleInfluancable.get(i).collision(cercleNonInfluancable.get(j));
				}

				for (int j = 0; j < formeNonInfluancable.size(); j++) {
					cercleInfluancable.get(i).collision( formeNonInfluancable.get(j));
				}
			}

			for (int i = 0; i < cercleInfluancable.size(); i++) {
				Cercle cerclePris = cercleInfluancable.get(i);
				MethodePhysique.Euler(cerclePris,GRAVITE);
			}

			this.repaint();

			try {
				Thread.sleep(ATTENTE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//James et Jason
	/**
	 * Permet de dessiner les formes pour faire l'essai d'une collision
	 */
	public void paintComponent(Graphics g) {
		//jason
		super.paintComponent(g);
		ModeleAffichage modele = new ModeleAffichage(this.getWidth(), this.getHeight(), LARGEUR_MONDE);
		Graphics2D g2d = (Graphics2D) g;
		if (premiereFois) {
			premiereFois =false;

			//Les cercles qui seront influencés
			cercle = new Cercle(5,5);
			cercle.setLargeur(LARGEUR_MONDE/10);
			cercle.setHauteur(LARGEUR_MONDE/10);	
			cercle.setVecteurVitesse(new Vecteur(VITESSE_X_INITIALE, VITESSE_Y_INITIALE));
			cercle.Influancable(true);

			cercle2 = new Cercle(modele.getLargUnitesReelles()/3,modele.getHautUnitesReelles()/5);
			cercle2.setLargeur(LARGEUR_MONDE/10);
			cercle2.setHauteur(LARGEUR_MONDE/10);	
			//cercle2.setVecteurVitesse(new Vecteur(VITESSE_X_INITIALE, 0));
			cercle2.Influancable(true);
			cercle2.setMasse(cercle2.getMasse()*3);

			cercle3 = new Cercle(3,modele.getHautUnitesReelles() - cercle2.getHauteur()-3);
			cercle3.setLargeur(LARGEUR_MONDE/10);
			cercle3.setHauteur(LARGEUR_MONDE/10);	
			cercle3.setVecteurVitesse(new Vecteur(VITESSE_X_INITIALE, -VITESSE_Y_INITIALE*2));
			//cercle3.Influancable(true);

			ballon  = new Ballon(3,modele.getHautUnitesReelles() - cercle2.getHauteur()-3);	
			ballon.setVecteurVitesse(new Vecteur(VITESSE_X_INITIALE, -VITESSE_Y_INITIALE*2));
			ballon.Influancable(true);

			cercleInfluancable.add(cercle);
			cercleInfluancable.add(cercle2);
			cercleInfluancable.add(cercle3);
			//cercleInfluancable.add(ballon);



			// Les cercles qui ne seront pas influencés
			collision = new Cercle(modele.getLargUnitesReelles()*2/3, modele.getHautUnitesReelles()/2);
			collision.setLargeur(LARGEUR_MONDE/10);
			collision.setHauteur(LARGEUR_MONDE/10);
			collision.setMasse(LARGEUR_MONDE*5);

			collision2 = new Cercle(modele.getLargUnitesReelles()/2, modele.getHautUnitesReelles()*3/4);
			collision2.setLargeur(LARGEUR_MONDE/10);
			collision2.setHauteur(LARGEUR_MONDE/10);
			cercleNonInfluancable.add(collision);
			//cercleNonInfluancable.add(collision2);

			// Pour les formes
			Rectangle rectangle = new Rectangle(0, -Forme.getTaillleInitiale());
			rectangle.setLargeur(modele.getLargUnitesReelles());

			Rectangle rectangle2 = new Rectangle(0, modele.getHautUnitesReelles());
			rectangle2.setLargeur(modele.getLargUnitesReelles());

			Rectangle rectangle3 = new Rectangle(- Forme.getTaillleInitiale(), 0);
			rectangle3.setHauteur(modele.getHautUnitesReelles());

			Rectangle rectangle4 = new Rectangle(modele.getLargUnitesReelles(), 0);
			rectangle4.setHauteur(modele.getHautUnitesReelles());

			Segment segment = new Segment(modele.getLargUnitesReelles()/2, modele.getHautUnitesReelles()/2);
			segment.setLargeurHauteur(Forme.getTaillleInitiale()*1.5);

			Segment segment2 = new Segment(modele.getLargUnitesReelles()/2, modele.getHautUnitesReelles()/2);
			segment2.setLargeurHauteur(Forme.getTaillleInitiale()*1.5);
			segment2.setRotation(90);
			Triangle triangle = new Triangle(modele.getLargUnitesReelles()/3, modele.getHautUnitesReelles()/2);
			triangle.setLargeurHauteur(Forme.getTaillleInitiale()*1.5);
			triangle.setRotation(ROTATION);
			
			//James
			Aimant aimant = new Aimant (modele.getLargUnitesReelles() /25, modele.getHautUnitesReelles()*2.0/3);
			Aimant aimant2 = new Aimant (modele.getLargUnitesReelles()/25, modele.getHautUnitesReelles()/3);
			aimant2.setLargeurHauteur(69);
			aimant2.setIntensite(1000);
			aimant.setIntensite(1000);
			Filet filet = new Filet (modele.getLargUnitesReelles()*2/3, modele.getHautUnitesReelles()/2);

			//Jason
			formeNonInfluancable.add(segment);
			formeNonInfluancable.add(segment2);
			formeNonInfluancable.add(rectangle);
			formeNonInfluancable.add(rectangle2);
			formeNonInfluancable.add(rectangle3);
			formeNonInfluancable.add(rectangle4);
			formeNonInfluancable.add(triangle);
			//James
			formeNonInfluancable.add(filet);
			formeNonInfluancable.add(aimant2);
			formeNonInfluancable.add(aimant);			
		}
		//Jason
		for (int i = 0; i < cercleNonInfluancable.size(); i++) {
			cercleNonInfluancable.get(i).dessiner(g2d, modele.getMatMC());

		}

		for (int i = 0; i< cercleInfluancable.size(); i++) {
			cercleInfluancable.get(i).voirVitesse(fleche);
			cercleInfluancable.get(i).dessiner(g2d, modele.getMatMC());
		}
		//Maintenant il accepte toutes les formes
		for (int i = 0; i < formeNonInfluancable.size(); i++) {
			formeNonInfluancable.get(i).dessiner(g2d, modele.getMatMC());
		}
	}

	//Jason
	/**
	 * Il permet de voir la flèche de la vitesse
	 * @param fleche si nous voulons voir la flèche de la vitesse
	 */
	public void voirVitesse(boolean fleche) {
		this.fleche = fleche;
		this.repaint();
	}

}
