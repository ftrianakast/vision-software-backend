package co.com.vision.prueba.services.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import co.com.vision.prueba.domain.Transition;

public class TransitionParser {

	/**
	 * Parse transitions
	 * @param transitionsNodes
	 * @param nodes
	 * @return
	 */
	public static List<Transition> parseTransitions(NodeList transitionsNodes,
			List<co.com.vision.prueba.domain.Node> nodes) {
		List<Transition> transitions = new ArrayList<Transition>();

		for (int i = 0; i < transitionsNodes.getLength(); i++) {
			String id = "";
			String fromId = "";
			String toId = "";

			org.w3c.dom.Node actualTransition = transitionsNodes.item(i);
			Element actualTransitionElement = (Element) actualTransition;

			id = actualTransitionElement.getAttribute("Id");
			fromId = actualTransitionElement.getAttribute("From");
			toId = actualTransitionElement.getAttribute("To");

			Optional<co.com.vision.prueba.domain.Node> fromNode = searchNode(
					fromId, nodes);
			Optional<co.com.vision.prueba.domain.Node> toNode = searchNode(
					toId, nodes);

			if (fromNode.isPresent() && toNode.isPresent()) {
				Transition currentTransition = new Transition(id,
						fromNode.get(), toNode.get());
				transitions.add(currentTransition);
			}
		}
		return transitions;
	}

	/**
	 * 
	 * @param id
	 * @param nodes
	 * @return
	 */
	private static Optional<co.com.vision.prueba.domain.Node> searchNode(
			String id, List<co.com.vision.prueba.domain.Node> nodes) {
		return nodes.stream().filter(node -> node.getId().equals(id))
				.findFirst();
	}

}
