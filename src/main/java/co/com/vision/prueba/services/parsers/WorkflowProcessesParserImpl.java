package co.com.vision.prueba.services.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import co.com.vision.prueba.domain.NodeType;
import co.com.vision.prueba.domain.WorkflowProcess;

/**
 * 
 * @author Felipe Triana
 *
 */
public class WorkflowProcessesParserImpl implements WorkflowProcessesParser {

	private static String WORKFLOW_PROCESS = "WorkflowProcess";

	@Override
	public Optional<List<WorkflowProcess>> parseXMLWorkflowProcesses(
			byte[] document) {
		Optional<Document> xmlDocument = getReadXMLObject(document);
		if (xmlDocument.isPresent()) {
			return Optional.of(parseProcesses(xmlDocument.get()));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * 
	 * @param document
	 * @return
	 * @throws Exception
	 */
	private Optional<Document> getReadXMLObject(byte[] document) {
		try {
			return Optional.of(getXMLObjectRepresentation(document));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			return Optional.empty();
		}
	}

	/**
	 * 
	 * @param document
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private Document getXMLObjectRepresentation(byte[] document)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new ByteArrayInputStream(document));
	}

	/**
	 * 
	 * @param xmlDocument
	 * @return
	 */
	private List<WorkflowProcess> parseProcesses(Document xmlDocument) {
		List<WorkflowProcess> workflowProcesses = new ArrayList<WorkflowProcess>();
		NodeList nodeList = xmlDocument.getElementsByTagName(WORKFLOW_PROCESS);
		for (int i = 0; i < nodeList.getLength(); i++) {
			WorkflowProcess workflowProcess = new WorkflowProcess();
			Node workProcessNode = nodeList.item(i);
			Element workProcessElement = (Element) workProcessNode;
			String processId = workProcessElement.getAttribute("Id");
			String processName = workProcessElement.getAttribute("Name");
			NodeList domainNodes = workProcessElement
					.getElementsByTagName("Activity");
			HashMap<NodeType, List<co.com.vision.prueba.domain.Node>> nodes = NodeParser
					.parseNodes(domainNodes);

			workflowProcess.setId(processId);
			workflowProcess.setName(processName);
			workflowProcess.setActivities(nodes.get(NodeType.ACTIVITY));
			workflowProcess.setEvents(nodes.get(NodeType.EVENT));

		}
		return null;
	}
}
