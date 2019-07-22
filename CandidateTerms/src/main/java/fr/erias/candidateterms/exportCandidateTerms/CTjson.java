package fr.erias.candidateterms.exportCandidateTerms;

import org.json.JSONObject;

/**
 * Any candidate term (CT) must implement a getJSONobject method
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 *
 */
public interface CTjson {

	public JSONObject getJSONobject();
}
