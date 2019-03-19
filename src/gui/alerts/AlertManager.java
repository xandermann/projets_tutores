package gui.alerts;

/**
 * [ GESTIONNAIRE DES TEMPS D'ALERTES ET DES ALERTES. ] 
 * 
 * Auteurs du projet : 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et CHEVRIER Jean-christophe.
 */
public class AlertManager implements AlertConstants {
	/**
	 * [ TEMPS. ]
	 */
	public static int SHORT = 2000, LONG = 3000;
	
	/**
	 * [ INTERRUPTEUR. ]
	 */
	public static boolean INTERRUPTOR = true;
	
	/**
	 * [ ACTIVER. ]
	 */
	public static void activate() {
		INTERRUPTOR = true;
	}
	
	/**
	 * [ DESACTIVATER. ]
	 */
	public static void disactivate() {
		INTERRUPTOR = false;
	}
}
