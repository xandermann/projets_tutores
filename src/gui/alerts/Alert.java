package gui.alerts;
import java.awt.event.*;
import javax.swing.*;
/**
 * [ ALERTES ( = NOTIFICATIONS TEMPORAIRES ). ]
 * 
 * Auteurs du projet : 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et CHEVRIER Jean-christophe.
 */
public final class Alert extends AlertManager implements AlertTypeConstants, AlertMessageConstants{
	/**
	 * [ VUE DE L'ALERTE. ]
	 */
	private AlertWindow view;
	
	
	/**
	 * [ CONSTRUIT UNE ALERTE. ]
	 * 
	 * @param title				Titre / resume de la notification.
	 * @param content			Details de la notification.
	 */
	public Alert(String title, String content) {
		if(INTERRUPTOR) {
			if(!title.equals(INFO) && !title.equals(SUCCESS) && !title.equals(FAILURE))
				throw new IllegalArgumentException("Type de notification inconnu !");
			
			view = new AlertWindow(title, content);
			
			view.addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {
					view.dispose();
				}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			
			view.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_DELETE || e.getKeyCode()==KeyEvent.VK_ESCAPE) 
						view.dispose();
				}
			});
		}
	}
	
	
	/**
	 * [ ALERTER L'UTILISATEUR PENDANT UN TEMPS FIXE. ]
	 * 
	 * @param timeOut		Temps d'affichage de la notification.
	 */
	public void alert(int timeOut) {
		if(INTERRUPTOR) {
			if(timeOut<0) throw new IllegalArgumentException("timeOut negatif !");
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	view.setVisible(true);
			    	new Thread() {
			    		 public void run() {
			 				try {
			 					while(!view.isVisible()) Thread.yield();
								Thread.sleep(timeOut);
							} catch (InterruptedException ie) {
								JOptionPane.showMessageDialog(null, ie.getMessage());
							}
			 				view.dispose();
			    		 }
			    	}.start();
			    }
			});
		}
	}
	
	
	/**
	 * [ ALERTER L'UTILISATEUR PENDANT UN TEMPS. ]
	 * 
	 * @param title				Titre / resume de la notification.
	 * @param content			Details de la notification.
	 * 
	 * @see #alert
	 */
	public static void alert(String title, String content, int timeOut) {
		new Alert(title, content).alert(timeOut);
	}
	
	
	/**
	 * [ ALERTER L'UTILISATEUR PENDANT "SHORT" SECONDES. ]
	 * 
	 * @see #alert
	 * @see #SORT
	 */
	public void shortAlert() {
		alert(SHORT);
	}
	
	
	/**
	 * [ ALERTER L'UTILISATEUR PENDANT "SHORT" SECONDES. ]
	 * 
	 * @param title				Titre / resume de la notification.
	 * @param content			Details de la notification.
	 * 
	 * @see #shortAlert
	 */
	public static void shortAlert(String title, String content) {
		new Alert(title, content).shortAlert();
	}
	
	
	/**
	 * [ ALERTER L'UTILISATEUR PENDANT "LONG" SECONDES. ]
	 * 
	 * @see #alert
	 * @see #LONG
	 */
	public void longAlert() {
		alert(LONG);
	}
	
	
	/**
	 * [ ALERTER L'UTILISATEUR PENDANT "LONG" SECONDES. ]
	 * 
	 * @param title				Titre / resume de la notification.
	 * @param content			Details de la notification.
	 * 
	 * @see #longAlert
	 */
	public static void longAlert(String title, String content) {
		new Alert(title, content).longAlert();
	}
}