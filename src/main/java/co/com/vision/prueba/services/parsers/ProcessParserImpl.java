package co.com.vision.prueba.services.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.WorkflowProcess;

public class ProcessParserImpl implements ProcessParser {

	@Override
	public Optional<Process> parseProcess(byte[] document) {
		Optional<Document> xmlDocument = getReadXMLObject(document);
		if (xmlDocument.isPresent()) {
			List<WorkflowProcess> workflowProcesses = parseWorkflowProcesses(xmlDocument
					.get());
			NodeList messageFlowsNodeList = xmlDocument.get()
					.getElementsByTagName("MessageFlow");
			List<Transition> messageFlows = MessageFlowParser
					.parseMessageFlows(messageFlowsNodeList, workflowProcesses);
			return Optional.of(new Process(workflowProcesses, messageFlows));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * 
	 * @param xmlDocument
	 * @return
	 */
	public List<WorkflowProcess> parseWorkflowProcesses(Document xmlDocument) {
		List<WorkflowProcess> workflowProcesses = new ArrayList<WorkflowProcess>();
		Optional<List<WorkflowProcess>> workflowProcessesOptional = WorkflowProcessesParser
				.parseXMLWorkflowProcesses(xmlDocument);

		if (workflowProcessesOptional.isPresent()) {
			workflowProcesses = workflowProcessesOptional.get();
		}
		return workflowProcesses;
	}

	/**
	 * 
	 * @param document
	 * @return
	 * @throws Exception
	 */
	private static Optional<Document> getReadXMLObject(byte[] document) {
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
	private static Document getXMLObjectRepresentation(byte[] document)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new ByteArrayInputStream(document));
	}

}
