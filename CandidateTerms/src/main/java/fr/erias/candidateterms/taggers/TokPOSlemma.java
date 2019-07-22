package fr.erias.candidateterms.taggers;

import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.candidateterms.tokenizers.Tokenizer;


/**
 * Object of this interface must implements three functionnalities
 * <ul>
 * <li> A tokenizer
 * <li> A POS analyzer
 * <li> A lemmatizer
 * </ul>
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 *
 */
public interface TokPOSlemma extends Tokenizer {
	
	/**
	 * A two-dimensional array.
	 * The first column stores the token number
	 * The second column is an array of 2 storing the start of the token in the sentence and the end 
	 * @return A two-dimensional array.
	 */
	public int[][] getTokenStartEndInSentence() throws UnfoundTokenInSentence;
	
	/**
	 * 
	 * @return The array of tokens
	 */
	public String[] getTokens();

	/**
	 * 
	 * @return the part-of-speach array
	 */
	public String[] getPosArray();

	/**
	 * 
	 * @return the lemmas array
	 */
	public String[] getLemmasArray();
	
	/**
	 * Tag a sentence
	 * @param sentence A sentence to process
	 * @throws ProcessSentenceException if an error occur during the processing of the sentence
	 */
	public void processSentence(String sentence) throws ProcessSentenceException;
	
	/**
	 * Close the resources of the tokenizer, POS tagger and lemmatizer...
	 */
	public void closeResources();
	
}
