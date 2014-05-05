package org.flyJenkins.analisys.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class SaxParserHandler extends DefaultHandler {

	public abstract Object getParsedData();
	
	@Override
	public abstract void startDocument() throws SAXException;
	
	@Override
	public abstract void endDocument() throws SAXException;
	
	@Override
	public abstract void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException;
	
	@Override
	public abstract void endElement(String uri, String localName, String qName) throws SAXException;
	
	@Override
	public abstract void characters(char[] ch, int start, int length) throws SAXException;
}