package ord.flyJenkins.common.util;


import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.flyJenkins.analysis.strategy.model.PomAnalysisDto;
import org.flyJenkins.analysis.strategy.model.WebAnalysisDto;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class DomParserUtilTest {

	@Test
	@Ignore
	public void testConvertNodesFromXml() throws Exception {

		SVNURL svnUrl = SVNURL.parseURIEncoded("https://github.com/realwater/GitHubApi");
		SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream( );
		repository.getFile( "/trunk/pom.xml" , -1 , null , byteStream );
		String sourceData = byteStream.toString();

		JAXBContext jc = JAXBContext.newInstance(PomAnalysisDto.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        PomAnalysisDto tests = (PomAnalysisDto) unmarshaller.unmarshal(new InputSource(new StringReader(sourceData)));

		System.out.println(tests.getModelVersion());
	}

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
	//@Ignore
	public void testWebParsing() throws SVNException {
		SVNURL svnUrl = SVNURL.parseURIEncoded("https://github.com/realwater/GitHubApi");
		SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream( );
		repository.getFile( "/trunk/src/main/webapp/WEB-INF/web.xml" , -1 , null , byteStream );
		String sourceData = byteStream.toString();

		Document xmldoc = DomParserUtil.getXmlSourceParsing(sourceData);
		Element webElement = xmldoc.getDocumentElement();

		WebAnalysisDto webAnlysisDto = new WebAnalysisDto();

		for (Node webChannel = webElement.getFirstChild(); webChannel != null; webChannel = webChannel.getNextSibling()) {
			if (webChannel.getNodeType() == Node.ELEMENT_NODE) {
				String channelName = webChannel.getNodeName();
				String fieldName = CommonRegexUtil.getExcludePrefrenceCont(channelName);

				try {
					// WebAnalysisDto에 필드가 있는지 체크
					if (WebAnalysisDto.class.getDeclaredField(fieldName) != null) {
						System.out.println(fieldName);




					}
				} catch (Exception e) {}
			}
		}
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
