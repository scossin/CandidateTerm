package fr.erias.ctapi.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.candidateTerm.CandidateTermLemma;
import fr.erias.candidateterms.exceptions.MyExceptions;
import fr.erias.candidateterms.exceptions.ProcessSentenceException;
import fr.erias.candidateterms.exportCandidateTerms.ToJsonCTlemma;
import fr.erias.ctapi.process.FileProcessStatic;

public class PostLemmaTerm extends HttpServlet implements SingleThreadModel  {
	
	final static Logger logger = LoggerFactory.getLogger(PostLemmaTerm.class);

	protected Charset charset;
	
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

		logger.info("New query receveid asked !");

		if (stringInput == null) {
			logger.info("Sentence is null, nothing to do");
			JSONObject obj = new JSONObject();
			obj.put("Error", "Nothing to do, file is empty");
			OutputStream os = resp.getOutputStream();
			os.write(obj.toString().getBytes());
			os.close();
			return;
		}
		JSONObject obj = new JSONObject();
		JSONObject lemmaJSONobject = new JSONObject();
		try {
			CandidateTermLemma candidateTermLemma = FileProcessStatic.processText.getCandidateTermsLemmaExtraction().getCandidateTermLemma(stringInput);
			ToJsonCTlemma toJsonCTlemma = new ToJsonCTlemma();
			toJsonCTlemma.setCandidateTermLemma(candidateTermLemma);
			lemmaJSONobject = toJsonCTlemma.getJSONobject();
		} catch (ProcessSentenceException e) {
			MyExceptions.logMessage(logger, "Fail to send JSON CandidateTerms");
			MyExceptions.logException(logger, e);
			e.printStackTrace();
		}
		obj.put("CTextraction", lemmaJSONobject);
		OutputStream os = resp.getOutputStream();
		os.write(obj.toString().getBytes());
		os.close();
	}
	
	protected synchronized String getStringInput(HttpServletRequest req) throws ServletException {
		logger.info("CTstringInputBody method called");
		ServletInputStream in;
		String stringInput = null;
		try {
			in = req.getInputStream();
			stringInput = CTstringInput.convert(in, charset);
			in.close();
		} catch (IOException e) {
			MyExceptions.logMessage(logger, "Something went wrong ready the file");
			MyExceptions.logException(logger, e);
			throw new ServletException();
		}
		logger.info("stringInput has " + stringInput.getBytes().length + " bytes");
		return(stringInput);
	}
}
