package jeux;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aapplication.Musique;
import aide.FenetreAideGuideUtilisation;
import custom.FormeChoisi;
import evenement.BallonListener;
/**
 * Cette classe permet de changer des valeurs et de jouer
 * @author Jason
 * @author James
 * @author Estelle
 */
public class Jeux extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ZoneAnimation zoneAnimation;
	private JPanel propriete;
	private JPanel variableBalle;
	private JPanel bouton;
	private JSlider sliderAimant;
	private JSlider sliderMasse;
	private JSlider sliderGravite;
	private JSpinner spinnerMasse;
	private JSpinner spinnerGravite;
	private JSpinner spinnerAimant;
	private JLabel lblMasse;
	private JLabel lblGravite;
	private JLabel lblAimant;
	private JLabel lblPasSimulation;
	private JPanel Nbreballe;
	private JComboBox<Object> comboBoxPasSimulation;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblVx;
	private JLabel lblVy;
	private JLabel lblAx;
	private JLabel lblAy;
	private JLabel lbldegre;
	private JLabel lblXSymbol;
	private JLabel lblYSymbol;
	private JLabel lblVxSymbol;
	private JLabel lblVySymbol;
	private JLabel lblAxSymbole;
	private JLabel lblAySymbol;
	private JLabel lbldegreSymbole;
	private JLabel lblXValeur;
	private JLabel lblYValeur;
	private JLabel lblVxValeur;
	private JLabel lblVyValeur;
	private JLabel lblAxValeur;
	private JLabel lblAyValeur;
	private JLabel lblDegreValeur;
	private JButton btnDemarrer;
	private JButton btnProchainImage;
	private JButton btnReDemarrer;
	private JButton btnReinitialisation;
	private JLabel lblNbreBalle;
	private JLabel lblFois;
	private FormeChoisi formeChoisi;
	private JLabel lblNomJeux;
	private JCheckBox chckbxFlecheVitesse;
	private boolean estCommence = false;

	private int MASSE_INITIALE = 50;
	private int MASSE_MINIMUM  = 25;
	private int MASSE_MAXIMUM  = 200;

	private double GRAVITE_INITIALE = -9.8;
	private double GRAVITE_MINIMUM  = -20;
	private double GRAVITE_MAXIMUM  = 20;

	private double AIMANT_INITIALE = 500;
	private double AIMANT_MINIMUM  = -1000;
	private double AIMANT_MAXIMUM  =  1000;

	String[] pasSimulation = new String[] {"Normal", "Vite!", "Long..."};
	FenetreAideGuideUtilisation aideNiveau = new FenetreAideGuideUtilisation(3);
	private JButton btnAide;
	private JButton btnFerrmer;
	private JButton btnMusique;
	private final String MUSIQUE = "son.png";
	private JLabel lblPasMusique;

	//Jason et James
	/**
	 * Création du frame qui permet de faire le mode Jouer
	 * @param appRetour l'application dans lequelle le frame retournera lorsque cette classe est fermée
	 * 
	 */
	public Jeux(JFrame appRetour) {
		//Jason
		setFont(new Font("Tahoma", Font.PLAIN, 18));
		setTitle("Jeux");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lblNbreBalle.setText(zoneAnimation.getNbreVie()+"");
				lblNomJeux.setText(zoneAnimation.getNom());
				zoneAnimation.setMasse(MASSE_INITIALE);
				zoneAnimation.setGravite(-GRAVITE_INITIALE);
				zoneAnimation.setAimant(AIMANT_INITIALE);
				zoneAnimation.setPasSimulation(comboBoxPasSimulation.getSelectedIndex());
				//Pour la musique
				if (Musique.getMusique()) {
					lblPasMusique.setVisible(false);
				}
			}
		});
		setBounds(100, 100, 1553, 781);
		contentPane = new JPanel();
		contentPane.setForeground(Color.ORANGE);
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		zoneAnimation = new ZoneAnimation();
		zoneAnimation.addBallonListener(new BallonListener() {
			public void accelerationXY(double aX, double aY) {
				lblAxValeur.setText(aX + "");
				lblAyValeur.setText(aY + "");
			}
			public void degre(double degre) {
				lblDegreValeur.setText("" + degre);
			}
			public void positionXY(double x, double y) {
				lblXValeur.setText("" + x);
				lblYValeur.setText("" + y);
			}
			public void vitesseXY(double vX, double vY) {
				lblVxValeur.setText("" + vX);
				lblVyValeur.setText("" + vY);
			}
			@Override
			public void estCommence(boolean jeuCommence) {
				estCommence = jeuCommence;
				// Il permet de savoir si nous devons laisser l'utilisateur modifier les paramètres ou non
				if (jeuCommence) {
					//Pour mettre les boutons indisponibles
					sliderAimant.setEnabled(false);
					sliderMasse.setEnabled(false);
					sliderGravite.setEnabled(false);
					spinnerAimant.setEnabled(false);
					spinnerGravite.setEnabled(false);
					spinnerMasse.setEnabled(false);
					btnProchainImage.setEnabled(true);
				}else {
					//Pour mettre les boutons disponibles
					if (zoneAnimation.aAimant()) {
						sliderAimant.setEnabled(true);
						spinnerAimant.setEnabled(true);
					}
					sliderMasse.setEnabled(true);
					sliderGravite.setEnabled(true);
					spinnerGravite.setEnabled(true);
					spinnerMasse.setEnabled(true);
					btnProchainImage.setEnabled(false);
				}
			}
			@Override
			public void getVie(int vie) {
				lblNbreBalle.setText(vie+"");				
			}
		});
		zoneAnimation.setBounds(218, 0, 1129, 741);
		contentPane.add(zoneAnimation);



		getWidth();		
		propriete = new JPanel();
		propriete.setBackground(new Color(30, 144, 255));
		propriete.setBounds(0, 0, 217, 664);
		contentPane.add(propriete);
		propriete.setLayout(null);


		sliderAimant = new JSlider();
		sliderAimant.setForeground(Color.BLACK);
		sliderAimant.setBackground(new Color(30, 144, 255));
		sliderAimant.setBounds(8, 458, 144, 51);
		propriete.add(sliderAimant);
		sliderAimant.setMinimum((int) AIMANT_MINIMUM);
		sliderAimant.setMaximum((int)AIMANT_MAXIMUM);
		sliderAimant.setValue((int)AIMANT_INITIALE);
		sliderAimant.setMajorTickSpacing((int) (AIMANT_MAXIMUM / 2));
		sliderAimant.setMinorTickSpacing((int) (AIMANT_MAXIMUM / 4));
		sliderAimant.setPaintTicks(true);
		sliderAimant.setPaintLabels(true);
		sliderAimant.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spinnerAimant.setValue((double)sliderAimant.getValue());
				zoneAnimation.setAimant((double)sliderAimant.getValue());
			}
		});


		sliderMasse = new JSlider();
		sliderMasse.setForeground(Color.BLACK);
		sliderMasse.setBackground(new Color(30, 144, 255));
		sliderMasse.setBounds(8, 173, 144, 51);
		propriete.add(sliderMasse);
		sliderMasse.setMinimum(MASSE_MINIMUM);
		sliderMasse.setMaximum(MASSE_MAXIMUM);
		sliderMasse.setMajorTickSpacing(MASSE_MAXIMUM/4);
		sliderMasse.setMinorTickSpacing(MASSE_MAXIMUM/2);
		sliderMasse.setPaintTicks(true);
		sliderMasse.setPaintLabels(true);
		sliderMasse.setValue(MASSE_INITIALE);
		sliderMasse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spinnerMasse.setValue((double)sliderMasse.getValue());
				zoneAnimation.setMasse((double)sliderMasse.getValue());
			}
		});

		sliderGravite = new JSlider();
		sliderGravite.setForeground(Color.BLACK);
		sliderGravite.setBackground(new Color(30, 144, 255));

		sliderGravite.setBounds(8, 315, 144, 51);
		propriete.add(sliderGravite);
		sliderGravite.setMinimum((int) GRAVITE_MINIMUM);
		sliderGravite.setMaximum((int)GRAVITE_MAXIMUM);
		sliderGravite.setMajorTickSpacing((int)GRAVITE_MAXIMUM/4);
		sliderGravite.setMinorTickSpacing((int)GRAVITE_MINIMUM/2);
		sliderGravite.setPaintTicks(true);
		sliderGravite.setPaintLabels(true);
		sliderGravite.setValue((int)GRAVITE_INITIALE);
		sliderGravite.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spinnerGravite.setValue((double)sliderGravite.getValue());
				zoneAnimation.setGravite(-(double)sliderGravite.getValue());
			}
		});

		spinnerMasse = new JSpinner();
		spinnerMasse.setForeground(Color.GRAY);
		spinnerMasse.setBackground(Color.GRAY);

		spinnerMasse.setBounds(155, 185, 62, 26);
		spinnerMasse.setModel(new SpinnerNumberModel(MASSE_INITIALE, MASSE_MINIMUM, MASSE_MAXIMUM, 1.0));
		propriete.add(spinnerMasse);
		spinnerMasse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Number valeur = (double) spinnerMasse.getValue();
				sliderMasse.setValue( valeur.intValue());
				zoneAnimation.setMasse(valeur.doubleValue());
			}
		});

		spinnerGravite = new JSpinner();
		spinnerGravite.setBackground(Color.GRAY);

		spinnerGravite.setBounds(155, 327, 62, 26);
		spinnerGravite.setModel(new SpinnerNumberModel(GRAVITE_INITIALE, GRAVITE_MINIMUM, GRAVITE_MAXIMUM, 0.1));
		propriete.add(spinnerGravite);
		spinnerGravite.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Number valeur = (double) spinnerGravite.getValue();
				sliderGravite.setValue( valeur.intValue());	
				zoneAnimation.setGravite(-valeur.doubleValue());
			}
		});

		spinnerAimant = new JSpinner();
		spinnerAimant.setBackground(Color.GRAY);
		spinnerAimant.setBounds(155, 470, 62, 26);
		spinnerAimant.setModel(new SpinnerNumberModel(AIMANT_INITIALE, AIMANT_MINIMUM, AIMANT_MAXIMUM, 1.0));
		propriete.add(spinnerAimant);
		spinnerAimant.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Number valeur = (double) spinnerAimant.getValue();
				sliderAimant.setValue( valeur.intValue());	
				zoneAnimation.setAimant(valeur.doubleValue());
			}
		});

		lblMasse = new JLabel("Masse de la balle (Kg)");
		lblMasse.setForeground(Color.BLACK);
		lblMasse.setBackground(Color.GRAY);
		lblMasse.setHorizontalAlignment(SwingConstants.CENTER);
		lblMasse.setBounds(8, 137, 201, 25);
		propriete.add(lblMasse);
		lblMasse.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblGravite = new JLabel("Gravit\u00E9 du syst\u00E8me (m/s\u00B2)");
		lblGravite.setForeground(Color.BLACK);
		lblGravite.setBackground(Color.GRAY);
		lblGravite.setHorizontalAlignment(SwingConstants.CENTER);
		lblGravite.setBounds(0, 279, 217, 25);
		propriete.add(lblGravite);
		lblGravite.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblAimant = new JLabel("Force des aimants (C)");
		lblAimant.setForeground(Color.BLACK);
		lblAimant.setBackground(Color.GRAY);
		lblAimant.setHorizontalAlignment(SwingConstants.CENTER);
		lblAimant.setBounds(5, 421, 207, 26);
		propriete.add(lblAimant);
		lblAimant.setFont(new Font("Tahoma", Font.PLAIN, 18));


		lblPasSimulation = new JLabel("Pas de simulation");
		lblPasSimulation.setForeground(Color.BLACK);
		lblPasSimulation.setBackground(Color.GRAY);
		lblPasSimulation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPasSimulation.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasSimulation.setBounds(36, 552, 144, 26);
		propriete.add(lblPasSimulation);

		Nbreballe = new JPanel();
		Nbreballe.setBackground(new Color(30, 144, 255));
		Nbreballe.setBounds(0, 662, 217, 79);
		contentPane.add(Nbreballe);
		Nbreballe.setLayout(null);

		formeChoisi = new FormeChoisi();
		formeChoisi.setBounds(7, 13, 62, 53);
		Nbreballe.add(formeChoisi);

		lblFois = new JLabel("X");
		lblFois.setForeground(Color.BLACK);
		lblFois.setBackground(new Color(30, 144, 255));
		lblFois.setBounds(76, 13, 62, 53);
		Nbreballe.add(lblFois);
		lblFois.setHorizontalAlignment(SwingConstants.CENTER);
		lblFois.setFont(new Font("Tahoma", Font.PLAIN, 35));

		lblNbreBalle = new JLabel("");
		lblNbreBalle.setForeground(Color.BLACK);
		lblNbreBalle.setBackground(new Color(30, 144, 255));
		lblNbreBalle.setBounds(145, 13, 62, 53);
		lblNbreBalle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNbreBalle.setHorizontalAlignment(SwingConstants.CENTER);
		Nbreballe.add(lblNbreBalle);

		comboBoxPasSimulation = new JComboBox<Object>(pasSimulation);
		comboBoxPasSimulation.setForeground(Color.BLACK);
		comboBoxPasSimulation.setBackground(Color.WHITE);
		comboBoxPasSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoneAnimation.setPasSimulation(comboBoxPasSimulation.getSelectedIndex());
			}
		});
		comboBoxPasSimulation.setBounds(70, 589, 76, 22);
		comboBoxPasSimulation.setSelectedIndex(0);
		propriete.add(comboBoxPasSimulation);

		lblNomJeux = new JLabel("");
		lblNomJeux.setBackground(new Color(100, 149, 237));
		lblNomJeux.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomJeux.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNomJeux.setBounds(0, 81, 217, 45);
		propriete.add(lblNomJeux);
		variableBalle = new JPanel();
		variableBalle.setBackground(new Color(30, 144, 255));
		variableBalle.setBounds(1345, 0, 196, 599);
		contentPane.add(variableBalle);
		variableBalle.setLayout(null);

		lblX = new JLabel("x:");
		lblX.setForeground(Color.BLACK);
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblX.setBounds(3, 47, 48, 21);
		variableBalle.add(lblX);

		lblY = new JLabel("y:");
		lblY.setForeground(Color.BLACK);
		lblY.setHorizontalAlignment(SwingConstants.CENTER);
		lblY.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblY.setBounds(3, 115, 48, 21);
		variableBalle.add(lblY);

		lblVx = new JLabel("Vx:");
		lblVx.setForeground(Color.BLACK);
		lblVx.setHorizontalAlignment(SwingConstants.CENTER);
		lblVx.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVx.setBounds(3, 183, 48, 21);
		variableBalle.add(lblVx);

		lblVy = new JLabel("Vy:");
		lblVy.setForeground(Color.BLACK);
		lblVy.setHorizontalAlignment(SwingConstants.CENTER);
		lblVy.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVy.setBounds(3, 251, 48, 21);
		variableBalle.add(lblVy);

		lblAx = new JLabel("Ax:");
		lblAx.setForeground(Color.BLACK);
		lblAx.setHorizontalAlignment(SwingConstants.CENTER);
		lblAx.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAx.setBounds(3, 319, 48, 21);
		variableBalle.add(lblAx);

		lblAy = new JLabel("Ay:");
		lblAy.setForeground(Color.BLACK);
		lblAy.setHorizontalAlignment(SwingConstants.CENTER);
		lblAy.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAy.setBounds(3, 387, 48, 21);
		variableBalle.add(lblAy);

		lbldegre = new JLabel("angle:");
		lbldegre.setForeground(Color.BLACK);
		lbldegre.setHorizontalAlignment(SwingConstants.CENTER);
		lbldegre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbldegre.setBounds(-1, 455, 68, 21);
		variableBalle.add(lbldegre);

		lblXSymbol = new JLabel("m");
		lblXSymbol.setForeground(Color.BLACK);
		lblXSymbol.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblXSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblXSymbol.setBounds(143, 50, 49, 14);
		variableBalle.add(lblXSymbol);

		lblYSymbol = new JLabel("m");
		lblYSymbol.setForeground(Color.BLACK);
		lblYSymbol.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblYSymbol.setBounds(143, 118, 49, 14);
		variableBalle.add(lblYSymbol);

		lblVxSymbol = new JLabel("m/s");
		lblVxSymbol.setForeground(Color.BLACK);
		lblVxSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblVxSymbol.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVxSymbol.setBounds(143, 186, 49, 14);
		variableBalle.add(lblVxSymbol);

		lblVySymbol = new JLabel("m/s");
		lblVySymbol.setForeground(Color.BLACK);
		lblVySymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblVySymbol.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVySymbol.setBounds(143, 254, 49, 14);
		variableBalle.add(lblVySymbol);

		lblAxSymbole = new JLabel("m/s" + "\u00B2");
		lblAxSymbole.setForeground(Color.BLACK);
		lblAxSymbole.setHorizontalAlignment(SwingConstants.CENTER);
		lblAxSymbole.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAxSymbole.setBounds(143, 322, 49, 14);
		variableBalle.add(lblAxSymbole);

		lblAySymbol = new JLabel("m/s" + "\u00B2");
		lblAySymbol.setForeground(Color.BLACK);
		lblAySymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblAySymbol.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAySymbol.setBounds(143, 390, 49, 14);
		variableBalle.add(lblAySymbol);

		lbldegreSymbole = new JLabel("\u2070");
		lbldegreSymbole.setForeground(Color.BLACK);
		lbldegreSymbole.setHorizontalAlignment(SwingConstants.CENTER);
		lbldegreSymbole.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbldegreSymbole.setBounds(151, 458, 49, 14);
		variableBalle.add(lbldegreSymbole);

		lblXValeur = new JLabel("");
		lblXValeur.setForeground(Color.BLACK);
		lblXValeur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblXValeur.setHorizontalAlignment(SwingConstants.CENTER);
		lblXValeur.setBounds(54, 47, 86, 21);
		variableBalle.add(lblXValeur);

		lblYValeur = new JLabel("");
		lblYValeur.setForeground(Color.BLACK);
		lblYValeur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYValeur.setHorizontalAlignment(SwingConstants.CENTER);
		lblYValeur.setBounds(54, 115, 86, 21);
		variableBalle.add(lblYValeur);

		lblVxValeur = new JLabel("");
		lblVxValeur.setForeground(Color.BLACK);
		lblVxValeur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVxValeur.setHorizontalAlignment(SwingConstants.CENTER);
		lblVxValeur.setBounds(54, 183, 86, 21);
		variableBalle.add(lblVxValeur);

		lblVyValeur = new JLabel("");
		lblVyValeur.setForeground(Color.BLACK);
		lblVyValeur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVyValeur.setHorizontalAlignment(SwingConstants.CENTER);
		lblVyValeur.setBounds(54, 251, 86, 21);
		variableBalle.add(lblVyValeur);

		lblAxValeur = new JLabel("");
		lblAxValeur.setForeground(Color.BLACK);
		lblAxValeur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAxValeur.setHorizontalAlignment(SwingConstants.CENTER);
		lblAxValeur.setBounds(54, 319, 86, 21);
		variableBalle.add(lblAxValeur);

		lblAyValeur = new JLabel("");
		lblAyValeur.setForeground(Color.BLACK);
		lblAyValeur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAyValeur.setHorizontalAlignment(SwingConstants.CENTER);
		lblAyValeur.setBounds(54, 387, 86, 21);
		variableBalle.add(lblAyValeur);

		lblDegreValeur = new JLabel("");
		lblDegreValeur.setForeground(Color.BLACK);
		lblDegreValeur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDegreValeur.setHorizontalAlignment(SwingConstants.CENTER);
		lblDegreValeur.setBounds(66, 455, 86, 21);
		variableBalle.add(lblDegreValeur);

		chckbxFlecheVitesse = new JCheckBox("Fl\u00E8che de vitesse");
		chckbxFlecheVitesse.setForeground(Color.BLACK);
		chckbxFlecheVitesse.setBackground(new Color(30, 144, 255));
		chckbxFlecheVitesse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneAnimation.flecheVitesse(chckbxFlecheVitesse.isSelected());
			}
		});
		chckbxFlecheVitesse.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxFlecheVitesse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxFlecheVitesse.setBounds(3, 523, 189, 21);
		variableBalle.add(chckbxFlecheVitesse);

		bouton = new JPanel();
		bouton.setForeground(Color.WHITE);
		bouton.setBackground(new Color(30, 144, 255));
		bouton.setBounds(1355, 610, 182, 111);
		contentPane.add(bouton);

		btnDemarrer = new JButton("D\u00E9marrer");
		btnDemarrer.setBounds(26, 3, 51, 51);
		btnDemarrer.setEnabled(false);
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneAnimation.start();
				btnDemarrer.setEnabled(false);
			}
		});
		bouton.setLayout(null);
		bouton.add(btnDemarrer);

		btnProchainImage = new JButton("New button");
		btnProchainImage.setBounds(26, 57, 51, 51);
		btnProchainImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (zoneAnimation.prochainImage()) {
					btnDemarrer.setEnabled(true);
				}
			}
		});
		bouton.add(btnProchainImage);

		btnReDemarrer = new JButton("New button");
		btnReDemarrer.setBounds(103, 3, 51, 51);
		btnReDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (estCommence) {
					zoneAnimation.recommencer();
					sliderMasse.setEnabled(true);
					sliderGravite.setEnabled(true);
					spinnerGravite.setEnabled(true);
					spinnerMasse.setEnabled(true);
					btnDemarrer.setEnabled(false);
					if (zoneAnimation.aAimant()) {
						sliderAimant.setEnabled(true);
						spinnerAimant.setEnabled(true);
					}	
				}

			}
		});
		bouton.add(btnReDemarrer);

		btnReinitialisation = new JButton("New button");
		btnReinitialisation.setBounds(103, 57, 51, 51);
		btnReinitialisation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pour réinitialiser les valeurs
				zoneAnimation.reInitialiser();
				zoneAnimation.setAimant(AIMANT_INITIALE);
				zoneAnimation.setGravite(-GRAVITE_INITIALE);
				zoneAnimation.setMasse(MASSE_INITIALE);
				zoneAnimation.setPasSimulation(0);

				//Pour mettre les boutons disponibles
				if (zoneAnimation.aAimant()) {
					sliderAimant.setEnabled(true);
					spinnerAimant.setEnabled(true);
				}				
				sliderMasse.setEnabled(true);
				sliderGravite.setEnabled(true);
				spinnerGravite.setEnabled(true);
				spinnerMasse.setEnabled(true);
				btnDemarrer.setEnabled(false);
				btnProchainImage.setEnabled(false);

				// Pour initialiser les boutons et le pas d'animation
				sliderAimant.setValue((int) AIMANT_INITIALE);
				sliderMasse.setValue(MASSE_INITIALE);
				sliderGravite.setValue((int) GRAVITE_INITIALE);
				spinnerAimant.setValue(AIMANT_INITIALE);
				spinnerGravite.setValue(GRAVITE_INITIALE);
				spinnerMasse.setValue(MASSE_INITIALE*1.0);
				comboBoxPasSimulation.setSelectedIndex(0);
				chckbxFlecheVitesse.setSelected(false);

				// pour réinitialiser les valeurs des événements personalisés
				lblXValeur.setText("");
				lblYValeur.setText("");
				lblAxValeur.setText("");
				lblAyValeur.setText("");
				lblVxValeur.setText("");
				lblVyValeur.setText("");
				lblDegreValeur.setText("");

				zoneAnimation.repaint();

			}
		});
		bouton.add(btnReinitialisation);

		associerBoutonAvecImage(btnDemarrer, "Animer.png");
		associerBoutonAvecImage(btnProchainImage, "PauseProchaineImage.png");
		associerBoutonAvecImage(btnReDemarrer,"Recommencer.png" );
		associerBoutonAvecImage(btnReinitialisation, "4Defaut.png");
		//James
		btnFerrmer = new JButton("Quitter");
		btnFerrmer.setBounds(110, 11, 100, 23);
		propriete.add(btnFerrmer);
		
		//Jason
		btnAide = new JButton("Aide");
		btnAide.setBounds(5, 11, 100, 23);
		propriete.add(btnAide);

		lblPasMusique = new JLabel("");
		lblPasMusique.setBounds(86, 45, 45, 38);
		lblPasMusique.setIcon(creerIcone("pasMusique.png", lblPasMusique.getWidth()));
		propriete.add(lblPasMusique);


		btnMusique = new JButton("New button");
		btnMusique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Musique();
			}
		});
		btnMusique.setBounds(86, 45, 45, 38);
		propriete.add(btnMusique);
		btnAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aideNiveau.setVisible(true);
			}
		});
		//James
		btnFerrmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (confirmation()) {
					zoneAnimation.reInitialiser();
					appRetour.setVisible(true);				
					dispose();
				}
			}
		});

		if (!zoneAnimation.aAimant()) {
			sliderAimant.setEnabled(false);
			spinnerAimant.setEnabled(false);
		}
		formeChoisi.setCouleurFondEcran(propriete.getBackground());
		formeChoisi.estBallon(true);
		associerBoutonAvecImage(btnMusique, MUSIQUE );

	}


	//Jason
	/**
	 * Il permet de gérer la musique
	 */
	private void Musique() {
		if (Musique.getMusique() == true) {
			lblPasMusique.setVisible(true);
			Musique.setMusique();

		} else {
			lblPasMusique.setVisible(false);
			Musique.setMusique();
		}
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



	//James
	/**
	 * Permet de savoir si l'utilisateur veut aller dans la JFrame précédent ou pas
	 * @return si l'utilisateur veut aller dans la JFrame précédent ou pas
	 */
	private boolean confirmation() {
		String[] options = {"Oui, je suis certain.e","Non, je n'ai pas encore gagné"};
		int rep = JOptionPane.showOptionDialog(null, "Voulez-vous réellement quitter le jeu? (Vous êtes peut-être proche de la victoire)", "Confirmation", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, null); 
		if (rep == JOptionPane.YES_OPTION) {
			return true;
		}else {
			return false;
		}

	}

	//Jason
	/**
	 * Il permet d'associer un bouton à une image
	 * @param leBouton le bouton qui sera illustré par l'image
	 * @param fichierImage le nom de l'image dans le répertoire ressources
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

		// au cas où le fond de l’image est transparent
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

	//Par Jason
	/**
	 * Il permet de savoir si l'utilisateur veut tricher ou non
	 * @param tricher si l'utilisateur veut tricher ou non
	 */
	public void modeTriche(boolean tricher) {
		zoneAnimation.modeTriche(tricher);
	}
}
