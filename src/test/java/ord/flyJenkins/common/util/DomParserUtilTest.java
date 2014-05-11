package ord.flyJenkins.common.util;


import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.flyJenkins.analysis.strategy.model.PomAnalysisDto;
import org.flyJenkins.analysis.strategy.model.WebAnalysisDto;
import org.flyJenkins.analysis.strategy.model.WebFilterDto;
import org.flyJenkins.analysis.strategy.model.WebServletDto;
import org.flyJenkins.common.util.CommonRegexUtil;
import org.flyJenkins.common.util.DomParserUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class DomParserUtilTest {	

	/**
	 * POM 분석 Test
	 * @throws SVNException
	 */
	@Test
	@Ignore
	public void testPomParsing() throws SVNException {
		SVNURL svnUrl = SVNURL.parseURIEncoded("https://github.com/realwater/GitHubApi");
		SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream( );
		repository.getFile( "/trunk/pom.xml" , -1 , null , byteStream );
		String sourceData = byteStream.toString();

		// XML DOM Parsing and Object unMarshaller
		Document xmldoc = DomParserUtil.getXmlSourceParsing(sourceData);
		PomAnalysisDto pomAnalysisDto = new PomAnalysisDto();
		HashMap<String, String> properties = new HashMap<String, String>();
		List<HashMap<String, String>> pomDependencyList = new ArrayList<HashMap<String, String>>();

		for (Field objectField : PomAnalysisDto.class.getDeclaredFields()) {

			String objectFieldName = objectField.getName();
			String pomCommonMethodName = "set"+objectFieldName.substring(0, 1).toUpperCase()+objectFieldName.substring(1, objectFieldName.length());
			Method pomCommonMethod = null;

			// POM Properties Setting
			if (objectFieldName.equals("properties")) {
				NodeList propertiesNodeList = xmldoc.getElementsByTagName("properties");
				for (Node propertiesNode : DomParserUtil.asList(propertiesNodeList)) {
					for (Node nodeChannel = propertiesNode.getFirstChild(); nodeChannel != null; nodeChannel = nodeChannel.getNextSibling()) {
						if (nodeChannel.getNodeType() == Node.ELEMENT_NODE) {
							properties.put(nodeChannel.getNodeName().toString(), nodeChannel.getTextContent().toString());
						}
					}
				}

				try {
					pomCommonMethod = pomAnalysisDto.getClass().getMethod(pomCommonMethodName, HashMap.class);
		    		pomCommonMethod.invoke(pomAnalysisDto, properties);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// POM Dependency Setting
			else if (objectFieldName.equals("dependency")) {
				NodeList dependencyNodeList = xmldoc.getElementsByTagName("dependency");
				for (Node dependencyNode : DomParserUtil.asList(dependencyNodeList)) {
					HashMap<String, String> pomDependencyMap = new HashMap<String, String>();
					for (Node nodeChannel = dependencyNode.getFirstChild(); nodeChannel != null; nodeChannel = nodeChannel.getNextSibling()) {
						if (nodeChannel.getNodeType() == Node.ELEMENT_NODE) {
							// 필드와 XML 필드명이 같은지 체크 있다면 필드에 맞는 setter 메서드 실행
							String value = nodeChannel.getTextContent().toString();

							if (nodeChannel.getNodeName().equals("version")) {
								Pattern p = Pattern.compile(".*[^가-힣a-zA-Z0-9.].*");
								Matcher m = p.matcher(value);

								if(m.matches()) {
									value = properties.get(CommonRegexUtil.getExcludePrefrenceCont(value));
								}
							}
							pomDependencyMap.put(nodeChannel.getNodeName(), value);
						}
					}
					pomDependencyList.add(pomDependencyMap);
				}

				try {
					pomCommonMethod = pomAnalysisDto.getClass().getMethod(pomCommonMethodName, List.class);
		    		pomCommonMethod.invoke(pomAnalysisDto, pomDependencyList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// POM Element Setting
			else {
				String elementValue = xmldoc.getElementsByTagName(objectField.getName()).item(0).getTextContent();

				if (!elementValue.isEmpty()) {
					try {
				    	pomCommonMethod = pomAnalysisDto.getClass().getMethod(pomCommonMethodName, String.class);
				    	pomCommonMethod.invoke(pomAnalysisDto, elementValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println(pomAnalysisDto);
	}

	/**
	 * Web.xml 분석 Test
	 * @throws SVNException
	 */
	@Test
	@Ignore
	public void testWebParsing() throws SVNException {
		SVNURL svnUrl = SVNURL.parseURIEncoded("https://github.com/realwater/GitHubApi");
		SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream( );
		repository.getFile( "/trunk/src/main/webapp/WEB-INF/web.xml" , -1 , null , byteStream );
		String sourceData = byteStream.toString();

		Document xmldoc = DomParserUtil.getXmlSourceParsing(sourceData);
		Element webElement = xmldoc.getDocumentElement();
		
		WebAnalysisDto webAnalysisDto = new WebAnalysisDto();
		webAnalysisDto.setServletVersion(xmldoc.getElementsByTagName("web-app").item(0).getAttributes().getNamedItem("version").getNodeValue());
				
		// web.xml에 중복되는 엘리먼트 제외 후 NodeList Type으로 HashMap에 저장
		HashMap<String, String> webElementMap = new HashMap<String, String>();
		for (Node webChannel = webElement.getFirstChild(); webChannel != null; webChannel = webChannel.getNextSibling()) {
			if(webElementMap.get(webChannel.getNodeName()) == null) {
				webElementMap.put(webChannel.getNodeName(), null);
				
				NodeList nodeList = xmldoc.getElementsByTagName(webChannel.getNodeName());
			    String channelName = webChannel.getNodeName();
				String fieldName = CommonRegexUtil.getExcludePrefrenceCont(channelName);	
				String webAnalysisMethodName = "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
				Method webAnalysisMethod = null;
				
				// WebAnalysisDto에 필드가 있는지 체크
				try {
					if (WebAnalysisDto.class.getDeclaredField(fieldName) != null) {			
					    HashMap<String, String> dataStringMap = null;
					    List<Object> dataObjectList = new ArrayList<Object>();
					    
					    Object dataObject = null;
						String genericType = null;
					    try {
					    	Field objectField = WebAnalysisDto.class.getDeclaredField(fieldName);
					    	Type fieldType = objectField.getGenericType(); 
							if (fieldType instanceof ParameterizedType) {
							    ParameterizedType ptype = (ParameterizedType) fieldType;
							    genericType = ptype.getActualTypeArguments()[0].toString();
							    
							    if (genericType.indexOf("HashMap") > 0) {
							    	dataStringMap = new HashMap<String, String>();
							    } else if (genericType.indexOf("WebFilterDto") > 0) {
							    	dataObject = new WebFilterDto();
							    } else if (genericType.indexOf("WebServletDto") > 0) {
							    	dataObject = new WebServletDto();
							    }
							}
					    } catch (Exception e) {}	
					
					    for (Node channelNode : DomParserUtil.asList(nodeList)) {
							// web.xml web-app 각각 하위 엘리먼트들의 자식 노드 수 만큼 처리
							List<HashMap<String, String>> initParamList = new ArrayList<HashMap<String, String>>();	
					    	for (Node lowElementNode = channelNode.getFirstChild(); lowElementNode != null; lowElementNode = lowElementNode.getNextSibling()) {			    						    		
								if (lowElementNode.getNodeType() == Node.ELEMENT_NODE) {					    		
									String lowElementNodeName = CommonRegexUtil.getExcludePrefrenceCont(lowElementNode.getNodeName());								
									String lowElementNodeValue = lowElementNode.getTextContent().toString();
									String lowElementMethodName = "set"+lowElementNodeName.substring(0, 1).toUpperCase()+lowElementNodeName.substring(1, lowElementNodeName.length());
									Method lowElementMethod = null;
									
									// init-param 일 경우 자식 노드가 존재하므로 별도 처리 한다.
									if (lowElementNodeName.equals("initparam")) {
										HashMap<String, String> initParamMap = new HashMap<String, String>();
										for (Node initNode = lowElementNode.getFirstChild(); initNode != null; initNode = initNode.getNextSibling()) {
											if (initNode.getNodeType() == Node.ELEMENT_NODE) {											
												initParamMap.put(initNode.getNodeName(), initNode.getTextContent().toString());											
											}
										}
										initParamList.add(initParamMap);
										
										try {
					    		    		lowElementMethod = dataObject.getClass().getMethod(lowElementMethodName, List.class);
					    		    		lowElementMethod.invoke(dataObject, initParamList);
					    				} catch (Exception e) {
					    					e.printStackTrace();
					    				}
									} else {
										if (genericType.indexOf("HashMap") > 0) {
											dataStringMap.put(lowElementNodeName, lowElementNodeValue);
						    		    } else {		    		    	
						    		    	try {
						    		    		lowElementMethod = dataObject.getClass().getMethod(lowElementMethodName, String.class);
						    		    		lowElementMethod.invoke(dataObject, lowElementNodeValue);
						    				} catch (Exception e) {
						    					e.printStackTrace();
						    				}
						    		    }
									}
								}
							}
					    	
					    	if (dataStringMap != null) {
					    		dataObjectList.add(dataStringMap);
					    	} else if (dataObject != null) {
					    		dataObjectList.add(dataObject);
					    	}	    	
					    }
					    
					    // webAnalysisDto에 저장
						try {				    		
							webAnalysisMethod = webAnalysisDto.getClass().getMethod(webAnalysisMethodName, List.class);							
							webAnalysisMethod.invoke(webAnalysisDto, dataObjectList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
				} 
			}
		}
		
		System.out.println(webAnalysisDto);
	}

	@Test
	@Ignore
	public void testMatch() {
		String value = "${spring.for.ffff}";

		Pattern p = Pattern.compile(".*[^가-힣a-zA-Z0-9.].*");
		Matcher m = p.matcher(value);

		if(m.matches()) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z.\\s]";
			value =value.replaceAll(match, "");
		} else {
			System.out.println("한글/영문/숫자만 있고만");
		}
	}
}
