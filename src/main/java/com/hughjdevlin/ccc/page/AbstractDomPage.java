package com.hughjdevlin.ccc.page;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

public class AbstractDomPage extends AbstractPage {
	protected Document document;
	
	public AbstractDomPage(URL url) throws ParserConfigurationException, IOException, SAXException {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Tidy tidy = new Tidy();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		URLConnection conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		tidy.setXHTML(true); 
		Document tidyDocument = tidy.parseDOM(in, null);
		tidy.pprint(tidyDocument, baos);
		document = db.parse(new ByteArrayInputStream(baos.toByteArray()));
	}

	public AbstractDomPage(String url) throws MalformedURLException, ParserConfigurationException, IOException, SAXException {
		this(getUrl(url));
	}

	protected NodeList getRows(String tableId) {
		Element table = document.getElementById(tableId);
		Element tbody = (Element) table.getElementsByTagName("tbody").item(0);
		return tbody.getElementsByTagName("tr");	
	}

}
