package gui.conversion.model;

import java.io.*;
import java.util.*;
import javax.swing.*;
import exceptions.IncorrectFileException;
import exceptions.UnfindableResourceException;
import files.*;
import files.enumerations.SettingType;
import files.files.SettingsFile;
import gui.Model;
import gui.conversion.views_controllers.EntryView;
import messages.MessageConstants;
import resources.ResourceConstants;
import resources.ResourcesManager;
import wrapper.runtime.global.UserRequests;


/**
 * [ MODELE POUR LA FENETRE DE CONVERSION. ]
 * 
 * TODO : gerer la presence des fichiers systemes .DS_store, etc dans la methode loadOldImports();
 * TODO : manage multiple filenames
 * 
 * Auteurs du projet : 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et CHEVRIER Jean-christophe.
 */
public final class ConversionModel extends Model {
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	/**
	 * [ ATTRIBUTS D'INSTANCE. ]
	 */
	
	//Fichier courant. 
	private SettingsFile currentFile;
	//Liste des fichiers siur lesquels on travaille. 
	private ArrayList<SettingsFile> files;
	//Tableau des fichiers precedemment importes.
	private FileInformation[] oldImportedFiles;
	//Repertoire de destination des fichiers exportes.
	private File destinationFolder;
	
	
	
	//=======================================================================================================================
	//=======================================================================================================================



	/**
	 * [ CONSTRUCTEUR. ]
	 */
	public ConversionModel() {
		files = new ArrayList<SettingsFile>();
		// maximum 10 last files
		oldImportedFiles = new FileInformation[10];
		destinationFolder = null;
	}

	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	/**
	 * [ SAUVER LES FICHIERS IMPORTEES. ]
	 * 
	 * Methode qui sauvegarde les fichiers recemments ouverts a la fermeture
	 * de la fenetre. 
	 * 
	 * @throws UnfindableResourceException		Exception sur les fichiers introuvables.
	 * @throws  
	 */	
	public void saveImports() throws UnfindableResourceException {
		ResourcesManager.secureConversionImports();
		for(int i = 0 ; i < oldImportedFiles.length ; i ++) {
			try {
			File f = new File(ResourceConstants.CONVERSION_OLD_IMPORTS_PATH + oldImportedFiles[i].getFileName() + ".acaja");
			if(!f.exists()) {
					FileOutputStream fos = new FileOutputStream(f);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(oldImportedFiles[i]);
					oos.close();
					fos.close();	
				}
			} catch (FileNotFoundException fde) {
			} catch (IOException ioe) {	
			} catch(Exception e) {}
		}
	}

	

	
	/**
	 * [ CHARGER LES FICHIERS PRECEDEMMENT IMPORTES. ]
	 * 
	 * Methode qui recupere les fichiers recemments ouverts a l'ouverture. 
	 * 
	 * @throws UnfindableResourceException 		Exception sur les fichiers introuvables.
	 */
	public void loadOldImports() throws UnfindableResourceException {
		ResourcesManager.secureConversionImports();
		File[] files = ResourceConstants.CONVERSION_OLD_IMPORTS.listFiles();	
		// tri des fichiers par date
		if(files != null) {
			Arrays.sort(files, new Comparator<File>(){
				public int compare(File f1, File f2){
					return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
				} 
			});
			for(int i = 0 ; i < files.length ; i++) {
				if(files[i].getName().contains(".acaja")) {
					try {	
						FileInputStream fis = new FileInputStream(files[i]);
						ObjectInputStream ois = new ObjectInputStream(fis);
						if(i <= 10) oldImportedFiles[i] = (FileInformation)ois.readObject(); 
						ois.close();
						fis.close();
					}  catch(SecurityException se) {
						JOptionPane.showMessageDialog(null, 
								"Vous n'avez pas les permissions pour lire le fichier d'import acaja " 
								 + files[i].getName() + " !");
					} catch(IOException ioe) {
						JOptionPane.showMessageDialog(null, 
								"Impossible d'acceder au fichier " + files[i].getName() 
								 + " : erreur d'entree/sortie !");
					} catch(Exception e) {
							JOptionPane.showMessageDialog(null, 
								"Erreur lors de l'acces aux fichiers precedemment importes : " 
							     + e.getMessage() + " !");
					}
				}
			}
		}
	}

	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	

	/**
	 * [ METHODE POUR AFFICHER LES FILES. ]
	 * 
	 * Methode pour recuperer les noms des fichiers sous la forme
	 * d'une DefaultListModel afin de l'afficher dans la liste du ConversionPanel. 
	 * 
	 * @return DefaultListModel<ListEntry>		La liste des noms des fichiers. 
	 */
	public DefaultListModel<EntryView> getFilenames() {
		DefaultListModel<EntryView> filenameList = new DefaultListModel<EntryView>();
		for (SettingsFile f : files) filenameList.addElement(new EntryView(f.getSourceFileName()));
		return filenameList;
	}

	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	/** 
	 * [ SUPPRIMER UN ANCIEN FICHIER D'IMPORTATION ]
	 */
	private boolean removeOldImport(String fileName) {
		try {
			File f = new File(ResourceConstants.CONVERSION_OLD_IMPORTS_PATH + fileName + ".acaja");
			if(!(f.exists())) return true;
			return f.delete();
		} catch(Exception e) {
			return false;
		}
	}
	
	
	/**
	 * [ AJOUTER UN FICHIER. ]
	 * 
	 * Methode qui ajoute un fichier a la bibliotheque
	 * 
	 * @param file SettingsFile 		Le fichier a ajouter a la bibliotheque
	 * 
	 * @throws IncorrectFileException 			Exception levee pour les fichers incorrects. 
	 * 
	 * @throws UnfindableResourceException 		Exception sur les fichiers introuvables.
	 */
	public void add(File file) throws IncorrectFileException, UnfindableResourceException {
		if(file.exists()) {
			files.add(new SettingsFile(file));
			sendChanges();
			FileInformation f = new FileInformation(file.getName(),file.getAbsolutePath());
			for(int i = 0; i < oldImportedFiles.length; i++) {
				if(oldImportedFiles[i] != null) {
					if(oldImportedFiles[i].equals(f)) break;	
				}
			    if(oldImportedFiles[i] == null) {
			        oldImportedFiles[i] = f;
			        saveImports();
			        break;
			    } 
			    if(i==9) {
			    	if(removeOldImport(oldImportedFiles[0].getFileName())) {
			    		for(int j = 1 ; j < oldImportedFiles.length ; j ++) {
			    			oldImportedFiles[j-1] = oldImportedFiles[j];
			    		}
			    		oldImportedFiles[9] = f;
			    		saveImports();	
			    	}
			    }
			}
		}else 
			JOptionPane.showMessageDialog(null, MessageConstants.ERROR_ABSENT_SELECTED_FILE);
	}

	
	/**
	 * [ SUPPRIMER UN FICHIER. ]
	 * 
	 * Methode pour supprimer un fichier de la bibliotheque. 
	 * 
	 * @param file SettingsFile Le fichier a supprimer de la bibliotheque.
	 */
	public void remove(SettingsFile file) {
		if(this.files.contains(file)) {
			files.remove(file);
			currentFile = null;
			sendChanges();
		}else
			JOptionPane.showMessageDialog(null, MessageConstants.ERROR_UNFINDABLE_FILE_TO_REMOVE);
	}
	
	
	/**
	 * [ VIDER LA BIBLIOTHEQUE. ]
	 * 
	 * Methode qui vide la bibliotheque. 
	 */
	public void clear() {
		files.clear();
		sendChanges();
	}

	
	
	//=======================================================================================================================
	//=======================================================================================================================
	

	
	/**
	 * [ LE MODELE CONTIENT-IL DES FICHIERS QUI ON ETE MODIFIES ? ]
	 */
	public boolean isModified() {
		for(SettingsFile sf : files) {
			if(sf.isModified()) return true;
		}
		return false;
	}
	
	
	/**
	 * [ MODIFIER LE FICHIER COURANT. ]
	 * 
	 * Methode qui permet de modifier un parametre du fichier
	 * actuellement selectionne
	 */
	public void modify(SettingType typeSetting, String setting) {
		currentFile.modify(typeSetting, setting);
	}
	
	
	/**
	 * MODIFIER LE FICHIER DE DESTINATION DU FICHIER COURANT. 
	 */
	public void setDestination(String destinationFile) {
		currentFile.setDestinationName(destinationFile);
	}
	
	
	/**
	 * [ CONVERTIR LES FICHIERS MODIFIES. ]
	 * 
	 * Methode pour demarrer la conversion des SettingsFile modifies. 
	 * 
	 * @throws UnfindableResourceException 		Exception sur les ressources introuvables. 
	 */
	public void save() throws UnfindableResourceException {
		for(SettingsFile sf : files) {
			if(sf.isModified()) UserRequests.execute(sf);		
		}
	}

	

	//=======================================================================================================================
	//=======================================================================================================================
	

	
	/**
	 * [ GETTER - OBTENIR LE FICHEIR COURANT. ]
	 * 
	 * Methode qui retourne le fichier actuellement selectionne par l'utilisateur
	 * 
	 * @return currentFile SettingsFile : fichier actuellement selectionne pour la
	 *         modification par l'utilisateur
	 */
	public SettingsFile getCurrentFile() {
		return currentFile;
	}

	
	/**
	 * [ SETTER - CHANGER DE FICHIER COURANT. ]
	 * 
	 * Methode pour modifier le fichier actuellement 
	 * selectionne par l'utilisateur
	 * 
	 * @param currentFile 		Le nom du fichier maintenant selectionne par l'utilisateur
	 */
	public void setCurrentFile(String fileName) {
		for (SettingsFile f : this.getFiles()) {
			if (f.getSourceFileName().equals(fileName)) currentFile = f;
		}
		sendChanges();
	}
	
	
	/**
	 * [ GETTER - OBTENIR LA LISTE DES FICHIERS. ]
	 * 
	 * Methode qui retourne la liste des fichiers actuellement dans la bibliotheque.
	 */
	public ArrayList<SettingsFile> getFiles() {
		return files;
	}
	
	
	/**
	 * [ GETTER - OBTENIR LE LE TABLEAU DES FICHEIRS PRECDEMMENT IMPORTES. ]
	 * 
	 * Methode qui retourne la liste des fichiers precdemment importes. 
	 */
	public FileInformation[] getOldImports() {
		return oldImportedFiles;
	}


	/**
	 * [ SETTER - CHANGER DE REPERTOIRE DE DESTINATION. ]
	 * 
	 * Methode pour modifier le repertoire de destination.
	 * 
	 * @param destinationFolder 	Le nouveau repertoire de destination.
	 */
	public void setDestinationFolder(File destinationFolder) {
		this.destinationFolder = destinationFolder;
		for(SettingsFile f : files) {
			f.setDestinationPath(destinationFolder.getPath());
		}
	}
	
	
	//=======================================================================================================================
	//=======================================================================================================================
}