package credit;

import java.awt.Image;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * Permet d'ouvrir la fenêtre de crédits
 * @author Estelle
 */
public class CreditFenetre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String texteSource = "";
	private String source;
	static CreditAnimation creditAnimation = new CreditAnimation();

	/**
	 *Constructeur de la fenêtre
	 */
	public CreditFenetre() {
		setTitle("Cr\u00E9dits");
		setBounds(600, 200, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		creditAnimation.commencerAnimation();
		creditAnimation.setBounds(5, 5, 774, 180);		
		contentPane.add(creditAnimation);

		JTextArea txtSources = new JTextArea("Bonjour");
		txtSources.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtSources);
		scrollPane.setBounds(5, 214, 774, 542);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		FichierSource.lireSources();
		for (int i = 0; i < FichierSource.listeSources.size()-1; i++) {
			source = FichierSource.listeSources.get(i);
			if (i == 0) {
				texteSource = texteSource+source;
			} else {
				texteSource = texteSource + "\n" + source;	
			}
		}
		txtSources.setText(texteSource);
		contentPane.add(scrollPane);



		JButton btnPauseLecture = new JButton("");
		btnPauseLecture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creditAnimation.commencerAnimation();
			}
		});

		btnPauseLecture.setOpaque(false);
		btnPauseLecture.setContentAreaFilled(false);
		btnPauseLecture.setBorderPainted(false);
		btnPauseLecture.setBounds(725, 184, 54, 31);
		Image image1 = null, image2 = null;
		java.net.URL imageNom = getClass().getClassLoader().getResource("PauseProchaineImage.png");
		try {
			image1 = ImageIO.read(imageNom);
			image2 = image1.getScaledInstance(btnPauseLecture.getWidth(), btnPauseLecture.getHeight(), Image.SCALE_SMOOTH);
			btnPauseLecture.setIcon(new ImageIcon(image2));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur pendant la lecture du fichier d'image");
		}
		contentPane.add(btnPauseLecture);

		JLabel lblTitreSources = new JLabel("Source de nos images et sons");
		lblTitreSources.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTitreSources.setBounds(5, 189, 291, 26);
		contentPane.add(lblTitreSources);

	}

}
