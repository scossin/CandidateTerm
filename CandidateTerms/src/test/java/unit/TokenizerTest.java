package unit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.candidateterms.frResources.Resources;
import fr.erias.candidateterms.tokenizers.TokenizerOpenNLP;
import opennlp.tools.util.InvalidFormatException;

public class TokenizerTest {

	
	@Test
	public void testTokenizerFRTest() throws InvalidFormatException, IOException {
		String tokenizerFilePath = Resources.classLoader.getResource(Resources.tokenizeModelFileFR).getPath();
		File tokenizerFile = new File(tokenizerFilePath);
		TokenizerOpenNLP tokenizer = new TokenizerOpenNLP(tokenizerFile);
		String sentence ="Traitement local des lésions inflammatoires limitées à la muqueuse buccale et des aphtes et l'oiseau.";
		tokenizer.tokenize(sentence);
		String[] tokens = tokenizer.getTokens();
//		for (String token : tokens){
//			System.out.println(token);
//		}
//		System.out.println(tokens.length);
		boolean test = tokens.length == 17;
		assertTrue(test);
	}
	
	@Test
	public void TokenizerFRtokensPosition() throws UnfoundTokenInSentence, InvalidFormatException, IOException{
		String tokenizerFilePath = Resources.classLoader.getResource(Resources.tokenizeModelFileFR).getPath();
		File tokenizerFile = new File(tokenizerFilePath);
		TokenizerOpenNLP tokenizer = new TokenizerOpenNLP(tokenizerFile);
		String sentence ="Traitement local des lésions inflammatoires limitées à la muqueuse buccale et des aphtes et l'oiseau.";
		tokenizer.tokenize(sentence);
		int[][] tokenStartEndInSentence = tokenizer.getTokenStartEndInSentence();
		int[] expectedResult0 = {0,9};
		int[] expectedResult16 = {100,100};
		boolean checkResult0 = Arrays.equals(tokenStartEndInSentence[0], expectedResult0);
		assertTrue(checkResult0);
		boolean checkResult16 = Arrays.equals(tokenStartEndInSentence[16], expectedResult16);
		assertTrue(checkResult16);
	}
}

