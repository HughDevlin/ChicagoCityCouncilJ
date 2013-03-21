package com.hughjdevlin.ccc.page;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hughjdevlin.ccc.Person;

public class PeoplePage extends AbstractDomPage {
	
	public PeoplePage() throws MalformedURLException, ParserConfigurationException, IOException, SAXException {
		super("People.aspx");
	}
	
	public List<Person> persons() throws IOException {
		List<Person> result = new ArrayList<Person>();
		NodeList trs = getRows("ctl00_ContentPlaceHolder1_gridPeople_ctl00");
	    for(int i = 0; i < trs.getLength(); i++) {
	    	Element tr = (Element) trs.item(i);
	    	NodeList tds = tr.getElementsByTagName("td");
	    	if(tds.getLength() < 2)
	    		continue;
	    	Element td0 = (Element) tds.item(0); // 1st column
	    	String name = normalize(td0.getTextContent());
	    	Element a = (Element) td0.getElementsByTagName("a").item(0);
	    	String href = a.getAttribute("href");
	    	URL url1 = AbstractWebDriverPage.getUrl(StringUtils.substringBeforeLast(href, "&Search="));
	    	String role = normalize(tds.item(1).getTextContent()); // 2nd column
			result.add(new Person(name, url1, role));
	    }
		return result;
	}

}
