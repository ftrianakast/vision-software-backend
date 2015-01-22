package co.com.vision.prueba.domain.event;

import java.util.Optional;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.NodeType;

/**
 * 
 * @author ftrianakast
 * @version 1.0
 */
public class Event extends Node {

	private EventGeneralType generalType;

	private Optional<EventSpecificType> specificType;

	private Optional<String> catchThrow;

	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param generalType
	 * @param specificType
	 * @param catchThrow
	 */
	public Event(String id, String name, String description,
			EventGeneralType generalType, Optional<EventSpecificType> specificType,
			Optional<String> catchThrow) {
		super(id, name, description, NodeType.EVENT);
		this.generalType = generalType;
		this.specificType = specificType;
		this.catchThrow = catchThrow;
	}

	public EventGeneralType getGeneralType() {
		return generalType;
	}

	public void setGeneralType(EventGeneralType generalType) {
		this.generalType = generalType;
	}

	public Optional<EventSpecificType> getSpecificType() {
		return specificType;
	}

	public void setSpecificType(Optional<EventSpecificType> specificType) {
		this.specificType = specificType;
	}

	public Optional<String> getCatchThrow() {
		return catchThrow;
	}

	public void setCatchThrow(Optional<String> catchThrow) {
		this.catchThrow = catchThrow;
	}
}
