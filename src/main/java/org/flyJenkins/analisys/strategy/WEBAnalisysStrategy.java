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

public class WEBAnalisysStrategy extends FileAnalisysStrategy {

	@Override
	public HashMap<String, Object> getResultAnalisysInfo(FileAnalisysDto fileAnalisysDto) {
	
		return null;
	}
}
