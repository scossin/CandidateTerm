package fr.erias.candidateterms.exportCandidateTerms;

import org.json.JSONObject;

import fr.erias.candidateterms.candidateTerm.CandidateTerm;

/**
 * A class to prepare the export of CandidateTerm
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 *
 */
public class ToJsonCT implements CTjson {

	private CandidateTerm candidateTerm;

	/**
	 * Constructor : export a JSON representation of a Candidate Term 
	 * @param candidateTerm {@link CandidateTerm}
	 */
	public ToJsonCT (CandidateTerm candidateTerm)  {
		this.candidateTerm = candidateTerm;
	}
	
	/**
	 * Constructor : export a JSON representation of a Candidate Term 
	 */
	public ToJsonCT ()  {
		
	}
	
	/**
	 * JsonField of a candidateTerm
	 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
	 */
	public enum JsonFieldCT {
		candidateTermString ("candidateTermString"), // as it is in the sentence : "ulceres    gastriques"
		candidateTerm("candidateTerm"), // after tokenization and concatenation : "ulceres gastriques"
		startPosition("startPosition"), // start position in the sentence
		endPosition("endPosition"); // end position in the sentence

		private String fieldName;

		private JsonFieldCT(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName(){
			return(fieldName);
		}
	}
	
	/**
	 * Create and get the JSONobject representation of this candidate term
	 * @return a JSONObject representing this candidate term
	 */
	@Override
	public JSONObject getJSONobject() {
		JSONObject json = new JSONObject();
		json.put(JsonFieldCT.candidateTermString.getFieldName(), candidateTerm.getCandidateTermString());
		json.put(JsonFieldCT.candidateTerm.getFieldName(), candidateTerm.getCandidateTerm());
		json.put(JsonFieldCT.startPosition.getFieldName(), candidateTerm.getStartPosition());
		json.put(JsonFieldCT.endPosition.getFieldName(), candidateTerm.getEndPosition());
		return(json);
	}
	
	/************************************* Setters ******************************/
	/**
	 * Change the candidate term
	 * @param candidateTerm  {@link CandidateTerm}
	 */
	public void setCandidateTerm(CandidateTerm candidateTerm) {
		this.candidateTerm = candidateTerm;
	}
}
