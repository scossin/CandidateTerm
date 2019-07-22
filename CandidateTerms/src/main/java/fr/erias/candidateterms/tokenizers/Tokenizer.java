package fr.erias.candidateterms.tokenizers;

import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;

/**
 * A tokenizer must return an array of tokens and the position (start, end) of each token in the sentence
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 *
 */
public interface Tokenizer {

	/**
	 * A two-dimensional array.
	 * The first column stores the token number (length of first column must be equal to getTokens().length)
	 * The second column is an array of 2 storing the start of the token in the sentence and the end 
	 * @throws UnfoundTokenInSentence If after tokenization the tokens can't be localized in the sentence
	 * @return A two-dimensional array.
	 */
	public int[][] getTokenStartEndInSentence() throws UnfoundTokenInSentence ;

	/**
	 * 
	 * @return The array of tokens
	 */
	public String[] getTokens();

	/**
	 * A method to tokenize a String
	 * @param sentence A string to tokenize
	 */
	public void tokenize(String sentence);

}
