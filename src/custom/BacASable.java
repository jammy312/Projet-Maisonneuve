package custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import aapplication.App15BallonCanon;
import aide.FenetreAideGuideUtilisation;
import fichiers.GestionnaireDeFichiers;
import forme.Aimant;
import forme.Cercle;
import forme.Forme;
import forme.Rectangle;
import forme.Segment;
import forme.Triangle;
import jeux.Jeux;

/**
 * Cette classe est le custom de l'objet. Il permet  l'utilisateur de crer son propre niveau
 *@author Jason
 *@author James
 *@author Estelle
 */
public class BacASable extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private FormeChoisi formeChoisi;
	private  ModeEditer modeEditer; 
	private FormeChoisi formeChoisi2;
	private JLabel lblNomForme;
	private JSpinner spinnerRotation;
	private JLabel lblRotation;
	private JLabel lblTaille;
	private JSpinner spinnerTaille;
	private JCheckBox chckbxInfluencable;
	private JButton btnSupprimer;
	private JButton btnSauver;
	private JButton btnEssayer;
	private final int ROTATION_MAXIMAL = 360;
	private final int TAILLE_MAXIMAL = 300;
	private final int TAILLE_MINIMALE = 50;
	private final int NOMBRE_ESSAI_MAXIMALE = 100;
	private JLabel lbLNombreEssai;
	private JSpinner spinnerNbEssai;
	private JTextField textNomJeux;
	private JLabel lblNomJeux;
	private JButton btnCopier;
	private JButton btnNGauche;
	private JButton btnDroite;
	private JButton btnPrendre;
	private JPanel propriete;
	private final Color COULEUR_FOND = new Color(0,191,255);
	private JTabbedPane tabbedPaneCreation;
	private String tabbedPaneCreationString = "Création";
	private JPanel panelMenu;
	private FenetreAideGuideUtilisation aide = new FenetreAideGuideUtilisation(2);
	private JList<String> listeDesSauvegarde;
	private String nomFichier;
	private ArrayList<String> nomsSauvegarde;
	private JScrollPane scrollPane;
	private DefaultListModel<String> modele = new DefaultListModel<String>();
	private JButton btnQuitter_1;

	//James, Jason, Estelle
	/**
	 * Création du frame qui permet de faire le bac à sable
	 * 
	 */
	public BacASable() {
		//James
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setTitle("Bac à sable");
		setBounds(100, 100, 1439, 780);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		modeEditer = new ModeEditer();


		modeEditer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				estClique();

			}
			@Override
			public void mousePressed(MouseEvent e) {
				estClique();
			}
		});

		modeEditer.setBounds(0,0,1129, 741);
		contentPane.add(modeEditer);

		tabbedPaneCreation = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneCreation.setBounds(1125, -2, 300, 743);
		contentPane.add(tabbedPaneCreation);

		JPanel panelCreation = new JPanel();
		tabbedPaneCreation.addTab(tabbedPaneCreationString, null, panelCreation, null);
		panelCreation.setLayout(null);
		panelCreation.setBackground(COULEUR_FOND);


		formeChoisi = new FormeChoisi();
		formeChoisi.setBounds(10, 10, 308, 93);
		panelCreation.add(formeChoisi);
		formeChoisi.setCouleurFondEcran(COULEUR_FOND);

		btnNGauche = new JButton("Gauche");
		btnNGauche.setBounds(71, 120, 60, 60);
		panelCreation.add(btnNGauche);
		associerBoutonAvecImage(btnNGauche, "gauche.png");

		btnDroite = new JButton("Droite");
		btnDroite.setBounds(189, 120, 60, 60);
		panelCreation.add(btnDroite);
		btnDroite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				//debut
				formeChoisi.droit();
				//fin
			}
		});
		associerBoutonAvecImage(btnDroite, "droite.png");


		//James
		btnPrendre = new JButton("Prendre");
		btnPrendre.setBounds(78, 197, 165, 59);
		panelCreation.add(btnPrendre);
		btnPrendre.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				//debut
				modeEditer.nouveauObjet(formeChoisi.getObjet());;
				//fin
			}
		});
		btnPrendre.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lbLNombreEssai = new JLabel("Nombre d'essai:");
		lbLNombreEssai.setBounds(10, 266, 165, 45);
		panelCreation.add(lbLNombreEssai);
		lbLNombreEssai.setHorizontalAlignment(SwingConstants.CENTER);
		lbLNombreEssai.setFont(new Font("Tahoma", Font.PLAIN, 18));


		spinnerNbEssai = new JSpinner();
		spinnerNbEssai.setBounds(221, 268, 73, 45);
		panelCreation.add(spinnerNbEssai);
		spinnerNbEssai.setFont(new Font("Tahoma", Font.BOLD, 18));


		spinnerNbEssai.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				modeEditer.setNbEssaie((int)spinnerNbEssai.getValue());
			}
		});
		spinnerNbEssai.setModel(new SpinnerNumberModel(1,1,NOMBRE_ESSAI_MAXIMALE,1));

		propriete = new JPanel();
		propriete.setBounds(0, 341, 295, 374);
		panelCreation.add(propriete);
		propriete.setBackground(COULEUR_FOND);
		propriete.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		propriete.setLayout(null);

		chckbxInfluencable = new JCheckBox("Influen\u00E7able");
		chckbxInfluencable.setBackground(COULEUR_FOND);
		chckbxInfluencable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cercle cercle = (Cercle) modeEditer.getFormeSelectionne();
				cercle.Influancable(chckbxInfluencable.isSelected());
				modeEditer.repaint();
			}
		});
		chckbxInfluencable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxInfluencable.setBounds(41, 186, 213, 23);
		propriete.add(chckbxInfluencable);
		chckbxInfluencable.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxInfluencable.setVisible(false);

		//Jason
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblNomForme.setVisible(false);
				lblRotation.setVisible(false);
				spinnerRotation.setVisible(false);
				lblTaille.setVisible(false);
				spinnerTaille.setVisible(false);
				formeChoisi2.setVisible(false);
				chckbxInfluencable.setVisible(false);
				btnSupprimer.setVisible(false);
				btnCopier.setVisible(false);

				boolean trouver = false;
				int numeroList = -1;
				while (!trouver) {
					numeroList++;
					if (modeEditer.getListForme().get(numeroList).equals(modeEditer.getFormeSelectionne())) {
						trouver = true;
						modeEditer.getListForme().remove(numeroList);
					}
				}
				modeEditer.repaint();

			}
		});
		btnSupprimer.setBounds(51, 219, 60, 60);
		propriete.add(btnSupprimer);
		btnSupprimer.setVisible(false);
		associerBoutonAvecImage(btnSupprimer,"supprimer.png");

		lblTaille = new JLabel("Taille(m):");
		lblTaille.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTaille.setHorizontalAlignment(SwingConstants.CENTER);
		lblTaille.setBounds(10, 152, 115, 30);
		propriete.add(lblTaille);
		lblTaille.setVisible(false);
		spinnerTaille = new JSpinner();
		spinnerTaille.setFont(new Font("Tahoma", Font.PLAIN, 17));
		spinnerTaille.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (modeEditer.getFormeSelectionne() != null) {
					modeEditer.getFormeSelectionne().setLargeurHauteur((double)spinnerTaille.getValue()*1.0);;	
					modeEditer.repaint();
				}
			}
		});
		spinnerTaille.setBounds(158, 157, 96, 23);
		propriete.add(spinnerTaille);
		spinnerTaille.setVisible(false);
		spinnerTaille.setModel(new SpinnerNumberModel(TAILLE_MINIMALE,TAILLE_MINIMALE,TAILLE_MAXIMAL,1.0));



		lblRotation = new JLabel("Rotation(" + "\u2070" + "):");
		lblRotation.setHorizontalAlignment(SwingConstants.CENTER);
		lblRotation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRotation.setBounds(10, 113, 115, 37);
		propriete.add(lblRotation);
		lblRotation.setVisible(false);

		spinnerRotation = new JSpinner();
		spinnerRotation.setFont(new Font("Tahoma", Font.PLAIN, 17));
		spinnerRotation.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (modeEditer.getFormeSelectionne() != null) {
					modeEditer.getFormeSelectionne().setRotation((double) spinnerRotation.getValue()*1.0);
					modeEditer.repaint();		
				}
			}
		});
		spinnerRotation.setModel(new SpinnerNumberModel(0,0,ROTATION_MAXIMAL,1.0));
		spinnerRotation.setBounds(158, 121, 96, 23);
		propriete.add(spinnerRotation);
		spinnerRotation.setVisible(false);

		formeChoisi2 = new FormeChoisi();
		formeChoisi2.setBounds(10, 8, 278, 65);
		propriete.add(formeChoisi2);
		formeChoisi2.setVisible(false);
		formeChoisi2.setVisible(false);
		formeChoisi2.setCouleurFondEcran(COULEUR_FOND);
		lblNomForme = new JLabel("");
		lblNomForme.setBackground(COULEUR_FOND);
		lblNomForme.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomForme.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNomForme.setBounds(8, 83, 278, 37);
		propriete.add(lblNomForme);

		btnCopier = new JButton("New button");
		btnCopier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				copier();
			}
		});
		btnCopier.setBounds(186, 219, 60, 60);
		propriete.add(btnCopier);
		btnCopier.setVisible(false);
		associerBoutonAvecImage(btnCopier,"copier.png");

		//James
		btnQuitter_1 = new JButton("Quitter");
		btnQuitter_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnQuitter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App15BallonCanon app = new App15BallonCanon();
				if (confirmation()) {
					dispose();
					app.setVisible(true);
				}
			}
		});
		btnQuitter_1.setBounds(200, 331, 95, 44);
		propriete.add(btnQuitter_1);

		panelMenu = new JPanel();
		tabbedPaneCreation.addTab("Actions et gestions de sauvegarde", null, panelMenu, null);
		panelMenu.setLayout(null);
		panelMenu.setBackground(COULEUR_FOND);


		btnSauver = new JButton("Sauver");
		btnSauver.setBounds(31, 130, 90, 90);
		panelMenu.add(btnSauver);
		btnSauver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Estelle
				if (confirmationNom()) {
					if (GestionnaireDeFichiers.fichierExiste()) {
						GestionnaireDeFichiers.lireFichierTexteBureau();
					} 
					GestionnaireDeFichiers.ecrireFichierTexteBureau(textNomJeux.getText(), modeEditer.getListForme(), (int) spinnerNbEssai.getValue(), textNomJeux);
				}

				//James
				modele.clear();
				nomsSauvegarde = GestionnaireDeFichiers.getNomSauvegarde();
				for (int i = 0; i < nomsSauvegarde.size(); i++) {
					modele.addElement(nomsSauvegarde.get(i));
				}
				nomFichier = null;
			}
		});
		btnSauver.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.associerBoutonAvecImage(btnSauver, "sauver.png");

		//Jason
		btnEssayer = new JButton("Essayer");
		btnEssayer.setBounds(152, 120, 110, 110);
		panelMenu.add(btnEssayer);
		btnEssayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jouer();

			}
		});
		btnEssayer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.associerBoutonAvecImage(btnEssayer, "essayer.png");

		//James
		lblNomJeux = new JLabel("Nom:");
		lblNomJeux.setBounds(13, 75, 66, 20);
		panelMenu.add(lblNomJeux);
		lblNomJeux.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNomJeux.setHorizontalAlignment(SwingConstants.CENTER);

		textNomJeux = new JTextField();
		textNomJeux.setBounds(92, 75, 188, 20);
		panelMenu.add(textNomJeux);
		textNomJeux.setColumns(10);

		//Jason
		JButton btnAide = new JButton("New button");
		btnAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!aide.isVisible()) {
					aide.setVisible(true);
				}
			}
		});
		btnAide.setBounds(71, 240, 152, 152);
		panelMenu.add(btnAide);

		//James
		this.associerBoutonAvecImage(btnAide, "aide.png");
		nomsSauvegarde = GestionnaireDeFichiers.getNomSauvegarde();
		for (int i = 0; i < nomsSauvegarde.size(); i++) {
			modele.addElement(nomsSauvegarde.get(i));
		}

		JButton btnChangerDeNiveaux = new JButton("Changer de niveau");
		btnChangerDeNiveaux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//James
				if (confirmationChanger()) {
					if (GestionnaireDeFichiers.fichierExiste(nomFichier)) {
						BacASable nouveau = new BacASable();
						GestionnaireDeFichiers.lireContenuBac(nomFichier, nouveau);
						nouveau.setVisible(true);
						dispose();
					} 
				}
			}
		});
		btnChangerDeNiveaux.setBounds(54, 588, 186, 30);
		panelMenu.add(btnChangerDeNiveaux);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App15BallonCanon app = new App15BallonCanon();
				if (confirmation()) {
					dispose();
					app.setVisible(true);
				}
			}
		});
		btnQuitter.setBounds(200, 672,95, 44);
		panelMenu.add(btnQuitter);


		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 402, 265, 148);
		panelMenu.add(scrollPane);
		listeDesSauvegarde = new JList<>(modele);
		scrollPane.setViewportView(listeDesSauvegarde);
		listeDesSauvegarde.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int indexSelectionne = listeDesSauvegarde.getSelectedIndex();
				if (indexSelectionne >= 0) {
					nomFichier = modele.elementAt(indexSelectionne);
				}
			}
		});

		lblNomForme.setVisible(false);
		btnNGauche.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				//debut
				formeChoisi.gauche();
				//fin
			}
		});
	}

	//Jason
	/**
	 * Il permet de copier la forme pris en valeur
	 */
	private void copier() {

		switch (modeEditer.getFormeSelectionne().getNomClasse()) {
		case "Cercle":
			modeEditer.nouveauObjet(new Cercle(0,0));

			Cercle cercle = (Cercle) modeEditer.getFormeSelectionne();
			Cercle cercleCopie = (Cercle) modeEditer.getListForme().get(modeEditer.getListForme().size()-1);

			cercleCopie.setLargeurHauteur(cercle.getHauteur());
			cercleCopie.setRotation(cercle.getRotation());
			cercleCopie.Influancable(cercle.isInfluancable());
			break;
		case "Aimant":
			modeEditer.nouveauObjet(new Aimant(0,0));
			break;		

		case "Rectangle":
			modeEditer.nouveauObjet(new Rectangle(0,0));

			break;		
		case "Segment":
			modeEditer.nouveauObjet(new Segment(0,0));

			break;		
		case "Triangle":
			modeEditer.nouveauObjet(new Triangle(0,0));
			break;		
		}
		if (!modeEditer.getFormeSelectionne().getNomClasse().equals("Cercle")) {
			Forme forme = (Forme) modeEditer.getFormeSelectionne();
			Forme formeCopie = (Forme) modeEditer.getListForme().get(modeEditer.getListForme().size()-1);
			formeCopie.setLargeurHauteur(forme.getHauteur());
			formeCopie.setRotation(forme.getRotation());
		}
	}

	//Jason
	/**
	 *Il permet d'afficher les propriétés  en bas à droite si un objet est touché (ne concerne pas le ballon et le filet).
	 * 
	 */
	private void estClique() {
		lblNomForme.setVisible(false);
		lblRotation.setVisible(false);
		spinnerRotation.setVisible(false);
		lblTaille.setVisible(false);
		spinnerTaille.setVisible(false);
		formeChoisi2.setVisible(false);
		chckbxInfluencable.setVisible(false);
		btnSupprimer.setVisible(false);
		btnCopier.setVisible(false);

		Forme forme = null;

		if (!(modeEditer.getFormeSelectionne() == forme) && !(modeEditer.getFormeSelectionne().getNomClasse().equals("Filet") ||modeEditer.getFormeSelectionne().getNomClasse().equals("Canon"))) {

			lblNomForme.setVisible(true);
			lblRotation.setVisible(true);
			spinnerRotation.setVisible(true);
			lblTaille.setVisible(true);
			spinnerTaille.setVisible(true);
			formeChoisi2.setVisible(true);
			btnSupprimer.setVisible(true);
			btnCopier.setVisible(true);

			lblNomForme.setText(modeEditer.getFormeSelectionne().getNomClasse());

			spinnerRotation.setValue(modeEditer.getFormeSelectionne().getRotation());
			spinnerTaille.setValue(modeEditer.getFormeSelectionne().getHauteur());
			formeChoisi2.setObjet(modeEditer.getFormeSelectionne());

			if (modeEditer.getFormeSelectionne().getNomClasse().equals("Cercle")) {
				chckbxInfluencable.setVisible(true);
				Cercle cercle = (Cercle) modeEditer.getFormeSelectionne();
				chckbxInfluencable.setSelected(cercle.isInfluancable());
			}
		}
	}

	//Jason
	/**
	 * Il permet de passer du custom au jeu
	 */
	public void jouer() {
		GestionnaireDeFichiers.sauvegardeTemporaire(textNomJeux.getText(),modeEditer.getListForme(), (int)spinnerNbEssai.getValue());
		Jeux jeux = new Jeux(this);
		jeux.setVisible(true);
		dispose();
	}

	//James
	/**
	 * permet de lier un bouton avec une image
	 */
	private void associerBoutonAvecImage(JButton leBouton, String fichierImage) {
		Image imgLue = null;
		java.net.URL urlImage = getClass().getClassLoader().getResource(fichierImage);
		if (urlImage == null) {
			JOptionPane.showMessageDialog(null, "Fichier " + fichierImage + " introuvable");
		}
		try {
			imgLue = ImageIO.read(urlImage);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur pendant la lecture du fichier d'image");
		}

		// redimensionner l'image de la même grandeur que le bouton
		Image imgRedim = imgLue.getScaledInstance(leBouton.getWidth(), leBouton.getHeight(), Image.SCALE_SMOOTH);

		// au cas où le fond de l’image serait transparent
		leBouton.setOpaque(false);
		leBouton.setContentAreaFilled(false);
		leBouton.setBorderPainted(false);

		// associer l'image au bouton
		leBouton.setText("");
		leBouton.setIcon(new ImageIcon(imgRedim));

		// on se débarrasse des images intermédiaires
		imgLue.flush();
		imgRedim.flush();
	}

	//James
	/**
	 * permet de savoir si l'utilisateur veut aller au menu principal ou non
	 * @return une boolean pour savoir si l'utilisateur veut aller au menu principal ou non
	 */
	private boolean confirmation() {
		String[] options = {"Retourner au menu principal","Attendre, je n'ai pas encore fini"};
		int rep = JOptionPane.showOptionDialog(null, "Voulez-vous retournez au menu principal? (pensez à sauvegarder avant) ", "Confirmation", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, null); 
		if (rep == JOptionPane.YES_OPTION) {
			return true;
		}else {
			return false;
		}

	}

	//James
	/**
	 * permet de savoir si l'utilisateur veut changer de niveaux non
	 * @return une boolean pour savoir si l'utilisateur veut changer de niveaux non
	 */
	private boolean confirmationChanger() {
		boolean verite = false;
		if(nomFichier != null) {
			String nom = "'" + nomFichier + "'";
			String[] options = {"Oui, je veux  modifier  " + nom ,"Attendre"};
			int rep = JOptionPane.showOptionDialog(null, "Voulez-vous modifier maintenant " + nom + " ?", "Confirmation", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, options, null); 
			if (rep == JOptionPane.YES_OPTION) {
				verite = true;
			}else {
				verite = false;
			}
		}
		return verite;
		
	}

	//Jason
	/**
	 * Il permet de savoir si l'utilisateur a mis un nom pour son jeu ou pas
	 * @return si l'utilisateur a mis un nom pour son jeu ou pas
	 */
	private boolean confirmationNom() {
		textNomJeux.setBackground(Color.white);
		if (textNomJeux.getText().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "Tâche impossible à faire. Il faut mettre un nom pour ton custom");
			textNomJeux.setBackground(Color.yellow);
			return false;
		}else {
			return true;
		}
	}

	//James
	/**
	 * Il permet de récupérer les formes d'un custom
	 */
	public void setSauvegarde() {
		modeEditer.setForme(GestionnaireDeFichiers.getForme());
	}

	//Estelle
	/**
	 * Il permet de modifier le texte de la zone comportant le nom du niveau
	 * @param nomFichier le nom du fichier qu'on va placer dans la zone de texte
	 */
	public void setNomSauvegarde (String nomFichier) {
		textNomJeux.setText(nomFichier);
	}

	//Estelle
	/**
	 * Il permet de modifier le nombre indiqué sur le tourniquet comportant le nombre d'essais
	 * @param nbEssaiSauvegarde le nombre qu'on va placer dans le tourniquet
	 */
	public void setNbEssai (int nbEssaiSauvegarde) {
		spinnerNbEssai.setModel(new SpinnerNumberModel(nbEssaiSauvegarde,1,NOMBRE_ESSAI_MAXIMALE,1));
	}

	//James
	/**
	 * Ajoute les noms sauvegardés dans une nouvelle liste
	 * @param nomSauvegarde le tableau de chaînes comportant les noms sauvegardés
	 */
	public void setListeSauvegarde (ArrayList<String> nomSauvegarde) {
		modele.clear();
		nomsSauvegarde = nomSauvegarde;
		for (int i = 0; i < nomSauvegarde.size(); i++) {
			modele.addElement(nomSauvegarde.get(i));
		}
		nomFichier = null;
	}



}
