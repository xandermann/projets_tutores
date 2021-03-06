package gui.answers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import exceptions.UnfindableResourceException;
import gui.alerts.Alert;
import resources.ResourceConstants;
import resources.ResourcesManager;
import resources.TimeTools;

/**
 * [ CLASSE VUE DES REPONSES. ]
 * 
 * Cette classe affiche dans un GridLayout les fichiers de sauvegarde des flux.
 * 
 * Les fichiers affiches dans l'ordre du plus recent au plus ancien.
 * 
 * Ceci est une classe concrete "sterile", c-a-d qu'aucune classe ne peut en
 * heriter ( d'ou la presence du final devant class).
 * 
 * Auteurs du projet :
 * 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et
 *         CHEVRIER Jean-christophe.
 */
public final class AnswersView extends JPanel {
	private static final long serialVersionUID = -7932719513497907116L;
	/**
	 * LISTE DES REPONSES DANS UN JPANEL GRIDLAYOUT.
	 */
	private JPanel main;
	/**
	 * LISTE DES REPONSES.
	 */
	private List<File> files;

	/**
	 * [ CONSTRUCTEUR VIDE. ]
	 * 
	 * @throws UnfindableResourceException
	 */
	public AnswersView() throws UnfindableResourceException {
		/**
		 * HISTORIQUE.
		 */
		super(new BorderLayout());
		setSize(new Dimension(400, 250));
		main = new JPanel();

		displayAnswers();

		/**
		 * RAFRAICHIR.
		 */
		JPanel head = new JPanel(new BorderLayout());
		head.setSize(new Dimension(400, 50));
		head.add(new JLabel("HISTORIQUE DES RAPPORTS", JLabel.CENTER), BorderLayout.CENTER);
		JButton refresh = null;
		try {
			refresh = new JButton(new ImageIcon(ImageIO.read(ResourceConstants.REFRESH)));
		} catch (IOException ioe) {
		}
		refresh.setPreferredSize(new Dimension(30, 30));
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int countOldFiles = files == null ? 0 : files.size();
				try {
					displayAnswers();
				} catch (UnfindableResourceException e1) {
				}
				Alert.shortAlert(Alert.INFO, files.size() - countOldFiles == 0 ? "Aucun nouveau rapport trouve."
						: files.size() - countOldFiles + " nouveau(x) rapport(s).");
			}
		});
		head.add(refresh, BorderLayout.EAST);

		add(head, BorderLayout.NORTH);
		add(new JScrollPane(main, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
				BorderLayout.CENTER);
	}

	/**
	 * [ CONSTRUIRE L'HISTORIQUE EN LISTANT LES REPONSES. ]
	 * 
	 * @throws UnfindableResourceException Exceptions sur les fichiers introuvables.
	 */
	private void displayAnswers() throws UnfindableResourceException {
		main.removeAll();

		/**
		 * REUNION DES FICHIERS.
		 */
		ResourcesManager.secureAnswers();
		ResourcesManager.secureErr();
		ResourcesManager.secureOut();
		File[] filesErr = ResourceConstants.STDERR_ANSWERS.listFiles();
		File[] filesOut = ResourceConstants.STDOUT_ANSWERS.listFiles();
		files = new ArrayList<File>();
		for (File f : filesErr)
			files.add(f);
		for (File f : filesOut)
			files.add(f);

		/**
		 * TRI DU PLUS RECENT AU PLUS ANCIEN.
		 */
		files.sort(new Comparator<File>() {
			public int compare(File f1, File f2) {
				String timeMillis1 = f1.getName().split("_")[f1.getName().split("_").length - 1];
				timeMillis1 = timeMillis1.substring(0, timeMillis1.indexOf("."));
				String timeMillis2 = f2.getName().split("_")[f2.getName().split("_").length - 1];
				timeMillis2 = timeMillis2.substring(0, timeMillis2.indexOf("."));
				return (int) (Long.parseLong(timeMillis2) - Long.parseLong(timeMillis1));
			}
		});

		/**
		 * HISTORIQUE.
		 */
		main.setLayout(new GridLayout(files.size(), 1));
		main.setSize(new Dimension(400, 200));
		for (File f : files) {
			String n = f.getName();
			JButton j = new JButton("Rapport du " + n.substring(n.indexOf("_") + 1, n.lastIndexOf("_")) + " a "
					+ TimeTools.millisToTime(Long.parseLong(n.substring(n.lastIndexOf("_") + 1, n.indexOf(".")))));
			j.setPreferredSize(new Dimension(400, 40));
			j.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AnswerWindow(f);
				}
			});
			main.add(j);
		}

		main.repaint();
		main.revalidate();
	}
}
