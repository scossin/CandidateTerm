package fr.erias.candidateterms.taggers;

import java.io.IOException;

import org.annolab.tt4j.TreeTaggerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.exceptions.MyExceptions;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;

/**
 * A class that implements {@link TokPOSlemma} : a tokenizer, a lemmatizer and a POS analyzer
 * Lemmatizer and POS analyzer is done by TreeTagger
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 */
public class TokPOSlemmaTT implements TokPOSlemma {

	final static Logger logger = LoggerFactory.getLogger(TreeTaggerPOS.class);
	
	/**
	 * A tokenizer
	 */
	private TokenizerOpenNLP tokenizer;
	 
	/**
	 * TreeTagger POS analyzer
	 */
	private TreeTaggerPOS treeTaggerPOS ;
	
	/**
	 * TreeTagger needs it : how token are handled by TT
	 */
	private TokenHandlerTreeTagger tokenHandlerTreeTagger;
	
	/**
	 * Constructor
	 * @param treeTaggerPOS A TreeTagger POS analyzer
	 * @param tokenizer A tokenizer
	 */
	public TokPOSlemmaTT(TreeTaggerPOS treeTaggerPOS, TokenizerOpenNLP tokenizer) {
		this.treeTaggerPOS = treeTaggerPOS;
		this.tokenizer = tokenizer;
	}

	@Override
	public String[] getTokens() {
		return(tokenHandlerTreeTagger.getTokens());
	}

	@Override
	public String[] getPosArray() {
		// TODO Auto-generated method stub
		return(tokenHandlerTreeTagger.getPosArray());
	}

	@Override
	public String[] getLemmasArray() {
		// TODO Auto-generated method stub
		return(tokenHandlerTreeTagger.getLemmasArray());
	}

	@Override
	public void processSentence(String sentence) throws ProcessSentenceException {
		tokenizer.tokenize(sentence);
		String[] tokensArray = tokenizer.getTokens();
		// initialize a new tokenHandler for treeTaggerPOS
		TokenHandlerTreeTagger tokenHandlerTreeTagger = new TokenHandlerTreeTagger(tokensArray);
		try {
			this.tokenHandlerTreeTagger = (TokenHandlerTreeTagger) treeTaggerPOS.POSanalyse(tokenHandlerTreeTagger, tokensArray);
		} catch (IOException | TreeTaggerException e) {
			MyExceptions.logException(logger, e);
			throw new ProcessSentenceException(logger, sentence);
		}
	}

	@Override
	public int[][] getTokenStartEndInSentence() throws UnfoundTokenInSentence {
		return tokenizer.getTokenStartEndInSentence();
	}

	@Override
	public void closeResources() {
		treeTaggerPOS.closeTreeTaggerWrapper();
	}

	@Override
	public void tokenize(String sentence) {
		this.tokenizer.tokenize(sentence);
	}
}
