package gui.processing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import exceptions.ImportationException;
import exceptions.IncorrectFileException;
import exceptions.UnfindableResourceException;
import files.files.SelectableFile;
import gui.JFileChooserManager;
import gui.WindowTools;
import gui.conversion.ConversionWindow;
import gui.style.StylizedJMenuBar;
import gui.style.StylizedJMenuItem;
import resources.ResourcesManager;
import wrapper.runtime.global.SystemRequests;

public class ProcessingWindow extends JFrame {
	
	private ModelARenomer model;

	
	private ProcessingWindow() {
		
		this.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				ResourcesManager.clearResources();
			}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
		});
		this.model = new ModelARenomer();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.createJMenu();
		this.setBackground(Color.lightGray);
		WindowTools.showLogo(this);
		this.setTitle("Traitement d'une vidéo");
		WindowTools.executeWindow(this);

		this.setSize(1000, 625);
		this.setLocationRelativeTo(null);
		ProcessingPan p = new ProcessingPan(model);

		this.setResizable(false);
		this.add(p);
		
		
	}

	
	private void createJMenu() {
		StylizedJMenuBar jm = new StylizedJMenuBar();
		JMenu fileMenu = new JMenu("Fichier");
		jm.add(fileMenu);
		JMenu libraryMenu = new JMenu("Bibliotheque");
		jm.add(libraryMenu);
		/*
		JMenu videoMenu = new JMenu("Video");
		jm.add(videoMenu);*/
		
		StylizedJMenuItem procToConv = new StylizedJMenuItem("Passer en mode conversion");
		procToConv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ConversionWindow.generateConversionWindow();
				dispose();
			}
		});
		StylizedJMenuItem convertir = new StylizedJMenuItem("Traiter");
		StylizedJMenuItem quit = new StylizedJMenuItem("Quitter");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		StylizedJMenuItem importFile = new StylizedJMenuItem("Importer un fichier");
		importFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					File f = JFileChooserManager.chooseFile();
					SelectableFile sf = new SelectableFile(f);
					model.setCurentFile(sf);
				} catch (IncorrectFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ImportationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		libraryMenu.add(importFile);
		
		fileMenu.add(procToConv);
		fileMenu.add(convertir);
		fileMenu.add(quit);

		this.setJMenuBar(jm);

	}
	
	
	public static void generateProcessingWindow() {
		new ProcessingWindow();
	}

	
	
}
