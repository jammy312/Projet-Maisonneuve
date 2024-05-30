package jeux;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aapplication.Musique;
import evenement.BallonListener;
import fichiers.GestionnaireDeFichiers;
import forme.Aimant;
import forme.Ballon;
import forme.Canon;
import forme.Cercle;
import forme.Filet;
import forme.Forme;
import physique.MethodePhysique;
import physique.ModeleAffichage;
import physique.Vecteur;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

/**
 *Il permet de faire l'animation du jeu
 * @author Jason
 *
 */
public class ZoneAnimation extends JPanel implements Runnable {


	private static final long serialVersionUID = 1L;
	private boolean enCourAnimation = false;
	private boolean jeuCommence = false;
	private boolean jeuTermine = false;
	private boolean premiereFois = true;
	private final double LARGEUR_MONDE = 1000;
	private Ballon ballonPrincipale = new Ballon(0,0);
	private double gravite = 0;
	private ArrayList<BallonListener> listeEcouteurs = new ArrayList<BallonListener>();
	private ArrayList<Forme> formeNonInfluancable = new ArrayList<Forme>();
	private ArrayList<Aimant> aimant = new ArrayList<Aimant>();
	private ArrayList<Cercle> cercle = new ArrayList<Cercle>();
	private Filet filet = new Filet(0,0);
	private Canon canon = new Canon(0,0);
	private Canon canonTemporaire = new Canon(0,0);
	private final long SLEEP = 2;
	private int vie = 1;
	private String nom = "Pokemon!!";
	private double hauteurUniteReelle;
	private boolean fleche = false;
	private int vieInitial =5;
	private final double VITESSE_INITIAL_MAXIMUM = 150;
	private final double VITESSE_INITIAL_FAUSSE = 300;
	private Image fondEcran = null;
	private final String FOND_ECRAN_NOM = "fondEcronJeux.jpg";
	private final int PROCHAIN_IMAGE = 10;
	private final int DECIMALE = 100;
	private boolean tricher = false;
	private double intensite;
	private Musique musique = new Musique();
	private final String GAGNER = "gagner.wav";
	private final String PERDU = "perdu.wav";
	private final String LANCER = "canon.wav";
	private final Vecteur POSITION_CANON = new Vecteur (14.380314437555358,8.40954384410983);


	/**
	 * Le constructeur de cette classe
	 */
	public ZoneAnimation() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				changerVitesse(e.getX(), e.getY());

			}
			@Override
			public void mouseMoved(MouseEvent e) {
				changerVitesse(e.getX(), e.getY());

			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				debutJeu();
			}


			@Override
			public void mouseEntered(MouseEvent e) {
				changerVitesse(e.getX(), e.getY());

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				debutJeu();	
			}
		});
		this.setBackground(Color.white);
	}

	/**
	 * Il permet de changer la vitesse de la balle au début du jeu
	 * @param x la position en x de la souris
	 * @param y la position en y de la souris
	 */
	private void changerVitesse(int x, int y) {
		// Pour s'assurer que le jeu n'est pas commencé
		if (!jeuCommence && !jeuTermine) {
			double sourisX = x * LARGEUR_MONDE / getWidth();
			double sourisY = y * hauteurUniteReelle / getHeight();
			Vecteur vitesse = new Vecteur((sourisX - canon.getVecteurPosition().getX()), (sourisY - canon.getVecteurPosition().getY()));
			if (vitesse.module() >VITESSE_INITIAL_FAUSSE) {
				try {
					vitesse = vitesse.normalise().multiplie(VITESSE_INITIAL_FAUSSE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ballonPrincipale.setVecteurVitesse(vitesse);
			ballonPrincipale.voirVitesse(true);

			// pour les événements personalisé
			this.leverEvenAccelerationXY();
			this.leverEvenDegre();
			this.leverEvenPositionXY();
			this.leverEvenVitesseXY();
			this.leverEvenJeuCommence();
			this.leverEvenGetVie();

			repaint();
		}
	}

	/**
	 * Il permet de démarrer le jeu selon la position de la souris
	 */
	private void debutJeu() {
		if (!jeuCommence && !jeuTermine) {
			if (ballonPrincipale.getVecteurVitesse().module() > VITESSE_INITIAL_MAXIMUM) {
				try {
					ballonPrincipale.setVecteurVitesse(ballonPrincipale.getVecteurVitesse().normalise().multiplie(VITESSE_INITIAL_MAXIMUM));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ballonPrincipale.voirVitesse(fleche);
			jeuCommence = true;
			start();
			musique.jouerMusique(LANCER);
		}
	}

	/**
	 * Il permet de récupérer le fond d'écran de l'animation
	 */
	private void recupereImage() {
		if (fondEcran == null) {		
			java.net.URL urlImage = getClass().getClassLoader().getResource(FOND_ECRAN_NOM);
			if (urlImage == null) {
				JOptionPane.showMessageDialog(null, "Fichier " + FOND_ECRAN_NOM + " introuvable");
			}
			try {
				fondEcran = ImageIO.read(urlImage);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur pendant la lecture du fichier d'image");
			}

			// redimensionner l'image de la même grandeur que le le composant
			Image imgRedim = fondEcran.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

			fondEcran = imgRedim;
		}
	}

	/**
	 * Il permet de dessiner les formes
	 * @param g le graphic du jeu
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		ModeleAffichage modele = new ModeleAffichage(this.getWidth(), this.getHeight(), LARGEUR_MONDE);
		hauteurUniteReelle = modele.getHautUnitesReelles();

		if (premiereFois) {
			this.preparerJeux();
		}

		//Pour dessine le fond d'écron
		recupereImage();
		g2d.drawImage(fondEcran,modele.getMatMC(),null);
		// pour dessiner les aimants
		for (int i = 0; i < aimant.size(); i++) {
			aimant.get(i).dessiner(g2d, modele.getMatMC());
		}


		// Pour dessiner les cercles
		for (int i = 0; i < cercle.size(); i++) {
			cercle.get(i).voirVitesse(fleche);
			cercle.get(i).dessiner(g2d, modele.getMatMC());
		}


		// Pour dessiner les formes non inluancable
		for (int i = 0; i < formeNonInfluancable.size(); i++) {
			formeNonInfluancable.get(i).dessiner(g2d, modele.getMatMC());
		}



		// Pour dessiner le filet et le canon
		filet.dessiner(g2d, modele.getMatMC());

		if (!jeuCommence) {
			canonTemporaire.setRotation(-(ballonPrincipale.getDegre()-30));
		}

		//pour dessiner la balle principale
		if (jeuCommence|| jeuTermine) {
			ballonPrincipale.estVisible(true);
			ballonPrincipale.voirVitesse(fleche);
			
			canonTemporaire.dessiner(g2d, modele.getMatMC());
			ballonPrincipale.dessiner(g2d, modele.getMatMC());
		}else {
			ballonPrincipale.estVisible(false);
			
			ballonPrincipale.dessiner(g2d, modele.getMatMC());
			canonTemporaire.dessiner(g2d, modele.getMatMC());
		}


		g2d.setColor(Color.white);
		g2d.drawString((int)modele.getHautUnitesReelles() + "",10, 12);
		g2d.drawString((int)modele.getLargUnitesReelles() + "",this.getWidth() - 35, this.getHeight() - 10);

		//Pour s'assurer que c'est plus la première fois
		premiereFois = false;
	}

	/**
	 * Il permet de démarrer l'animation
	 */
	public void start() {
		if (!enCourAnimation && !jeuTermine) {
			Thread start = new Thread(this);
			start.start();
			enCourAnimation = true;
			jeuCommence = true;
		}

	}

	/**
	 * Il permet à l'application de procéder image après imgae
	 * @return si la prochaine image a été éxécutée
	 */
	public boolean prochainImage() {
		if (enCourAnimation) {
			enCourAnimation = false;
			for (int temps = 0; temps < this.PROCHAIN_IMAGE; temps++) {
				deplacement();
				gagner();
				horsTerrain();

				// pour les événements personalisé
				this.leverEvenAccelerationXY();
				this.leverEvenDegre();
				this.leverEvenPositionXY();
				this.leverEvenVitesseXY();
				this.leverEvenJeuCommence();
				this.leverEvenGetVie();

				this.repaint();

			}

			return true;
		}else{
			if (jeuCommence) {
				for (int temps = 0; temps < this.PROCHAIN_IMAGE; temps++) {
					deplacement();
					gagner();
					horsTerrain();

					// pour les événements personalisé
					this.leverEvenAccelerationXY();
					this.leverEvenDegre();
					this.leverEvenPositionXY();
					this.leverEvenVitesseXY();
					this.leverEvenJeuCommence();
					this.leverEvenGetVie();

					this.repaint();

				}

			}
			return false;
		}

	}

	/**
	 * Il permet de savoir si le jeu contient un aimant ou pas
	 * @return si le jeu contient un aimant ou pas
	 */
	public boolean aAimant() {
		return GestionnaireDeFichiers.getListeAimant().size() != 0;
	}

	/**
	 * Il permet de recommencer le jeu à zéro
	 */
	public void reInitialiser() {
		enCourAnimation = false;
		jeuCommence = false;
		jeuTermine = false;
		premiereFois = true;
		fleche =false;
		vie = this.vieInitial;
		repaint();
	}

	/**
	 * Il permet de démarrer l'animation
	 */
	public void run() {
		while (enCourAnimation) {

			deplacement();
			gagner();
			horsTerrain();

			// pour les événements personalisé
			this.leverEvenAccelerationXY();
			this.leverEvenDegre();
			this.leverEvenPositionXY();
			this.leverEvenVitesseXY();
			this.leverEvenJeuCommence();
			this.leverEvenGetVie();

			this.repaint();

			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Il permet de voir si la partie est terminée
	 * @return si la partie est terminée
	 */
	private boolean gameOver() {
		if (vie == 0 && !tricher) {
			enCourAnimation = false;
			jeuTermine = true;
			this.jeuCommence = false;
			musique.jouerMusique(PERDU);
			JOptionPane.showMessageDialog(null,"Fin de la partie" + "\n" + "Tu as perdu. Meilleure chance la prochaine fois");

			// pour les événements personalisé
			this.leverEvenAccelerationXY();
			this.leverEvenDegre();
			this.leverEvenPositionXY();
			this.leverEvenVitesseXY();
			this.leverEvenJeuCommence();
			this.leverEvenGetVie();


			return true;
		}else {
			return false;
		}
	}

	/**
	 * Il permet de vérifier si l'utilisateur a gagné
	 */
	private void gagner() {
		if (ballonPrincipale.collisionFilet(filet)) {
			enCourAnimation = false;
			jeuTermine = true;
			this.jeuCommence = false;
			musique.jouerMusique(GAGNER);
			JOptionPane.showMessageDialog(null,"Fin de la partie" + "\n" + "Félicitations! Tu as réussi ce niveau.");
		}

		this.leverEvenAccelerationXY();
		this.leverEvenDegre();
		this.leverEvenPositionXY();
		this.leverEvenVitesseXY();
		this.leverEvenJeuCommence();
		this.leverEvenGetVie();

	}

	/**
	 * Il permet de vérifier si une des balles influençables est hors-terrain
	 */
	private void horsTerrain() {
		//Il permet d'enlever les balles qui ne sont pas principales si elles sortent du terrain
		for (int cercleInfluancable = 0; cercleInfluancable < cercle.size(); cercleInfluancable++) {
			Cercle cercleEnQuestion = cercle.get(cercleInfluancable);
			if (cercleEnQuestion.isInfluancable()) {

				if ((cercleEnQuestion.getX() >= LARGEUR_MONDE || cercleEnQuestion.getX() <= -cercleEnQuestion.getLargeur())&& cercleEnQuestion.isInfluancable()) {
					cercle.remove(cercleInfluancable);
				}
			}
		}

		//Il permet de vérifier si la balle principale est hors-terrain
		if (ballonPrincipale.getX() >= LARGEUR_MONDE || ballonPrincipale.getX() <= -ballonPrincipale.getLargeur()|| ballonPrincipale.getY() >= hauteurUniteReelle + ballonPrincipale.getHauteur() || ballonPrincipale.getY() <= -ballonPrincipale.getHauteur() * 5) {
			System.out.println("perdu");
			recommencer();
		}



	}

	/**
	 * Il permet de modifier la masse du ballon principal
	 * @param masse nouvelle masse de la balle
	 */
	public void setMasse(double masse) {
		this.ballonPrincipale.setMasse(masse);
	}


	/**
	 * Il permet de modifier la gravité du système
	 * @param gravite la nouvelle gravite du systeme
	 */
	public void setGravite(double gravite) {
		this.gravite = gravite;
		this.leverEvenAccelerationXY();
	}

	/**
	 * Il permet de créer nos événements personnalisés dans cette classe
	 * @param ecouteur l'EventListener à ajouter
	 */
	public void addBallonListener (BallonListener ecouteur) {
		listeEcouteurs.add(ecouteur);
	}

	/**
	 * Il permet de modifier la valeur de la position de la balle principale pour les événements personnalisés
	 */
	private void leverEvenPositionXY() {
		double x = ballonPrincipale.getVecteurPosition().getX();
		double y = this.hauteurUniteReelle - ballonPrincipale.getVecteurPosition().getY();
		x = (double) Math.round(x* DECIMALE) / DECIMALE;
		y = (double) Math.round(y* DECIMALE) / DECIMALE;
		for (BallonListener ecout : listeEcouteurs) {
			ecout.positionXY(x,y);
		}

	}

	/**
	 * Il permet de modifier la valeur de la vitesse de la balle principale pour les événements personnalisés
	 */
	private void leverEvenVitesseXY() {
		double x = ballonPrincipale.getVecteurVitesse().getX();
		double y = -ballonPrincipale.getVecteurVitesse().getY();
		x = (double) Math.round(x* DECIMALE) / DECIMALE;
		y = (double) Math.round(y* DECIMALE) / DECIMALE;
		for (BallonListener ecout : listeEcouteurs) {
			ecout.vitesseXY(x, y);
		}

	}

	/**
	 * Il permet de modifier la valeur de l'accélération de la balle principale pour les événements personnalisés
	 */
	private void leverEvenAccelerationXY() {
		double x = ballonPrincipale.getAcceleration().getX();
		double y = -(ballonPrincipale.getAcceleration().getY() + gravite);
		x = (double) Math.round(x* DECIMALE) / DECIMALE;
		y = (double) Math.round(y* DECIMALE) / DECIMALE;
		for (BallonListener ecout : listeEcouteurs) {
			ecout.accelerationXY(x,y);
		}

	}

	/**
	 * Il permet de modifier la valeur de l'orientation de la balle principale pour les événements personnalisés
	 */
	private void leverEvenDegre() {
		double degre = ballonPrincipale.getDegre();
		degre = (double) Math.round(degre* DECIMALE) / DECIMALE;
		for (BallonListener ecout : listeEcouteurs) {
			ecout.degre(degre);
		}

	}

	/**
	 * Il permet de dire si le jeu a commencé ou non pour les événements personnalisés
	 */
	private void leverEvenJeuCommence() {
		for (BallonListener ecout : listeEcouteurs) {
			ecout.estCommence(jeuCommence);
		}
	}

	/**
	 * Il permet de modifier le nombre d'essais de l'utilisateur pour les événements personnalisés
	 */
	private void leverEvenGetVie() {
		for (BallonListener ecout : listeEcouteurs) {
			ecout.getVie(this.vie);
		}
	}

	/**
	 * Il permet de modifier le pas de simulation du jeu selon 3 choix:
	 * <br> 0- l'animation va à sa vitesse normale
	 * <br> 1- l'animation va plus vite que d'habitude!
	 * <br> 2- l'animation va être extrèmement lente...
	 * <br> Si le choix n'est pas 0,1 ou 2, alors l'animation ira à sa vitesse normal
	 * @param selectedIndex pour savoir si l'animation doit aller vite, très vite ou lentement
	 */
	public void setPasSimulation(int selectedIndex) {
		switch (selectedIndex) {
		case 0:
			MethodePhysique.setDeltaT(MethodePhysique.getDeltaTOriginal());
			break;
		case 1:
			MethodePhysique.setDeltaT(MethodePhysique.getDeltaTOriginal()*3);
			break;
		default:
			MethodePhysique.setDeltaT(MethodePhysique.getDeltaTOriginal()*0.45);
			break;
		}
	}

	/**
	 * Il permet recommencer le jeu, mais l'utilisateur va perdre une vie si la balle était lancée
	 */
	public void recommencer() {
		if (jeuCommence) {
			vie += - 1;
			if (tricher) {
				vie +=1;
			}
			if (!gameOver()) {
				ballonPrincipale.setVecteurVitesse(new Vecteur(1,-1));
				ballonPrincipale.setVecteurPosition(new Vecteur(canon.getVecteurPosition().getX() + POSITION_CANON.getX(),canon.getVecteurPosition().getY() +POSITION_CANON.getY()));
				jeuCommence = false;
			}
			start();

		}
	}

	/**
	 * Il permet de retourner le nombre de vies que l'utilisateur possède
	 * @return le nombre de vies que l'utilisateur possède
	 */
	public int getNbreVie() {
		return vie;
	}

	/**
	 * Il permet d'appliquer le déplacement des cercles ainsi que leurs collisions
	 */
	private void deplacement() {


		for (int i = 0; i < cercle.size(); i++) {

			cercle.get(i).setAcceleration(new Vecteur(0,0));
			
			if (jeuCommence) {
				// pour vérifier la collision entre un cercle et la balle principale
				if (ballonPrincipale.collision(cercle.get(i))) {
				}	
			}		

			// pour vérifier s'il y a une collision entre deux cercles non principaux
			for (int choixCercle = i+1; choixCercle < cercle.size(); choixCercle++) {
					if (cercle.get(i).collision(cercle.get(choixCercle))) {
					}
			}


			// pour vérifier la collision entre un cercle et une forme
			for (int choixCercle = 0; choixCercle < formeNonInfluancable.size(); choixCercle++) {
					if (cercle.get(i).collision( formeNonInfluancable.get(choixCercle))) {
				}
			}

			//pour vérifier la collision entre un cercle non principal et un aimant
			for (int choixCercle = 0; choixCercle < aimant.size(); choixCercle++) {
				cercle.get(i).collision(aimant.get(choixCercle));
			}

			//Pour vérifier la collision entre un cercle non principal et le filet
			cercle.get(i).collisionFilet(filet);
		}

		// pour vérifier la collision entre le ballon principal et une forme
		for (int choixForme = 0; choixForme < formeNonInfluancable.size(); choixForme++) {
			if (jeuCommence) {
					if (ballonPrincipale.collision( formeNonInfluancable.get(choixForme))) {
				}


			}
		}

		//Pour vérifier la collision entre le ballon et l'aiamnt

		ballonPrincipale.setAcceleration(new Vecteur(0,0));
		for (int choixAimant = 0; choixAimant < aimant.size(); choixAimant++) {
			if (jeuCommence) {
				ballonPrincipale.collision(aimant.get(choixAimant));

			}
		}

		// Pour utiliser la méthode d'Euler sur les balles
		if (jeuCommence) {
			MethodePhysique.Euler(ballonPrincipale,gravite);

		}
		for (int choixCercle = 0; choixCercle < cercle.size(); choixCercle++) {
			Cercle cerclePris = cercle.get(choixCercle);
			MethodePhysique.Euler(cerclePris,gravite);
		}

	}

	/**
	 * Il permet de modifier l'intensité des aimants
	 * @param intensite l'intensité des aimants
	 */
	public void setAimant(double intensite) {
		// pour modifier l'aimant
		this.intensite = intensite;
		for (int choixAimant = 0; choixAimant < this.aimant.size(); choixAimant++) {
			this.aimant.get(choixAimant).setIntensite(intensite);
		}
		this.leverEvenAccelerationXY();
	}

	/**
	 * Il retoune le nom  du custom / Niveau
	 * @return le nom du custom / Niveau
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Il permet de voir la flèche de la vitesse
	 * @param fleche si nous voulons voir la flèche de la vitesse
	 */
	public void flecheVitesse(boolean fleche) {
		this.fleche  = fleche;

		this.repaint();
	}

	/**
	 * Il permet de préparer les composants du jeux
	 */
	private void preparerJeux() {
		filet = new Filet(GestionnaireDeFichiers.getFilet().getX(),GestionnaireDeFichiers.getFilet().getY());
		canon = GestionnaireDeFichiers.getCanon();
		canonTemporaire = new Canon(canon.getX(),canon.getY());
		nom = GestionnaireDeFichiers.getNom();
		vie = GestionnaireDeFichiers.getNbreEssai();
		vieInitial  =vie;
		formeNonInfluancable.clear();
		cercle.clear();
		aimant.clear();
		for (int aimantChoix = 0; aimantChoix < GestionnaireDeFichiers.getListeAimant().size(); aimantChoix++) {
			aimant.add(new Aimant(GestionnaireDeFichiers.getListeAimant().get(aimantChoix).getX(),GestionnaireDeFichiers.getListeAimant().get(aimantChoix).getY()));
			aimant.get(aimantChoix).setLargeurHauteur(GestionnaireDeFichiers.getListeAimant().get(aimantChoix).getHauteur());
			aimant.get(aimantChoix).setIntensite(intensite);
		}
		for (int i = 0; i < GestionnaireDeFichiers.getListeCercle().size(); i++) {
			GestionnaireDeFichiers.getListeCercle().get(i).setMasse(GestionnaireDeFichiers.getListeCercle().get(i).getLargeur());
			cercle.add(new Cercle(GestionnaireDeFichiers.getListeCercle().get(i)));
		}
		for (int segment = 0; segment < GestionnaireDeFichiers.getListeSegment().size(); segment++) {
			formeNonInfluancable.add(GestionnaireDeFichiers.getListeSegment().get(segment));
		}

		for (int triangle = 0; triangle < GestionnaireDeFichiers.getListeTriangle().size(); triangle++) {
			formeNonInfluancable.add(GestionnaireDeFichiers.getListeTriangle().get(triangle));
		}

		for (int recatngle = 0; recatngle < GestionnaireDeFichiers.getListeRectangle().size(); recatngle++) {
			formeNonInfluancable.add(GestionnaireDeFichiers.getListeRectangle().get(recatngle));
		}

		jeuTermine = false;
		ballonPrincipale = new Ballon(0,0);
		ballonPrincipale.setVecteurPosition(new Vecteur(canon.getVecteurPosition().getX() + POSITION_CANON.getX(),canon.getVecteurPosition().getY() + POSITION_CANON.getY()));
		MethodePhysique.changerInelasticite(2);
		this.leverEvenGetVie();
		this.setPasSimulation(0);
	}


	/**
	 * Il permet de savoir si l'utilisateur veut tricher ou non
	 * @param tricher si l'utilisateur veut tricher ou non
	 */
	public void modeTriche(boolean tricher) {
		this.tricher = tricher;	
		if (tricher) {
			vie = 999;
		}else {
			vie = vieInitial;
		}

		this.leverEvenGetVie();

	}
}
