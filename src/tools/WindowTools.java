package tools;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class WindowTools {
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	public static void showLogo(JFrame f) {
		try {
			f.setIconImage(ImageIO.read(Resources.ACAJA_LOGO));
		} catch (IOException ioe) {}
	}
	
	
	//=======================================================================================================================
	//=======================================================================================================================

	
	public static void executeWindow(JFrame window) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		        window.setVisible(true);
		    }
		});
	}
	
	
	//=======================================================================================================================
	//=======================================================================================================================
}
