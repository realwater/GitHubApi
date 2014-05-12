package org.flyJenkins.analysis.strategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.strategy.model.PomAnalysisDto;
import org.flyJenkins.common.util.CommonRegexUtil;
import org.flyJenkins.common.util.DomParserUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class POMAnalysisStrategy extends FileAnalysisStrategy {

	/**
	 * POM XML 파일 분석
	 */
	@Override
	public HashMap<String, Object> getResultAnalisysInfo(FileAnalysisDto fileAnalysisDto) {

		// XML DOM Parsing and Object unMarshaller
		Document xmldoc = DomParserUtil.getXmlSourceParsing(fileAnalysisDto.getSourceData());
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

							// version 엘리먼트는 버전 정보를 properties에서 가져온다.
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


		HashMap<String, Object> resultAnalysisMap = new HashMap<String, Object>();
		resultAnalysisMap.put("POM", pomAnalysisDto);
		return resultAnalysisMap;
	}
}
