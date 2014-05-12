package org.flyJenkins.analysis.strategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.strategy.model.WebAnalysisDto;
import org.flyJenkins.analysis.strategy.model.WebFilterDto;
import org.flyJenkins.analysis.strategy.model.WebServletDto;
import org.flyJenkins.common.util.CommonRegexUtil;
import org.flyJenkins.common.util.DomParserUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WEBAnalysisStrategy extends FileAnalysisStrategy {

	@Override
	public HashMap<String, Object> getResultAnalisysInfo(FileAnalysisDto fileAnalysisDto) {
		Document xmldoc = DomParserUtil.getXmlSourceParsing(fileAnalysisDto.getSourceData());
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
		
		HashMap<String, Object> resultAnalysisMap = new HashMap<String, Object>();
		resultAnalysisMap.put("WEB", webAnalysisDto);
		return resultAnalysisMap;
	}
}
