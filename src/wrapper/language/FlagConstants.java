package wrapper.language;

/**
 * [ CLASSE POUR ACCEDER AUX FLAGS ACCEPTES DANS LES REQUETES FFMPEG. ]
 * 
 * Ceci est une classe concrete "sterile", c-a-d qu'aucune classe ne peut 
 * en heriter ( d'ou la presence du final devant class).
 * 
 * Auteurs du projet : 
 * @author HUBLAU Alexandre, PAMIERI Adrien, DA SILVA CARMO Alexandre, et CHEVRIER Jean-christophe.
 */
public interface FlagConstants {
	public final static String FLAG_SUPPORTED_CODECS = "-codecs";
	
	public final static String FLAG_QUALITY = "-crf";
	
	public final static String FLAG_VIDEO_CODEC = "-vcodec";
	public final static String FLAG_VIDEO_BITRATE = "-b:v";
	public final static String FLAG_FRAMERATE = "-r";
	public final static String FLAG_RESOLUTION = "-s";
	
	public final static String FLAG_AUDIO_CODEC = "-acodec";
	public final static String FLAG_AUDIO_BITRATE = "-b:a";
	public final static String FLAG_SAMPLING_RATE =  "-ar";
	public final static String FLAG_NUMBER_AUDIO_CHANNELS = "-ac";
	
	public final static String[] FLAG_RESIZE = {"-vf", "scale="};
	public final static String[] FLAG_CROP = {"-filter:v", "\"crop=", "\""};
	public final static String[] FLAG_PERIOD = {"-ss", "-t"};
	public final static String[] FLAG_ROTATE = {"-vf", "transpose=1"};
	public final static String[] FLAG_BLUR = {"-filter_complex", 
											  "\"[0:v]crop=", ",boxblur=10[fg];[0:v][fg]overlay=", "[v]\"",
										      "-map", "\"[v]\"", "-map", "0:a", 
										      "-c:v", "libx264", "-c:a", "copy", "-movflags", "+faststart"};
	
	public final static String[] FLAG_ADD = {"-f", "concat", "-i", "-c:copy"};
}
