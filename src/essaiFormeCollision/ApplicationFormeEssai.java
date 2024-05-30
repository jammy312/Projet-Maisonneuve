package essaiFormeCollision;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
/**
 * Cette classe permet de voir nos essais sur les collisions
 * @author Jason
 *
 */
public class ApplicationFormeEssai extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Permet de d�marrer depuis cette classe la v�rification du dessin
	 * @param args pour d�marrer l'application depuis cette classe
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationFormeEssai frame = new ApplicationFormeEssai();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 *Cr�ation de l'application pour faire les essais de forme
	 */
	public ApplicationFormeEssai() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ZoneFormeEssai zoneEssai = new ZoneFormeEssai();
		zoneEssai.setBounds(0, 0, 434, 261);
		contentPane.add(zoneEssai);
	}
}
