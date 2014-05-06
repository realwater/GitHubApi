package org.flyJenkins.analisys.strategy;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.flyJenkins.analisys.handler.PomParserHandler;
import org.flyJenkins.analisys.handler.SaxParserHandler;
import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class POMAnalisysStrategy extends FileAnalisysStrategy {

	@Override
	public HashMap<String, Object> getResultAnalisysInfo(FileAnalisysDto fileAnalisysDto) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = saxParserFactory.newSAXParser();			
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.parse(new InputSource( new StringReader( fileAnalisysDto.getSourceData() )));
			
			if (fileAnalisysDto.getFileName().equals("pom")) {
				SaxParserHandler parserHandler = new PomParserHandler();
				xmlReader.setContentHandler(parserHandler);
				
				parserHandler.getParsedData();
				
				
			} else if (fileAnalisysDto.getFileName().equals("web")) {
				
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		//myExampleHandler.getParsedData();
		return null;
	}
}
