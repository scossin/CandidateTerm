package unit;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.annolab.tt4j.TreeTaggerException;
import org.junit.Test;

import fr.erias.candidateterms.frResources.Resources;
import fr.erias.candidateterms.taggers.TokenHandlerTreeTagger;
import fr.erias.candidateterms.taggers.TreeTaggerPOS;

public class TreeTaggerPOSTest {

	@Test
	public void TreeTaggerPOSFRlemmasTest() throws IOException, TreeTaggerException{
		   TreeTaggerPOS treetaggerPOS = new TreeTaggerPOS(Resources.treeTaggerPath, Resources.treeTaggerFrenchModelFile);
		   String[] tokens = { "L'", "oiseau", "n'", "a","pas","manger"};
		   TokenHandlerTreeTagger oneSentencePOS = new TokenHandlerTreeTagger(tokens);
		   oneSentencePOS = (TokenHandlerTreeTagger) treetaggerPOS.POSanalyse(oneSentencePOS, tokens);
		   treetaggerPOS.closeTreeTaggerWrapper();
		   String[] lemmas = oneSentencePOS.getLemmasArray();
		   String[] expectedLemmas = {"le","oiseau","ne","avoir","pas","manger"};
		   boolean isEqual = Arrays.equals(lemmas, expectedLemmas);
		   System.out.println("\t Testing lemmas of TreeTagger in french...");
		   assertTrue(isEqual);
	}
	
	@Test
	public void TreeTaggerPOSFRposTest() throws IOException, TreeTaggerException{
		   TreeTaggerPOS treetaggerPOS = new TreeTaggerPOS(Resources.treeTaggerPath, Resources.treeTaggerFrenchModelFile);
		   String[] tokens = { "L'", "oiseau", "n'", "a","pas","manger"};
		   TokenHandlerTreeTagger oneSentencePOS = new TokenHandlerTreeTagger(tokens);
		   oneSentencePOS = (TokenHandlerTreeTagger) treetaggerPOS.POSanalyse(oneSentencePOS, tokens);
		   treetaggerPOS.closeTreeTaggerWrapper();
		   String[] lemmas = oneSentencePOS.getPosArray();
		   String[] expectedLemmas = {"DET:ART","NOM","ADV","VER:pres","ADV","VER:infi"};
		   boolean isEqual = Arrays.equals(lemmas, expectedLemmas);
		   System.out.println("\t Testing POS of TreeTagger in french...");
		   assertTrue(isEqual);
	}
}
