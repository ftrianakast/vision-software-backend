package co.com.vision.prueba.services.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.WorkflowProcess;

/**
 * 
 * @author Felipe Triana<frianakast@gmail.com>
 * @version 1.0
 */
public class ProcessParserImpl implements ProcessParser {
	@Override
	public Process parseProcess(Document xmlDocument) {
		List<WorkflowProcess> workflowProcesses = parseWorkflowProcesses(xmlDocument);
		NodeList messageFlowsNodeList = xmlDocument.getElementsByTagName(
				"MessageFlow");
		Optional<List<Transition>> transitions = MessageFlowParser
				.parseMessageFlows(messageFlowsNodeList, workflowProcesses);
		return new Process(workflowProcesses, transitions);
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

}
