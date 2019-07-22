package fr.erias.candidateterms.taggers;

import org.annolab.tt4j.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;

/**
 * {@link TreeTaggerPOS} requires a TokenHandler
 * This TreeTagger token handler stores tokens and POS in an array
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 */
public class TokenHandlerTreeTagger implements TokenHandler<String>{

	final static Logger logger = LoggerFactory.getLogger(TokenHandlerTreeTagger.class);
	
	/**
	 * An array of token generated with {@link TreeTaggerPOS}
	 */
	private String[] tokensArray;
	
	/**
	 * An array of part-of-speach generated with {@link TreeTaggerPOS} ; same size as tokensArray
	 */
	private String[] posArray;
	
	/**
	 * An array of lemmas generated with {@link TreeTaggerPOS} ; same size as tokensArray
	 */
	private String[] lemmasArray;
	
	/**
	 * Each entry of posArray and lemmasArray is set by iteration. Used by token function only
	 */
	private int iter = 0 ;

	
	/**
	 * A {@link TokenHandler} storing tokens, POS and lemmas in an array
	 * @param tokens An array of tokens from a tokenizer (ex : {@link TokenizerOpenNLP})
	 */
	public TokenHandlerTreeTagger(String[] tokens){
		this.tokensArray = tokens ;
		this.posArray = new String[tokens.length];
		this.lemmasArray = new String[tokens.length];
	}
	
	/**
	 * The implemented method of {@link TokenHandler} interface
	 */
	public void token(String token, String pos, String lemma) {
		this.posArray[iter] = pos;
		this.lemmasArray[iter] = lemma;
		this.iter = iter + 1 ;
	}
	
	
	/******************************************* Getters ***************************************/
	/**
	 * 
	 * @return The array of tokens
	 */
	public String[] getTokens() {
		return tokensArray;
	}

	/**
	 * 
	 * @return the part-of-speach array
	 */
	public String[] getPosArray() {
		return posArray;
	}

	/**
	 * 
	 * @return the lemmas array
	 */
	public String[] getLemmasArray() {
		return lemmasArray;
	}
	
	
	/**
	 * For debbuging only
	 */
	public void seePos(){
		for (String onePos : posArray){
			System.out.println(onePos);
		}
	}
}
