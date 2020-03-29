package fr.erias.candidateterms.exportCandidateTerms;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.annolab.tt4j.TreeTaggerException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.candidateTerm.CandidateTermLemma;
import fr.erias.candidateterms.candidateTerm.CandidateTermsLemmaExtraction;
import fr.erias.candidateterms.exceptions.InvalidArraysLength;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.candidateterms.frResources.Resources;
import fr.erias.candidateterms.sentence.Sentence;
import fr.erias.candidateterms.sentence.SentenceSegmentation;
import fr.erias.candidateterms.taggers.TokPOSlemma;
import fr.erias.candidateterms.taggers.TokPOSlemmaTT;
import fr.erias.candidateterms.taggers.TreeTaggerPOS;
import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;

/**
 * Take text in input, sentences segmentation. For each sentence, extract {@link CandidateTermLemma}.
 * <br> 
 * Output a JSON format
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */
public class ProcessText implements CTjson {

	final static Logger logger = LoggerFactory.getLogger(ProcessText.class);

	/**
	 * To segment sentences
	 */
	private SentenceSegmentation sentenceSegmentation;

	/**
	 * Take in input a sentence and extract a HashSet of CandidateTermsLemma
	 */
	private CandidateTermsLemmaExtraction candidateTermsLemmaExtraction;

	/**
	 * the JSONArray output
	 */
	private JSONObject jsonResults ;

	/**
	 * Constructor. Create an object to analyze text input, segment sentences and extract {@link CandidateTermLemma}.
	 * @param candidateTermsLemmaExtraction Instance of {@link CandidateTermsLemmaExtraction} to extract {@link CandidateTermLemma}
	 * @param sentenceSegmentation A sentenceSemgentation instance to extract sentences from text
	 */
	public ProcessText(CandidateTermsLemmaExtraction candidateTermsLemmaExtraction, 
			SentenceSegmentation sentenceSegmentation) {
		this.candidateTermsLemmaExtraction = candidateTermsLemmaExtraction;
		this.sentenceSegmentation = sentenceSegmentation;
	}

	/**
	 * Give a textInput to process
	 * @param inputString A text with one or many sentences to analyze
	 * @throws ProcessSentenceException If the analysis process of a sentence fails
	 * @throws InvalidArraysLength Will not occur
	 * @throws UnfoundTokenInSentence If the tokenizer fails to found the extract token in the sentence
	 */
	public synchronized void processInput(String inputString) throws ProcessSentenceException, InvalidArraysLength, UnfoundTokenInSentence {
		this.sentenceSegmentation.setContent(inputString);
		processSentences(); // process each one
	}

	/**
	 * Get all CT extracted in the textInput
	 * @return A JSON object, Candidate Terms extracted for each sentence in the textInput
	 */
	public JSONObject getJsonArraySentences() {
		return(jsonResults);
	}

	/**
	 * Get a JSON object for each sentence (one sentence = a HashSet of CandidateTermLemma)
	 * @param candidateTermLemmas one sentence = a HashSet of CandidateTermLemma
	 * @param sentence the sentence
	 * @return a JSON object of CT for a sentence
	 */
	private JSONArray getJsonCT(JSONArray jsonArrayCT, HashSet<CandidateTermLemma> candidateTermLemmas, Sentence sentence) {
		ToJsonCTlemma toJsonCTlemma = new ToJsonCTlemma();
		for (CandidateTermLemma candidateTermLemma : candidateTermLemmas) {
			toJsonCTlemma.setCandidateTermLemma(candidateTermLemma);
			int absoluteStartPosition = sentence.getStart() + candidateTermLemma.getStartPosition();
			int absoluteEndPosition = sentence.getEnd();
			JSONObject ctLemmaJSON = toJsonCTlemma.getJSONobject();
			ctLemmaJSON.put("absoluteStartPosition", absoluteStartPosition);
			ctLemmaJSON.put("absoluteEndPosition", absoluteEndPosition);
			ctLemmaJSON.put("phraseNumber", sentence.getPhraseNumber());
			jsonArrayCT.put(ctLemmaJSON);
		}
		return(jsonArrayCT);
	}
	
	/**
	 * Get a JSON object for each sentence (one sentence = a HashSet of CandidateTermLemma)
	 * @param candidateTermLemmas one sentence = a HashSet of CandidateTermLemma
	 * @param sentence the sentence
	 * @return a JSON object of CT for a sentence
	 */
	private JSONArray getJsonSentence(JSONArray jsonArraySentence, Sentence sentence) {
		JSONObject jsonObjectSentence = new JSONObject();
		jsonObjectSentence.put("phraseNumber", sentence.getPhraseNumber());
		jsonObjectSentence.put("phraseStart", sentence.getStart());
		jsonObjectSentence.put("phraseEnd", sentence.getEnd());
		jsonObjectSentence.put("content", sentence.getContent());
		jsonArraySentence.put(jsonObjectSentence);
		return(jsonArraySentence);
	}

	/**
	 * 
	 * @throws ProcessSentenceException If the process of extraction CT fails
	 * @throws InvalidArraysLength will not occur
	 * @throws UnfoundTokenInSentence If the tokenizer fails to found start/endPosition of a token in the sentence
	 */
	private void processSentences() throws ProcessSentenceException, InvalidArraysLength, UnfoundTokenInSentence {
		JSONObject jsonObjectSentence = new JSONObject();
		JSONArray jsonArrayCT = new JSONArray();
		JSONArray jsonArraySentences = new JSONArray();
		for (Sentence sentence : this.sentenceSegmentation.getSentences()) {
			candidateTermsLemmaExtraction.extractCandidateTerm(sentence);
			jsonArrayCT = getJsonCT(jsonArrayCT,candidateTermsLemmaExtraction.getCandidateTermsLemma(), sentence);
			jsonArraySentences = getJsonSentence(jsonArraySentences, sentence);
		}
		jsonObjectSentence.put("sentences", jsonArraySentences);
		jsonObjectSentence.put("CT", jsonArrayCT);
		this.jsonResults = jsonObjectSentence;
	}
	
	/**ProcessText
	 * Get a CandidateTermsLemmaExtraction instance
	 * @return a {@link CandidateTermsLemmaExtraction}
	 */
	public CandidateTermsLemmaExtraction getCandidateTermsLemmaExtraction() {
		return(this.candidateTermsLemmaExtraction);
	}

	@Override
	public JSONObject getJSONobject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("CTextraction", jsonResults);
		return jsonObject;
	}
	
	public static void main(String[] args) throws IOException, TreeTaggerException, ProcessSentenceException, InvalidArraysLength, UnfoundTokenInSentence {
		// create a new CandidateTermsLemmaExtraction
		TreeTaggerPOS treetaggerPOS = new TreeTaggerPOS(Resources.treeTaggerPath, Resources.treeTaggerFrenchModelFile);
		String tokenizerFilePath = Resources.classLoader.getResource(Resources.tokenizeModelFileFR).getPath();
		File tokenizerFile = new File(tokenizerFilePath);
		TokenizerOpenNLP tokenizer = new TokenizerOpenNLP(tokenizerFile);
		TokPOSlemma tokPOSlemma = new TokPOSlemmaTT(treetaggerPOS, tokenizer);
		CandidateTermsLemmaExtraction candidateTermsLemmaExtraction = new CandidateTermsLemmaExtraction(tokPOSlemma);
		

		File modelFile = new File(Resources.classLoader.getResource(Resources.sentenceSegmentationModelFileFR).getPath());
		SentenceSegmentation sentenceSegmentation = new SentenceSegmentation(modelFile);
		
		String content = "En association à des antibiotiques appropriés, éradication de Helicobacter pylori dans la maladie ulcéreuse gastroduodénale. "
				+ "Deuxième phrase : ceci est la deuxième phrase.";
		
		ProcessText processText = new ProcessText(candidateTermsLemmaExtraction, sentenceSegmentation);
		processText.processInput(content);
		JSONObject jsonObject = processText.getJSONobject();
		System.out.println(jsonObject.toString());
	}
}
