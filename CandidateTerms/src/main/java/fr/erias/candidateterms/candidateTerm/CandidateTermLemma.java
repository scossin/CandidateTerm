package fr.erias.candidateterms.candidateTerm;

import fr.erias.candidateterms.stemmers.FrenchStemmer;
import fr.erias.candidateterms.stemmers.Stemmer;
import fr.erias.candidateterms.utils.Util;


/**
 * Candidate Term with lemmatization
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 *
 */
public class CandidateTermLemma extends CandidateTerm {

	
	/**
	 * An array of lemmas of the candidateTerm
	 */
	private String[] candidateLemmasArray;
	
	/**
	 * By default a french Stemmer
	 */
	private Stemmer stemmer = new FrenchStemmer();
	
	/**
	 * Constructor 
	 * @param candidateTermString The candidate term string as it is in the sentence
	 * @param candidateTokensArray An array containing each token of candidateTermString
	 * @param candidateLemmasArray An array of lemmas of the candidateTerm
	 * @param startPosition The start position of this candidate term in the sentence
	 * @param endPosition The end position of this candidate term in the sentence
	 */
	public CandidateTermLemma(String candidateTermString, String[] candidateTokensArray,
			String[] candidateLemmasArray, int startPosition, int endPosition) {
		super(candidateTermString, candidateTokensArray, startPosition, endPosition);
		this.candidateLemmasArray = candidateLemmasArray;
	}
	
	
	/******************************* Setters ********************************************/
	
	/**
	 * Change the Stemmer by default
	 * @param stemmer A {@link Stemmer}
	 */
	public void setStemmer (Stemmer stemmer) {
		this.stemmer = stemmer;
	}
	
	
	/********************************** Getters *****************************************/
	
	/**
	 * Normalized lemma term 
	 * @return result of {@link CandidateTerm#getSimpleNormalizedTerm()} applied to the lemmatized term
	 */
	public String getLemmaNormalizedTerm() {
		String lemmaNormalizedTerm = getSimpleNormalizedTerm(getLemmaTerm());
		return(lemmaNormalizedTerm);
	}
	
	/**
	 * @return A stem version of the candidate term lemma
	 */
	public String getStemTerm() {
		String stemTerm = stemmer.stemString(getLemmaTerm());
		return(stemTerm);
	}
	
	/**
	 * 
	 * @return An array of candidate term lemmatized
	 */
	public String[] getCandidateLemmasArray(){
		return(candidateLemmasArray);
	}
	
	/**
	 * 
	 * @return a lemmatized string of the candidate term
	 */
	public String getLemmaTerm() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<candidateLemmasArray.length ; i++) {
			String tokenLemma = candidateLemmasArray[i];
			if (tokenLemma.equals("@card@") || tokenLemma.contains("|")) {
				tokenLemma = getCandidateTokensArray()[i];
			}
			sb.append(tokenLemma);
			sb.append(" ");
		}
		sb.setLength(sb.length() - 1);
		return(sb.toString());
		// return Util.arrayToString(candidateLemmasArray," ".charAt(0));
	}
}
