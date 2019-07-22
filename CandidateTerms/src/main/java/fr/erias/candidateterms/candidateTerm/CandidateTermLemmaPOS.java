package fr.erias.candidateterms.candidateTerm;

/**
 * Candidate Term with lemmatization and POS
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */
public class CandidateTermLemmaPOS extends CandidateTermLemma {

	private String[] posArray;
	
	/**
	 * Constructor 
	 * @param candidateTermString The candidate term string as it is in the sentence
	 * @param candidateTokensArray An array containing each token of candidateTermString
	 * @param candidateLemmasArray An array of lemmas of the candidateTerm
	 * @param startPosition The start position of this candidate term in the sentence
	 * @param endPosition The end position of this candidate term in the sentence
	 * @param posArray part of speech array
	 */
	public CandidateTermLemmaPOS(String candidateTermString, String[] candidateTokensArray,
			String[] candidateLemmasArray, int startPosition, int endPosition, String[] posArray) {
		super(candidateTermString, candidateTokensArray, candidateLemmasArray, startPosition, endPosition);
		this.posArray = posArray;
	}
	
	/******************************* Setters ********************************************/
	/**
	 * 
	 * @return An array of POS
	 */
	public String[] getPosArray(){
		return(posArray);
	}
	
	/**
	 * Print association betwee a POS tag and a token 
	 */
	public void printPOS(){
		System.out.println("----> " + this.getCandidateTermString() + " <----");
		for (int i = 0; i<this.getCandidateTokensArray().length;i++){
			System.out.println("\t" + this.getCandidateTokensArray()[i] + "\t" + getPosArray()[i]);
		}
	}
}
