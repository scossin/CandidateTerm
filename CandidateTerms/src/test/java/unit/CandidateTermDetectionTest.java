package unit;



import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.annolab.tt4j.TreeTaggerException;
import org.junit.Test;

import fr.erias.candidateterms.candidateTerm.CandidateTermsDetectionTTPOS;
import fr.erias.candidateterms.exceptions.InvalidArraysLength;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.frResources.Resources;
import fr.erias.candidateterms.taggers.TokPOSlemmaTT;
import fr.erias.candidateterms.taggers.TreeTaggerPOS;
import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;
import fr.erias.candidateterms.utils.Util;

public class CandidateTermDetectionTest {

	@Test
	public void CandidateDetectionTest() throws IOException, TreeTaggerException, InvalidArraysLength, ProcessSentenceException{

		TreeTaggerPOS treetaggerPOS = new TreeTaggerPOS(Resources.treeTaggerPath, Resources.treeTaggerFrenchModelFile);
		String tokenizerFilePath = Resources.classLoader.getResource(Resources.tokenizeModelFileFR).getPath();
		File tokenizerFile = new File(tokenizerFilePath);
		TokenizerOpenNLP tokenizer = new TokenizerOpenNLP(tokenizerFile);
		TokPOSlemmaTT treeTaggerTokenizer = new TokPOSlemmaTT(treetaggerPOS,tokenizer);
		
		String sentence = "Traitement local des lésions infectieuses";
		treeTaggerTokenizer.processSentence(sentence);

		// detect candidate term with POS and Tokens
		String[] posArray = treeTaggerTokenizer.getPosArray();
		String[] tokens = treeTaggerTokenizer.getTokens();
		CandidateTermsDetectionTTPOS candidateTermsDetection = new CandidateTermsDetectionTTPOS(posArray,tokens);   
		
		//oneSentencePOS.seePos();
		int nCandidates = 0;
		String[] tokensArray = candidateTermsDetection.getTokensArray();
		String candidateString = null;
		for (int[] OneCandidateTokenStartEnd : candidateTermsDetection.GetCandidateTermsTokenStartEnd()){
			int tokenStart = OneCandidateTokenStartEnd[0];
			int tokenEnd = OneCandidateTokenStartEnd[1];
			String[] candidateTokenArray = Arrays.copyOfRange(tokensArray, tokenStart, tokenEnd + 1);
			candidateString = Util.arrayToString(candidateTokenArray, " ".charAt(0));
			nCandidates = nCandidates + 1 ;
			System.out.println(candidateString);
		}
		assertTrue(nCandidates == 6);
		assertTrue(candidateString.equals("lésions infectieuses")); // last one
	}
}
