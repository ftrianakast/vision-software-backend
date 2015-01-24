package co.com.vision.prueba.services.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.WorkflowProcess;

public class MessageFlowParser {

	/**
	 * 
	 * @param messageFlowsNodes
	 * @param workflowProcesses
	 * @return
	 */
	public static Optional<List<Transition>> parseMessageFlows(
			NodeList messageFlowsNodes, List<WorkflowProcess> workflowProcesses) {
		List<Transition> messageFlows = new ArrayList<Transition>();
		for (int i = 0; i < messageFlowsNodes.getLength(); i++) {
			org.w3c.dom.Node messageFlowNode = messageFlowsNodes.item(i);
			Element nodeElement = (Element) messageFlowNode;
			Node sourceNode = findNodeInWorkFlowProcesses(
					nodeElement.getAttribute("Source"), workflowProcesses);
			Node targetNode = findNodeInWorkFlowProcesses(
					nodeElement.getAttribute("Target"), workflowProcesses);
			String name = nodeElement.getAttribute("Name");
			messageFlows.add(new Transition(name, sourceNode, targetNode));
		}

		return Optional.of(messageFlows);

	}

	/**
	 * 
	 * @param id
	 * @param workflowProcesses
	 * @return
	 */
	private static Node findNodeInWorkFlowProcesses(String id,
			List<WorkflowProcess> workflowProcesses) {
		return workflowProcesses
				.stream()
				.filter(workflowProcess -> workflowProcess.getNodes()
						.isPresent())
				.map(workFlowProcess -> workFlowProcess.getNodes().get())
				.flatMap((workFlowProcessList) -> workFlowProcessList.stream())
				.filter(node -> node.getId().equals(id)).findFirst().get();
	}

}
