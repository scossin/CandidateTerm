package fr.erias.ctapi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.exceptions.MyExceptions;

public class CTstringInputBody extends CTstringInput implements SingleThreadModel {
	
	final static Logger logger = LoggerFactory.getLogger(CTstringInputBody.class);

	@Override
	protected synchronized String getStringInput(HttpServletRequest req) throws ServletException {
		logger.info("CTstringInputBody method called");
		ServletInputStream in;
		String stringInput = null;
		try {
			in = req.getInputStream();
			stringInput = convert(in, charset);
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
