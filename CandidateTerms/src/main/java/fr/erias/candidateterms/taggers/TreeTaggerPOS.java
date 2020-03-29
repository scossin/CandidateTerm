package fr.erias.candidateterms.taggers;
import java.io.IOException;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains a TreeTaggerWrapper to invoke Treetagger
 * TreeTaggerWrapper does POS only (no tokenizer)
 * @author Cossin Sebastien (cossin.sebastien@gmail.com)
 *
 */

public class TreeTaggerPOS {

	final static Logger logger = LoggerFactory.getLogger(TreeTaggerPOS.class);
	
	/**
	 * The treeTagger path : where to find the executable file treetagger
	 */
	private String treeTaggerPath;
	
	private String treeTaggerModelFile; 
	
	/**
	 * the wrapper
	 */
	private TreeTaggerWrapper<String> treeTaggerWrapper = null;

	/**
	 * 
	 * @return A {@link TreeTaggerWrapper} that handles a sentence (String)
	 */
	public TreeTaggerWrapper<String> getTreeTaggerWrapper(){
		return(treeTaggerWrapper);
	}

	/**
	 * Stop the treetagger process  
	 */
	public void closeTreeTaggerWrapper(){
		treeTaggerWrapper.destroy();
	}

	/**
	 * Part-of-speach analysis of a sequence of tokens
	 * @param handler A class implementing {@link TokenHandler}, handles the output of TreeTagger POS analysis
	 * @param tokensArray An array of tokens
	 * @return An instance of a class implementing {@link TokenHandler}
	 * @throws IOException if there is a problem providing the model or executable.
	 * @throws TreeTaggerException if there is a problem communication with TreeTagger
	 */
	public TokenHandler<String> POSanalyse(TokenHandler<String> handler, String[] tokensArray) throws IOException, TreeTaggerException{
		treeTaggerWrapper.setHandler(handler);
		treeTaggerWrapper.process(tokensArray);
		return(treeTaggerWrapper.getHandler());
	}

	
	/**
	 * Constructor
	 * @param treeTaggerPath Where to find the treetagger executable file 
	 * @param treeTaggerModelFile A path to TT model file depending on the language. See {@linkplain TreeTaggerWrapper#setModel(org.annolab.tt4j.Model)}
	 * @throws IOException if there is a problem to find the model or executable.
	 * @throws TreeTaggerException if there is a problem with TreeTagger
	 */
	public TreeTaggerPOS(String treeTaggerPath, String treeTaggerModelFile) throws IOException, TreeTaggerException{
		// Point TT4J to the TreeTagger installation directory. The executable is expected
		// in the "bin" subdirectory : "treetagger/bin/tree-tagger"
		this.treeTaggerPath = treeTaggerPath;
		this.treeTaggerModelFile = treeTaggerModelFile;
		logger.info("TreeTaggerPath: " + treeTaggerPath);
		System.setProperty("treetagger.home", treeTaggerPath);
		treeTaggerWrapper = new TreeTaggerWrapper<String>();
		treeTaggerWrapper.setModel(treeTaggerModelFile);
	}
	
	/***************************************** Getter ***********************************/
	
	
	/**
	 * Get the TT executable path
	 * @return The executable path
	 */
	public String getTreeTaggerPath() {
		return(treeTaggerPath);
	}
	
	/**
	 * Get the TT model file path
	 * @return the TT model file path
	 */
	public String getTreeTaggerModelFile() {
		return(treeTaggerModelFile);
	}
}
