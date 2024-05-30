package essaiFormeCollision;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * C'est la classe qui permet d'afficher nos tests
 *@author Jason
 */
public class ApplicationCollisionEssai extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCommencer;
	private ZoneCollisionEssai zoneCollisionEssai;


	//Jason
	/**
	 * Permet de lancer l'application d'essai
	 * @param args pour commencer l'application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationCollisionEssai frame = new ApplicationCollisionEssai();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Jason
	/**
	 * Fenêtre de l'application
	 */
	public ApplicationCollisionEssai() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnCommencer = new JButton("Commencer");
		btnCommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoneCollisionEssai.start();
			}
		});
		btnCommencer.setBounds(323, 344, 147, 28);
		contentPane.add(btnCommencer);

		zoneCollisionEssai = new ZoneCollisionEssai();
		zoneCollisionEssai.setBounds(0, 0, 714, 333);
		contentPane.add(zoneCollisionEssai);

		JCheckBox chckbxVitesse = new JCheckBox("flèche vitesse");
		chckbxVitesse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneCollisionEssai.voirVitesse(chckbxVitesse.isSelected());
			}
		});
		chckbxVitesse.setBounds(35, 349, 91, 23);
		contentPane.add(chckbxVitesse);
	}

}

