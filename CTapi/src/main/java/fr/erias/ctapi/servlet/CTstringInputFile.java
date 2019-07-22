package fr.erias.ctapi.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.erias.candidateterms.exceptions.MyExceptions;

public class CTstringInputFile extends CTstringInput implements SingleThreadModel{
	
	final static Logger logger = LoggerFactory.getLogger(CTstringInputFile.class);

	@Override
	protected synchronized String getStringInput(HttpServletRequest req) throws ServletException {
		InputStream xmlFileIn = null ; // get InputStream of XMLFile
		String sentence = null;
		try{
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			logger.info("Number of file received : " + items.size());
			for (FileItem item : items) {
				xmlFileIn = item.getInputStream();
				logger.info("number of bytes received : " + xmlFileIn.available());
				break; // only the first file will be treated
			}
			sentence = convert(xmlFileIn, charset);
			xmlFileIn.close();
		} catch (FileUploadException | IOException e) {
			MyExceptions.logMessage(logger, "Something went wrong with FileUpload");
			MyExceptions.logException(logger, e);
			throw new ServletException("Cannot parse multipart request.", e);
		}
		return(sentence);
	};
}
