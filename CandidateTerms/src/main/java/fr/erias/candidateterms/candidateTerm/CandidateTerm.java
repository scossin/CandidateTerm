package fr.erias.candidateterms.candidateTerm;

import java.text.Normalizer;

import fr.erias.candidateterms.utils.Util;

/**
 * This class represents a candidate term
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */

public class CandidateTerm {

	/**
	 * The candidate term as it is in the sentence 
	 */
	private String candidateTermString;
	
	/**
	 * startPosition in the sentence
	 */
	private int startPosition;

	/**
	 * endPosition in the sentence
	 */
	private int endPosition;

	/**
	 * An array of tokens of the candidateTerm
	 */
	private String[] candidateTokensArray;

	/**
	 * Constructor. This one is used after processing a sentence
	 * @param candidateTermString The candidate term string as it is in the sentence
	 * @param candidateTokensArray An array containing each token of candidateTermString
	 * @param startPosition The start position of this candidate term in the sentence
	 * @param endPosition The end position of this candidate term in the sentence
	 */
	public CandidateTerm(String candidateTermString, String[] candidateTokensArray, int startPosition, int endPosition){
		this.candidateTermString = candidateTermString;
		this.candidateTokensArray = candidateTokensArray;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		//debugInitialization();
	}

	/**
	 * Constructor. This one is used for subclasses
	 * @param candidateTerm A candidateTerm
	 */
	public CandidateTerm(CandidateTerm candidateTerm) {
		this.candidateTermString = candidateTerm.getCandidateTermString();
		this.candidateTokensArray = candidateTerm.getCandidateTokensArray();
		this.startPosition = candidateTerm.getStartPosition();
		this.endPosition = candidateTerm.getEndPosition();
	}
	
	/******************************************** Getters **************************************/
	
	/**
	 * 
	 * @return The CandidateTerm as it is in the sentence/string  (ex : ulceres  (multispace) gastriques)
	 */
	public String getCandidateTermString(){
		return(candidateTermString);
	}
	
	/**
	 * 
	 * @return The candidate term after tokenization (ex : ulceres gastriques)
	 */
	public String getCandidateTerm(){
		return(Util.arrayToString(candidateTokensArray, " ".charAt(0)));
	}
	
	/**
	 * Candidate term is a set of token initially
	 * @return The candidate term in an array
	 */
	public String[] getCandidateTokensArray(){
		return(candidateTokensArray);
	}
	
	/**
	 * 
	 * @return startPosition in the sentence
	 */
	public int getStartPosition() {
		return(startPosition);
	}
	
	/**
	 * 
	 * @return endPosition in the sentence
	 */
	public int getEndPosition() {
		return(endPosition);
	}
	
	
	/**
	 * Simple Normalization of a term : 
	 * <ul>
	 * <li> remove accents ([^\\p{ASCII}])
	 * <li> remove punctuation regex : (\\p{P}) 
	 * <li> remove multispace ([ ]+)
	 * <li> remove space if begin and ends with (trim function)
	 * <li> lowerCase the string
	 * </ul>
	 * @param term A term/sentence to normalize
	 * @return a normalized string
	 */
	public static String getSimpleNormalizedTerm(String term){
		//remove accents : 
		String string = Normalizer.normalize(term, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		// remove punctuation
		string = string.replaceAll("\\p{P}", " ");
		// remove multispace
		string = string.replaceAll("[ ]+", " ");
		// remove space at the end
		string = string.trim();
		// lowercase
		string = string.toLowerCase();
		return (string);
	}
	
	/**
	 * Normalized term
	 * @return result of {@link #getSimpleNormalizedTerm()} applied to the term
	 */
	public String getSimpleNormalizedTerm() {
		// TODO Auto-generated method stub
		return (getSimpleNormalizedTerm(getCandidateTerm()));
	}

	/**
	 * Debugging purpose only
	 */
	private void debugInitialization(){
		System.out.println("New candidateTerm");
		System.out.println("\t New candidate term : " + candidateTermString);
		System.out.println("\t Starting at : " + startPosition);
		System.out.println("\t Ending at : " + endPosition);
		System.out.println("\t Tokens : " + Util.arrayToString(candidateTokensArray, " ".charAt(0)));
		//System.out.println("\t Lemmas : " + Util.arrayToString(candidateLemmasArray," ".charAt(0)));
	}
}
