package gui;

import java.awt.*;
import javax.swing.*;

import gui.style.StyleTheme;
import gui.style.StylizedJFrame;
import messages.NotificationConstants;
/**
 * [ VUE DES NOTIFICATIONS  / FEEDBACK. ]
 * 
 * Auteurs du projet : 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et CHEVRIER Jean-christophe.
 */
public final class NotificationView extends StylizedJFrame implements NotificationConstants{
	
	
	/**
	 * [ CONSTRUIT UNE NOTIFICATION. ]
	 * 
	 * @param title				Titre / resume de la notification.
	 * @param content			Details de la notification.
	 */
	public NotificationView(String title, String content) {
		super(title);
		if(content==null) throw new NullPointerException("Content null !");
		setLayout(new BorderLayout());
		setSize(300, 150);
		setResizable(false);
		setLocationRelativeTo(null);
		
		Color theme = title.equals(FAILURE) ? Color.RED : 
				   	  (title.equals(SUCCESS) ? new Color(0,128,0) : StyleTheme.BACKGROUND_COLOR_SECONDARY);
		
		setContentPane(new JPanel() {
			public void paintComponent(Graphics g) {
				g.setColor(theme);
				g.drawRect(15, 10, 265, 100); 
			}	
		});
		
		JLabel notification = new JLabel("<html>" + 
				"<head>" +
				"<style> body { text-align: center; } </style>" +
				"</head>" +
				"<body>" +
		        "<br><br><br>" + 
				content +
				"</body>" + 
				"</html>", JLabel.CENTER);
		notification.setForeground(theme);
		add(notification, BorderLayout.CENTER);
		
		WindowTools.showLogo(this);
	}
	
	
	/**
	 * [ ALERTER L'UTILISATEUR PENDANT UN TEMPS FIXE. ]
	 * 
	 * @param timeOut		Temps d'affichage de la notification.
	 */
	public void alert(int timeOut) {
		if(timeOut<=0) throw new IllegalArgumentException("timeOut nul ou negatif !");
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	setVisible(true);
		    	new Thread() {
		    		 public void run() {
		 				try {
		 					while(!NotificationView.this.isVisible());
							Thread.sleep(timeOut);
						} catch (InterruptedException ie) {
							JOptionPane.showMessageDialog(null, ie.getMessage());
						}
						dispose();
		    		 }
		    	}.start();
		    }
		});
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
		new NotificationView(title, content).alert(timeOut);
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
		new NotificationView(title, content).shortAlert();
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
		new NotificationView(title, content).longAlert();
	}
}
