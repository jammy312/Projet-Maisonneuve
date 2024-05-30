package credit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

/**Cette classe permet d'animer la fenêtre de crédits
 * @author Estelle
 */
public class CreditAnimation extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private static String texteDefile = "Jeu : Physico-Ballon" +	
								" ~ Collaborateurs : Voahary Estelle Rajerison, Jason Brutus, James Brutus" +
								" ~ Professeur : Caroline Houle" +
								" ~ Session : Hiver 2020"; 
	private final int DEPLACEMENT = 15;
	private static int x, y , debutString;

	public static boolean  anime = false;
	
	/**
	 * Dessine le texte
	 * @param g pour faire le graphique
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		y = super.getHeight()/2;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		Font font = new Font("Chapparal Pro Light", Font.ITALIC, 30);
		g2d.setFont(font);
		g2d.drawString(texteDefile, x, y);
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		debutString = (int)(font.getStringBounds(texteDefile, frc).getWidth());
	}

	/**
	 * Permet de faire défiler le texte
	 */
	public void run() {
		while (anime == true) { 
		x-= DEPLACEMENT;
		if (x < -debutString) {
			x = getWidth();
		} 
		repaint();
		try {
			Thread.sleep(175); 
		}		
		catch (InterruptedException e) {
			System.out.println("Processus interrompu!"); 
		}
		
		}
	}
	
	/**
	 * Permet à l'animation de commencer ou de se mettre en pause, selon son état précédent
	 */
	public void commencerAnimation() {
		if (anime == false) {
			anime = true;
		Thread processusAnim = new Thread(this);
		processusAnim.start();
		} else {
			mettrePause();
		}
	}//fin methode
	
	/**
	 * Met en pause l'animation
	 */
	public void mettrePause() {
		anime = false;
		run();
	}
	

}
