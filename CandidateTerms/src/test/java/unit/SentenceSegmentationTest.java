package unit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import fr.erias.candidateterms.frResources.Resources;
import fr.erias.candidateterms.sentence.SentenceSegmentation;
import opennlp.tools.util.InvalidFormatException;

public class SentenceSegmentationTest {

	@Test
	public void frenchSentencesSegmentationTest() throws InvalidFormatException, IOException{
		File modelFile = new File(Resources.classLoader.getResource(Resources.sentenceSegmentationModelFileFR).getPath());
		SentenceSegmentation sentenceSegmentation = new SentenceSegmentation(modelFile);
		String str = "Détecter la phrase numéro 1. Arrive t-il à détécter la phrase numéro 2 ? "
				+ "Ceci est la phrase numéro 3 ! Et la 4 la voici... Il s'agit maintenant de la phrase 5";
		String[] sentences = sentenceSegmentation.getSentenceDetector().sentDetect(str);
		for (String sent : sentences){
			System.out.println(sent);
		}
		assertTrue(sentences.length == 5) ;
	}
}
