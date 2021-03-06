package gui.processing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import files.files.ProcessingFile;
import gui.style.StyleTheme;

public class ConcatPanel extends JPanel{
	private static final long serialVersionUID = -5509545756632580353L;
	private ArrayList<ProcessingFile> listOfFile;
	
	
	
	
	public ConcatPanel(ArrayList<ProcessingFile> listOfFile) {
		setBackground(Color.black);
		this.setSize(450,300);
		this.listOfFile = listOfFile;
	}
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!listOfFile.isEmpty()) {
			int a = 0;
			int b = 0;
			for(ProcessingFile f : listOfFile) {
				if(b%3 == 0 && b != 0) {
					a++;
					b = 0;
				}
				try {
					Image pic = ImageIO.read(f.getThumbnail());
					g.drawImage(pic, 150*b,100*a,150,100, this);	
				} catch (IOException e) {}
				b++;
			}
			this.repaint();
		}
	}

}
