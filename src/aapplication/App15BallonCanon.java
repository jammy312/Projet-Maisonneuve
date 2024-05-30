package aapplication;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import aide.FenetreAideGuideUtilisation;
import credit.CreditFenetre;
import custom.BacASable;
import fichiers.GestionnaireDeFichiers;
import jeux.Jeux;


/**Cette classe permet de lancer l'application et d'accéder à toutes les fenêtres
 * @author Estelle
 * @author Jason 
 * @author James 
 */
public class App15BallonCanon extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BacASable ouvrir = new BacASable();
	private String nomFichier;
	private JButton btnInstruNiveaux;
	private FenetreAideGuideUtilisation aide = new FenetreAideGuideUtilisation(1);
	private JButton btnInstruBac;
	private JButton btnInstruScientifique;
	private JButton btnJouer;
	private JButton btnBac;
	private JButton btnTriche;
	private JButton btnMusique;
	private JButton btnInstructions;
	private JLabel lblTitrePrincipal;
	private JLabel lblFondPrincipal;
	private JPanel panelJouer;
	private JPanel panelPrincipal;
	private JPanel panelChoixNiveaux;
	private JButton btnChoixNiveau1;
	private JButton btnChoixNiveau2;
	private JButton btnChoixNiveau3;
	private JButton btnChoixNiveau4;
	private JButton btnInstruDeJouer;
	private JButton btnRMenuDeJouer;
	private JButton btnMusiqueJouer;
	private JLabel lblTitreJouer;
	private JLabel lblFondJouer;
	private JPanel panelBac;
	private JPanel panelChoixActionsBac;
	private JButton btnAjouterNiveau;
	private JButton btnRMenuDeBac;
	private JButton btnInstruDeBac;
	private JScrollPane scrollPane;
	private JButton btnMusiqueBac;
	private JLabel lblFondBac;
	private JPanel panelInstructions;
	private JPanel panelChoixInstructions;
	private JLabel lblTitreInstru;
	private JButton btnRMenuDeInstru;
	private JButton btnMusiqueInstru;
	private JLabel lblFondInstru;
	private JButton btnEditerNiveau;
	private JButton btnSupprimerNiveau;
	private JLabel lblPasMusique;
	private JLabel lblPasMusiqueJouer;
	private JLabel lblPasMusiqueBac;
	private JLabel lblPasMusiqueInstru;
		
	private ArrayList<String> nomsSauvegarde;
	private JList<String> listeDesSauvegarde;
	public static boolean modeTricheClique = false;
	public static boolean avecSon = true;
	private boolean active = false;
	private JButton btnCredit;
	private CreditFenetre credit;

		
	ImageIcon iconeInstru = creerIcone("info.png", 15);
	ImageIcon iconeMenu = creerIcone("retour.png", 15);
	ImageIcon iconePasMusique = creerIcone("pasMusique.png", 30);

	
	//Estelle
	/**
	 * Permet de démarrer l'application
	 * @param args pour démarrer l'application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App15BallonCanon frame = new App15BallonCanon();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Estelle, Jason et James
	/** 
	 * Classe principale qui sert de constructeur à l'application
	 */
	public App15BallonCanon() {
		//Estelle
		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		panelPrincipal = new JPanel();
		contentPane.add(panelPrincipal, "menu");
		panelPrincipal.setLayout(null);

		ImageIcon iconeJouer = creerIcone("canon.png", 15);
		btnJouer = new JButton("Jouer", iconeJouer);
		btnJouer.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent arg0) {
				modeTricheClique = false;
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "jouer");
			}
		});
		btnJouer.setBounds(28, 92, 116, 23);
		panelPrincipal.add(btnJouer);


		ImageIcon iconeBac = creerIcone("crayon.png", 15);
		btnBac = new JButton("Bac \u00E0 sable", iconeBac);
		btnBac.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "bac");
			}
		});
		btnBac.setBounds(275, 92, 125, 23);
		panelPrincipal.add(btnBac);

		ImageIcon iconeTriche = creerIcone("triche.png", 15);
		btnTriche = new JButton("Mode triche", iconeTriche);
		btnTriche.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent arg0) {
				modeTricheClique = true;
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "jouer");
			}
		});
		btnTriche.setBounds(147, 152, 125, 23);	
		panelPrincipal.add(btnTriche);

		lblPasMusique = new JLabel("");
		lblPasMusique.setVisible(false);
		lblPasMusique.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPasMusique.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasMusique.setBounds(10, 11, 45, 38);
		lblPasMusique.setIcon(iconePasMusique);
		panelPrincipal.add(lblPasMusique);
		
		btnMusique = new JButton();
		btnMusique.setOpaque(false);
		btnMusique.setBounds(10, 11, 45, 38);
		btnMusique.setIcon(changerIcone("son.png", btnMusique));		
		btnMusique.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				Musique.setMusique();
				setCroixVisible();
			}
		});
		panelPrincipal.add(btnMusique);
			
		btnInstructions = new JButton("Instructions", iconeInstru);
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Estelle
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "instructions");
			}
		});
		btnInstructions.setBounds(274, 217, 140, 23);
		panelPrincipal.add(btnInstructions);
		
		
		ImageIcon iconeCredit = creerIcone("ordinateur.png", 15);
		btnCredit = new JButton("Cr\u00E9dits", iconeCredit);
		btnCredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Estelle
				credit = new CreditFenetre();
				credit.setVisible(true);
			}
		});
		btnCredit.setBounds(10, 217, 104, 23);
		panelPrincipal.add(btnCredit);

		
		lblTitrePrincipal = new JLabel("Physico-Ballon");
		lblTitrePrincipal.setFont(new Font("Chaparral Pro Light", Font.BOLD, 35));
		lblTitrePrincipal.setForeground(Color.BLACK);
		lblTitrePrincipal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitrePrincipal.setBounds(71, 11, 282, 64);
		panelPrincipal.add(lblTitrePrincipal);

		lblFondPrincipal = new JLabel("");
		lblFondPrincipal.setFont(new Font("Monotype Corsiva", Font.PLAIN, 11));
		lblFondPrincipal.setBounds(0, 0, 424, 251);
		lblFondPrincipal.setIcon(changerIcone("fondPrincipal.jpg", lblFondPrincipal));
		panelPrincipal.add(lblFondPrincipal);

		panelJouer = new JPanel();
		contentPane.add(panelJouer, "jouer");
		panelJouer.setLayout(new CardLayout(0, 0));

		panelChoixNiveaux = new JPanel();
		panelJouer.add(panelChoixNiveaux, "choixNiveaux");
		panelChoixNiveaux.setLayout(null);

		btnChoixNiveau1 = new JButton("NIVEAU 1");
		btnChoixNiveau1.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				ouvrirNiveau(1);
			}
		});
		btnChoixNiveau1.setBounds(18, 114, 89, 23);
		panelChoixNiveaux.add(btnChoixNiveau1);

		btnChoixNiveau2 = new JButton("NIVEAU 2");
		btnChoixNiveau2.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				ouvrirNiveau(2);
			}
		});
		btnChoixNiveau2.setBounds(118, 114, 89, 23);
		panelChoixNiveaux.add(btnChoixNiveau2);

		btnChoixNiveau3 = new JButton("NIVEAU 3");
		btnChoixNiveau3.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				ouvrirNiveau(3);
			}
		});
		btnChoixNiveau3.setBounds(218, 114, 89, 23);
		panelChoixNiveaux.add(btnChoixNiveau3);

		btnChoixNiveau4 = new JButton("NIVEAU 4");
		btnChoixNiveau4.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				ouvrirNiveau(4);				
			}
		});
		btnChoixNiveau4.setBounds(318, 114, 89, 23);
		panelChoixNiveaux.add(btnChoixNiveau4);

		btnInstruDeJouer = new JButton("Instructions", iconeInstru);
		btnInstruDeJouer.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "instructions");
			}
		});
		btnInstruDeJouer.setBounds(274, 217, 140, 23);
		panelChoixNiveaux.add(btnInstruDeJouer);

		btnRMenuDeJouer = new JButton("Menu principal", iconeMenu);
		btnRMenuDeJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Estelle
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "menu");
			}
		});
		btnRMenuDeJouer.setBounds(10, 217, 140, 23);
		panelChoixNiveaux.add(btnRMenuDeJouer);

		lblPasMusiqueJouer = new JLabel("");
		lblPasMusiqueJouer.setVisible(false);
		lblPasMusiqueJouer.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPasMusiqueJouer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasMusiqueJouer.setBounds(10, 11, 45, 38);
		lblPasMusiqueJouer.setIcon(iconePasMusique);
		panelChoixNiveaux.add(lblPasMusiqueJouer);
		
		btnMusiqueJouer = new JButton();
		btnMusiqueJouer.setOpaque(false);
		btnMusiqueJouer.setBounds(10, 11, 45, 38);
		btnMusiqueJouer.setIcon(changerIcone("son.png", btnMusiqueJouer));
		btnMusiqueJouer.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				Musique.setMusique();
				setCroixVisible();
			}
		});	
		panelChoixNiveaux.add(btnMusiqueJouer);

		lblTitreJouer = new JLabel("Physico-Ballon");
		lblTitreJouer.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreJouer.setForeground(Color.BLACK);
		lblTitreJouer.setFont(new Font("Chaparral Pro Light", Font.BOLD, 35));
		lblTitreJouer.setBackground(Color.WHITE);
		lblTitreJouer.setBounds(64, 11, 297, 64);
		panelChoixNiveaux.add(lblTitreJouer);

		lblFondJouer = new JLabel("New label");
		lblFondJouer.setBounds(0, 0, 424, 251);
		lblFondJouer.setIcon(changerIcone("fondPrincipal.jpg", lblFondJouer));
		panelChoixNiveaux.add(lblFondJouer);

		DefaultListModel<String> modele = new DefaultListModel<String>();
		panelBac = new JPanel();
		contentPane.add(panelBac, "bac");
		panelBac.setLayout(new CardLayout(0, 0));

		panelChoixActionsBac = new JPanel();
		panelBac.add(panelChoixActionsBac, "actionsBac");
		panelChoixActionsBac.setLayout(null);

		ImageIcon iconeSuppr = creerIcone("poubelle.png", 15);
		btnSupprimerNiveau = new JButton("Supprimer", iconeSuppr);
		btnSupprimerNiveau.setEnabled(false);
		btnSupprimerNiveau.addActionListener(new ActionListener() {
			//James
			public void actionPerformed(ActionEvent e) {
				if (nomFichier != null) {
					char guillemets = '"';
					String[] options = {"Oui, supprimez-le","Non, je veux le garder "};
					int rep = JOptionPane.showOptionDialog(null, "Voulez-vous vraiment supprimer "  + guillemets + nomFichier + guillemets + "?", "Confirmation", JOptionPane.YES_NO_OPTION, 
							JOptionPane.QUESTION_MESSAGE, null, options, null); 

					//S'il veut supprimer le fichier
					if(rep== JOptionPane.YES_OPTION) {
						//1) on le supprime
						GestionnaireDeFichiers.supprimer(nomFichier);
						//2) on efface et on refait une nouvelle liste de noms
						modele.clear();
						nomsSauvegarde = GestionnaireDeFichiers.getNomSauvegarde();
						for (int i = 0; i < nomsSauvegarde.size(); i++) {
							modele.addElement(nomsSauvegarde.get(i));
						}
						nomFichier = null;
						ouvrir.setListeSauvegarde(nomsSauvegarde);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Prenez le temps de choisir un fichier!");
				}
				active();
			}
		});


		btnSupprimerNiveau.setBounds(25, 183, 115, 23);
		panelChoixActionsBac.add(btnSupprimerNiveau);

		ImageIcon iconeEdit = creerIcone("edit.png", 15);
		btnEditerNiveau = new JButton("\u00C9diter", iconeEdit);
		btnEditerNiveau.setEnabled(false);		
		btnEditerNiveau.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				editerFichierSelectionne(nomFichier);				
			}
		});
		btnEditerNiveau.setBounds(155, 183, 115, 23);
		panelChoixActionsBac.add(btnEditerNiveau);

		ImageIcon iconeAjouter = creerIcone("plus.png", 15);
		btnAjouterNiveau = new JButton("Ajouter", iconeAjouter);
		btnAjouterNiveau.addActionListener(new ActionListener() {
			//James
			public void actionPerformed(ActionEvent e) {
				BacASable ajouter = new BacASable();
				ajouter.setVisible(true);
				dispose();
			}
		});
		btnAjouterNiveau.setBounds(285, 183, 115, 23);
		panelChoixActionsBac.add(btnAjouterNiveau);
	
		btnRMenuDeBac = new JButton("Menu principal", iconeMenu);
		btnRMenuDeBac.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "menu");
			}
		});
		btnRMenuDeBac.setBounds(10, 217, 140, 23);
		panelChoixActionsBac.add(btnRMenuDeBac);
		
		btnInstruDeBac = new JButton("Instructions", iconeInstru);
		btnInstruDeBac.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "instructions");
			}
		});
		btnInstruDeBac.setBounds(274, 217, 140, 23);
		panelChoixActionsBac.add(btnInstruDeBac);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 24, 267, 150);
		panelChoixActionsBac.add(scrollPane);


		listeDesSauvegarde = new JList<>(modele);
		listeDesSauvegarde.addListSelectionListener(new ListSelectionListener() {
			//Estelle
			public void valueChanged(ListSelectionEvent e) {
				int indexSelectionne = listeDesSauvegarde.getSelectedIndex();
				if (indexSelectionne >= 0) {
					nomFichier = modele.elementAt(indexSelectionne);	
				}
				active();
			}
		});
		scrollPane.setViewportView(listeDesSauvegarde);

		lblPasMusiqueBac = new JLabel("");
		lblPasMusiqueBac.setVisible(false);
		lblPasMusiqueBac.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPasMusiqueBac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasMusiqueBac.setBounds(10, 11, 45, 38);
		lblPasMusiqueBac.setIcon(iconePasMusique);
		panelChoixActionsBac.add(lblPasMusiqueBac);
		
		btnMusiqueBac = new JButton();
		btnMusiqueBac.setOpaque(false);
		btnMusiqueBac.setBounds(10, 11, 45, 38);
		btnMusiqueBac.setIcon(changerIcone("son.png", btnMusiqueBac));
		btnMusiqueBac.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				Musique.setMusique();
				setCroixVisible();
			}
		});		
		panelChoixActionsBac.add(btnMusiqueBac);

		lblFondBac = new JLabel("New label");
		lblFondBac.setBounds(0, 0, 424, 251);
		lblFondBac.setIcon(changerIcone("fondPrincipal.jpg", lblFondBac));
		panelChoixActionsBac.add(lblFondBac);
		nomsSauvegarde = GestionnaireDeFichiers.getNomSauvegarde();
		for (int i = 0; i < nomsSauvegarde.size(); i++) {
			modele.addElement(nomsSauvegarde.get(i));
		}

		panelInstructions = new JPanel();
		contentPane.add(panelInstructions, "instructions");
		panelInstructions.setLayout(new CardLayout(0, 0));

		panelChoixInstructions = new JPanel();
		panelInstructions.add(panelChoixInstructions, "choixInstructions");
		panelChoixInstructions.setLayout(null);

		lblTitreInstru = new JLabel("Physico-Ballon");
		lblTitreInstru.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreInstru.setFont(new Font("Chaparral Pro Light", Font.BOLD, 35));
		lblTitreInstru.setBounds(76, 11, 271, 64);
		panelChoixInstructions.add(lblTitreInstru);

		//Jason
		btnInstruNiveaux = new JButton("Jeux");
		btnInstruNiveaux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aide(3);
			}
		});
		btnInstruNiveaux.setBounds(1, 113, 140, 25);
		panelChoixInstructions.add(btnInstruNiveaux);

		//Jason
		btnInstruBac = new JButton("Bac \u00E0 sable");
		btnInstruBac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aide(2);
			}
		});
		btnInstruBac.setBounds(142, 113, 140, 25);
		panelChoixInstructions.add(btnInstruBac);

		//Jason
		btnInstruScientifique = new JButton("Menu Principal");
		btnInstruScientifique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aide(1);
			}
		});
		btnInstruScientifique.setBounds(283, 113, 140, 25);
		panelChoixInstructions.add(btnInstruScientifique);

		btnRMenuDeInstru = new JButton("Menu principal", iconeMenu);
		btnRMenuDeInstru = new JButton("Menu principal");
		btnRMenuDeInstru.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout)(contentPane.getLayout());
				c.show(contentPane, "menu");
			}
		});
		btnRMenuDeInstru.setBounds(10, 215, 140, 25);
		panelChoixInstructions.add(btnRMenuDeInstru);
		
		lblPasMusiqueInstru = new JLabel("");
		lblPasMusiqueInstru.setVisible(false);
		lblPasMusiqueInstru.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPasMusiqueInstru.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasMusiqueInstru.setBounds(10, 11, 45, 38);
		lblPasMusiqueInstru.setIcon(iconePasMusique);
		panelChoixInstructions.add(lblPasMusiqueInstru);
		
		btnMusiqueInstru = new JButton();
		btnMusiqueInstru.setOpaque(false);
		btnMusiqueInstru.setBounds(10, 11, 45, 38);
		btnMusiqueInstru.setIcon(changerIcone("son.png", btnMusiqueInstru));
		btnMusiqueInstru.addActionListener(new ActionListener() {
			//Estelle
			public void actionPerformed(ActionEvent e) {
				Musique.setMusique();
				setCroixVisible();
			}
		});		
		panelChoixInstructions.add(btnMusiqueInstru);

		lblFondInstru = new JLabel("New label");
		lblFondInstru.setBounds(0, 0, 424, 251);
		lblFondInstru.setIcon(changerIcone("fondPrincipal.jpg", lblFondInstru));
		panelChoixInstructions.add(lblFondInstru);

	}

	//Estelle
	/**
	 * Il permet de récupérer un niveau que l'utilisateur veut éditer
	 * @param nomSelectionne le nom du niveau
	 */
	public void editerFichierSelectionne(String nomSelectionne)  {
		if (GestionnaireDeFichiers.fichierExiste(nomSelectionne)) {
			GestionnaireDeFichiers.lireContenuBac(nomSelectionne, ouvrir);
			ouvrir.setVisible(true);
			dispose();
		} 
	}

	//Jason
	/**
	 * Il permet d'obtenir le menu d'instructions
	 * @param aide la page d'aide que l'utilisateur souhaite avoir selon le nombre qu'il donne
	 * <br> 1- Il montre le menu aide concernant le menu principal
	 * <br> 2- Il montre le menu aide concernant le bac à sable 
	 * <br> 3- Il montre le menu aide concernant le jeu et les niveaux
	 */
	public void aide(int aide) {
		if (this.aide.isVisible()) {
			this.aide.dispose();
		}
		this.aide = new FenetreAideGuideUtilisation(aide);
		this.aide.setVisible(true);

	}

	//Estelle
	/**
	 * Il permet d'ouvrir le niveau que l'utilisateur a choisi de jouer
	 * @param niveau le niveau selectionné
	 */
	public void ouvrirNiveau(int niveau) {
		GestionnaireDeFichiers.lireNiveau(niveau, ouvrir);
		jouerNiveau();
		dispose(); 
	}

	//Estelle
	/**
	 * Il permet d'ouvrir et d'afficher la classe Jeux
	 */
	public void jouerNiveau() {
		Jeux jeux = new Jeux(this);
		jeux.setVisible(true);
		if (modeTricheClique == true) {
			jeux.modeTriche(true);
		}
		dispose();
	}


	//Estelle
	/**
	 * Il permet de créer une icône qui illustrera l'entièreté d'une composante
	 * @param nomImage le nom de l'image pour créer l'icône
	 * @param composante la composante dans laquelle se trouvera l'image
	 * @return l'icône de l'image choisie
	 */
	public ImageIcon changerIcone(String nomImage, Component composante) {		
		Image image1 = null, image2 = null;
		ImageIcon nouvelleIcone = null;
		java.net.URL imageNom = getClass().getClassLoader().getResource(nomImage);
		try {
			image1 = ImageIO.read(imageNom);
			image2 = image1.getScaledInstance(composante.getWidth(), composante.getHeight(), Image.SCALE_SMOOTH);
			nouvelleIcone = new ImageIcon(image2);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur pendant la lecture du fichier d'image");
		}
		return nouvelleIcone;	
	}
	
	//Estelle
	/**
	 * Il permet de créer une icône de la taille que l'on souhaite
	 * @param nomIcone le nom de l'image pour créer l'icône
	 * @param taille la largeur et la hauteur de l'icône
	 * @return l'icône de l'image choisie
	 */
	public ImageIcon creerIcone(String nomIcone, int taille) {
		Image image1 = null, image2 = null;
		ImageIcon nouvelleIcone = null;
		java.net.URL imageNom = getClass().getClassLoader().getResource(nomIcone);
		try {
			image1 = ImageIO.read(imageNom);
			image2 = image1.getScaledInstance(taille, taille, Image.SCALE_SMOOTH);
			nouvelleIcone = new ImageIcon(image2);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur pendant la lecture du fichier d'image");
		} 
		return nouvelleIcone;
	}
	
	//Estelle
	/**
	 * Fait apparaître ou disparaître la croix rouge qui indique la présence ou non des sons
	 */
	public void setCroixVisible() {
		if (Musique.getMusique() == true) {
			lblPasMusique.setVisible(false);
			lblPasMusiqueJouer.setVisible(false);
			lblPasMusiqueBac.setVisible(false);
			lblPasMusiqueInstru.setVisible(false);
		} else {
			lblPasMusique.setVisible(true);
			lblPasMusiqueJouer.setVisible(true);
			lblPasMusiqueBac.setVisible(true);
			lblPasMusiqueInstru.setVisible(true);
		}
	}

	//Par james
	/**
	 * Permet d'activer ou non le bouton Supprimer et le bouton Éditer s'il y a un fichier sélectionné
	 */
	public void active() {
		if(nomFichier != null) {
			active = true;
		}else {
			active = false;
		}
		btnSupprimerNiveau.setEnabled(active);
		btnEditerNiveau.setEnabled(active);


	}
}
