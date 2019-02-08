package gui;

import java.awt.Color;
import java.awt.Graphics;

import style.StylizedJPanel;
import style.StyleConfigurator;

/**
 * Classe qui genere le panel de chargement de l'application
 *
 */
public class LoadingWindowPanel extends StylizedJPanel {

	/**
	 * Pourcentage de la barre de chargement
	 */
	private int percentCurrentProgress;

	/**
	 * Affiche les elements a l'interieur de la fenetre
	 */
	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(20, 20, 200, 20);

		g.setColor(StyleConfigurator.BACKGROUND_COLOR_SECONDARY);
		g.fillRect(20, 20, (this.percentCurrentProgress * 200) / 100, 20);

	}

	/**
	 * Met a jour la progression de la barre de chargement contenue dans ce JPanel
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		this.percentCurrentProgress = progress;
		this.repaint();
	}

}
