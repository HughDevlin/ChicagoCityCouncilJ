/*
 * source of sponsors
 */
package com.hughjdevlin.ccc.page;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LegislationPage extends AbstractDomPage {
	
	/**
	 * @param url
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws MalformedURLException 
	 */
	public LegislationPage(String url) throws MalformedURLException, ParserConfigurationException, IOException, SAXException {
		super(url);
	}
	
	public List<String> sponsors() {
		List<String> result = new ArrayList<String>();
		NodeList trs = getRows("ctl00_ContentPlaceHolder1_tblSponsors");
    	Element tr = (Element) trs.item(0);
    	NodeList as = tr.getElementsByTagName("a");
	    for(int i = 0; i < as.getLength(); i++) { // skip label
	    	Element a = (Element) as.item(i);
	    	String name = normalize(a.getTextContent());
			result.add(name);
	    }
		return result;
	}

	public String pdf() {
		NodeList trs = getRows("ctl00_ContentPlaceHolder1_tblAttachments");
    	Element tr = (Element) trs.item(0);
    	NodeList as = tr.getElementsByTagName("a");
	    Element a = (Element) as.item(0);
	    return a.getAttribute("href");
	}

}
