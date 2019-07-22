package fr.erias.candidateterms.utils;

public class Util {
	/**
	 * Transform an array of String to a String
	 * 
	 * @param anArray A string array
	 * @param sep A separator
	 * @return Concatenated String
	 */
	public static String arrayToString(String[] anArray, char sep){
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < anArray.length; i++) {
		  if (i != 0) {
			  strBuilder.append(sep);
		  }
		   strBuilder.append(anArray[i]);
		}
		String newString = strBuilder.toString();
		return(newString);
	}
}
