package gui.processing;

import java.io.File;

import exceptions.IncorrectFileException;
import exceptions.UnfindableResourceException;
import files.enumerations.ProcessingType;
import files.files.ProcessingFile;
import gui.alerts.Alert;
import gui.alerts.AlertWindow;
import gui.general.Context;
import resources.ResourceConstants;
import threads.RuntimeSpaceManager;
import threads.ThreadForSave;
import threads.ThreadForWaitWindow;
import wrapper.streams.managers.consumers.WatchedConsumer;

public class ProcessThreadManager {


	
	public static void treatOneProcess(ProcessingFile f) {
		new Thread() {
			public void run (){
				/**
				 * ATTENDRE QU'ON ME RENDE LA MAIN.
				 */
				while(RuntimeSpaceManager.hand.took()) Thread.yield();

				/**
				 * DEBUT DU TRAITEMENT :
				 * 
				 * Prendre la main sur l'espace d'execution.
				 * Prendre la main sur ffmpeg.
				 */
				RuntimeSpaceManager.hand.take();
				WatchedConsumer.hand.take();
				/**
				 * LANCEMENT DU TRAITEMENT DANS UN AUTRE PROCESSUS.
				 */
				ThreadForSave.saveInNewThread(f);
				/**
				 * LANCEMENT ET GESTION DE LA FENETRE D'ATTENTE DANS UN AUTRE PROCESSUS.
				 */
				ThreadForWaitWindow.waitInNewThread(
						new AlertWindow(
								AlertWindow.INFO,
								"Traitement du fichier "+f.getSourceFileName()+"<br>Veuillez patientez..."),
						f.getSourceFile());
				
			}
		}.start();
	}
	
	public static void treatTwoProcesses(ProcessingFile f1, ProcessingType secondAction) {
		String finalFileName = f1.getDestinationFileName();
		String finalPath = f1.getDestinationPath();
		String temp = ResourceConstants.TEMPORARY_FILES_PATH;
		System.out.println("Nom du fichier final : " + finalFileName);
		//NamesSpaceManager._temporary()
		f1.setDestinationPath(temp);
		String secondProcessing = f1.getValue(secondAction);
		f1.cancel(secondAction);
		treatOneProcess(f1);
		new Thread() {
			public void run (){
				while(RuntimeSpaceManager.hand.took());
				System.out.println("Demarrage du traitement du second fichier");
				try {
					String firstFilePath = f1.getDestinationFileFullName();
					//System.out.println("Recuperation du premier fichier genere dans " + firstFilePath);
					ProcessingFile secondFile = new ProcessingFile(new File(firstFilePath));
					secondFile.setDestinationPath(finalPath);
					//System.out.println("Rajout de l'action retiree precedemment");
					secondFile.modify(secondAction, secondProcessing);
					String outputPath = ((ProcessingModel)Context.$M).getDestinationFolder() ;
					//System.out.println("Chemin du dossier final :" + outputPath);
					secondFile.setDestinationPath(outputPath);
					secondFile.setDestinationName(finalFileName);
					//System.out.println("Nom fichier final  : "+ finalFileName + f1.getSourceFileExtension());
					secondFile.setFileExtension(f1.getSourceFileExtension());
					treatOneProcess(secondFile);
					
				
				} catch (IncorrectFileException | UnfindableResourceException e) {
					Alert.shortAlert(Alert.FAILURE, "Impossible de traiter le fichier");
				}			
			}
		}.start();
	}
	
}
