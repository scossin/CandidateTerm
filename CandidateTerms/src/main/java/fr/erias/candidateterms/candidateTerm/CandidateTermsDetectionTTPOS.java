package fr.erias.candidateterms.candidateTerm;

import java.util.regex.Pattern;

import fr.erias.candidateterms.exceptions.InvalidArraysLength;


/**
 * {@link CandidateTermsDetection} implementation with POS coming from TreeTagger and a set of rules
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 */

public class CandidateTermsDetectionTTPOS extends CandidateTermsDetection{

	/**
	 * Constructor
	 * @param posArray An array containing the grammatical category of each token
	 * @param tokensArray An array of tokens obtained with a tokenizer
	 * @throws InvalidArraysLength If the length of the 2 arrays are different
	 */
	public CandidateTermsDetectionTTPOS(String[] posArray, String[] tokensArray) throws InvalidArraysLength {
		super(posArray, tokensArray);
	}

	/**
	 * The key regular expression to extract candidate terms
	 */
	private final String regularE = "J*NJ*(PD?J*NJ*)*";

	/**
	 * Set of rules specific to TreeTagger POS analyzer !!
	 * @param posSequence An array of Part-of-speech
	 * @return true if posSequence match with the regular expression, return false otherwise
	 */
	protected boolean checkPOSsequence(String[] posSequence){
		String pattern = "";
		for(int i = 0; i < posSequence.length; i++){
			if(posSequence[i] == null)pattern=pattern+"Z";
			else if(posSequence[i].equals("DET:ART"))pattern=pattern+"D";
			else if(posSequence[i].equals("NOM")|| posSequence[i].equals("NAM")|| posSequence[i].equals("ABR"))pattern=pattern+"N";
			else if(posSequence[i].equals("NUM") || posSequence[i].equals("ADJ") || posSequence[i].equals("VER:pper"))pattern=pattern+"J";
			else if(posSequence[i].equals("PRP") || posSequence[i].equals("PRP:det"))pattern=pattern+"P";
			else pattern = pattern+"Z";
		}

		if (matchPattern(pattern)){
			return(true);
		} else {
			return(false);
		}
	}

	/**
	 * If the sequence of Part-Of-Speech match the regular expression the function return true
	 * @param pattern
	 * @return
	 */
	private boolean matchPattern(String pattern){
		if(Pattern.matches(regularE, pattern) && !pattern.equals("J")){
			return(true);
		} else {
			return(false);
		}
	}

}
