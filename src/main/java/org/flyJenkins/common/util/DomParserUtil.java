package org.flyJenkins.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DomParserUtil {

	/**
	 * DOM Parsing
	 * @param source
	 * @return
	 */
	public static Document getXmlSourceParsing(String source) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser;
		Document xmldoc = null;
		
		try {
			parser = dbf.newDocumentBuilder();
			xmldoc = parser.parse(new InputSource(new StringReader(source)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return xmldoc;
	}
	
	/**
	 * DOM Node List 형으로 변환 
	 * @param nodeList
	 * @return
	 */
	public static List<Node> asList(NodeList nodeList) {		
		 return nodeList.getLength()==0?
		      Collections.<Node>emptyList(): new NodeListWrapper(nodeList);
	}
	static final class NodeListWrapper extends AbstractList<Node> {
	    private NodeList nodeList;
	    NodeListWrapper(NodeList nodeList) {
	    	this.nodeList = nodeList;
	    }
	    public Node get(int index) {
	      return nodeList.item(index);
	    }
	    public int size() {
	      return nodeList.getLength();
	    }
	}
}
