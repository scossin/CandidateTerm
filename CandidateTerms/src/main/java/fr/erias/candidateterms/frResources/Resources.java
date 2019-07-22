package fr.erias.candidateterms.frResources;


/**
 * Resources for the French. File and folders are located in src/main/resources
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */
public class Resources {

	/**
	 * folderName containing NLP models for sentence detection, tokenization...
	 */
	public final static String openNLPfolder = "openNLP/";
	
	/**
	 * Folder containing stopwords file
	 */
	public final static String stopWordsFolder = "stopwords/";
	
	/**
	 * A french Model to segment sentences (expected in the openNLP folder)
	 */
	public final static String sentenceSegmentationModelFileFR = Resources.openNLPfolder + "fr-sent.bin";
	
	/**
	 * A french model to tokenize a sentence
	 */
	public final static String tokenizeModelFileFR = Resources.openNLPfolder + "fr-token.bin";
	
	/**
	 * A file containing french stopwords
	 */
	public final static String frenchStopWordsFile = Resources.stopWordsFolder + "stopWordsFrench.txt";
	
	/**
	 * To load resources with classLoader's context
	 */
	public final static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	
	/**
	 * Path to the treetagger executable and french model file for the test
	 */
	public static String treeTaggerPath;
	public static String treeTaggerFrenchModelFile;
	
	
	static {
		String treetagger_home = System.getenv("TREETAGGER_HOME");
		if (treetagger_home == null) {
			new NullPointerException("Can't find TREETAGGER_HOME environment variable");	
		}
		treeTaggerPath = treetagger_home;
		treeTaggerFrenchModelFile = treeTaggerPath + "/lib/french.par";
	}
}
