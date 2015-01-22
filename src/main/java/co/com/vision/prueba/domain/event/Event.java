package co.com.vision.prueba.domain.event;

import co.com.vision.prueba.domain.Node;

/**
 * 
 * @author ftrianakast
 * @version 1.0
 */
public class Event extends Node {

	private EventGeneralType generalType;

	private EventSpecificType specificType;

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param generalType
	 * @param specificType
	 */
	public Event(String id, String name, String description,
			EventGeneralType generalType, EventSpecificType specificType) {
		super(id, name, description);
		this.generalType = generalType;
		this.specificType = specificType;
	}

	public EventGeneralType getGeneralType() {
		return generalType;
	}

	public void setGeneralType(EventGeneralType generalType) {
		this.generalType = generalType;
	}

	public EventSpecificType getSpecificType() {
		return specificType;
	}

	public void setSpecificType(EventSpecificType specificType) {
		this.specificType = specificType;
	}
}
