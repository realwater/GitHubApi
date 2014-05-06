package ord.flyJenkins.common.util;


import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.flyJenkins.analisys.strategy.model.PomCommonDto;
import org.flyJenkins.analisys.strategy.model.PomDependencyDto;
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

import scala.collection.mutable.HashMap;

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
		
		JAXBContext jc = JAXBContext.newInstance(PomCommonDto.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        PomCommonDto tests = (PomCommonDto) unmarshaller.unmarshal(new InputSource(new StringReader(sourceData)));
		
		System.out.println(tests.getModelVersion());
	}

	@Test	
	//@Ignore
	public void testPomParsing() throws SVNException {		
		SVNURL svnUrl = SVNURL.parseURIEncoded("https://github.com/realwater/GitHubApi");		
		SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream( );
		repository.getFile( "/trunk/pom.xml" , -1 , null , byteStream );
		String sourceData = byteStream.toString();
			
		// XML DOM Parsing and Object unMarshaller
		Document xmldoc = DomParserUtil.getXmlSourceParsing(sourceData);

		String modelVersion = xmldoc.getElementsByTagName("modelVersion").item(0).getTextContent();
		String artifactId 	= xmldoc.getElementsByTagName("artifactId").item(0).getTextContent();
		String groupId 		= xmldoc.getElementsByTagName("groupId").item(0).getTextContent();
		String name 		= xmldoc.getElementsByTagName("name").item(0).getTextContent();
		String version 		= xmldoc.getElementsByTagName("version").item(0).getTextContent();
		String packaging 	= xmldoc.getElementsByTagName("packaging").item(0).getTextContent();
		HashMap<String, String> properties = new HashMap<String, String>();		
		List<PomDependencyDto> pomDependencyList = new ArrayList<PomDependencyDto>();
		
		// POM Properties
		NodeList propertiesNodeList = xmldoc.getElementsByTagName("properties");
		for (Node propertiesNode : DomParserUtil.asList(propertiesNodeList)) {
			for (Node nodeChannel = propertiesNode.getFirstChild(); nodeChannel != null; nodeChannel = nodeChannel.getNextSibling()) {
				if (nodeChannel.getNodeType() == Node.ELEMENT_NODE) {
					properties.put(nodeChannel.getNodeName().toString(), nodeChannel.getTextContent().toString());
				}					
			}
		}
				
		// POM Dependency
		NodeList dependencyNodeList = xmldoc.getElementsByTagName("dependency");		
		for (Node dependencyNode : DomParserUtil.asList(dependencyNodeList)) {
			
			PomDependencyDto pomDependencyDto = new PomDependencyDto();
			
			for (Node nodeChannel = dependencyNode.getFirstChild(); nodeChannel != null; nodeChannel = nodeChannel.getNextSibling()) {
				
				if (nodeChannel.getNodeType() == Node.ELEMENT_NODE) {
					
					// 필드와 XML 필드명이 같은지 체크 있다면 필드에 맞는 setter 메서드 실행
					for (Field field : pomDependencyDto.getClass().getDeclaredFields()) {
						if (field.getName().equals(nodeChannel.getNodeName())) {							
							String methodName = "set"+nodeChannel.getNodeName().substring(0, 1).toUpperCase()+nodeChannel.getNodeName().substring(1, nodeChannel.getNodeName().length());							
							
							Class thisClass = pomDependencyDto.getClass();
							Method method = null;
							Class[] parameterTypes = new Class[] { String.class };  // 파라미터가 String 한개
						    Object[] arguments = null;
						    
						    arguments = new Object[] { new String(nodeChannel.getTextContent()) };

						    try {
								method = thisClass.getMethod( methodName, parameterTypes );
								method.invoke(this, arguments);
							} catch (Exception e) {
								e.printStackTrace();
							}						   
							
							//pomDependencyDto.getClass().getMethod(methodName, cArg);
						}
						System.out.println(field.getName());
					}
					//pom.getMethod("", parameterTypes);
					//System.out.println(ch.getNodeName());
				}
				
			}
			
			pomDependencyList.add(pomDependencyDto);
		}
		
		//System.out.println(properties.get("java-version").toString());
						
		PomCommonDto pomCommonDto = new PomCommonDto();
		pomCommonDto.setModelVersion(modelVersion);
		pomCommonDto.setArtifactId(artifactId);
		pomCommonDto.setGroupId(groupId);
		pomCommonDto.setName(name);
		pomCommonDto.setVersion(version);
		pomCommonDto.setPackaging(packaging);

	}
	
	@Test
	@Ignore
	public void testWebParsing() throws SVNException {
		SVNURL svnUrl = SVNURL.parseURIEncoded("https://github.com/realwater/GitHubApi");		
		SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream( );
		repository.getFile( "/trunk/src/main/webapp/WEB-INF/web.xml" , -1 , null , byteStream );
		String sourceData = byteStream.toString();
		
		Document xmldoc = DomParserUtil.getXmlSourceParsing(sourceData);
		Element root = xmldoc.getDocumentElement();

		for(Node ch = root.getFirstChild(); ch != null; ch = ch.getNextSibling()) {
			System.out.println(ch.getNodeName());
		}
	}
}
