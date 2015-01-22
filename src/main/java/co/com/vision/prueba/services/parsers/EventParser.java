package co.com.vision.prueba.services.parsers;

import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.domain.event.EventGeneralType;
import co.com.vision.prueba.domain.event.EventSpecificType;

public class EventParser {

	/**
	 * 
	 * @param eventElement
	 * @param id
	 * @param name
	 * @return
	 */
	public static Event parseEvent(Element eventElement, String id, String name) {
		NodeList eventChildElements = eventElement.getChildNodes();
		Optional<Event> newEvent = Optional.empty();

		for (int i = 0; i < eventChildElements.getLength(); i++) {
			Node eventGeneralType = eventChildElements.item(i);
			if (eventGeneralType.getNodeValue().equals(
					EventGeneralType.IntermediateEvent)) {
				newEvent = Optional.of(parseIntermmediateEvent(eventElement,
						id, name, EventGeneralType.IntermediateEvent));
			} else if (eventGeneralType.getNodeValue().equals(
					EventGeneralType.StartEvent)) {
				Event simpleEvent = new Event(id, name, "",
						EventGeneralType.StartEvent, Optional.empty(),
						Optional.empty());
				newEvent = Optional.of(simpleEvent);
			} else if (eventGeneralType.getNodeValue().equals(
					EventGeneralType.EndEvent)) {
				eventGeneralType.getNodeValue().equals(
						EventGeneralType.EndEvent);
				Event simpleEvent = new Event(id, name, "",
						EventGeneralType.EndEvent, Optional.empty(),
						Optional.empty());
				newEvent = Optional.of(simpleEvent);
			}
		}
		return newEvent.get();
	}

	/**
	 * 
	 * @param eventElement
	 * @param id
	 * @param name
	 * @param eventType
	 * @return
	 */
	private static Event parseIntermmediateEvent(Element eventElement,
			String id, String name, EventGeneralType eventType) {
		Optional<String> specificType = Optional.of(eventElement
				.getAttribute("Trigger"));

		if (specificType.get().equals(EventSpecificType.Message)) {
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
	private static Event parseEventCatchThrow(Element eventElement, String id,
			String name, EventGeneralType eventType,
			EventSpecificType eventSpecificType) {
		Optional<Node> triggerResultMessage = Optional.of(eventElement
				.getFirstChild());
		Optional<Event> newEvent = Optional.empty();

		if (triggerResultMessage.isPresent()) {
			Element triggerResultMessageElement = (Element) triggerResultMessage
					.get();
			Optional<String> catchthrow = Optional
					.of(triggerResultMessageElement.getAttribute("CatchThrow"));

			Event event = new Event(id, name, "", eventType,
					Optional.of(eventSpecificType), catchthrow);
			newEvent = Optional.of(event);
			return newEvent.get();
		} else {
			return new Event(id, name, "", eventType,
					Optional.of(eventSpecificType), Optional.empty());
		}
	}
}