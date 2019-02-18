package gui.traitement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.*;

public class PanelImage extends JPanel{
	public PanelImage() {
		this.setPreferredSize(new Dimension(600, 550));
		this.add(new JLabel("Vidéo à éditer"));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ToolTraitement.drawDeco(g, this.size().height, this.size().width);
		ImageIcon m = new ImageIcon(tools.ResourceConstants.ACAJA_LOGO_PATH_Opacity);
        Image monImage = m.getImage();
        g.drawImage(monImage, this.size().height/2-130, this.size().width/2-150,this);
	}
}
