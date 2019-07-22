package fr.erias.candidateterms.candidateTerm;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.exceptions.InvalidArraysLength;

/**
 * A class to detect candidate terms with a set of rules <br>
 * Input : An array of tokens and an array of Part-of-speech category for each token. 
 * A candidate term is a subset, a n-gram sequence, of the tokens array. 
 * A set of rules is used to extract these n-gram sequence. <br>
 * Ouput : Each candidate term is represented by an array of 2 integers : tokenStart and tokenEnd. 
 * The candidate term is the n-gram sequence : from tokenStart to tokenEnd
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */

public abstract class CandidateTermsDetection {
	
	final static Logger logger = LoggerFactory.getLogger(CandidateTermsDetectionTTPOS.class);
	
	/**
	 * The tokensArray obtained with a tokenizer. 
	 */
	private String[] tokensArray = null;
	
	/**
	 * Part-of-speach array ; This array contains the grammatical category of each token
	 */
	private String[] posArray = null;
	
	/**
	 * ArrayList storing arrays of 2 integer. An array of 2 integer is used to get a candidate term
	 * A candidate term is a n-gram sequence of the tokensArray
	 * For example, {1,3} means the candidate term begins at tokensArray[1] and stop at tokensArray[3]
	 */
	private ArrayList<int[]> candidateTermsTokenStartEnd = new ArrayList<int[]>();
	
	/**
	 * The maxNgrams sequence that can be tested
	 */
	private int maxNgrams = 5;
	
	
	/**
	 * Constructor 
	 * @param posArray An array containing the grammatical category of each token
	 * @param tokensArray An array of tokens obtained with a tokenizer
	 * @throws InvalidArraysLength If the length of the 2 arrays are different
	 */
	public CandidateTermsDetection(String[] posArray, String[] tokensArray) throws InvalidArraysLength{
		if (posArray.length != tokensArray.length){
			throw new InvalidArraysLength(logger, posArray.length, tokensArray.length);
		}
		this.posArray = posArray;
		this.tokensArray = tokensArray;
		setCandidateTerms();
	}
	
	
	/**
	 * ArrayList storing arrays of 2 integer. An array of 2 integer is used to get a candidate term
	 * @return For example, {1,3} means the candidate term begins at tokensArray[1] and stop at tokensArray[3]
	 */
	public ArrayList<int[]> GetCandidateTermsTokenStartEnd(){
		return(candidateTermsTokenStartEnd);
	}
	
	/**
	 * The array of tokens
	 * @return an array of tokens (tokenizer)
	 */
	public String[] getTokensArray(){
		return tokensArray;
	}
	
	
	/**
	 * This function loops over all index in the POS array and test all possible 1 -> maxNgrams combinations of POS 
	 * if posSequence sequence match the regular expression, then the candidate term (tokenStart - tokenEnd) is stored  
	 */
	private void setCandidateTerms(){
		int from  = 0;
		int maxNgrams = this.maxNgrams;
		
		while (from != posArray.length){ // From 0 index till the end of the array
			
			int arrayRemainingLength = posArray.length - (from); // when reaching the end of the array ;
			if (arrayRemainingLength < maxNgrams){ 
				maxNgrams = arrayRemainingLength; // avoid an array out of bound
//				System.out.println("new maxNgarms : " +  maxNgrams +  " at position : " + from + " to reach " + posArray.length);
			}
			
//			System.out.println("from : " + from);
			int ngrams = 1; 
			while (ngrams != maxNgrams + 1){ // For each index position in the POS array, test the sequence : 1 -> maxNgrams 
				
				int to = from + ngrams ;
				// if ngrams = 1 ; posSequence is only one token but since "to" is exclusive with copyOfRange, to = from + ngrams
				String[] posSequence = Arrays.copyOfRange(posArray, from, to);
				
//				System.out.println("\t testing : " + arrayToString(posSequence));
				
				boolean isCandidate = checkPOSsequence(posSequence); // test this sequence. 
				if (isCandidate){ // If true, then candidate is added
					int start = from ;
					int end = start + (ngrams-1) ; // !important : end is INCLUSIVE ; so -1 is important ! if ngrams = 1 then tokenStart = tokenEnd (only one token)
					int[] OnecandidateTermsStartEnd = {start,end};
					candidateTermsTokenStartEnd.add(OnecandidateTermsStartEnd);
				}
//				if (!isCandidate & ngrams == 1){ // if first POS is rejected, don't test another ngrams sequence. go to the next index
//					break;
//				}
				ngrams = ngrams + 1;
			}
			
			from = from + 1 ; // next index in the array
		}
	}
	

	/***************************************** MUST IMPLEMENT THIS *******************************/
	
	
	/**
	 * Set of rules specific to POS analyzer to check if a POS sequence is a candidate term or not
	 * @param posSequence An array of Part-of-speech
	 * @return true if posSequence match with the regular expression, return false otherwise
	 */
	protected abstract boolean checkPOSsequence(String[] posSequence);
	
	
	
}
