package org.flyJenkins.analisys.handler;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PomParserHandler extends SaxParserHandler {
	
	private String artifactId;
	
	private String elementName = "";
	
	private HashMap<String, String> properties;
	
	private HashMap<String, String> dependencies;
			
	public Object getParsedData() {
		
		
		return null;
	}
		
	@Override
	public void startDocument() throws SAXException {
		
	}
	
	@Override
	public void endDocument() throws SAXException {
		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println(qName);
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.println((new String(ch, start, length)));
	}
	
}
