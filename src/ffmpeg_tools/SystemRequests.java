package ffmpeg_tools;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import files.SelectableFile;

/**
 * 
 * 
 * Auteurs du projet : 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et CHEVRIER Jean-christophe.
 */
public final class SystemRequests extends FFmpegRuntime{
	//=======================================================================================================================
	//=======================================================================================================================
	
	

	/**
	 * TODO Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 */
	public final String PATH_TEMPORARY_FILES = "";

	/**
	 * TODO Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 */
	public final int[] LIBRARY_THUMBAIL_RESOLUTION = new int[] {};

	/**
	 * TODO Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 */
	public final int[] VIDEO_TIMELINE_THUMBAIL_RESOLUTION = new int[] {};

	/**
	 * TODO Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 */
	public final int[] SOUND_TIMELINE_THUMBAIL_RESOLUTION = new int[] {};

	/**
	 * TODO  Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 */
	public final int[] VIDEO_RESOLUTION = new int[] {};

	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	/**
	 * TODO  Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 * 
	 * @param file
	 * @param l
	 * @return
	 */
	public File createVideoFrame(File file, long l) {
		return file;
	}

	/**
	 * TODO  Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 * 
	 * @param file
	 * @param tab
	 * @return
	 */
	public File createThumbail(File file, int[] tab) {
		return file;
	}

	/**
	 * TODO  Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 * 
	 * @param file
	 * @param l
	 * @return
	 */
	private File extractImage(File file, long l) {
		return file;
	}

	/**
	 * TODO  Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 * 
	 * @param file
	 * @param tab
	 * @return
	 */
	private int[] resizeImage(File file, int[] tab) {
		return tab;
	}
	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
	

	
	/**
	 * TODO  Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 * 
	 * @param file
	 * @param tab
	 * @return
	 */
	private int[] findGoodResolution(File file, int[] tab) {
		return tab;
	}

	/**
	 * TODO  Concerne la fenetre de traitement et donc ne concerne pas l'iteration 1. 
	 * 
	 * @param file
	 * @return
	 */
	public long getDuration(File file) {
		return 0;
	}
	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	/**
	 * Cette methode retourne les parametres du fichier. 
	 * 
	 * Les valeurs des parametres du fichier sont recuperes par l'intrermediaire de requetes FFmpeg. 
	 * 
	 * Un fichier video posssede des parametres video ET audio alors qu'un fichier audio 
	 * ne possede que des parametres audio. 
	 * 
	 * Parametres video : le codec video, le bitrate video, la resolution, les nombre d'images par 
	 * secondes (FPS). 
	 * 
	 * Paramteres audio : le codec audio, le bitrate audio, le volume en sortie, taux d'echantillonnage,
	 * nombre de canaux audio en sortie. 
	 * 
	 * @param file		Le fichier dont on souhaite connaitre les parametres. 
	 * @param fileType  L'indice correspondant au type du fichier audio ou video. 
	 * 
	 * @return Le tableau des parametres du fichier. 
	 */
	public Object[] getSettings(File file, int fileType) {
		Object[] fileSettings = new Object[10];		
		if(fileType==SelectableFile.FILE_TYPE_AUDIO || fileType==SelectableFile.FILE_TYPE_AUDIO){
			return fileSettings;
		}
		return null;
	}
	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
	
	
	
	public static void main(String[] args) throws IOException {
		Process p = FFmpegRuntime.execute("--help");
		
		BufferedReader bf_reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		InputStream out = new BufferedInputStream(p.getInputStream());  
		 
		String s = null; 
		while ((s = bf_reader.readLine()) != null) { 
			byte[] b = new byte[1024];  
			int n = out.read(b); 
			for(int i=0; i<n; i++)  System.out.print((char)b[i]); 
		} 
	}
	
	
	
	//=======================================================================================================================
	//=======================================================================================================================
}
