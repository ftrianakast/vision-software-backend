package co.com.vision.prueba.services.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.com.vision.prueba.domain.NodeType;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.WorkflowProcess;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class WorkflowProcessesParser {
	private static String WORKFLOW_PROCESS = "WorkflowProcess";

	/**
	 * 
	 * @param document
	 * @return
	 */
	public static Optional<List<WorkflowProcess>> parseXMLWorkflowProcesses(
			Document document) {
		List<WorkflowProcess> workflowProcesses = parseWorkflow(document);
		Optional<List<WorkflowProcess>> response = (workflowProcesses.size() > 0) ? Optional
				.of(workflowProcesses) : Optional.empty();
		return response;
	}

	/**
	 * 
	 * @param xmlDocument
	 * @return
	 */
	private static List<WorkflowProcess> parseWorkflow(Document xmlDocument) {
		List<WorkflowProcess> workflowProcesses = new ArrayList<WorkflowProcess>();
		NodeList nodeWorkFlowList = xmlDocument
				.getElementsByTagName(WORKFLOW_PROCESS);

		for (int i = 0; i < nodeWorkFlowList.getLength(); i++) {
			WorkflowProcess workflowProcess = new WorkflowProcess();
			Node workProcessNode = nodeWorkFlowList.item(i);
			Element workProcessElement = (Element) workProcessNode;
			String processId = workProcessElement.getAttribute("Id");
			String processName = workProcessElement.getAttribute("Name");
			NodeList domainNodes = workProcessElement
					.getElementsByTagName("Activity");
			NodeList transitions = workProcessElement
					.getElementsByTagName("Transition");

			HashMap<NodeType, List<co.com.vision.prueba.domain.Node>> nodes = NodeParser
					.parseNodes(domainNodes);

			List<co.com.vision.prueba.domain.Node> activities = nodes
					.get(NodeType.ACTIVITY);
			List<co.com.vision.prueba.domain.Node> events = nodes
					.get(NodeType.EVENT);
			activities.addAll(events);

			List<Transition> realTransitions = TransitionParser
					.parseTransitions(transitions, activities);

			workflowProcess.setId(processId);
			workflowProcess.setName(processName);
			workflowProcess.setActivities(nodes.get(NodeType.ACTIVITY));
			workflowProcess.setEvents(nodes.get(NodeType.EVENT));
			workflowProcess.setTransitions(realTransitions);
			workflowProcesses.add(workflowProcess);
		}
		return workflowProcesses;
	}
}
