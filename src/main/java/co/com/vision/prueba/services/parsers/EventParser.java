package co.com.vision.prueba.services.parsers;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.domain.event.EventGeneralType;
import co.com.vision.prueba.domain.event.EventSpecificType;

public class EventParser {

	private static String EVENT = "Event";

	/**
	 * 
	 * @param eventElement
	 * @param id
	 * @param name
	 * @return
	 */
	public static Event parseEvent(Element eventElement, String id, String name) {
		NodeList eventChildElements = eventElement.getChildNodes();
		Stream<Node> eventChildElementsStream = IntStream.range(0,
				eventChildElements.getLength()).mapToObj(
				eventChildElements::item);

		Node eventTag = eventChildElementsStream
				.filter(node -> !node.getNodeName().equals("#text")
						&& node.getNodeName().equals(EVENT)).findFirst().get();

		NodeList eventTagChilds = eventTag.getChildNodes();
		Stream<Node> eventTagChildElementsStream = IntStream.range(0,
				eventTagChilds.getLength()).mapToObj(eventTagChilds::item);

		Node subTypeTag = eventTagChildElementsStream
				.filter(node -> !node.getNodeName().equals("#text")
						&& isAValidEvent(node)).findFirst().get();

		Event newEvent = createEventBasedOnGeneralType(subTypeTag, id, name);

		return newEvent;
	}

	/**
	 * 
	 * @param event
	 * @param id
	 * @param name
	 * @return
	 */
	private static Event createEventBasedOnGeneralType(Node event, String id,
			String name) {
		Optional<Event> newEvent = Optional.empty();
		if (event.getNodeName().equals(
				EventGeneralType.IntermediateEvent.toString())) {
			newEvent = Optional.of(parseIntermmediateEvent(event, id, name,
					EventGeneralType.IntermediateEvent));
		} else if (event.getNodeName().equals(
				EventGeneralType.StartEvent.toString())) {
			Event simpleEvent = new Event(id, name, "",
					EventGeneralType.StartEvent, Optional.empty(),
					Optional.empty());
			newEvent = Optional.of(simpleEvent);
		} else if (event.getNodeName().equals(
				EventGeneralType.EndEvent.toString())) {
			Event simpleEvent = new Event(id, name, "",
					EventGeneralType.EndEvent, Optional.empty(),
					Optional.empty());
			newEvent = Optional.of(simpleEvent);
		}
		return newEvent.get();
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private static boolean isAValidEvent(Node node) {
		return node.getNodeName().equals(EventGeneralType.EndEvent.toString())
				|| node.getNodeName().equals(
						EventGeneralType.StartEvent.toString())
				|| node.getNodeName().equals(
						EventGeneralType.IntermediateEvent.toString());
	}

	/**
	 * 
	 * @param eventElement
	 * @param id
	 * @param name
	 * @param eventType
	 * @return
	 */
	private static Event parseIntermmediateEvent(Node eventNode, String id,
			String name, EventGeneralType eventType) {

		Element eventElement = (Element) eventNode;
		Optional<String> specificType = Optional.of(eventElement
				.getAttribute("Trigger"));
		if (specificType.get().equals(EventSpecificType.Message.toString())) {
			return parseEventCatchThrow(eventElement, id, name, eventType,
					EventSpecificType.Message);
		} else {
			return new Event(id, name, "", eventType, Optional.empty(),
					Optional.empty());
		}
	}

	/**
	 * 
	 * @param eventElement
	 * @param id
	 * @param name
	 * @param eventType
	 * @param eventSpecificType
	 * @return
	 */
	private static Event parseEventCatchThrow(Node eventElement, String id,
			String name, EventGeneralType eventType,
			EventSpecificType eventSpecificType) {
		NodeList eventChildElements = eventElement.getChildNodes();
		Stream<Node> eventChildElementsStream = IntStream.range(0,
				eventChildElements.getLength()).mapToObj(
				eventChildElements::item);
		Element triggerResultMessageNode = (Element) eventChildElementsStream
				.filter(eventChild -> eventChild.getNodeName().equals(
						"TriggerResultMessage")).findFirst().get();

		Optional<String> catchthrow = Optional.of(triggerResultMessageNode
				.getAttribute("CatchThrow"));

		Event event = new Event(id, name, "", eventType,
				Optional.of(eventSpecificType), catchthrow);
		return event;
	}
}