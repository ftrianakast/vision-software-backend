package co.com.vision.prueba.domain;

import java.util.List;
import java.util.Optional;

import co.com.vision.prueba.utils.ListTools;

/**
 * 
 * @author Felipe Triana <ftrianakast@gmail.com>
 * @version 1.0
 */
public class WorkflowProcess {

	private String id;

	private String name;

	private String description;

	private Optional<List<Node>> activities;

	private Optional<List<Node>> events;

	private List<Transition> transitions;

	private String author;

	private String version;

	private String countryKey;

	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param activities
	 * @param events
	 * @param transitions
	 * @param author
	 * @param version
	 * @param countryKey
	 */
	public WorkflowProcess(String id, String name, String description,
			Optional<List<Node>> activities, Optional<List<Node>> events,
			List<Transition> transitions, String author, String version,
			String countryKey) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.activities = activities;
		this.events = events;
		this.transitions = transitions;
		this.author = author;
		this.version = version;
		this.countryKey = countryKey;
	}

	public WorkflowProcess() {
	}

	/**
	 * 
	 * @return
	 */
	public Optional<List<Node>> getNodes() {
		if (this.getActivities().isPresent() && this.getEvents().isPresent()) {
			return Optional.of(ListTools.joinLists(this.getActivities().get(), this
					.getEvents().get()));
		} else {
			return Optional.empty();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Optional<List<Node>> getActivities() {
		return activities;
	}

	public void setActivities(Optional<List<Node>> activities) {
		this.activities = activities;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCountryKey() {
		return countryKey;
	}

	public void setCountryKey(String countryKey) {
		this.countryKey = countryKey;
	}

	public Optional<List<Node>> getEvents() {
		return events;
	}

	public void setEvents(Optional<List<Node>> events) {
		this.events = events;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
