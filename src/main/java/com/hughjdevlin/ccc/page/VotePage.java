package com.hughjdevlin.ccc.page;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class VotePage extends AbstractDomPage {
	
	/**
	 * @param url
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws MalformedURLException 
	 */
	public VotePage(String url) throws MalformedURLException, ParserConfigurationException, IOException, SAXException {
		super(url);
	}

	/**
	 * @return map name -> vote
	 */
	public Map<String, String> votes() {
		Map<String, String> result = new HashMap<String, String>();
		NodeList trs = getRows("ctl00_ContentPlaceHolder1_gridVote_ctl00");
		if(trs.getLength() > 1) { // placed on file?
			for(int i = 0; i < trs.getLength(); i++) {
				Element tr = (Element) trs.item(i);
				NodeList tds = tr.getElementsByTagName("td");
		    	if(tds.getLength() < 2)
		    		continue;
		    	String name = normalize(tds.item(0).getTextContent());
		    	String vote = normalize(tds.item(1).getTextContent());
		    	result.put(name, vote);
		    } // end for
		} // end if
	 	return result;
	}

}
