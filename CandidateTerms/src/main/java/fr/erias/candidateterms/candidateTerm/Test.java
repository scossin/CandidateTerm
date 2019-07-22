package fr.erias.candidateterms.candidateTerm;

import java.io.File;
import java.io.IOException;

import org.annolab.tt4j.TreeTaggerException;

import fr.erias.candidateterms.exceptions.InvalidArraysLength;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.candidateterms.frResources.Resources;
import fr.erias.candidateterms.sentence.Sentence;
import fr.erias.candidateterms.taggers.TokPOSlemmaTT;
import fr.erias.candidateterms.taggers.TreeTaggerPOS;
import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;
import opennlp.tools.util.InvalidFormatException;

public class Test {

	public static void main(String[] args) throws InvalidArraysLength, ProcessSentenceException, InvalidFormatException, IOException, TreeTaggerException, UnfoundTokenInSentence {

		TreeTaggerPOS treetaggerPOS = new TreeTaggerPOS(Resources.treeTaggerPath, Resources.treeTaggerFrenchModelFile);
		String tokenizerFilePath = Resources.classLoader.getResource(Resources.tokenizeModelFileFR).getPath();
		File tokenizerFile = new File(tokenizerFilePath);
		TokenizerOpenNLP tokenizer = new TokenizerOpenNLP(tokenizerFile);
		TokPOSlemmaTT treeTaggerTokenizer = new TokPOSlemmaTT(treetaggerPOS,tokenizer);
		
		String content = "J'ai fait du velo";
		Sentence sentence = new Sentence(content,0,0);
		treeTaggerTokenizer.processSentence(sentence.getContent());

//		// detect candidate term with POS and Tokens
//		String[] posArray = treeTaggerTokenizer.getPosArray();
//		String[] tokens = treeTaggerTokenizer.getTokens();
//		CandidateTermsDetectionTTPOS candidateTermsDetection = new CandidateTermsDetectionTTPOS(posArray,tokens);   
		
		CandidateTermsLemmaPOSExtraction ctExtraction = new CandidateTermsLemmaPOSExtraction(treeTaggerTokenizer);
		ctExtraction.extractCandidateTerm(sentence);
		for (CandidateTermLemmaPOS ctLemmaPOS : ctExtraction.getCandidateTermsLemmaPOS()) {
			ctLemmaPOS.printPOS();
		}
		
		ctExtraction.getCandidateTermLemmaPOS(sentence.getContent()).printPOS();
		
		
//		for (int[] OneCandidateTokenStartEnd : candidateTermsDetection.GetCandidateTermsTokenStartEnd()){
//			int tokenStart = OneCandidateTokenStartEnd[0];
//			int tokenEnd = OneCandidateTokenStartEnd[1];
//			String[] candidateTokenArray = Arrays.copyOfRange(tokensArray, tokenStart, tokenEnd + 1);
//			candidateString = Util.arrayToString(candidateTokenArray, " ".charAt(0));
//			nCandidates = nCandidates + 1 ;
//			System.out.println(candidateString);
//		}
	}

}
