package fr.erias.ctapi.process;

import java.io.IOException;
import java.io.InputStream;

import org.annolab.tt4j.TreeTaggerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.candidateTerm.CandidateTermLemma;
import fr.erias.candidateterms.candidateTerm.CandidateTermsLemmaExtraction;
import fr.erias.candidateterms.exceptions.InvalidArraysLength;
import fr.erias.candidateterms.exceptions.MyExceptions;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.candidateterms.exportCandidateTerms.ProcessText;
import fr.erias.candidateterms.exportCandidateTerms.ToJsonCTlemma;
import fr.erias.candidateterms.frResources.Resources;
import fr.erias.candidateterms.sentence.SentenceSegmentation;
import fr.erias.candidateterms.taggers.TokPOSlemmaTT;
import fr.erias.candidateterms.taggers.TreeTaggerPOS;
import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;
import opennlp.tools.util.InvalidFormatException;

public class FileProcessStatic {

	final static Logger logger = LoggerFactory.getLogger(FileProcessStatic.class);
	
	public static ProcessText processText;
	
	static {
		logger.info("Initializing FileProcessStatic to analyze sentences");
		
		TreeTaggerPOS treetaggerPOS;
		try {
			treetaggerPOS = new TreeTaggerPOS(Resources.treeTaggerPath, Resources.treeTaggerFrenchModelFile);
			InputStream in = Resources.classLoader.getResourceAsStream(Resources.tokenizeModelFileFR);
			TokenizerOpenNLP tokenizer = new TokenizerOpenNLP(in); // in is closed at this step
			TokPOSlemmaTT tokPOSlemma = new TokPOSlemmaTT(treetaggerPOS, tokenizer);
			
			in = Resources.classLoader.getResourceAsStream(Resources.sentenceSegmentationModelFileFR);
			SentenceSegmentation sentenceSegmentation = new SentenceSegmentation(in);
			CandidateTermsLemmaExtraction candidateTermsLemmaExtraction = new CandidateTermsLemmaExtraction(tokPOSlemma);
			FileProcessStatic.processText = new ProcessText(candidateTermsLemmaExtraction, sentenceSegmentation);
		} catch (IOException | TreeTaggerException e) {
			logger.info("An error was thrown");
			MyExceptions.logException(logger, e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InvalidFormatException, IOException, TreeTaggerException, ProcessSentenceException, InvalidArraysLength, UnfoundTokenInSentence {
		String term = "gastriques";
		CandidateTermLemma candidateTermLemma = processText.getCandidateTermsLemmaExtraction().getCandidateTermLemma(term);
		ToJsonCTlemma toJsonCTlemma = new ToJsonCTlemma();
		toJsonCTlemma.setCandidateTermLemma(candidateTermLemma);
		System.out.println(toJsonCTlemma.getJSONobject());
	}
}
