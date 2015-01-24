package co.com.vision.prueba.services.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.com.vision.prueba.domain.NodeType;
import co.com.vision.prueba.domain.activity.Activity;

public class NodeParser {

	private static String EVENT = "Event";
	private static String IMPLEMENTATION = "Implementation";

	/**
	 * Parse all the nodes (activities and nodes)
	 * 
	 * @param nodeList
	 * @return
	 */
	public static HashMap<NodeType, List<co.com.vision.prueba.domain.Node>> parseNodes(
			NodeList nodeList) {
		HashMap<NodeType, List<co.com.vision.prueba.domain.Node>> nodes = new HashMap<NodeType, List<co.com.vision.prueba.domain.Node>>();
		List<co.com.vision.prueba.domain.Node> activities = new ArrayList<co.com.vision.prueba.domain.Node>();
		List<co.com.vision.prueba.domain.Node> events = new ArrayList<co.com.vision.prueba.domain.Node>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Element nodeElement = (Element) node;
			String nodeId = nodeElement.getAttribute("Id");
			String nodeName = nodeElement.getAttribute("Name");

			NodeList eventNodes = nodeElement.getElementsByTagName(EVENT);
			NodeList implementationNodes = nodeElement
					.getElementsByTagName(IMPLEMENTATION);

			if (eventNodes.getLength() > 0) {
				events.add((co.com.vision.prueba.domain.Node) EventParser
						.parseEvent(nodeElement, nodeId, nodeName));
				nodes.put(NodeType.EVENT, events);
			} else if (implementationNodes.getLength() > 0) {
				Activity activity = new Activity(nodeId, nodeName, "",
						Optional.empty());
				activities.add((co.com.vision.prueba.domain.Node) activity);
				nodes.put(NodeType.ACTIVITY, activities);
			}
		}
		return nodes;
	}
}
