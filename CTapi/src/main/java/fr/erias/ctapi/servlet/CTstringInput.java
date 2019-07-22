package fr.erias.ctapi.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.exceptions.InvalidArraysLength;
import fr.erias.candidateterms.exceptions.MyExceptions;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exceptions.UnfoundTokenInSentence;
import fr.erias.ctapi.process.FileProcessStatic;

public abstract class CTstringInput extends HttpServlet implements SingleThreadModel {
	
	public static int count = 1;
	
	final static Logger logger = LoggerFactory.getLogger(CTstringInputBody.class);

	protected Charset charset;

	// implement this function : 
	protected abstract String getStringInput(HttpServletRequest req) throws ServletException ;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		resp.setContentType("application/json");
		resp.setHeader("Content-Disposition","attachment;filename="+"candidateTerms.json");

		String reqCharset = req.getCharacterEncoding();
		if (reqCharset == null) {
			charset = Charset.forName("UTF-8");
		} else if (Charset.isSupported(reqCharset)) {
			charset = Charset.forName(reqCharset);
		} else {
			charset = Charset.forName("UTF-8");
		}

		String stringInput = getStringInput(req);

		logger.info("query"+count+ " receveid");
		count = count + 1;

		if (stringInput == null) {
			logger.info("Sentence is null, nothing to do");
			JSONObject obj = new JSONObject();
			obj.put("Error", "Nothing to do, file is empty");
			OutputStream os = resp.getOutputStream();
			os.write(obj.toString().getBytes());
			os.close();
			return;
		}
		try {
			FileProcessStatic.processText.processInput(stringInput);
		} catch (ProcessSentenceException | InvalidArraysLength | UnfoundTokenInSentence e) {
			MyExceptions.logMessage(logger, "Fail to send JSON CandidateTerms");
			MyExceptions.logException(logger, e);
			e.printStackTrace();
			logger.info(stringInput); // debug
		}
		JSONObject jsonArraySentences = FileProcessStatic.processText.getJsonArraySentences();
		JSONObject obj = new JSONObject();
		obj.put("CTextraction", jsonArraySentences);
		OutputStream os = resp.getOutputStream();
		os.write(obj.toString().getBytes());
		os.close();
	}
	
	protected static String convert(InputStream inputStream, Charset charset) throws IOException {
		try (Scanner scanner = new Scanner(inputStream, charset.name())) {
			return scanner.useDelimiter("\\A").next();
		}
	}
	
}
