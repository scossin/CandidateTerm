package fr.erias.candidateterms.stopwords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.erias.candidateterms.frResources.Resources;

/**
 * A stopword class that handles a list of stopwords
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)s
 *
 */
public class StopWords {

	private String[] stopWordsArray;
	
	/**
	 * Constructor
	 * @param fileName A fileName to put in the ressource folder of the maven project
	 * @throws IOException A the file is not found
	 */
	public StopWords(String fileName) throws IOException{
		InputStream in = Resources.classLoader.getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		ArrayList<String> liste = new ArrayList<String>();
		String line = "";
		while((line = br.readLine()) != null){
			liste.add(line);
		}
		stopWordsArray = liste.toArray(new String[liste.size()]);
		in.close();
	}
	
	/**
	 * An array of stopwords
	 * @return A string array containing stopwords
	 */
	public String[] getStopWordsArray() {
		return stopWordsArray;
	}
	
}
