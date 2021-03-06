package gui.answers;

import exceptions.UnfindableResourceException;
import gui.general.*;

/**
 * [ CLASSE FENETRE DE LA VUE DES REPONSES. ]
 * 
 * Cette classe permet par le biais du constructeur de generer une fenetre
 * contenant la vue des reponses.
 * 
 * Auteurs du projet :
 * 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et
 *         CHEVRIER Jean-christophe.
 */
public final class AnswersWindow extends GeneralWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2972302797790090255L;

	/**
	 * [ CONSTRUCTEUR VIDE. ]
	 * @throws UnfindableResourceException 
	 */
	public AnswersWindow() throws UnfindableResourceException {
		super("Historique des reponses de FFmpeg.", new AnswersView());
	}
}
