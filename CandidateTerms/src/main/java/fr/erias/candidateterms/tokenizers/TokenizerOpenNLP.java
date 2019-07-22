package fr.erias.candidateterms.tokenizers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * This class is used to tokenize a String (e.g. sentence)
 * A {@link opennlp.tools.tokenize.TokenizerME} for a given language
 * It's used to get an array of tokens
 * There's also a method to get each token position (start,end) in the sentence
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */
public class TokenizerOpenNLP implements Tokenizer {

	final static Logger logger = LoggerFactory.getLogger(TokenizerOpenNLP.class);
	
	/**
	 * The string to tokenize
	 */
	private String sentence = null;
	
	/**
	 * The array of tokens
	 */
	private String[] tokensArray = {};
	
	/**
	 * A {@link TokenizerME} tokenizer
	 */
	private TokenizerME tokenizerME = null;
	
	/**
	 * A two-dimensional array.
	 * The first column stores the token number
	 * The second column is an array of 2 storing the start of the token in the sentence and the end 
	 */
	private int[][] tokenStartEndInSentence ;
	
	/**
	 * Create a new TOkenizerOpenNLP to tokenize sentence with {@link TokenizerME}
	 * @param tokenizeModelFile A file model to create an instance of {@link TokenizerME}
	 * @throws InvalidFormatException If the file model is incorrect
	 * @throws IOException If the file model is not found
	 */
	public TokenizerOpenNLP (File tokenizeModelFile) throws InvalidFormatException, IOException{
	      TokenizerModel tmodel = new TokenizerModel(tokenizeModelFile);
	      tokenizerME = new TokenizerME(tmodel);
	}
	
	/**
	 * Create a new TOkenizerOpenNLP to tokenize sentence with {@link TokenizerME}
	 * @param in An inputstream of a model file
	 * @throws InvalidFormatException If the file model is incorrect
	 * @throws IOException If the file model is not found
	 */
	public TokenizerOpenNLP (InputStream in) throws InvalidFormatException, IOException{
	      TokenizerModel tmodel = new TokenizerModel(in);
	      tokenizerME = new TokenizerME(tmodel);
	      in.close();
	}
	
	/**
	 * Tokenize a string with {@link opennlp.tools.tokenize.TokenizerME}
	 * @param sentence A string to tokenize (e.g. a sentence)
	 */
	public void tokenize(String sentence){
		tokensArray = tokenizerME.tokenize(sentence);
		this.sentence = sentence;
	}
	
	
	/************************************* Getter *******************************/
	
	/**
	 * The array of tokens
	 * @return The array of tokens
	 */
	@Override
	public String[] getTokens(){
		return tokensArray;
	}
	
	/**
	 * For each token, where it starts and where it ends in the sentence
	 * @return An array of 2-dimensions. First dim : the token number. Second dim : an array of 2 integers containing the start position and the end position
	 * @throws UnfoundTokenInSentence If something goes wrong and the token can't be found in the sentence
	 */
	@Override
	public int[][] getTokenStartEndInSentence() throws UnfoundTokenInSentence {
		setTokensStartEndInSentence();
		return(tokenStartEndInSentence);
	}
	
	/**
	 * @return The tokenized sentence
	 */
	public String getSentence(){
		return(this.sentence);
	}
	
	/**
	 * Private function called when getTokenStartEndInSentence is called, calculate the start and end position of each token
	 * @throws UnfoundTokenInSentence If something goes wrong and the token can't be found in the sentence
	 */
	private void setTokensStartEndInSentence() throws UnfoundTokenInSentence{
		int sentenceLength = sentence.length();
		tokenStartEndInSentence = new int[tokensArray.length][2];
		int sentencePosition = 0;
		int tokenStart = 0 ;
		int tokenEnd = 0;
		char[] sentenceCharArray = sentence.toCharArray();
		
		for (int i = 0 ; i<tokensArray.length ; i++){ // for each token
			String token = tokensArray[i]; 
			char[] tokenCharArray = token.toCharArray();
			
			for (int y = 0 ; y<tokenCharArray.length ; y++){ // for each char of the token
				char tokenChar = tokenCharArray[y];
				char sentenceChar = sentenceCharArray[sentencePosition]; // first token char = first sentence char ?
				while (tokenChar != sentenceChar){ // it's not true in case of whites space => go to the next char of the sentence
					sentencePosition ++ ;
					if (sentencePosition > sentenceLength){
						throw new UnfoundTokenInSentence(logger, token, sentence);
					}
					sentenceChar = sentenceCharArray[sentencePosition]; // next sentence char
				}
				if (y == 0){ // First char the token => start position
					tokenStart = sentencePosition ;
				}
				if (y == tokenCharArray.length - 1){ // Last char of the token => end position
					tokenEnd = sentencePosition ;
				}
				sentencePosition = sentencePosition + 1 ; // don't stay at the end of the previous token : move (tokenChar moves to + 1 too with the loop)
			}
			int[] OneTokenStartEnd = {tokenStart,tokenEnd};
			tokenStartEndInSentence[i] = OneTokenStartEnd;
			//System.out.println("token : " + token + " found in the sentence, starting at : " + tokenStart + " and ending : " + tokenEnd);
		}
	}

}
