package fr.erias.candidateterms.language;

import org.annolab.tt4j.TreeTaggerWrapper;

import fr.erias.candidateterms.stemmers.Stemmer;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;


/**
 * This class is used to store a set of model files for a specific languages
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */
public class Language {

	/**
	 * A model to segment sentence for {@link SentenceDetectorME}
	 */
	private String sentenceSegmentationModelFile ;
	
	/**
	 * A model file for {@link TokenizerME}
	 */
	private String tokenizeModelFile ;
	
	/**
	 * A treetagger model to do POS for {@linkplain TreeTaggerWrapper#setModel(org.annolab.tt4j.Model)}
	 */
	private String treeTaggerModelFile ; 
	
	/**
	 * A stemmer
	 */
	private Stemmer stemmer ;
	
	/**
	 * A stopword file for analyzer
	 */
	private String stopWordsFile; 
	
	
	/********************************************** Setters *****************************************/
	
	/**
	 * Add a sentenceSegmentationModelFile
     * @param sentenceSegmentationModelFile A model to segment sentence for {@link SentenceDetectorME}. See {@link SentenceModel}
	 */
	public void setSentenceSegmentationModelFile(String sentenceSegmentationModelFile) {
		this.sentenceSegmentationModelFile = sentenceSegmentationModelFile;
	}
	
	/**
	 * Add a tokenizeModelFile
	 * @param tokenizeModelFile A model file for {@link TokenizerModel}
	 */
	public void setTokenizeModelFile(String tokenizeModelFile) {
		this.tokenizeModelFile = tokenizeModelFile;
	}
	
	/**
	 * Add a treeTaggerModelFile
	 * @param treeTaggerModelFile A treetagger model to do POS for {@linkplain TreeTaggerWrapper#setModel(org.annolab.tt4j.Model)}
	 */
	public void setTreeTaggerModelFile(String treeTaggerModelFile) {
		this.treeTaggerModelFile = treeTaggerModelFile;
	}
	
	/**
	 * Add a stemmer
	 * @param stemmer A stemmer to perform stemming
	 */
	public void setStemmer(Stemmer stemmer) {
		this.stemmer = stemmer;
	}
	
	/**
	 * Add a stopwords file
	 * @param stopWordsFile A stopword 
	 */
	public void setStopWordsFile(String stopWordsFile) {
		this.stopWordsFile = stopWordsFile ;
	}
	
	
	/********************************************************* Getters **************************************/
	
	
	/**
	 * 
	 * @return sentenceSegmentationModelFile A model to segment sentence for {@link SentenceDetectorME}. See {@link SentenceModel}
	 */
	
	public String getSentenceSegmentationModelFile(){
		return(this.sentenceSegmentationModelFile);
	}
	
	/**
	 * 
	 * @return tokenizeModelFile A model file for {@link TokenizerModel}
	 */
	public String getTokenizeModelFile(){
		return(this.tokenizeModelFile);
	}
	
	/**
	 * 
	 * @return treeTaggerModelFile A treetagger model to do POS for {@linkplain TreeTaggerWrapper#setModel(org.annolab.tt4j.Model)}
	 */
	public String getTreeTaggerModelFile(){
		return(this.treeTaggerModelFile);
	}
	
	/**
	 * 
	 * @return A stemmer to perform stemming
	 */
	public Stemmer getStemmer(){
		return(this.stemmer);
	}
	
	/**
	 * 
	 * @return A string fileName of a stopwords file
	 */
	public String getStopWordsFile(){
		return(stopWordsFile);
	}
}
