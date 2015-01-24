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
import co.com.vision.prueba.utils.ListTools;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class WorkflowProcessesParser {

	private static String WORKFLOW_PROCESS = "WorkflowProcess";
	private static String ACTIVITIES = "Activities";
	private static String ACTIVITY = "Activity";
	private static String TRANSITIONS = "Transitions";
	private static String TRANSITION = "Transition";

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

			Element activitiesParent = (Element) workProcessElement
					.getElementsByTagName(ACTIVITIES).item(0);
			Element transitionsParent = (Element) workProcessElement
					.getElementsByTagName(TRANSITIONS).item(0);

			NodeList domainNodes = activitiesParent
					.getElementsByTagName(ACTIVITY);

			NodeList transitions = transitionsParent
					.getElementsByTagName(TRANSITION);

			if (domainNodes.getLength() > 0) {
				HashMap<NodeType, List<co.com.vision.prueba.domain.Node>> nodes = NodeParser
						.parseNodes(domainNodes);

				List<co.com.vision.prueba.domain.Node> activities = nodes
						.get(NodeType.ACTIVITY);
				List<co.com.vision.prueba.domain.Node> events = nodes
						.get(NodeType.EVENT);

				List<Transition> realTransitions = TransitionParser
						.parseTransitions(transitions,
								ListTools.joinLists(activities, events));

				nodes.get(NodeType.ACTIVITY)
						.stream()
						.forEach(
								activity -> System.out.println(activity
										.getName()));

				workflowProcess.setActivities(Optional.of(nodes
						.get(NodeType.ACTIVITY)));
				workflowProcess
						.setEvents(Optional.of(nodes.get(NodeType.EVENT)));
				workflowProcess.setTransitions(realTransitions);
			} else {
				workflowProcess.setActivities(Optional.empty());
				workflowProcess.setEvents(Optional.empty());
			}

			workflowProcess.setId(processId);
			workflowProcess.setName(processName);
			workflowProcesses.add(workflowProcess);
		}
		return workflowProcesses;
	}
}
