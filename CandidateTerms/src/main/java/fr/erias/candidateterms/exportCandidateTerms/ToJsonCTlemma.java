package fr.erias.candidateterms.exportCandidateTerms;

import org.json.JSONObject;

import fr.erias.candidateterms.candidateTerm.CandidateTermLemma;

/**
 * 
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 *
 */
public class ToJsonCTlemma implements CTjson {

	private ToJsonCT toJsonCT;
	private CandidateTermLemma candidateTermLemma = null;
	
	/**
	 * Constructor 
	 * @param candidateTermLemma {@link CandidateTermLemma}
	 */
	public ToJsonCTlemma (CandidateTermLemma candidateTermLemma) {
		toJsonCT = new ToJsonCT(candidateTermLemma);
	}
	
	/**
	 * Default constructor - candidateTermLemma is null. Please {@link #setCandidateTermLemma} after construction
	 */
	public ToJsonCTlemma() {
		this.toJsonCT = new ToJsonCT();
	}
	
	/**
	 * JsonField of a candidateTerm Json
	 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
	 */
	public enum JsonFieldCTlemma {
		normalizedTerm("normalizedTerm"), // term normalized
		lemmaTerm("lemmaTerm"), // lemmaTerm normalized 
		stemTerm("stemTerm"); // stemTerm 
		//relativeEndPosition("relativeEndPosition"), // relativeEndPosition 
		//relativeStartPosition("relativeStartPosition"); // relativeStartPosition 

		private String fieldName;

		private JsonFieldCTlemma(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName(){
			return(fieldName);
		}
	}
	
	/************************************* Setters *****************************************/
	
	/**
	 * Replace the candidateTermLemma
	 * @param candidateTermLemma {@link CandidateTermLemma}
	 */
	public void setCandidateTermLemma(CandidateTermLemma candidateTermLemma) {
		this.candidateTermLemma = candidateTermLemma;
		this.toJsonCT.setCandidateTerm(candidateTermLemma);
	}
	
	
	/**
	 * Create and get the JSONobject representation of this candidate term lemmatized
	 * @return a JSONObject representing this candidate term
	 */
	public JSONObject getJSONobject() {
		JSONObject json = toJsonCT.getJSONobject();
		json.put(JsonFieldCTlemma.normalizedTerm.getFieldName(), candidateTermLemma.getSimpleNormalizedTerm());
		json.put(JsonFieldCTlemma.lemmaTerm.getFieldName(), candidateTermLemma.getLemmaNormalizedTerm());
		json.put(JsonFieldCTlemma.stemTerm.getFieldName(), candidateTermLemma.getStemTerm());
		//json.put(JsonFieldCTlemma.relativeStartPosition.getFieldName(), candidateTermLemma.getStartPosition());
		//json.put(JsonFieldCTlemma.relativeEndPosition.getFieldName(), candidateTermLemma.getEndPosition());
		return(json);
	}
}
