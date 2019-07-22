package fr.erias.candidateterms.candidateTerm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.exceptions.InvalidArraysLength;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.candidateterms.sentence.Sentence;
import fr.erias.candidateterms.taggers.TokPOSlemma;
import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;

/**
 * Extraction of {@link CandidateTermLemmaPOS} from a sentence
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */
public class CandidateTermsLemmaPOSExtraction {
	final static Logger logger = LoggerFactory.getLogger(CandidateTermsLemmaPOSExtraction.class);

	/**
	 * The sentence analyzed
	 */
	private Sentence sentence ;
	
	/**
	 * A set of CandidateTermLemma
	 */
	private HashSet<CandidateTermLemmaPOS> candidateTermsLemmaPOS;

	/**
	 * See {@link TokPOSlemma}
	 */
	private TokPOSlemma tokPOSlemma; 
	
	/**
	 * Constructor
	 * @param tokPOSlemma See {@link TokPOSlemma}
	 */
	public CandidateTermsLemmaPOSExtraction(TokPOSlemma tokPOSlemma) {
		this.tokPOSlemma = tokPOSlemma;
		candidateTermsLemmaPOS = new HashSet<CandidateTermLemmaPOS>();
	}
	
	/**
	 * Extract {@link CandidateTermLemma of a sentence}
	 * @param sentence A sentence where candidate terms will be extracted
	 * @throws ProcessSentenceException If something goes wrong during the extraction
	 * @throws InvalidArraysLength Impossible to occur 
	 * @throws UnfoundTokenInSentence If the tokenization goes wrong
	 */
	public void extractCandidateTerm(Sentence sentence) throws ProcessSentenceException, InvalidArraysLength, UnfoundTokenInSentence {
		// reset hashet
		candidateTermsLemmaPOS.clear();
		
		this.sentence = sentence ;
		
		tokPOSlemma.processSentence(sentence.getContent());
		
		// detect candidate term with POS and Tokens
		String[] posArray = tokPOSlemma.getPosArray();
		String[] tokens = tokPOSlemma.getTokens();
		CandidateTermsDetectionTTPOS candidateTermsDetection = new CandidateTermsDetectionTTPOS(posArray,tokens);
		
		// create CandidateTerms
		String[] lemmasArray = tokPOSlemma.getLemmasArray();
		ArrayList<int[]> candidateTermsTokenStartEnd = candidateTermsDetection.GetCandidateTermsTokenStartEnd();
		setCandidateTermsLemma(sentence.getContent(),candidateTermsTokenStartEnd,tokPOSlemma.getTokenStartEndInSentence(),
				tokens,lemmasArray,posArray);
	}
	
	/**
	 * Main function to extraction candidate terms
	 * @param sentence A string to analyse (e.g. a sentence)
	 * @param tokenStartEndInSentence For each token, the start and end position in the sentence {@link TokenizerOpenNLP}
	 * @param candidateTermsTokenStartEnd An arrayList of candidate term. Each candidate term is represented with 2 numbers:
	 * a startToken and a endToken in the tokensArray[]
	 * @param posArray a part of speech Array
	 */
	private void setCandidateTermsLemma(String sentence, ArrayList<int[]> candidateTermsTokenStartEnd, 
			int[][] tokenStartEndInSentence,String[] tokensArray,String[] lemmasArray, String[] posArray){

		for (int[] OneCandidateTermTokenStartEnd : candidateTermsTokenStartEnd){
			int candidateTokenStart = OneCandidateTermTokenStartEnd[0];
			int candidateTokenEnd = OneCandidateTermTokenStartEnd[1];

			String[] candidateTokensArray = Arrays.copyOfRange(tokensArray, candidateTokenStart, candidateTokenEnd + 1); // because to is exlusive, we want to include
			String[] candidateLemmasArray = Arrays.copyOfRange(lemmasArray, candidateTokenStart, candidateTokenEnd + 1); // because to is exlusive, we want to include
			String[] candidatePosArray = Arrays.copyOfRange(posArray, candidateTokenStart, candidateTokenEnd + 1);
			//			System.out.println("candidateTokenStart : " + candidateTokenStart);
			//			System.out.println("candidateTokenEnd : " + candidateTokenEnd);

			int startPosition = tokenStartEndInSentence[candidateTokenStart][0];
			int endPosition = tokenStartEndInSentence[candidateTokenEnd][1];

			//			System.out.println("candidateSentenceStart : " + startPosition);
			//			System.out.println("candidateSentenceEnd : " + endPosition);
			// +1 because endPosition is exclusive and we want to include the last char
			String candidateTermString = sentence.substring(startPosition, endPosition + 1 ); 

			CandidateTermLemmaPOS candidateTermPOS = new CandidateTermLemmaPOS(candidateTermString, 
					candidateTokensArray, 
					candidateLemmasArray, 
					startPosition, 
					endPosition,
					candidatePosArray);
			candidateTermsLemmaPOS.add(candidateTermPOS);
		}
	}
	
	/**
	 * @return The {@link TokPOSlemma}
	 */
	public TokPOSlemma getTokPOSlemma() {
		return(tokPOSlemma);
	}
	
	/**
	 * @return A hashSet of {@link CandidateTermLemmaPOS}
	 */
	public HashSet<CandidateTermLemmaPOS> getCandidateTermsLemmaPOS(){
		return(candidateTermsLemmaPOS);
	}
	
	/**
	 * Here we don't want to parse and extract CT from this string, it's already a 'Candidate Term'. 
	 * <br>Ex : a term in a terminology. We just want to create a CandidateTermLemma instance from this term
	 * @param term A term to normalize
	 * @return A {@link CandidateTermLemma} (normalized candidate term)
	 * @throws ProcessSentenceException If the normalizing process goes wrong
	 */
	public CandidateTermLemmaPOS getCandidateTermLemmaPOS(String term) throws ProcessSentenceException {
		tokPOSlemma.processSentence(term);
		int startPosition = 0;
		int endPosition = term.length();
		CandidateTermLemmaPOS candidateTermLemmaPOS = new CandidateTermLemmaPOS(term, 
				tokPOSlemma.getTokens(), tokPOSlemma.getLemmasArray(), 
				startPosition, endPosition, tokPOSlemma.getPosArray());
		return(candidateTermLemmaPOS);
	}
	
	
	/**
	 * @return The sentence analyzed
	 */
	public Sentence getSentence() {
		return(sentence);
	}
}
