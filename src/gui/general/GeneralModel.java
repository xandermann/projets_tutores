package gui.general;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;

import exceptions.IncorrectFileException;
import exceptions.UnfindableResourceException;
import files.files.Modifiable;
import files.files.SelectableFile;
import wrapper.streams.managers.consumers.WatchedConsumer;

/**
 * [ SUPERCLASSE ABSTRAITE MODEL. ]
 * 
 * Cette classe abstraite declare les methodes communes aux 2 modeles :
 * ConversionModel et ProcessingModel.
 * 
 * Auteurs du projet : 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et CHEVRIER Jean-christophe.
 */
public abstract class GeneralModel extends Observable implements Modifiable{
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	/**
	 * [ METHODE POUR "ENVOYER" AUX VUES LES DERNIERS CHANGEMENTS DU MODELE. ]
	 * 
	 * Methodes pour que les vues (Observeurs) soit "up to date" par rapport 
	 * aux derniers changements internes du modele (Observable).  
	 */
	public void sendChanges() {
		setChanged();
		notifyObservers();
	}
	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	/**
	 * [ METHODE POUR PRECISER MANUELLEMENT QUE LA SAUVEGARDDE COMMENCE. ]
	 * 
	 * La sauvegarde correpond a la conversion des fichiers modifies 
	 * (sous-classe ConversionModel) ou a l'export des fichiers traites
	 * (sous-classe ProcessingPanel) selon la selon la sous-classe.
	 * 
	 * Cette methode a pour but d'eviter des imprecisions (du a
	 * un Thread ayant commence en retard ou en avance son travail).
	 * Le booleen WatchedConsumer.hand.took ne doit pas souffrir de ses 
	 * imprecisions, ce pourquoi avec cette methode on peut repreciser 
	 * la valeur du booleen. 
	 */
	public void startSave() {
		WatchedConsumer.hand.take();
	}
	
	
	/**
	 * [ METHODE POUR "SAUVER" LES FICHIERS MODIFIES. ]
	 * 
	 * Methode pour demarrer la conversion des SettingsFile modifies
	 * (sous-classe ConversionModel) ou l'export des ProcessingFile traites 
	 * (sous-classe ProcessingPanel). 
	 * 
	 * Le code de la methode est diffreent selon la sous-classe, ce pourquoi
	 * on declare la methode pour l'instant en abstract.
	 * 
	 * @throws UnfindableResourceException		Exception sur les ressources introuvables. 
	 */
	public abstract void save() throws UnfindableResourceException;

	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	
	public abstract void add(File file)  throws IncorrectFileException, UnfindableResourceException, FileNotFoundException;
	
	
	public abstract void remove(SelectableFile file);
	
	
	public abstract void clear();
	
	
	public abstract SelectableFile getCurrentFile();
	
	
	public abstract void setCurrentFile(SelectableFile file);
	
	
	public abstract List<SelectableFile> getFiles();
	
	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
}
