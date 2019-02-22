package test.gui;

import java.awt.Dimension;
import javax.swing.*;
import gui.style.*;

/**
 * Pour ne pas à avoir à relancer l'application à chaque fois, nous créons ici
 * une "application miniature" pour tester si les composants créées s'affichent
 * correctement ou pas.
 * 
 * @author alex
 *
 */
public class JMenuTest {

	/**
	 * Creation d'une frame qui test le style des composants graphiques de la
	 * fenetre.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creation JMenu
		StylizedJMenuBar barre = new StylizedJMenuBar();

		JMenu profilesMenu = new JMenu("Profils");
		profilesMenu.add(new StylizedJMenuItem("Creer un profil"));
		profilesMenu.add(new StylizedJMenuItem("Creer un profil"));
		profilesMenu.add(new StylizedJMenuItem("Creer un profil"));

		barre.add(profilesMenu);
		frame.setJMenuBar(barre);

		frame.setPreferredSize(new Dimension(800, 200));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
		
		frame.dispose();
	}

}
